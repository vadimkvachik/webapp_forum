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
 * Adds a forum topic from authorized user
 */
@WebServlet("/add_topic")
public class AddTopicController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddTopicController.class);
    private ITopicService topicService = TopicService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        String firstMessage = req.getParameter("message_text");
        String topicName = req.getParameter("topic_name");
        String sectionId = req.getParameter("section_id");

        if (validateToNull(sessionUser, firstMessage, topicName, sectionId)) {
            try {
                String path = topicService.addTopicAndReturnPath(sessionUser, sectionId, topicName, firstMessage);
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

    private boolean validateToNull(User sessionUser, String text, String topicName, String sectionId) {
        return sessionUser != null && text != null && topicName != null && sectionId != null;
    }
}
