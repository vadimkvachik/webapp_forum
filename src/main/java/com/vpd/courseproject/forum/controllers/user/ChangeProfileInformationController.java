package com.vpd.courseproject.forum.controllers.user;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.service.UserService;
import com.vpd.courseproject.forum.service.api.IUserService;
import com.vpd.courseproject.forum.utils.LoggerMessages;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Edits user profile.
 */
@WebServlet("/edit_profile")
public class ChangeProfileInformationController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ChangeProfileInformationController.class);
    private IUserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String newName = req.getParameter("new_name");
        String newPhone = req.getParameter("new_phone");
        String newDescription = req.getParameter("new_description");

        if (validate(login, newName, newPhone, newDescription)) {
            try {
                userService.editProfile(login, newName, newPhone, newDescription);
                req.getSession().setAttribute("user", userService.getUserByLogin(login));
                resp.sendRedirect(req.getContextPath() + "/profile?login=" + login);
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

    private boolean validate(String login, String newName, String newPhone, String newDescription) {
        return login != null && newName != null && newPhone != null && newDescription != null;
    }
}
