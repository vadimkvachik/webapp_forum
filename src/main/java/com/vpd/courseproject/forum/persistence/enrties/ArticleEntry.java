package com.vpd.courseproject.forum.persistence.enrties;

import com.vpd.courseproject.forum.persistence.entity.Article;

public class ArticleEntry implements Comparable<ArticleEntry> {
    private Article article;
    private String textPreview;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getTextPreview() {
        return textPreview;
    }

    public void setTextPreview(String textPreview) {
        this.textPreview = textPreview;
    }

    @Override
    public int compareTo(ArticleEntry o) {
        return (int) (o.article.getId() - article.getId());
    }
}
