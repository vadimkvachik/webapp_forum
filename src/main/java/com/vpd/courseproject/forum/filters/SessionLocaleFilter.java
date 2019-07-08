package com.vpd.courseproject.forum.filters;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Checks if a locale is installed. If not, sets from forum properties.
 */
@WebFilter("/*")
public class SessionLocaleFilter implements Filter {
    private Properties properties = new Properties();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getSession().getAttribute("lang") == null) {
            properties.load(Objects.requireNonNull(SessionLocaleFilter.class.getClassLoader().getResourceAsStream("forum.properties")));
            String lang = properties.getProperty("forum_lang");
            if (!(lang.equals("en") || lang.equals("ru"))) lang = "ru";
            req.getSession().setAttribute("lang", lang);
        }
        chain.doFilter(request, response);
    }

}
