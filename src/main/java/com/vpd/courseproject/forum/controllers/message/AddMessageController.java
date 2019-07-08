package com.vpd.courseproject.forum.controllers.message;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.MessageService;
import com.vpd.courseproject.forum.service.api.IMessageService;
import com.vpd.courseproject.forum.utils.LoggerMessages;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Adds a forum post from authorized user
 */
@WebServlet("/add_message")
public class AddMessageController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddMessageController.class);
    private IMessageService messageService = MessageService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        String topicId = req.getParameter("topic_id");
        String text = req.getParameter("message_text");

        if (validate(sessionUser, topicId, text)) {
            try {
                String path = messageService.addMessageAndReturnPath(sessionUser, topicId, text);
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

    private boolean validate(User user, String topicId, String text) {
        return user != null && topicId != null && text != null;
    }

}
