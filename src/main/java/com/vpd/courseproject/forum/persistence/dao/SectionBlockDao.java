package com.vpd.courseproject.forum.persistence.dao;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.persistence.dao.api.ISectionBlockDao;
import com.vpd.courseproject.forum.persistence.entity.SectionBlock;
import com.vpd.courseproject.forum.utils.HibernateSessionFactory;
import org.hibernate.Session;

import javax.persistence.PersistenceException;
import java.util.List;

public class SectionBlockDao implements ISectionBlockDao {

    private static final SectionBlockDao instance = new SectionBlockDao();

    private SectionBlockDao() {
    }

    public static SectionBlockDao getInstance() {
        return instance;
    }

    public void addSectionBlock(SectionBlock sectionBlock) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.save(sectionBlock);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't add section block");
        }
    }

    public List<SectionBlock> getAllSectionBlocks() throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.createQuery("SELECT a FROM Section_block a", SectionBlock.class).getResultList();
        } catch (PersistenceException e) {
            throw new DaoException("Can't get all section blocks");
        }
    }

    public SectionBlock getSectionBlockById(long id) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.get(SectionBlock.class, id);
        } catch (PersistenceException e) {
            throw new DaoException("Can't get section block by id");
        }
    }

    public void deleteSectionBlock(SectionBlock sectionBlock) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.delete(sectionBlock);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't delete section block");
        }
    }

    public void updateSectionBlock(SectionBlock sectionBlock) throws DaoException {
        try (Session session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.update(sectionBlock);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DaoException("Can't update section block");
        }
    }
}
