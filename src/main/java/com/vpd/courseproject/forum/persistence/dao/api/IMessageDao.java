package com.vpd.courseproject.forum.persistence.dao.api;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.persistence.entity.Message;

import java.util.List;

public interface IMessageDao {

    void addMessage(Message message) throws DaoException;

    Message getMessageById(long id) throws DaoException;

    List<Message> getAllMessagesByLogin(String login) throws DaoException;

    List<Message> getAllMessagesByTopicId(long topicId) throws DaoException;

    long getNumberOfMessagesByLogin(String login) throws DaoException;

    long getNumberOfMessagesBySectionId(long sectionId) throws DaoException;

    long getNumberOfMessagesByTopicId(long topicId) throws DaoException;

    Message getLastMessageBySectionId(long sectionId) throws DaoException;

    Message getLastMessageByTopicId(long topicId) throws DaoException;

    void updateMessage(Message message) throws DaoException;

    void deleteMessage(Message message) throws DaoException;
}
