package com.vpd.courseproject.forum.service;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.dao.MessageDao;
import com.vpd.courseproject.forum.persistence.dao.SectionBlockDao;
import com.vpd.courseproject.forum.persistence.dao.SectionDao;
import com.vpd.courseproject.forum.persistence.dao.TopicDao;
import com.vpd.courseproject.forum.persistence.dao.api.IMessageDao;
import com.vpd.courseproject.forum.persistence.dao.api.ISectionBlockDao;
import com.vpd.courseproject.forum.persistence.dao.api.ISectionDao;
import com.vpd.courseproject.forum.persistence.dao.api.ITopicDao;
import com.vpd.courseproject.forum.persistence.enrties.SectionEntry;
import com.vpd.courseproject.forum.persistence.entity.Message;
import com.vpd.courseproject.forum.persistence.entity.Section;
import com.vpd.courseproject.forum.persistence.entity.SectionBlock;
import com.vpd.courseproject.forum.service.api.ISectionService;
import com.vpd.courseproject.forum.utils.Formatter;
import com.vpd.courseproject.forum.utils.FromISO88591toUTF8Encoder;
import com.vpd.courseproject.forum.utils.api.IEncoder;
import com.vpd.courseproject.forum.utils.api.IFormatter;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SectionService implements ISectionService {
    private static final SectionService instance = new SectionService();
    private static final Logger logger = Logger.getLogger(SectionService.class);
    private ISectionBlockDao sectionBlockDao = SectionBlockDao.getInstance();
    private ISectionDao sectionDao = SectionDao.getInstance();
    private ITopicDao topicDao = TopicDao.getInstance();
    private IMessageDao messageDao = MessageDao.getInstance();
    private IFormatter formatter = Formatter.getInstance();
    private IEncoder encoder = FromISO88591toUTF8Encoder.getInstance();

    private SectionService() {
    }

    public static SectionService getInstance() {
        return instance;
    }

    public String addSectionAndReturnPath(String login, String name, String sectionBlockId) throws ServiceException {
        try {
            SectionBlock sectionBlock = sectionBlockDao.getSectionBlockById(Long.valueOf(sectionBlockId));
            Section section = new Section();
            section.setName(encoder.encode(name));
            section.setSectionBlock(sectionBlock);
            sectionDao.addSection(section);
            logger.info("User '" + login + "' added the section '" + section.getName()
                    + "' in the section block '" + sectionBlock.getName() + "'");
            return "/#section" + section.getId();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Section getSection(String id) throws ServiceException {
        try {
            return sectionDao.getSectionById(Long.valueOf(id));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<SectionEntry> getSectionEntries() throws ServiceException {
        try {
            List<SectionEntry> sectionEntryList = new ArrayList<>();
            for (Section section : sectionDao.getAllSections()) {
                SectionEntry sectionEntry = new SectionEntry();
                sectionEntry.setSection(section);
                sectionEntry.setNumberOfTopics(topicDao.getNumberOfTopicsBySectionId(section.getId()));
                sectionEntry.setNumberOfMessages(messageDao.getNumberOfMessagesBySectionId(section.getId()));
                Message lastMessage = messageDao.getLastMessageBySectionId(section.getId());
                if (lastMessage != null) {
                    sectionEntry.setLastMessage(lastMessage);
                    sectionEntry.setLastMessageTopicNamePreview(formatter.formatTextForPreview(lastMessage.getTopic().getName(), 20));
                }
                sectionEntryList.add(sectionEntry);
            }
            Collections.sort(sectionEntryList);
            return sectionEntryList;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public String renameSectionAndReturnPath(String login, String id, String newName) throws ServiceException {
        try {
            Section section = sectionDao.getSectionById(Long.valueOf(id));
            String oldName = section.getName();
            section.setName(encoder.encode(newName));
            sectionDao.updateSection(section);
            logger.info("User '" + login + "' renamed the section '" + oldName + "' to '" + section.getName() + "'");
            return "/#section" + section.getId();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void deleteSection(String login, String id) throws ServiceException {
        try {
            Section section = sectionDao.getSectionById(Long.valueOf(id));
            sectionDao.deleteSection(section);
            logger.info("User '" + login + "' deleted the section '" + section.getName()
                    + "' in the section block '" + section.getSectionBlock().getName() + "'");
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
