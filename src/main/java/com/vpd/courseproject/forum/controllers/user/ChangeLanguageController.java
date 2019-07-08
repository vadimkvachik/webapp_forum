package com.vpd.courseproject.forum.controllers.user;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Changes the language on the forum.
 */
@WebServlet(("/locale"))
public class ChangeLanguageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String locale = req.getParameter("lang");
        String redirectPage = req.getParameter("page");

        if (validate(locale)) {
            req.getSession().setAttribute("lang", locale);
        }
        resp.sendRedirect(req.getContextPath() + redirectPage);
    }

    private boolean validate(String locale) {
        return locale != null && (locale.equals("ru") || locale.equals("en"));
    }
}
