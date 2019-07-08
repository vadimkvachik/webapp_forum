package com.vpd.courseproject.forum.controllers.user;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.service.UserService;
import com.vpd.courseproject.forum.service.api.IUserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Registers administrator.
 */
@WebServlet("/initialization")
public class RegistrationAdminController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(RegistrationAdminController.class);
    private IUserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            userService.createAdministrator();
            req.setAttribute("information", "admin_register_successful");
            req.getRequestDispatcher("/informationPage.jsp").forward(req, resp);
        } catch (ServiceException e) {
            req.setAttribute("errorInfo", e.getMessage());
            req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
            logger.error(e.getMessage());
        }
    }
}
