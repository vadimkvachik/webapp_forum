package com.vpd.courseproject.forum.controllers.user;

import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.utils.LoggerMessages;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User logout.
 */
@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LogoutController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User sessionUser = (User) req.getSession().getAttribute("user");

        if (sessionUser != null) {
            req.getSession().setAttribute("user", null);
            resp.sendRedirect(req.getContextPath() + "/");
            logger.info("User '" + sessionUser.getLogin() + "' logged out");
        } else {
            resp.sendRedirect(req.getContextPath() + "/");
            logger.warn(LoggerMessages.PARAMETERS_ARE_INCORRECT);
        }
    }
}
