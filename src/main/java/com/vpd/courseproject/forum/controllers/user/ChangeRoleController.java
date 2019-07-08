package com.vpd.courseproject.forum.controllers.user;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.User;
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
 * Changes the role of the user.
 */
@WebServlet("/role")
public class ChangeRoleController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ChangeRoleController.class);
    private IUserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        String login = req.getParameter("login");
        String role = req.getParameter("role");

        if (validate(sessionUser, login, role)) {
            try {
                userService.changeRole(login, role);
                resp.sendRedirect(req.getContextPath() + "/users");
                logger.info("User '" + sessionUser.getLogin() + "' changed the role of user '" + login + "' to '" + role + "'");
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

    private boolean validate(User sessionUser, String login, String role) {
        return sessionUser != null && login != null && role != null && sessionUser.getRole().equals(User.Role.ADMIN) && !login.equals("admin");
    }
}
