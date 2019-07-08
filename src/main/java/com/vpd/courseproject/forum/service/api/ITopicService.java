package com.vpd.courseproject.forum.service.api;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.enrties.TopicEntry;
import com.vpd.courseproject.forum.persistence.entity.Topic;
import com.vpd.courseproject.forum.persistence.entity.User;

import java.util.List;

public interface ITopicService {

    String addTopicAndReturnPath(User user, String sectionId, String name, String firstMessage) throws ServiceException;

    Topic getTopicById(String id) throws ServiceException;

    List<TopicEntry> getTopicEntries(long sectionId) throws ServiceException;

    String renameTopicAndReturnPath(String login, String id, String newName) throws ServiceException;

    String deleteTopicAndReturnPath(String login, String id) throws ServiceException;
}
