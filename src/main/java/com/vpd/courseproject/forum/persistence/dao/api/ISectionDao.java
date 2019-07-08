package com.vpd.courseproject.forum.persistence.dao.api;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.persistence.entity.Section;

import java.util.List;

public interface ISectionDao {

    void addSection(Section section) throws DaoException;

    Section getSectionById(long id) throws DaoException;

    List<Section> getAllSections() throws DaoException;

    void updateSection(Section section) throws DaoException;

    void deleteSection(Section section) throws DaoException;
}