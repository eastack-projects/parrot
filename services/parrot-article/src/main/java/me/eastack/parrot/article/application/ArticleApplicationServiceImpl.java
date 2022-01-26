package me.eastack.parrot.article.application;

import lombok.RequiredArgsConstructor;
import me.eastack.parrot.article.domain.Article;
import me.eastack.parrot.article.domain.ArticleException;
import me.eastack.parrot.article.domain.ArticleService;
import me.eastack.parrot.article.infrastructure.ArticleBeanMapper;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ArticleApplicationServiceImpl implements ArticleApplicationService {
    private final ArticleService articleService;

    @Override
    public Mono<ArticleDto> articleDetail(Long id) {
        return articleService
            .detail(id)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new ArticleException())))
            .map(ArticleBeanMapper.INSTANCE::entityToDto);
    }

    @Override
    public Mono<Long> create(ArticleCreateCommand articleCreateCommand) {
        return articleService
            // 新建文章
            .create(ArticleBeanMapper.INSTANCE.commandToEntity(articleCreateCommand))
            .map(Article::getId);
        // 发布消息
        // ...
    }
}
