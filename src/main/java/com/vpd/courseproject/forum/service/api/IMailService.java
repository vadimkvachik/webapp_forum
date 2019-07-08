package com.vpd.courseproject.forum.service.api;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.User;

public interface IMailService {

    void sendMailForPasswordRecovery(User user, String lang) throws ServiceException;

}
