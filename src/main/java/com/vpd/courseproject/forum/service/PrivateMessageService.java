package com.vpd.courseproject.forum.service;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.dao.PrivateMessageDao;
import com.vpd.courseproject.forum.persistence.dao.UserDao;
import com.vpd.courseproject.forum.persistence.dao.api.IPrivateMessageDao;
import com.vpd.courseproject.forum.persistence.dao.api.IUserDao;
import com.vpd.courseproject.forum.persistence.entity.PrivateMessage;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.IPrivateMessageService;
import com.vpd.courseproject.forum.utils.FromISO88591toUTF8Encoder;
import com.vpd.courseproject.forum.utils.api.IEncoder;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class PrivateMessageService implements IPrivateMessageService {
    private static final PrivateMessageService instance = new PrivateMessageService();
    private static final Logger logger = Logger.getLogger(PrivateMessageService.class);
    private IPrivateMessageDao privateMessageDao = PrivateMessageDao.getInstance();
    private IUserDao userDao = UserDao.getInstance();
    private IEncoder encoder = FromISO88591toUTF8Encoder.getInstance();

    private PrivateMessageService() {
    }

    public static PrivateMessageService getInstance() {
        return instance;
    }

    public void addPrivateMessage(String topic, String text, String senderLogin, String recipientLogin) throws ServiceException {
        PrivateMessage message = new PrivateMessage();
        message.setTopic(encoder.encode(topic));
        message.setText(encoder.encode(text));
        try {
            message.setFrom(userDao.getUserByLogin(senderLogin));
            message.setTo(userDao.getUserByLogin(recipientLogin));
            privateMessageDao.addPrivateMessage(message);
            logger.info("User '" + senderLogin + "' sent private message to user '" + recipientLogin + "'");
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public PrivateMessage getPrivateMessage(User user, String id) throws ServiceException {
        try {
            PrivateMessage message = privateMessageDao.getPrivateMessageById(Long.parseLong(id));
            if (message.getTo().equals(user) || message.getFrom().equals(user)) {
                if (user.equals(message.getTo())) {
                    message.setRead(true);
                    privateMessageDao.updatePrivateMessage(message);
                }
                return message;
            } else {
                throw new NumberFormatException();
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<PrivateMessage> getPrivateMessagesByRecipient(String login) throws ServiceException {
        try {
            List<PrivateMessage> messages = privateMessageDao.getAllPrivateMessagesByRecipientIfNotDeleted(login);
            Collections.sort(messages);
            return messages;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<PrivateMessage> getPrivateMessagesBySender(String login) throws ServiceException {
        try {
            List<PrivateMessage> messages = privateMessageDao.getAllPrivateMessagesBySenderIfNotDeleted(login);
            Collections.sort(messages);
            return messages;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public String deletePrivateMessageAndReturnPath(User user, String id, String activity) throws ServiceException {
        try {
            PrivateMessage message = privateMessageDao.getPrivateMessageById(Long.parseLong(id));
            if (activity.equals("sender")) {
                if (!message.isRead() || message.isDeletedTo()) {
                    privateMessageDao.deletePrivateMessage(message);
                    logger.info("User '" + user.getLogin() + "' deleted private message '" + message.getTopic() + "' from DB");
                } else {
                    message.setDeletedFrom(true);
                    privateMessageDao.updatePrivateMessage(message);
                    logger.info("User '" + user.getLogin() + "' deleted private message '" + message.getTopic() + "' for yourself");
                }
                return "/private_out";
            } else if (activity.equals("recipient")) {
                if (message.isDeletedFrom()) {
                    privateMessageDao.deletePrivateMessage(message);
                    logger.info("User '" + user.getLogin() + "' deleted private message '" + message.getTopic() + "' from DB");
                } else {
                    message.setDeletedTo(true);
                    privateMessageDao.updatePrivateMessage(message);
                    logger.info("User '" + user.getLogin() + "' deleted private message '" + message.getTopic() + "' for yourself");
                }
                return "/private_in";
            }
            return "/";
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
