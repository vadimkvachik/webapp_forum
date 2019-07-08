package com.vpd.courseproject.forum.persistence.dao.api;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.persistence.entity.PrivateMessage;

import java.util.List;

public interface IPrivateMessageDao {

    void addPrivateMessage(PrivateMessage privateMessage) throws DaoException;

    PrivateMessage getPrivateMessageById(long id) throws DaoException;

    List<PrivateMessage> getAllPrivateMessagesByRecipientIfNotDeleted(String login) throws DaoException;

    List<PrivateMessage> getAllPrivateMessagesBySenderIfNotDeleted(String login) throws DaoException;

    long getNumberOfUnreadMessagesByLoginTo(String login) throws DaoException;

    void updatePrivateMessage(PrivateMessage privateMessage) throws DaoException;

    void deletePrivateMessage(PrivateMessage privateMessage) throws DaoException;

}
