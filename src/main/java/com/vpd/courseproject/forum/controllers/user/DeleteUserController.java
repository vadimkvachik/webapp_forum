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
 * Deletes a user.
 */
@WebServlet("/delete_user")
public class DeleteUserController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DeleteUserController.class);
    private IUserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");

        if (login != null) {
            try {
                userService.deleteUser(login);
                req.getSession().setAttribute("user", null);
                req.setAttribute("login", login);
                req.setAttribute("information", "your_profile_deleted");
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
}
