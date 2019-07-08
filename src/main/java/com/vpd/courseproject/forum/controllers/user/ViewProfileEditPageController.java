package com.vpd.courseproject.forum.controllers.user;

import com.vpd.courseproject.forum.utils.FromISO88591toUTF8Encoder;
import com.vpd.courseproject.forum.utils.LoggerMessages;
import com.vpd.courseproject.forum.utils.api.IEncoder;
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
@WebServlet("/edit")
public class ViewProfileEditPageController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ViewProfileEditPageController.class);
    private IEncoder encoder = FromISO88591toUTF8Encoder.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String phone = req.getParameter("phone");

        if (validate(login, name, description)) {
            try {
                req.setAttribute("login", login);
                req.setAttribute("name", encoder.encode(name));
                req.setAttribute("phone", phone);
                req.setAttribute("description", encoder.encode(description.replace("<br>", "")));
                req.getRequestDispatcher("/profileEditPage.jsp").forward(req, resp);
            } catch (ServletException e) {
                req.setAttribute("errorInfo", e.getMessage());
                req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
                logger.error(e.getMessage());
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/");
            logger.warn(LoggerMessages.PARAMETERS_ARE_INCORRECT);
        }
    }

    private boolean validate(String login, String name, String description) {
        return login != null && name != null && description != null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
