package com.jalsetu.servlet;

import com.jalsetu.dao.AdminDAO;
import com.jalsetu.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet {

    private final AdminDAO adminDAO = new AdminDAO();

    // Shows the login page. If already logged in, go to the dashboard.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("adminUser") != null) {
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/adminLogin.jsp").forward(req, resp);
    }

    // Checks the username and password.
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        username = (username == null) ? "" : username.trim();
        password = (password == null) ? "" : password;

        if (username.isEmpty() || password.isEmpty()) {
            showError(req, resp, "Please enter username and password.");
            return;
        }

        try {
            String storedHash = adminDAO.getPasswordHash(username);

            if (storedHash != null && PasswordUtil.checkPassword(password, storedHash)) {
                // Login is correct. Throw away any old session and start a new one.
                HttpSession old = req.getSession(false);
                if (old != null) {
                    old.invalidate();
                }
                HttpSession session = req.getSession(true);
                session.setAttribute("adminUser", username);
                session.setMaxInactiveInterval(30 * 60);   // 30 minutes

                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else {
                // Same message for a wrong username or a wrong password.
                showError(req, resp, "Invalid username or password.");
            }

        } catch (SQLException e) {
            getServletContext().log("Admin login database error", e);
            showError(req, resp, "Login is not available right now. Please try again later.");
        }
    }

    private void showError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("error", message);
        req.getRequestDispatcher("/WEB-INF/views/adminLogin.jsp").forward(req, resp);
    }
}