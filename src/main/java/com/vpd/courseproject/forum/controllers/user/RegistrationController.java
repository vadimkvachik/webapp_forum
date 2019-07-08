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
 * Registers user.
 */
@WebServlet("/reg")
public class RegistrationController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(RegistrationController.class);
    private IUserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            try {
                req.setAttribute("users", userService.getAllUsers());
                req.getRequestDispatcher("/registrationPage.jsp").forward(req, resp);
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String description = req.getParameter("description");

        if (validate(login, pass, name, email, phone, description)) {
            try {
                User user = userService.createUser(login, pass, name, email, phone, description);
                req.getSession().setAttribute("user", user);
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

    private boolean validate(String login, String pass, String name, String email, String phone, String description) {
        return login != null && pass != null && name != null && email != null && phone != null && description != null;
    }
}


