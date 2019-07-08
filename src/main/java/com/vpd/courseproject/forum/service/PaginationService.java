package com.vpd.courseproject.forum.service;

import com.vpd.courseproject.forum.persistence.entity.Message;
import com.vpd.courseproject.forum.service.api.IPaginationService;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PaginationService implements IPaginationService {
    private static final PaginationService instance = new PaginationService();

    private PaginationService() {
    }

    public static PaginationService getInstance() {return instance; }

    public int[] getPagesArray(int messages) {
        if (messages > 10) {
            int[] pages = new int[(messages - 1) / 10 + 1];
            for (int i = 0; i < pages.length; i++) {
                pages[i] = i + 1;
            }
            return pages;
        } else {
            return null;
        }
    }

    public int pageValidate(String page) {
        try {
            if (page == null || Integer.parseInt(page) < 1) {
                return 1;
            } else {
                return Integer.parseInt(page);
            }
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    public List<Message> get10messagesForCurrentPage(int page, List<Message> messages) {
        return messages.stream().skip((page - 1) * 10).limit(10).collect(Collectors.toCollection(LinkedList::new));
    }
}
