package me.eastack.parrot.article.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.inject.Singleton;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    @Override
    public Mono<Article> detail(Long id) {
        log.info("query article detail: {}", id);
        return articleRepository.findById(id);
    }

    @Override
    public Mono<Article> create(Article article) {
        return articleRepository.save(article);
    }
}
