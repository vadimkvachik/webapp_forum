package com.vpd.courseproject.forum.service.api;

import com.vpd.courseproject.forum.persistence.entity.Message;

import java.util.List;

public interface IPaginationService {

    int[] getPagesArray(int messages);

    int pageValidate(String page);

    List<Message> get10messagesForCurrentPage(int page, List<Message> messages);
}
