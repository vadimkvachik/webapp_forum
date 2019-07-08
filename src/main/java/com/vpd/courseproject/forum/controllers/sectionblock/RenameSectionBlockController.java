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
 * Renames a forum section block from administrator
 */
@WebServlet("/rename_block")
public class RenameSectionBlockController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(RenameSectionBlockController.class);
    private ISectionBlockService sectionBlockService = SectionBlockService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        String id = req.getParameter("id");
        String newName = req.getParameter("new_name");

        if (validateToNull(sessionUser, id, newName)) {
            try {
                String path = sectionBlockService.renameSectionBlockAndReturnPath(sessionUser.getLogin(), id, newName);
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

    private boolean validateToNull(User sessionUser, String id, String newName) {
        return sessionUser != null && id != null && newName != null && sessionUser.getRole().equals(User.Role.ADMIN);
    }
}
