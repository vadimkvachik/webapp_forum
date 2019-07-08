package com.vpd.courseproject.forum.service.api;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.User;

import java.util.List;

public interface IUserService {

    User createUser(String login, String pass, String name, String email, String phone, String description) throws ServiceException;

    void createAdministrator() throws ServiceException;

    User getUserByLogin(String login) throws ServiceException;

    User getUserByEmail(String email) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;

    void changeRole(String login, String role) throws ServiceException;

    boolean changePassword(String login, String oldPassword, String newPassword) throws ServiceException;

    void editProfile(String login, String newName, String newPhone, String newDescription) throws ServiceException;

    void lockOrUnlockUser(String loginBlocker, String login, String reasonForBlocking) throws ServiceException;

    void deleteUser(String login) throws ServiceException;

    User restoreUser(String login) throws ServiceException;
}
