package me.eastack.parrot.article.application;

import reactor.core.publisher.Mono;

public interface ArticleApplicationService {
    Mono<ArticleDto> articleDetail(Long id);

    Mono<Long> create(ArticleCreateCommand article);
}
