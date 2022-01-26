package me.eastack.parrot.article.domain;

import reactor.core.publisher.Mono;

public interface ArticleRepository {
    Mono<Article> findById(Long id);

    Mono<Article> save(Article article);
}
