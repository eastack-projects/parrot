package me.eastack.parrot.article;

import com.google.gson.Gson;
import dagger.Binds;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.eastack.parrot.article.application.ArticleApplicationService;
import me.eastack.parrot.article.application.ArticleApplicationServiceImpl;
import me.eastack.parrot.article.domain.ArticleRepository;
import me.eastack.parrot.article.domain.ArticleService;
import me.eastack.parrot.article.domain.ArticleServiceImpl;
import me.eastack.parrot.article.infrastructure.ArticleRepositoryImpl;
import me.eastack.parrot.article.resource.ArticleRouter;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import javax.inject.Inject;
import javax.inject.Singleton;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ArticleApplication {
    private final ArticleRouter articleRouter;
    private final Config config;

    public static void main(String[] args) {
        DaggerMainModule.create()
            .articleApplication()
            .start();
    }

    public void start() {
        log.info("Server listing on port: {}", config.getServer().getPort());
        HttpServer.create()
            .host(config.getServer().getHost())
            .port(config.getServer().getPort())
            .handle((req, resp) -> {
                log.info("request url: {}", req.hostAddress());
                return Mono.empty();
            })
            .route(articleRouter::accept)
            .bindNow()
            .onDispose()
            .block();
    }
}

@Module
class ArticleModule {
    @Singleton
    @Provides
    ArticleApplicationService articleApplicationService(ArticleService articleService) {
        return new ArticleApplicationServiceImpl(articleService);
    }

    @Singleton
    @Provides
    ArticleService articleService(ArticleRepository articleRepository) {
        return new ArticleServiceImpl(articleRepository);
    }

    @Singleton
    @Provides
    ArticleRepository articleRepository(ConnectionFactory connectionFactory) {
        return new ArticleRepositoryImpl(connectionFactory);
    }

    @Provides
    ArticleApplication articleApplication(ArticleRouter router, Config config) {
        return new ArticleApplication(router, config);
    }

    @Provides
    Gson gson() {
        return new Gson();
    }

    @Provides
    Config config(Gson gson) {
        return new Config(gson)
            .load(System.getProperty("env", "development"));
    }

    @Singleton
    @Provides
    static ArticleRouter articleRouter(ArticleApplicationService articleService, Gson gson) {
        return new ArticleRouter(articleService, gson);
    }

    @Singleton
    @Provides
    static ConnectionFactory r2dbcPool(Config config) {
        Config.R2dbc r2dbc = config.getR2dbc();
        return ConnectionFactories.get(String
            .format("r2dbc:pool:postgresql://%s:%s@%s/parrot",
                r2dbc.getUsername(),
                r2dbc.getPassword(),
                r2dbc.getHost()));
    }
}

@Singleton
@Component(modules = ArticleModule.class)
interface MainModule {
    ArticleService articleService();

    ArticleRepository articleRepository();

    ArticleApplication articleApplication();
}
