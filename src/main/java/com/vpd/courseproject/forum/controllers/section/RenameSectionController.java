package com.vpd.courseproject.forum.controllers.section;


import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.SectionService;
import com.vpd.courseproject.forum.service.api.ISectionService;
import com.vpd.courseproject.forum.utils.LoggerMessages;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Renames a forum section from administrator
 */
@WebServlet("/rename_section")
public class RenameSectionController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(RenameSectionController.class);
    private ISectionService sectionService = SectionService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        String id = req.getParameter("id");
        String newName = req.getParameter("new_name");

        if (validate(sessionUser, id, newName)) {
            try {
                String path = sectionService.renameSectionAndReturnPath(sessionUser.getLogin(), id, newName);
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

    private boolean validate(User sessionUser, String sectionId, String newName) {
        return sessionUser != null && sectionId != null && newName != null && sessionUser.getRole().equals(User.Role.ADMIN);
    }
}







