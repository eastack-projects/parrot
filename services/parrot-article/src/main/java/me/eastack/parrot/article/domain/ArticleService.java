package me.eastack.parrot.article.domain;

import reactor.core.publisher.Mono;

public interface ArticleService {
    Mono<Article> detail(Long id);

    Mono<Article> create(Article article);
}
