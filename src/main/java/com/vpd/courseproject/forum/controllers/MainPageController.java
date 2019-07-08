package com.vpd.courseproject.forum.controllers;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.service.SectionBlockService;
import com.vpd.courseproject.forum.service.SectionService;
import com.vpd.courseproject.forum.service.api.ISectionBlockService;
import com.vpd.courseproject.forum.service.api.ISectionService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Redirects the user to the main page.
 */
@WebServlet("/index")
public class MainPageController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(MainPageController.class);
    private ISectionBlockService sectionBlockServicenService = SectionBlockService.getInstance();
    private ISectionService sectionService = SectionService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            req.setAttribute("sectionBlockList", sectionBlockServicenService.getAllSectionBlocks());
            req.setAttribute("sectionEntries", sectionService.getSectionEntries());
            req.getRequestDispatcher("/mainPage.jsp").forward(req, resp);
        } catch (ServiceException e) {
            req.setAttribute("errorInfo", e.getMessage());
            req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
            logger.error(e.getMessage());
        }
    }
}
