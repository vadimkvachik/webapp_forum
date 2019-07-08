package com.vpd.courseproject.forum.service.api;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.SectionBlock;

import java.util.List;

public interface ISectionBlockService {

    String addSectionBlockAndReturnPath(String login, String name) throws ServiceException;

    List<SectionBlock> getAllSectionBlocks() throws ServiceException;

    String renameSectionBlockAndReturnPath(String login, String id, String newName) throws ServiceException;

    void deleteSectionBlock(String login, String id) throws ServiceException;
}
