package me.eastack.parrot.article.application;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ArticleCreateCommand {
    private Long id;
    private String title;
    private String authorId;
    private String content;

    public void setTitle(String title) {
        if (title.length() < 10) {
            throw new RuntimeException("title too small");
        }
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
