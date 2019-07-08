package com.vpd.courseproject.forum.controllers.topic;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.Message;
import com.vpd.courseproject.forum.persistence.entity.Topic;
import com.vpd.courseproject.forum.service.MessageService;
import com.vpd.courseproject.forum.service.PaginationService;
import com.vpd.courseproject.forum.service.TopicService;
import com.vpd.courseproject.forum.service.api.IMessageService;
import com.vpd.courseproject.forum.service.api.IPaginationService;
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
 * Shows the topic page.
 */
@WebServlet("/topic")
public class ViewTopicPageController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ViewTopicPageController.class);
    private ITopicService topicService = TopicService.getInstance();
    private IMessageService messageService = MessageService.getInstance();
    private IPaginationService paginationService = PaginationService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int page = paginationService.pageValidate(req.getParameter("page"));
        String id = req.getParameter("id");

        if (id != null) {
            try {
                Topic topic = topicService.getTopicById(id);
                List<Message> messages = messageService.getMessagesByTopicId(id);
                req.setAttribute("topic", topic);
                req.setAttribute("messages", paginationService.get10messagesForCurrentPage(page, messages));
                req.setAttribute("pages", paginationService.getPagesArray(messages.size()));
                req.setAttribute("currentPage", page);
                req.getRequestDispatcher("/topicPage.jsp").forward(req, resp);
            } catch (NumberFormatException e) {
                resp.sendRedirect(req.getContextPath() + "/");
                logger.warn(LoggerMessages.PARAMETERS_ARE_INCORRECT);
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


