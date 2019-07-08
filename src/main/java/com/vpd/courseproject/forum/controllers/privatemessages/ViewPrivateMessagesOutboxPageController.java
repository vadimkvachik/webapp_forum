package com.vpd.courseproject.forum.controllers.privatemessages;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.PrivateMessageService;
import com.vpd.courseproject.forum.service.api.IPrivateMessageService;
import com.vpd.courseproject.forum.utils.LoggerMessages;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Shows personal outgoing messages from a forum user.
 */
@WebServlet("/private_out")
public class ViewPrivateMessagesOutboxPageController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ViewPrivateMessagesOutboxPageController.class);
    private IPrivateMessageService privateMessageService = PrivateMessageService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        if (sessionUser != null) {
            try {
                req.setAttribute("messages", privateMessageService.getPrivateMessagesBySender(sessionUser.getLogin()));
                req.getRequestDispatcher("/privateMessagesOutboxPage.jsp").forward(req, resp);
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
