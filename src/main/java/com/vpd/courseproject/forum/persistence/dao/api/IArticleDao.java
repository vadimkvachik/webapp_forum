package com.vpd.courseproject.forum.persistence.dao.api;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.persistence.entity.Article;

import java.util.List;

public interface IArticleDao {

    void addArticle(Article article) throws DaoException;

    Article getArticleById(long id) throws DaoException;

    List<Article> getAllArticles() throws DaoException;

    List<Article> getAllArticlesByLogin(String login) throws DaoException;

    long getNumberOfArticlesByLogin(String login) throws DaoException;

    void deleteArticle(Article article) throws DaoException;
}
