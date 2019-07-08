package com.vpd.courseproject.forum.persistence.dao;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.persistence.dao.api.IMessageDao;
import com.vpd.courseproject.forum.utils.HibernateSessionFactory;
import com.vpd.courseproject.forum.persistence.entity.Message;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.List;

public class MessageDao implements IMessageDao {

    private static final MessageDao instance = new MessageDao();

    private MessageDao() {
    }

    public static MessageDao getInstance() {
        return instance;
    }

    public Message getMessageById(long id) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.get(Message.class, id);
        } catch (PersistenceException e) {
            throw new DaoException("Can't get message by id");
        }
    }

    public void deleteMessage(Message message) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.delete(message);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't delete message");
        }
    }

    public List<Message> getAllMessagesByTopicId(long topicId) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.createQuery("SELECT a FROM Message a where a.topic.id=:topicId", Message.class)
                    .setParameter("topicId", topicId).getResultList();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get all messages by topic id");
        }
    }

    public List<Message> getAllMessagesByLogin(String login) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.createQuery("SELECT a FROM Message a where a.user.login=:login", Message.class)
                    .setParameter("login", login).getResultList();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get all messages by login");
        }
    }


    public void addMessage(Message message) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.save(message);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't add message");
        }
    }

    public long getNumberOfMessagesBySectionId(long sectionId) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            Query query = session.createQuery("SELECT COUNT(*) FROM Message a WHERE a.topic.section.id=:sectionId");
            return (long) query.setParameter("sectionId", sectionId).uniqueResult();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get number of messages by section id");
        }
    }

    public void updateMessage(Message message) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.update(message);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't update message");
        }
    }

    public long getNumberOfMessagesByTopicId(long topicId) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            Query query = session.createQuery("SELECT COUNT(*) FROM Message a WHERE a.topic.id=:topicId");
            return (long) query.setParameter("topicId", topicId).uniqueResult();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get number of messages by topic id");
        }
    }

    public long getNumberOfMessagesByLogin(String login) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            Query query = session.createQuery("SELECT COUNT(*) FROM Message a WHERE a.user.login=:login");
            return (long) query.setParameter("login", login).uniqueResult();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get number of messages by login");
        }
    }

    public Message getLastMessageBySectionId(long sectionId) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            Date dateOfLastMessage = session.createQuery("SELECT MAX(dateOfPublication)FROM Message a " +
                    "WHERE a.topic.section.id=:sectionId", Date.class).setParameter("sectionId", sectionId).uniqueResult();
            return session.createQuery("SELECT a FROM Message a WHERE a.dateOfPublication=:date", Message.class)
                    .setParameter("date", dateOfLastMessage).uniqueResult();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get last message by section id");
        }
    }

    public Message getLastMessageByTopicId(long topicId) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            Date dateOfLastMessage = session.createQuery("SELECT MAX(dateOfPublication)FROM Message a " +
                    "WHERE a.topic.id=:topicId", Date.class).setParameter("topicId", topicId).uniqueResult();
            return session.createQuery("SELECT a FROM Message a WHERE a.dateOfPublication=:date", Message.class)
                    .setParameter("date", dateOfLastMessage).uniqueResult();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get last message by topic id");
        }
    }

}
