package com.vpd.courseproject.forum.persistence.dao.api;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.persistence.entity.Topic;

import java.util.List;

public interface ITopicDao {

    void addTopic(Topic topic) throws DaoException;

    Topic getTopicById(long id) throws DaoException;

    List<Topic> getAllTopicsBySectionId(long sectionId) throws DaoException;

    long getNumberOfTopicsBySectionId(long sectionId) throws DaoException;

    void updateTopic(Topic topic) throws DaoException;

    void deleteTopic(Topic topic) throws DaoException;
}
