package me.eastack.parrot.article.resource;

import lombok.RequiredArgsConstructor;
import me.eastack.parrot.article.application.ArticleApplicationService;
import me.eastack.parrot.article.application.ArticleCreateCommand;
import me.eastack.parrot.article.application.ArticleDto;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ArticleHandler {
    private final ArticleApplicationService articleService;

    public Mono<Long> create(ArticleCreateCommand command) {
        return articleService.create(command);
    }

    public Mono<ArticleDto> detail(Long id) {
        return articleService.articleDetail(id);
    }
}
