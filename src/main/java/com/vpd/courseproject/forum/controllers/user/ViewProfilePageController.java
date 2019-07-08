package com.vpd.courseproject.forum.controllers.user;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.ArticleService;
import com.vpd.courseproject.forum.service.MessageService;
import com.vpd.courseproject.forum.service.UserService;
import com.vpd.courseproject.forum.service.api.IArticleService;
import com.vpd.courseproject.forum.service.api.IMessageService;
import com.vpd.courseproject.forum.service.api.IUserService;
import com.vpd.courseproject.forum.utils.LoggerMessages;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Shows user profile.
 */
@WebServlet("/profile")
public class ViewProfilePageController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ViewProfilePageController.class);
    private IArticleService articleService = ArticleService.getInstance();
    private IMessageService messageService = MessageService.getInstance();
    private IUserService userService = UserService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");

        if (login != null) {
            try {
                User user = userService.getUserByLogin(login);
                req.setAttribute("user", user);
                req.setAttribute("messages", messageService.getNumberOfMessagesByLogin(login));
                req.setAttribute("articles", articleService.getNumberOfArticlesByLogin(login));
                req.getRequestDispatcher("/profilePage.jsp").forward(req, resp);
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
