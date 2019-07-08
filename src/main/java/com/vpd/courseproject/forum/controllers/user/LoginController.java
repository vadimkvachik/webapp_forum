package com.vpd.courseproject.forum.controllers.user;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.UserService;
import com.vpd.courseproject.forum.service.api.IUserService;
import com.vpd.courseproject.forum.utils.LoggerMessages;
import com.vpd.courseproject.forum.utils.FromISO88591toUTF8Encoder;
import com.vpd.courseproject.forum.utils.api.IEncoder;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User login.
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginController.class);
    private IEncoder encoder = FromISO88591toUTF8Encoder.getInstance();
    private IUserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        if (validate(login, pass)) {
            try {
                User user = userService.getUserByLogin(login);
                if (user != null) {
                    if (user.getPassword().equals(encoder.encode(pass))) {
                        if (user.isDeleted()) {
                            req.setAttribute("information", "your_profile_deleted");
                            req.setAttribute("login", user.getLogin());
                            req.getRequestDispatcher("/informationPage.jsp").forward(req, resp);
                        } else {
                            req.getSession().setAttribute("user", user);
                            resp.sendRedirect(req.getContextPath() + "/");
                            logger.info("User '" + user.getLogin() + "' logged in");
                        }
                    } else {
                        req.getRequestDispatcher("loginPage.jsp").forward(req, resp);
                    }
                } else {
                    req.getRequestDispatcher("loginPage.jsp").forward(req, resp);
                }
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/");
    }

    private boolean validate(String login, String pass) {
        return login != null && pass != null;
    }
}
