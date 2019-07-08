package com.vpd.courseproject.forum.controllers.message;

import com.vpd.courseproject.forum.exception.ServiceException;
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
 * Shows all posts by the forum user.
 */
@WebServlet("/messages")
public class ViewMessagesPageController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ViewMessagesPageController.class);
    private IMessageService messageService = MessageService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        if (login != null) {
            try {
                req.setAttribute("messages", messageService.getMessagesByLogin(login));
                req.setAttribute("login", login);
                req.getRequestDispatcher("/messagesFromUserPage.jsp").forward(req, resp);
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
}
