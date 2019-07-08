package com.vpd.courseproject.forum.persistence.dao;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.persistence.dao.api.ISectionDao;
import com.vpd.courseproject.forum.persistence.entity.Section;
import com.vpd.courseproject.forum.utils.HibernateSessionFactory;
import org.hibernate.Session;

import javax.persistence.PersistenceException;
import java.util.List;

public class SectionDao implements ISectionDao {

    private static final SectionDao instance = new SectionDao();

    private SectionDao() {
    }

    public static SectionDao getInstance() {
        return instance;
    }

    public List<Section> getAllSections() throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.createQuery("SELECT a FROM Section a", Section.class).getResultList();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get all sections");
        }
    }

    public Section getSectionById(long id) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.get(Section.class, id);
        } catch (PersistenceException e) {
            throw new DaoException("Can't get section by id");
        }
    }

    public void addSection(Section section) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.save(section);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't add section");
        }
    }

    public void deleteSection(Section section) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.delete(section);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't delete section");
        }
    }

    public void updateSection(Section section) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.update(section);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't update section");
        }
    }

}
