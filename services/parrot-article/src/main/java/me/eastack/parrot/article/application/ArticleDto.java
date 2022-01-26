package me.eastack.parrot.article.application;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String authorId;
    private String content;
}
