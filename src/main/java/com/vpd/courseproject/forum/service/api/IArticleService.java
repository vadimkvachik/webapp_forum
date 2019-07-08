package com.vpd.courseproject.forum.service.api;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.enrties.ArticleEntry;
import com.vpd.courseproject.forum.persistence.entity.Article;
import com.vpd.courseproject.forum.persistence.entity.User;

import java.util.List;

public interface IArticleService {

    void addArticle(User user, String topic, String text) throws ServiceException;

    Article getArticle(String id) throws ServiceException;

    List<ArticleEntry> getArticleEntries(String login) throws ServiceException;

    void deleteArticle(String login, String id) throws ServiceException;

    long getNumberOfArticlesByLogin(String login) throws ServiceException;
}
