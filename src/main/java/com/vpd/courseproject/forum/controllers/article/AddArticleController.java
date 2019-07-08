package com.vpd.courseproject.forum.controllers.article;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.ArticleService;
import com.vpd.courseproject.forum.service.api.IArticleService;
import com.vpd.courseproject.forum.utils.LoggerMessages;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Adds an article to the forum.
 */
@WebServlet("/add_article")
public class AddArticleController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddArticleController.class);
    private IArticleService articleService = ArticleService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        String topic = req.getParameter("topic");
        String text = req.getParameter("text");

        if (validate(sessionUser, topic, text)) {
            try {
                articleService.addArticle(sessionUser, topic, text);
                resp.sendRedirect(req.getContextPath() + "/articles");
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

    private boolean validate(User sessionUser, String topic, String text) {
        return sessionUser != null && topic != null && text != null;
    }
}
