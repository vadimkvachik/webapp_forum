package com.vpd.courseproject.forum.controllers.sectionblock;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.SectionBlockService;
import com.vpd.courseproject.forum.service.api.ISectionBlockService;
import com.vpd.courseproject.forum.utils.LoggerMessages;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Removes a forum section block from administrator
 */
@WebServlet("/delete_block")
public class DeleteSectionBlockController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DeleteSectionBlockController.class);
    private ISectionBlockService sectionBlockService = SectionBlockService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        String id = req.getParameter("block_id");

        if (validate(sessionUser, id)) {
            try {
                sectionBlockService.deleteSectionBlock(sessionUser.getLogin(), id);
                resp.sendRedirect(req.getContextPath() + "/");
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

    private boolean validate(User sessionUser, String blockId) {
        return sessionUser != null && blockId != null && sessionUser.getRole().equals(User.Role.ADMIN);
    }
}
