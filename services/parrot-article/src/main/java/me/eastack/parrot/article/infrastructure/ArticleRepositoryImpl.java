package me.eastack.parrot.article.infrastructure;

import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import me.eastack.parrot.article.domain.Article;
import me.eastack.parrot.article.domain.ArticleRepository;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ArticleRepositoryImpl implements ArticleRepository {
    private final ConnectionFactory connectionFactory;

    @Override
    public Mono<Article> findById(Long id) {
        return Mono.from(connectionFactory.create())
            .flatMapMany(connection -> connection
                .createStatement("SELECT title FROM article WHERE id = $1")
                .bind("$1", id)
                .execute())
            .flatMap(result -> result
                .map((row, rowMetadata) -> row.get("title", String.class)))
            .map(title -> {
                Article article = new Article();
                article.setTitle(title);
                return article;
            }).next();

//        if (id == 10) {
//            return Mono.empty();
//        } else {
//            Article article = new Article();
//            article.setTitle("Java Reactor");
//            return Mono.just(article);
//        }
    }

    @Override
    public Mono<Article> save(Article article) {
        article.setId(10086L);
        return Mono.just(article);
    }
}
