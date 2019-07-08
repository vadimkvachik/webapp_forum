package com.vpd.courseproject.forum.service;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.dao.UserDao;
import com.vpd.courseproject.forum.persistence.dao.api.IUserDao;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.IUserService;
import com.vpd.courseproject.forum.utils.FromISO88591toUTF8Encoder;
import com.vpd.courseproject.forum.utils.api.IEncoder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class UserService implements IUserService {
    private static final UserService instance = new UserService();
    private static final Logger logger = Logger.getLogger(UserService.class);
    private Properties properties = new Properties();
    private IUserDao userDao = UserDao.getInstance();
    private IEncoder encoder = FromISO88591toUTF8Encoder.getInstance();

    private UserService() {
    }

    public static UserService getInstance() {
        return instance;
    }

    public User createUser(String login, String pass, String name, String email, String phone, String description)throws ServiceException  {
        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(encoder.encode(pass));
        newUser.setName(encoder.encode(name));
        newUser.setEmail(encoder.encode(email));
        newUser.setDescription(encoder.encode(description));
        newUser.setPhone(phone);
        newUser.setRole(User.Role.USER);
        try {
            userDao.addUser(newUser);
            logger.info("Registered a new user - " + login);
            return newUser;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }


    public void changeRole(String login, String role) throws ServiceException {
        try {
            User user = userDao.getUserByLogin(login);
            user.setRole(User.Role.valueOf(role));
            userDao.updateUser(user);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void deleteUser(String login) throws ServiceException {
        try {
            User user = userDao.getUserByLogin(login);
            user.setDeleted(true);
            userDao.updateUser(user);
            logger.info("User '" + login + "' deleted");
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void lockOrUnlockUser(String loginBlocker, String login, String reasonForBlocking) throws ServiceException {
        try {
            User user = userDao.getUserByLogin(login);
            if (reasonForBlocking != null) {
                user.setReasonForBlocking(encoder.encode(reasonForBlocking));
                logger.info("User '" + loginBlocker + "' banned user '" + user.getLogin()
                        + "' for a reason '" + user.getReasonForBlocking() + "'");
            } else {
                user.setReasonForBlocking(null);
                logger.info("User '" + loginBlocker + "' unbanned user '" + user.getLogin() + "'");
            }
            userDao.updateUser(user);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public User getUserByLogin(String login) throws ServiceException {
        try {
            return userDao.getUserByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public User getUserByEmail(String email) throws ServiceException {
        try {
            return userDao.getUserByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<User> getAllUsers() throws ServiceException {
        try {
            List<User> users = userDao.getAllUsers();
            Collections.sort(users);
            return users;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void createAdministrator() throws ServiceException {
        try {
            properties.load(Objects.requireNonNull(UserService.class.getClassLoader().getResourceAsStream("forum.properties")));
            if (userDao.getUserByLogin(properties.getProperty("login")) == null) {
                User user = new User();
                user.setLogin(properties.getProperty("login"));
                user.setPassword(properties.getProperty("password"));
                user.setEmail(properties.getProperty("email"));
                user.setName(properties.getProperty("name"));
                user.setPhone(properties.getProperty("phone"));
                user.setDescription(properties.getProperty("description"));
                user.setRole(User.Role.ADMIN);
                userDao.addUser(user);
                logger.info("User '" + user.getLogin() + "' autogenerated");
            } else {
                throw new ServiceException("Administrator has already been initialized");
            }
        } catch (IOException | DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean changePassword(String login, String oldPassword, String newPassword) throws ServiceException {
        try {
            User user = userDao.getUserByLogin(login);
            if (user.getPassword().equals(encoder.encode(oldPassword))) {
                user.setPassword(encoder.encode(newPassword));
                userDao.updateUser(user);
                logger.info("User '" + user.getLogin() + "' changed his password");
                return true;
            } else {
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void editProfile(String login, String newName, String newPhone, String newDescription) throws ServiceException {
        try {
            User user = userDao.getUserByLogin(login);
            user.setName(encoder.encode(newName));
            user.setPhone(newPhone);
            user.setDescription(encoder.encode(newDescription));
            userDao.updateUser(user);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public User restoreUser(String login) throws ServiceException {
        try {
            User user = userDao.getUserByLogin(login);
            user.setDeleted(false);
            userDao.updateUser(user);
            logger.info("User '" + login + "' is restored");
            return user;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
