package com.vpd.courseproject.forum.controllers.user;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.MailService;
import com.vpd.courseproject.forum.service.UserService;
import com.vpd.courseproject.forum.service.api.IMailService;
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
 * Recovers user password to email.
 */
@WebServlet("/pass_recovery")
public class PasswordRecoveryController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(PasswordRecoveryController.class);
    private IUserService userService = UserService.getInstance();
    private IMailService mailService = MailService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String email = req.getParameter("email");
        String lang = (String) req.getSession().getAttribute("lang");

        if (email != null) {
            try {
                User user = userService.getUserByEmail(email);
                if (user != null) {
                    mailService.sendMailForPasswordRecovery(user, lang);
                    logger.info("User '" + user.getLogin() + "' recovered password by email");
                    req.setAttribute("information", "successful_send_new_password");
                } else {
                    req.setAttribute("information", "fail_send_new_password");
                }
                req.getRequestDispatcher("/informationPage.jsp").forward(req, resp);
            } catch (ServiceException e) {
                req.setAttribute("information", "fail_send_new_password_no_internet");
                req.getRequestDispatcher("/informationPage.jsp").forward(req, resp);
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/");
            logger.warn(LoggerMessages.PARAMETERS_ARE_INCORRECT);
        }
    }

}


