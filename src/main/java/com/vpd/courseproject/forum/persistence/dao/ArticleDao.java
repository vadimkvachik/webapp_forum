package com.vpd.courseproject.forum.persistence.dao;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.persistence.dao.api.IArticleDao;
import com.vpd.courseproject.forum.persistence.entity.Article;
import com.vpd.courseproject.forum.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.util.List;

public class ArticleDao implements IArticleDao {

    private static final ArticleDao instance = new ArticleDao();

    private ArticleDao() {
    }

    public static ArticleDao getInstance() {
        return instance;
    }

    public void addArticle(Article article) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.save(article);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't add article");
        }
    }

    public void deleteArticle(Article article) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.delete(article);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't delete article");
        }
    }

    public Article getArticleById(long id) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.get(Article.class, id);
        } catch (PersistenceException e) {
            throw new DaoException("Can't get article by id");
        }
    }

    public List<Article> getAllArticles() throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.createQuery("SELECT a FROM Article a", Article.class).getResultList();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get all articles");
        }
    }

    public List<Article> getAllArticlesByLogin(String login) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.createQuery("SELECT a FROM Article a where a.user.login=:login", Article.class)
                    .setParameter("login", login).getResultList();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get all articles by login");
        }
    }

    public long getNumberOfArticlesByLogin(String login) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            Query query = session.createQuery("SELECT COUNT(*) FROM Article a WHERE a.user.login=:login");
            return (long) query.setParameter("login", login).uniqueResult();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get number of articles by login");
        }
    }


}
