package com.vpd.courseproject.forum.controllers.privatemessages;

import com.vpd.courseproject.forum.exception.ServiceException;
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
 * Sends the private message.
 */
@WebServlet("/send_private_message")
public class SendPrivateMessageController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(SendPrivateMessageController.class);
    private IPrivateMessageService privateMessageService = PrivateMessageService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String topic = req.getParameter("topic");
        String text = req.getParameter("text");
        String senderLogin = req.getParameter("login_from");
        String recipientLogin = req.getParameter("login_to");

        if (senderLogin != null && recipientLogin != null) {
            try {
                privateMessageService.addPrivateMessage(topic, text, senderLogin, recipientLogin);
                req.setAttribute("login", recipientLogin);
                req.setAttribute("information", "message_sent");
                req.getRequestDispatcher("/informationPage.jsp").forward(req, resp);
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
