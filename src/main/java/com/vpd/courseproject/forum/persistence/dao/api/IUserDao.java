package com.vpd.courseproject.forum.persistence.dao.api;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.persistence.entity.User;

import java.util.List;

public interface IUserDao {

    void addUser(User user) throws DaoException;

    User getUserByLogin(String login) throws DaoException;

    User getUserByEmail(String email) throws DaoException;

    List<User> getAllUsers() throws DaoException;

    void updateUser(User user) throws DaoException;
}
