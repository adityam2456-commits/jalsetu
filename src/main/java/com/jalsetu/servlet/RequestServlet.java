package com.jalsetu.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.jalsetu.dao.SocietyDAO;
import com.jalsetu.dao.WaterRequestDAO;
import com.jalsetu.model.Society;
import com.jalsetu.model.WaterRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/request")
public class RequestServlet extends HttpServlet {

    private final SocietyDAO societyDAO = new SocietyDAO();
    private final WaterRequestDAO requestDAO = new WaterRequestDAO();

    // Runs when the browser opens the page (GET).
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if ("1".equals(req.getParameter("done"))) {
            req.getRequestDispatcher("/WEB-INF/views/requestSuccess.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/requestForm.jsp").forward(req, resp);
        }
    }

    // Runs when the form is submitted (POST).
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // 1. Read and trim the form values.
        String societyName   = clean(req.getParameter("societyName"));
        String contactPerson = clean(req.getParameter("contactPerson"));
        String contactNumber = clean(req.getParameter("contactNumber"));
        String address       = clean(req.getParameter("address"));
        String quantityText  = clean(req.getParameter("quantity"));
        String dateText      = clean(req.getParameter("preferredDate"));
        String urgency       = clean(req.getParameter("urgency"));
        String remarks       = clean(req.getParameter("remarks"));

        // 2. Validate. Every problem is added to this list.
        List<String> errors = new ArrayList<>();

        if (societyName.length() < 2 || societyName.length() > 100) {
            errors.add("Society name must be 2 to 100 characters.");
        }
        if (contactPerson.length() < 2 || contactPerson.length() > 100) {
            errors.add("Contact person must be 2 to 100 characters.");
        }
        if (!contactNumber.matches("[0-9]{10}")) {
            errors.add("Contact number must be exactly 10 digits.");
        }
        if (address.length() > 255) {
            errors.add("Address is too long (maximum 255 characters).");
        }

        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity < 100 || quantity > 100000) {
                errors.add("Water quantity must be between 100 and 100000 litres.");
            }
        } catch (NumberFormatException e) {
            errors.add("Water quantity must be a number.");
        }

        LocalDate preferredDate = null;
        try {
            preferredDate = LocalDate.parse(dateText);
            if (preferredDate.isBefore(LocalDate.now())) {
                errors.add("Preferred date cannot be in the past.");
            }
        } catch (DateTimeParseException e) {
            errors.add("Please choose a valid delivery date.");
        }

        if (!urgency.equals("Low") && !urgency.equals("Medium") && !urgency.equals("High")) {
            errors.add("Please choose a valid urgency level.");
        }
        if (remarks.length() > 255) {
            errors.add("Remarks are too long (maximum 255 characters).");
        }

        // 3. If there are errors, show the form again with the messages.
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/views/requestForm.jsp").forward(req, resp);
            return;
        }

        // 4. Everything is valid, so save to the database.
        try {
            int societyId = societyDAO.findSocietyId(societyName, contactNumber);

            if (societyId == -1) {
                Society society = new Society();
                society.setSocietyName(societyName);
                society.setContactPerson(contactPerson);
                society.setContactNumber(contactNumber);
                society.setAddress(address);
                societyId = societyDAO.addSociety(society);
            }

            WaterRequest request = new WaterRequest();
            request.setSocietyId(societyId);
            request.setRequiredQuantity(quantity);
            request.setPreferredDate(preferredDate);
            request.setUrgency(urgency);
            request.setRemarks(remarks);
            requestDAO.addRequest(request);

        } catch (SQLException e) {
            // Log the real problem on the server, but show a safe message to the user.
            getServletContext().log("Could not save water request", e);
            errors.add("Sorry, the request could not be saved. Please try again later.");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/views/requestForm.jsp").forward(req, resp);
            return;
        }

        // 5. Redirect so that refreshing the page does not submit the form twice.
        resp.sendRedirect(req.getContextPath() + "/request?done=1");
    }

    // Turns null into "" and removes spaces at both ends.
    private String clean(String value) {
        return value == null ? "" : value.trim();
    }
}