package com.vpd.courseproject.forum.persistence.dao;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.persistence.dao.api.IUserDao;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.utils.HibernateSessionFactory;
import org.hibernate.Session;

import javax.persistence.PersistenceException;
import java.util.List;

public class UserDao implements IUserDao {

    private static final UserDao instance = new UserDao();

    private UserDao() {
    }

    public static UserDao getInstance() {
        return instance;
    }

    public User getUserByLogin(String login) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.get(User.class, login);
        } catch (PersistenceException e) {
            throw new DaoException("Can't get user by login");
        }
    }

    public User getUserByEmail(String email) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.createQuery("SELECT a FROM Author a WHERE a.email =:email", User.class)
                    .setParameter("email", email).uniqueResult();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get user by email");
        }
    }


    public void addUser(User user) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't add user");
        }
    }

    public List<User> getAllUsers() throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.createQuery("SELECT a FROM Author a", User.class).getResultList();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get all users");
        }
    }

    public void updateUser(User user) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't update user");
        }
    }


}
