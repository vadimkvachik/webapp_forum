package com.vpd.courseproject.forum.service;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.dao.MessageDao;
import com.vpd.courseproject.forum.persistence.dao.SectionDao;
import com.vpd.courseproject.forum.persistence.dao.TopicDao;
import com.vpd.courseproject.forum.persistence.dao.api.IMessageDao;
import com.vpd.courseproject.forum.persistence.dao.api.ISectionDao;
import com.vpd.courseproject.forum.persistence.dao.api.ITopicDao;
import com.vpd.courseproject.forum.persistence.enrties.TopicEntry;
import com.vpd.courseproject.forum.persistence.entity.Message;
import com.vpd.courseproject.forum.persistence.entity.Section;
import com.vpd.courseproject.forum.persistence.entity.Topic;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.ITopicService;
import com.vpd.courseproject.forum.utils.Formatter;
import com.vpd.courseproject.forum.utils.FromISO88591toUTF8Encoder;
import com.vpd.courseproject.forum.utils.api.IEncoder;
import com.vpd.courseproject.forum.utils.api.IFormatter;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopicService implements ITopicService {
    private static final TopicService instance = new TopicService();
    private static final Logger logger = Logger.getLogger(TopicService.class);
    private IFormatter formatter = Formatter.getInstance();
    private IMessageDao messageDao = MessageDao.getInstance();
    private ITopicDao topicDao = TopicDao.getInstance();
    private ISectionDao sectionDao = SectionDao.getInstance();
    private IEncoder encoder = FromISO88591toUTF8Encoder.getInstance();

    private TopicService() {
    }

    public static TopicService getInstance() {
        return instance;
    }

    public String addTopicAndReturnPath(User user, String sectionId, String name, String firstMessage) throws ServiceException {
        try {
            Section section = sectionDao.getSectionById(Long.valueOf(sectionId));
            Topic topic = new Topic();
            topic.setName(encoder.encode(name));
            topic.setSection(section);
            topicDao.addTopic(topic);
            messageDao.addMessage(new Message(user, topic, encoder.encode(firstMessage)));
            logger.info("User '" + user.getLogin() + "' added the topic '" + topic.getName()
                    + "' in the section '" + topic.getSection().getName() + "'");
            return "/topic?id=" + topic.getId();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Topic getTopicById(String id) throws ServiceException {
        try {
            return topicDao.getTopicById(Long.valueOf(id));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<TopicEntry> getTopicEntries(long sectionId) throws ServiceException {
        try {
            List<TopicEntry> topicEntryList = new ArrayList<>();
            for (Topic topic : topicDao.getAllTopicsBySectionId(sectionId)) {
                TopicEntry topicEntry = new TopicEntry();
                topicEntry.setTopic(topic);
                topicEntry.setNumberOfMessages(messageDao.getNumberOfMessagesByTopicId(topic.getId()));
                Message lastMessage = messageDao.getLastMessageByTopicId(topic.getId());
                if (lastMessage != null) {
                    topicEntry.setLastMessage(lastMessage);
                    topicEntry.setLastMessagePreview(formatter.formatTextForPreview(lastMessage.getText(), 20));
                }
                topicEntryList.add(topicEntry);
            }
            Collections.sort(topicEntryList);
            return topicEntryList;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public String renameTopicAndReturnPath(String login, String id, String newName) throws ServiceException {
        try {
            Topic topic = topicDao.getTopicById(Long.valueOf(id));
            String oldName = topic.getName();
            topic.setName(encoder.encode(newName));
            topicDao.updateTopic(topic);
            logger.info("User '" + login + "' renamed the topic '" + oldName + "' to '" + topic.getName() + "'");
            return "/section?id=" + topic.getSection().getId() + "#topic" + topic.getId();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public String deleteTopicAndReturnPath(String login, String id) throws ServiceException {
        try {
            Topic topic = topicDao.getTopicById(Long.valueOf(id));
            topicDao.deleteTopic(topic);
            logger.info("User '" + login + "' deleted the topic '" + topic.getName() + "' in the section '" + topic.getSection().getName() + "'");
            return "/section?id=" + topic.getSection().getId();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
