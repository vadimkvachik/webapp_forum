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
 * Change user password.
 */
@WebServlet("/change_password")
public class ChangePasswordController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ChangePasswordController.class);
    private IUserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String oldPassword = req.getParameter("old_password");
        String newPassword = req.getParameter("new_password");

        if (validate(login, oldPassword, newPassword)) {
            try {
                if (userService.changePassword(login, oldPassword, newPassword)) {
                    req.setAttribute("information", "successful_password_change");
                } else {
                    req.setAttribute("information", "fail_password_change");
                }
                req.getRequestDispatcher("/informationPage.jsp").forward(req, resp);
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

    private boolean validate(String login, String oldPassword, String newPassword) {
        return login != null && oldPassword != null && newPassword != null;
    }

}
