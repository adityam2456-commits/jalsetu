package com.jalsetu.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/admin/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // The login page itself must stay open to everyone.
        String path = req.getServletPath();
        if (path.equals("/admin/login")) {
            chain.doFilter(request, response);
            return;
        }

        // Is there a logged-in admin in the session?
        HttpSession session = req.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("adminUser") != null;

        if (loggedIn) {
            // Ask the browser not to keep admin pages, so Back does not show them after logout.
            resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            resp.setHeader("Pragma", "no-cache");
            resp.setDateHeader("Expires", 0);
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
        }
    }
}