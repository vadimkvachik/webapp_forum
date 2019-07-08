package com.vpd.courseproject.forum.service;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.dao.MessageDao;
import com.vpd.courseproject.forum.persistence.dao.TopicDao;
import com.vpd.courseproject.forum.persistence.dao.api.IMessageDao;
import com.vpd.courseproject.forum.persistence.dao.api.ITopicDao;
import com.vpd.courseproject.forum.persistence.entity.Message;
import com.vpd.courseproject.forum.persistence.entity.Topic;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.IMessageService;
import com.vpd.courseproject.forum.utils.FromISO88591toUTF8Encoder;
import com.vpd.courseproject.forum.utils.api.IEncoder;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class MessageService implements IMessageService {
    private static final MessageService instance = new MessageService();
    private static final Logger logger = Logger.getLogger(MessageService.class);
    private ITopicDao topicDao = TopicDao.getInstance();
    private IMessageDao messageDao = MessageDao.getInstance();
    private IEncoder encoder = FromISO88591toUTF8Encoder.getInstance();

    private MessageService() {
    }

    public static MessageService getInstance() {
        return instance;
    }

    public String addMessageAndReturnPath(User user, String topicId, String text) throws ServiceException {
        try {
            Topic topic = topicDao.getTopicById(Long.valueOf(topicId));
            String page = messageDao.getNumberOfMessagesByTopicId(topic.getId()) / 10 + 1 + "";
            Message message = new Message();
            message.setUser(user);
            message.setTopic(topic);
            message.setText(encoder.encode(text));
            messageDao.addMessage(message);
            logger.info("User '" + user.getLogin() + "' wrote a message in the topic '" + topic.getName() + "'");
            return "/topic?id=" + topicId + "&page=" + page + "#id" + message.getId();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public String changeMessageAndReturnPath(String login, String messageId, String text, String page) throws ServiceException {
        try {
            Message message = messageDao.getMessageById(Long.valueOf(messageId));
            message.setText(encoder.encode(text));
            messageDao.updateMessage(message);
            logger.info("User '" + login + "' changed a message in the topic " + message.getTopic().getName());
            return "/topic?id=" + message.getTopic().getId() + "&page=" + page + "#id" + message.getId();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public String deleteMessageAndReturnPath(String login, String messageId, String page, String numberOfMessages) throws ServiceException {
        if (Integer.valueOf(numberOfMessages) == 1) {
            page = (Integer.valueOf(page) - 1) + "";
        }
        try {
            Message message = messageDao.getMessageById(Long.valueOf(messageId));
            messageDao.deleteMessage(message);
            logger.info("User '" + login + "' deleted a message in the topic " + message.getTopic().getName());
            return "/topic?id=" + message.getTopic().getId() + "&page=" + page;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Message> getMessagesByLogin(String login) throws ServiceException {
        try {
            return messageDao.getAllMessagesByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Message> getMessagesByTopicId(String topicId) throws ServiceException {
        try {
            List<Message> messages = messageDao.getAllMessagesByTopicId(Long.valueOf(topicId));
            Collections.sort(messages);
            return messages;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public long getNumberOfMessagesByLogin(String login) throws ServiceException {
        try {
            return messageDao.getNumberOfMessagesByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
