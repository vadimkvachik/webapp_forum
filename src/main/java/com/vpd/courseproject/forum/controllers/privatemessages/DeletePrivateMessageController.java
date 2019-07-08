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
 * Deletes the private message.
 */
@WebServlet("/delete_private_message")
public class DeletePrivateMessageController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DeletePrivateMessageController.class);
    private IPrivateMessageService privateMessageService = PrivateMessageService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        String id = req.getParameter("id");
        String activity = req.getParameter("activity");

        if (validate(sessionUser, id, activity)) {
            try {
                String path = privateMessageService.deletePrivateMessageAndReturnPath(sessionUser, id, activity);
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

    private boolean validate(User sessionUser, String messageId, String activity) {
        return sessionUser != null && messageId != null && activity != null;
    }
}
