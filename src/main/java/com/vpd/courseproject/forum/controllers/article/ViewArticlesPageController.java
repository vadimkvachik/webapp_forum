package com.vpd.courseproject.forum.controllers.article;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.service.ArticleService;
import com.vpd.courseproject.forum.persistence.enrties.ArticleEntry;
import com.vpd.courseproject.forum.service.api.IArticleService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Shows a page with articles.
 */
@WebServlet("/articles")
public class ViewArticlesPageController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ViewArticlesPageController.class);
    private IArticleService articleService = ArticleService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");

        try {
            List<ArticleEntry> articleEntries = articleService.getArticleEntries(login);
            req.setAttribute("articles", articleEntries);
            req.getRequestDispatcher("/articlesPage.jsp").forward(req, resp);
        } catch (ServiceException e) {
            req.setAttribute("errorInfo", e.getMessage());
            req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
            logger.error(e.getMessage());
        }
    }
}
