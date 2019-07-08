package com.vpd.courseproject.forum.service.api;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.Message;
import com.vpd.courseproject.forum.persistence.entity.User;

import java.util.List;

public interface IMessageService {

    String addMessageAndReturnPath(User user, String topicId, String text) throws ServiceException;

    List<Message> getMessagesByLogin(String login) throws ServiceException;

    List<Message> getMessagesByTopicId(String topicId) throws ServiceException;

    String changeMessageAndReturnPath(String login, String messageId, String text, String page) throws ServiceException;

    String deleteMessageAndReturnPath(String login, String messageId, String page, String numberOfMessages) throws ServiceException;

    long getNumberOfMessagesByLogin(String login) throws ServiceException;
}
