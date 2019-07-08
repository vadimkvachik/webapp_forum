package com.vpd.courseproject.forum.persistence.dao;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.persistence.dao.api.IPrivateMessageDao;
import com.vpd.courseproject.forum.persistence.entity.PrivateMessage;
import com.vpd.courseproject.forum.utils.HibernateSessionFactory;
import org.hibernate.query.Query;
import org.hibernate.Session;

import javax.persistence.PersistenceException;
import java.util.List;

public class PrivateMessageDao implements IPrivateMessageDao {

    private static final PrivateMessageDao instance = new PrivateMessageDao();

    private PrivateMessageDao() {
    }

    public static PrivateMessageDao getInstance() {
        return instance;
    }

    public List<PrivateMessage> getAllPrivateMessagesByRecipientIfNotDeleted(String login) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.createQuery("SELECT a FROM Private_message a where a.to.login=:login and a.deletedTo=false", PrivateMessage.class)
                    .setParameter("login", login).getResultList();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get all private messages by login to");
        }
    }

    public List<PrivateMessage> getAllPrivateMessagesBySenderIfNotDeleted(String login) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.createQuery("SELECT a FROM Private_message a where a.from.login=:login and a.deletedFrom=false", PrivateMessage.class)
                    .setParameter("login", login).getResultList();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get all private messages by login from");
        }
    }

    public PrivateMessage getPrivateMessageById(long id) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.get(PrivateMessage.class, id);
        } catch (PersistenceException e) {
            throw new DaoException("Can't get private message by id");
        }
    }

    public void addPrivateMessage(PrivateMessage privateMessage) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.save(privateMessage);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't add private message");
        }
    }

    public void updatePrivateMessage(PrivateMessage privateMessage) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.update(privateMessage);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't update private message");
        }
    }

    public long getNumberOfUnreadMessagesByLoginTo(String login) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            Query query = session.createQuery("SELECT COUNT(*) FROM Private_message a " +
                    "WHERE a.to.login=:login AND a.read=false AND a.deletedTo=false ");
            return (long) query.setParameter("login", login).uniqueResult();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get number of private messages by login");
        }
    }

    public void deletePrivateMessage(PrivateMessage privateMessage) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.delete(privateMessage);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't delete private message");
        }
    }
}
