package com.vpd.courseproject.forum.filters;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.persistence.dao.PrivateMessageDao;
import com.vpd.courseproject.forum.persistence.dao.api.IPrivateMessageDao;
import com.vpd.courseproject.forum.persistence.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Sends the number of unread private messages of an authorized user.
 */
@WebFilter("/*")
public class UserNewMessagesChecker implements Filter {
    private static final Logger logger = Logger.getLogger(UserNewMessagesChecker.class);
    private IPrivateMessageDao privateMessageDao = PrivateMessageDao.getInstance();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        User sessionUser = (User) req.getSession().getAttribute("user");

        try {
            if (sessionUser != null) {
                req.setAttribute("number_of_unread_messages", privateMessageDao
                        .getNumberOfUnreadMessagesByLoginTo(sessionUser.getLogin()));
            }
        } catch (DaoException e) {
            req.setAttribute("errorInfo", e.getMessage());
            req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
            logger.error(e.getMessage());
        }
        chain.doFilter(request, response);
    }


}
