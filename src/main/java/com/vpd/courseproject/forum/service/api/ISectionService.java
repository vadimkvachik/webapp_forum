package com.vpd.courseproject.forum.service.api;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.enrties.SectionEntry;
import com.vpd.courseproject.forum.persistence.entity.Section;

import java.util.List;

public interface ISectionService {

    String addSectionAndReturnPath(String login, String name, String sectionBlockId) throws ServiceException;

    Section getSection(String id) throws ServiceException;

    List<SectionEntry> getSectionEntries() throws ServiceException;

    String renameSectionAndReturnPath(String login, String id, String newName) throws ServiceException;

    void deleteSection(String login, String id) throws ServiceException;
}
