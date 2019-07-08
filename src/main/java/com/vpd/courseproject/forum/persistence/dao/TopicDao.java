package com.vpd.courseproject.forum.persistence.dao;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.persistence.dao.api.ITopicDao;
import com.vpd.courseproject.forum.persistence.entity.Topic;
import com.vpd.courseproject.forum.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.util.List;

public class TopicDao implements ITopicDao {

    private static final TopicDao instance = new TopicDao();

    private TopicDao() {
    }

    public static TopicDao getInstance() {
        return instance;
    }

    public Topic getTopicById(long id) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.get(Topic.class, id);
        } catch (PersistenceException e) {
            throw new DaoException("Can't get topic by id");
        }
    }

    public List<Topic> getAllTopicsBySectionId(long sectionId) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.createQuery("SELECT a FROM Topic a WHERE a.section.id=:sectionId", Topic.class)
                    .setParameter("sectionId", sectionId).getResultList();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get all topics by section id");
        }
    }

    public void addTopic(Topic topic) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.save(topic);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't add topic");
        }
    }

    public long getNumberOfTopicsBySectionId(long sectionId) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            Query query = session.createQuery("SELECT COUNT(*) FROM Topic a WHERE a.section.id=:sectionId");
            return (long) query.setParameter("sectionId", sectionId).uniqueResult();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get number of topics by section id");
        }
    }

    public void deleteTopic(Topic topic) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.delete(topic);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't delete topic");
        }
    }

    public void updateTopic(Topic topic) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.update(topic);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't update topic");
        }
    }

}
