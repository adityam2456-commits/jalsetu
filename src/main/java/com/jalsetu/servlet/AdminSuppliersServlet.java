package com.jalsetu.servlet;

import com.jalsetu.dao.SupplierDAO;
import com.jalsetu.model.Supplier;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/suppliers")
public class AdminSuppliersServlet extends HttpServlet {

    private final SupplierDAO supplierDAO = new SupplierDAO();

    // Shows the supplier list and the add/edit form.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // By default the form is empty (adding a new supplier).
        Supplier form = new Supplier();
        form.setAvailabilityStatus("Available");

        // If the address has ?edit=3, load supplier 3 into the form.
        String editId = req.getParameter("edit");
        if (editId != null && !editId.isEmpty()) {
            try {
                Supplier found = supplierDAO.getSupplierById(Integer.parseInt(editId));
                if (found != null) {
                    form = found;
                }
            } catch (NumberFormatException e) {
                // not a number, so just show the empty form
            } catch (SQLException e) {
                getServletContext().log("Could not load supplier for editing", e);
                req.setAttribute("error", "Supplier could not be loaded right now.");
            }
        }

        showPage(req, resp, form, null);
    }

    // Handles the Save button (add or update).
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String idText        = clean(req.getParameter("id"));
        String name          = clean(req.getParameter("supplierName"));
        String contactPerson = clean(req.getParameter("contactPerson"));
        String contactNumber = clean(req.getParameter("contactNumber"));
        String capacityText  = clean(req.getParameter("tankerCapacity"));
        String status        = clean(req.getParameter("availabilityStatus"));

        List<String> errors = new ArrayList<>();

        if (name.length() < 2 || name.length() > 100) {
            errors.add("Supplier name must be 2 to 100 characters.");
        }
        if (contactPerson.length() < 2 || contactPerson.length() > 100) {
            errors.add("Contact person must be 2 to 100 characters.");
        }
        if (!contactNumber.matches("[0-9]{10}")) {
            errors.add("Contact number must be exactly 10 digits.");
        }

        int capacity = 0;
        try {
            capacity = Integer.parseInt(capacityText);
            if (capacity < 500 || capacity > 50000) {
                errors.add("Tanker capacity must be between 500 and 50000 litres.");
            }
        } catch (NumberFormatException e) {
            errors.add("Tanker capacity must be a number.");
        }

        if (!status.equals("Available") && !status.equals("Not Available")) {
            errors.add("Please choose a valid availability status.");
        }

        int id = 0;
        if (!idText.isEmpty()) {
            try {
                id = Integer.parseInt(idText);
            } catch (NumberFormatException e) {
                errors.add("Invalid supplier id.");
            }
        }

        // Put what the admin typed into a Supplier, so the form can show it again.
        Supplier s = new Supplier();
        s.setId(id);
        s.setSupplierName(name);
        s.setContactPerson(contactPerson);
        s.setContactNumber(contactNumber);
        s.setTankerCapacity(capacity);
        s.setAvailabilityStatus(status.isEmpty() ? "Available" : status);

        if (!errors.isEmpty()) {
            showPage(req, resp, s, errors);
            return;
        }

        try {
            if (id == 0) {
                supplierDAO.addSupplier(s);
                resp.sendRedirect(req.getContextPath() + "/admin/suppliers?msg=added");
            } else {
                boolean changed = supplierDAO.updateSupplier(s);
                resp.sendRedirect(req.getContextPath() + "/admin/suppliers?msg="
                                  + (changed ? "updated" : "notfound"));
            }
        } catch (SQLException e) {
            getServletContext().log("Could not save supplier", e);
            resp.sendRedirect(req.getContextPath() + "/admin/suppliers?msg=error");
        }
    }

    // Loads the list and shows the page.
    private void showPage(HttpServletRequest req, HttpServletResponse resp,
                          Supplier form, List<String> errors)
            throws ServletException, IOException {

        try {
            req.setAttribute("suppliers", supplierDAO.getAllSuppliers());
        } catch (SQLException e) {
            getServletContext().log("Could not load suppliers", e);
            req.setAttribute("error", "Suppliers could not be loaded right now.");
        }

        req.setAttribute("form", form);
        req.setAttribute("errors", errors);
        req.getRequestDispatcher("/WEB-INF/views/adminSuppliers.jsp").forward(req, resp);
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }
}