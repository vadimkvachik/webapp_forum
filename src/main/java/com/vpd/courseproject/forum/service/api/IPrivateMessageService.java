package com.vpd.courseproject.forum.service.api;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.PrivateMessage;
import com.vpd.courseproject.forum.persistence.entity.User;

import java.util.List;

public interface IPrivateMessageService {

    void addPrivateMessage(String topic, String text, String senderLogin, String recipientLogin) throws ServiceException;

    PrivateMessage getPrivateMessage(User user, String id) throws ServiceException;

    List<PrivateMessage> getPrivateMessagesByRecipient(String login) throws ServiceException;

    List<PrivateMessage> getPrivateMessagesBySender(String login) throws ServiceException;

    String deletePrivateMessageAndReturnPath(User user, String id, String activity) throws ServiceException;
}
