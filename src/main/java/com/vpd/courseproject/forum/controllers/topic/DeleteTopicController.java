package com.vpd.courseproject.forum.controllers.topic;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.TopicService;
import com.vpd.courseproject.forum.service.api.ITopicService;
import com.vpd.courseproject.forum.utils.LoggerMessages;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Removes a forum topic from moderator or administrator
 */
@WebServlet("/delete_topic")
public class DeleteTopicController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DeleteTopicController.class);
    private ITopicService topicService = TopicService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        String id = req.getParameter("topicId");

        if (validate(sessionUser, id)) {
            try {
                String path = topicService.deleteTopicAndReturnPath(sessionUser.getLogin(), id);
                resp.sendRedirect(req.getContextPath() + path);
            } catch (ServiceException e) {
                req.setAttribute("errorInfo", e.getMessage());
                req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
                logger.error(e.getMessage());
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/");
            logger.warn(LoggerMessages.PARAMETERS_ARE_INCORRECT);
        }
    }

    private boolean validate(User sessionUser, String topicId) {
        return sessionUser != null && topicId != null && !sessionUser.getRole().equals(User.Role.USER);
    }
}
