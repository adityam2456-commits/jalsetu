package com.jalsetu.dao;

import com.jalsetu.model.Supplier;
import com.jalsetu.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    private static final String SELECT_COLUMNS =
            "SELECT id, supplier_name, contact_person, contact_number, "
          + "tanker_capacity, availability_status FROM supplier ";

    // Saves a new supplier.
    public void addSupplier(Supplier s) throws SQLException {
        String sql = "INSERT INTO supplier "
                   + "(supplier_name, contact_person, contact_number, tanker_capacity, availability_status) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getSupplierName());
            ps.setString(2, s.getContactPerson());
            ps.setString(3, s.getContactNumber());
            ps.setInt(4, s.getTankerCapacity());
            ps.setString(5, s.getAvailabilityStatus());
            ps.executeUpdate();
        }
    }

    // Changes the details of an existing supplier.
    public boolean updateSupplier(Supplier s) throws SQLException {
        String sql = "UPDATE supplier SET supplier_name = ?, contact_person = ?, "
                   + "contact_number = ?, tanker_capacity = ?, availability_status = ? "
                   + "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getSupplierName());
            ps.setString(2, s.getContactPerson());
            ps.setString(3, s.getContactNumber());
            ps.setInt(4, s.getTankerCapacity());
            ps.setString(5, s.getAvailabilityStatus());
            ps.setInt(6, s.getId());
            return ps.executeUpdate() == 1;
        }
    }

    // Returns every supplier.
    public List<Supplier> getAllSuppliers() throws SQLException {
        return runQuery(SELECT_COLUMNS + "ORDER BY supplier_name", null);
    }

    // Returns only suppliers whose status is 'Available'. Used when dispatching.
    public List<Supplier> getAvailableSuppliers() throws SQLException {
        return runQuery(SELECT_COLUMNS + "WHERE availability_status = ? ORDER BY tanker_capacity DESC",
                        "Available");
    }

    // Returns one supplier, or null if the id does not exist.
    public Supplier getSupplierById(int id) throws SQLException {
        List<Supplier> list = runQuery(SELECT_COLUMNS + "WHERE id = ?", String.valueOf(id));
        return list.isEmpty() ? null : list.get(0);
    }

    // Runs a SELECT with at most one text parameter and builds the list.
    private List<Supplier> runQuery(String sql, String parameter) throws SQLException {
        List<Supplier> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (parameter != null) {
                ps.setString(1, parameter);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Supplier s = new Supplier();
                    s.setId(rs.getInt("id"));
                    s.setSupplierName(rs.getString("supplier_name"));
                    s.setContactPerson(rs.getString("contact_person"));
                    s.setContactNumber(rs.getString("contact_number"));
                    s.setTankerCapacity(rs.getInt("tanker_capacity"));
                    s.setAvailabilityStatus(rs.getString("availability_status"));
                    list.add(s);
                }
            }
        }
        return list;
    }
}