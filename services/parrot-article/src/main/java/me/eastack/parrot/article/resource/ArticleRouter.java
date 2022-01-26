package me.eastack.parrot.article.resource;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import me.eastack.parrot.article.application.ArticleApplicationService;
import me.eastack.parrot.article.application.ArticleCreateCommand;
import reactor.netty.http.server.HttpServerRoutes;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ArticleRouter {
    private static final String ARTICLES = "/articles";
    private final ArticleApplicationService service;
    private final Gson gson;

    public void accept(HttpServerRoutes routes) {
        routes
            .post(ARTICLES, (req, resp) -> resp.sendString(
                req.receive().asString()
                    .map(body -> gson.fromJson(body, ArticleCreateCommand.class))
                    .map(command -> command.setAuthorId(req.requestHeaders().get("uid")))
                    .flatMap(service::create)
                    .map(gson::toJson)))

            .get(ARTICLES + "/{id}", (req, resp) -> {
                req.param()
                Long id = Optional.ofNullable(req.param("id"))
                    .map(Long::parseLong)
                    .orElseThrow();

                return resp.sendString(
                    service
                        .articleDetail(id)
                        .map(gson::toJson)
                );
            });
    }
}
