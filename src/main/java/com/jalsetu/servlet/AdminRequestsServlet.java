package com.jalsetu.servlet;

import com.jalsetu.dao.WaterRequestDAO;
import com.jalsetu.model.WaterRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/requests")
public class AdminRequestsServlet extends HttpServlet {

    private final WaterRequestDAO requestDAO = new WaterRequestDAO();

    // Shows the list of requests, optionally filtered by status.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String status = req.getParameter("status");
        if (!isValidStatus(status)) {
            status = "";   // empty means: show all
        }

        try {
            List<WaterRequest> requests = status.isEmpty()
                    ? requestDAO.getAllRequests()
                    : requestDAO.getRequestsByStatus(status);
            req.setAttribute("requests", requests);
        } catch (SQLException e) {
            getServletContext().log("Could not load requests", e);
            req.setAttribute("error", "Requests could not be loaded right now.");
        }

        req.setAttribute("selectedStatus", status);
        req.getRequestDispatcher("/WEB-INF/views/adminRequests.jsp").forward(req, resp);
    }

    // Handles the Approve, Reject and Complete buttons.
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        String filter = req.getParameter("filter");
        if (!isValidStatus(filter)) {
            filter = "";
        }

        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            redirect(req, resp, filter, "invalid");
            return;
        }

        // Each action is allowed only from one particular current status.
        String fromStatus;
        String toStatus;
        if ("approve".equals(action)) {
            fromStatus = "Pending";
            toStatus = "Approved";
        } else if ("reject".equals(action)) {
            fromStatus = "Pending";
            toStatus = "Rejected";
        } else if ("complete".equals(action)) {
            fromStatus = "Approved";
            toStatus = "Completed";
        } else {
            redirect(req, resp, filter, "invalid");
            return;
        }

        try {
            boolean changed = requestDAO.updateStatus(id, fromStatus, toStatus);
            redirect(req, resp, filter, changed ? "updated" : "notallowed");
        } catch (SQLException e) {
            getServletContext().log("Could not update request status", e);
            redirect(req, resp, filter, "error");
        }
    }

    // Only these four words are accepted as a status filter.
    private boolean isValidStatus(String s) {
        return "Pending".equals(s) || "Approved".equals(s)
            || "Completed".equals(s) || "Rejected".equals(s);
    }

    // Goes back to the list, keeping the filter and showing a message.
    private void redirect(HttpServletRequest req, HttpServletResponse resp,
                          String filter, String msg) throws IOException {
        String url = req.getContextPath() + "/admin/requests?msg=" + msg;
        if (!filter.isEmpty()) {
            url += "&status=" + filter;
        }
        resp.sendRedirect(url);
    }
}