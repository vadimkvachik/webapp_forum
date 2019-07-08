package com.vpd.courseproject.forum.persistence.dao.api;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.persistence.entity.SectionBlock;

import java.util.List;

public interface ISectionBlockDao {

    void addSectionBlock(SectionBlock sectionBlock) throws DaoException;

    SectionBlock getSectionBlockById(long id) throws DaoException;

    List<SectionBlock> getAllSectionBlocks() throws DaoException;

    void updateSectionBlock(SectionBlock sectionBlock) throws DaoException;

    void deleteSectionBlock(SectionBlock sectionBlock) throws DaoException;
}
