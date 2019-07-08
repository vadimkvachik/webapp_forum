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
 * Removes an article from the forum.
 */
@WebServlet("/delete_article")
public class DeleteArticleController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DeleteArticleController.class);
    private IArticleService articleService = ArticleService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        String id = req.getParameter("id");

        if (validate(sessionUser, id)) {
            try {
                articleService.deleteArticle(sessionUser.getLogin(), id);
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/");
    }

    private boolean validate(User sessionUser, String id) {
        return id != null && sessionUser != null;
    }


}
