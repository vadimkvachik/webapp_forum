package com.vpd.courseproject.forum.controllers.article;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.Article;
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
 * Shows a page with article.
 */
@WebServlet("/article")
public class ViewArticleController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ViewArticleController.class);
    private IArticleService articleService = ArticleService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String id = req.getParameter("id");
        if (id != null) {
            try {
                Article article = articleService.getArticle(id);
                req.setAttribute("article", article);
                req.getRequestDispatcher("/articlePage.jsp").forward(req, resp);
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
