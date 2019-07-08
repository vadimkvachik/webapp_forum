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
 * Changes a forum post from authorized user
 */
@WebServlet("/change_message")
public class ChangeMessageController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ChangeMessageController.class);
    private IMessageService messageService = MessageService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        String id = req.getParameter("id");
        String page = req.getParameter("page");
        String text = req.getParameter("new_text");

        if (validate(sessionUser, id, page, text)) {
            try {
                String path =  messageService.changeMessageAndReturnPath(sessionUser.getLogin(), id, text, page);
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


    private boolean validate(User user, String messageId, String page, String text) {
        return user != null && messageId != null && page != null && text != null;
    }
}
