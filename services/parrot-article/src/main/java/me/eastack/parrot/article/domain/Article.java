package me.eastack.parrot.article.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Article implements Serializable {
    /**
     * 文章 ID
     * 发布文章的并发压力和数量级应该不会太大，
     * 这里直接使用数据库自增主键了。
     */
    private Long id;

    /**
     * 文章作者 ID
     */
    private Integer authorId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     * AsciiDoc 格式纯文本
     */
    private String content;

    /**
     * 文章状态
     */
    private Status status;

    /**
     * 文章发布日期
     */
    private LocalDateTime publishTime;

    /**
     * 文章更新日期
     */
    private LocalDateTime updateTime;

    /**
     * 软删除标志
     */
    private Boolean deleted;

    /**
     * 文章的几种状态
     */
    private enum Status {
        /**
         * 草稿
         */
        DRAFT,
        /**
         * 已发布
         */
        PUBLISHED,
        /**
         * 已删除
         */
        TRASHED,
    }
}
