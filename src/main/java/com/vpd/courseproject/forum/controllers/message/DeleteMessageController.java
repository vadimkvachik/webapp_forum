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
 * Deletes a forum post from authorized user.
 */
@WebServlet("/delete_message")
public class DeleteMessageController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DeleteMessageController.class);
    private IMessageService messageService = MessageService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        String numberOfMessages = req.getParameter("numberOfMessages");
        String id = req.getParameter("id");
        String page = req.getParameter("page");

        if (validate(sessionUser, id, page, numberOfMessages)) {
            try {
                String path = messageService.deleteMessageAndReturnPath(sessionUser.getLogin(), id, page, numberOfMessages);
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

    private boolean validate(User user, String messageId, String page, String numberOfMessages) {
        return user != null && messageId != null && page != null && numberOfMessages != null;
    }


}
