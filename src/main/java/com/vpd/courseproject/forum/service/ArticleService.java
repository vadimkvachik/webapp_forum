package com.vpd.courseproject.forum.service;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.dao.ArticleDao;
import com.vpd.courseproject.forum.persistence.dao.api.IArticleDao;
import com.vpd.courseproject.forum.persistence.entity.Article;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.persistence.enrties.ArticleEntry;
import com.vpd.courseproject.forum.service.api.IArticleService;
import com.vpd.courseproject.forum.utils.Formatter;
import com.vpd.courseproject.forum.utils.FromISO88591toUTF8Encoder;
import com.vpd.courseproject.forum.utils.api.IEncoder;
import com.vpd.courseproject.forum.utils.api.IFormatter;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArticleService implements IArticleService {
    private static final ArticleService instance = new ArticleService();
    private static final Logger logger = Logger.getLogger(ArticleService.class);
    private IArticleDao articleDao = ArticleDao.getInstance();
    private IFormatter formatter = Formatter.getInstance();
    private IEncoder encoder = FromISO88591toUTF8Encoder.getInstance();

    private ArticleService() {
    }

    public static ArticleService getInstance() {
        return instance;
    }

    public void addArticle(User user, String topic, String text) throws ServiceException {
        Article article = new Article();
        article.setUser(user);
        article.setTopic(encoder.encode(topic));
        article.setText(encoder.encode(text));
        try {
            articleDao.addArticle(article);
            logger.info("User '" + user.getLogin() + "' added the article '" + topic + "'");
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Article getArticle(String id) throws ServiceException {
        try {
            return articleDao.getArticleById(Long.valueOf(id));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<ArticleEntry> getArticleEntries(String login) throws ServiceException {
        try {
            List<ArticleEntry> articleEntryList = new ArrayList<>();
            List<Article> articles;
            if (login == null) articles = articleDao.getAllArticles();
            else articles = articleDao.getAllArticlesByLogin(login);
            for (Article article : articles) {
                ArticleEntry articleEntry = new ArticleEntry();
                articleEntry.setArticle(article);
                articleEntry.setTextPreview(formatter.formatTextForPreview(article.getText(), 800));
                articleEntryList.add(articleEntry);
            }
            Collections.sort(articleEntryList);
            return articleEntryList;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void deleteArticle(String login, String id) throws ServiceException {
        try {
            Article article = articleDao.getArticleById(Long.valueOf(id));
            articleDao.deleteArticle(article);
            logger.info("User '" + login + "' deleted the article '" + article.getTopic() + "'");
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public long getNumberOfArticlesByLogin(String login) throws ServiceException {
        try {
            return articleDao.getNumberOfArticlesByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
