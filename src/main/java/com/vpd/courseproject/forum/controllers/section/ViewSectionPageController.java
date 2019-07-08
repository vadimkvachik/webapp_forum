package com.vpd.courseproject.forum.controllers.section;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.enrties.TopicEntry;
import com.vpd.courseproject.forum.persistence.entity.Section;
import com.vpd.courseproject.forum.service.SectionService;
import com.vpd.courseproject.forum.service.TopicService;
import com.vpd.courseproject.forum.service.api.ISectionService;
import com.vpd.courseproject.forum.service.api.ITopicService;
import com.vpd.courseproject.forum.utils.LoggerMessages;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Shows the section page.
 */
@WebServlet("/section")
public class ViewSectionPageController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ViewSectionPageController.class);
    private ISectionService sectionService = SectionService.getInstance();
    private ITopicService topicService = TopicService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String id = req.getParameter("id");
        if (id != null) {
            try {
                Section section = sectionService.getSection(id);
                List<TopicEntry> topicEntries = topicService.getTopicEntries(Long.valueOf(id));
                req.setAttribute("section", section);
                req.setAttribute("topicEntries", topicEntries);
                req.getRequestDispatcher("/sectionPage.jsp").forward(req, resp);
            } catch (ServiceException e) {
                req.setAttribute("errorInfo", e.getMessage());
                req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
                logger.error(e.getMessage());
            } catch (NumberFormatException e) {
                resp.sendRedirect(req.getContextPath() + "/");
                logger.warn(LoggerMessages.PARAMETERS_ARE_INCORRECT);
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/");
            logger.warn(LoggerMessages.PARAMETERS_ARE_INCORRECT);
        }
    }
}
