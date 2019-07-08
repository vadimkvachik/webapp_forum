package com.vpd.courseproject.forum.filters;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.UserService;
import com.vpd.courseproject.forum.service.api.IUserService;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Checks if the user in the session has been banned. If banned, it notifies and blocks
 */
@WebFilter("/*")
public class UserLockChecker implements Filter {
    private static final Logger logger = Logger.getLogger(UserLockChecker.class);
    private IUserService userService = UserService.getInstance();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        User sessionUser = (User) req.getSession().getAttribute("user");
        try {
            if (sessionUser != null) {
                User sessionUserFromDB = userService.getUserByLogin(sessionUser.getLogin());
                if (sessionUserFromDB.getReasonForBlocking() != null) {
                    req.getSession().setAttribute("user", null);
                    req.setAttribute("information", "your_profile_banned");
                    req.setAttribute("reason", sessionUserFromDB.getReasonForBlocking());
                    req.getRequestDispatcher("/informationPage.jsp").forward(req, response);
                    logger.info("User '" + sessionUserFromDB.getLogin() + "' is banned");
                }
            }
        } catch (ServiceException e) {
            req.setAttribute("errorInfo", e.getMessage());
            req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
            logger.error(e.getMessage());
        }
        chain.doFilter(request, response);
    }

}
