package me.eastack.parrot.article.infrastructure;

import me.eastack.parrot.article.application.ArticleCreateCommand;
import me.eastack.parrot.article.application.ArticleDto;
import me.eastack.parrot.article.domain.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleBeanMapper {
    ArticleBeanMapper INSTANCE = Mappers.getMapper(ArticleBeanMapper.class);

    @Mapping(source = "title", target = "title")
    ArticleDto entityToDto(Article article);

    Article commandToEntity(ArticleCreateCommand articleCreateCommand);
}
