package com.jalsetu.dao;

import com.jalsetu.model.WaterRequest;
import com.jalsetu.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WaterRequestDAO {

    // The query used to read requests together with the society name.
    private static final String SELECT_WITH_SOCIETY =
            "SELECT r.id, r.society_id, s.society_name, r.required_quantity, "
          + "r.preferred_date, r.urgency, r.remarks, r.status, r.created_at "
          + "FROM water_request r "
          + "JOIN society s ON r.society_id = s.id ";

    // Saves a new water request. The status starts as 'Pending'.
    public void addRequest(WaterRequest request) throws SQLException {
        String sql = "INSERT INTO water_request "
                   + "(society_id, required_quantity, preferred_date, urgency, remarks, status) "
                   + "VALUES (?, ?, ?, ?, ?, 'Pending')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, request.getSocietyId());
            ps.setInt(2, request.getRequiredQuantity());
            ps.setDate(3, Date.valueOf(request.getPreferredDate()));
            ps.setString(4, request.getUrgency());
            ps.setString(5, request.getRemarks());
            ps.executeUpdate();
        }
    }

    // Returns all requests, newest first.
    public List<WaterRequest> getAllRequests() throws SQLException {
        return runQuery(SELECT_WITH_SOCIETY + "ORDER BY r.created_at DESC", null);
    }

    // Returns only the requests that have the given status.
    public List<WaterRequest> getRequestsByStatus(String status) throws SQLException {
        return runQuery(SELECT_WITH_SOCIETY + "WHERE r.status = ? ORDER BY r.created_at DESC", status);
    }

    // Returns one request, or null if the id does not exist.
    public WaterRequest getRequestById(int id) throws SQLException {
        List<WaterRequest> list = runQuery(SELECT_WITH_SOCIETY + "WHERE r.id = ?", String.valueOf(id));
        return list.isEmpty() ? null : list.get(0);
    }

    // Changes the status ONLY if the request currently has the expected status.
    // Returns true if a row was changed, false if not.
    public boolean updateStatus(int id, String expectedStatus, String newStatus) throws SQLException {
        String sql = "UPDATE water_request SET status = ? WHERE id = ? AND status = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, id);
            ps.setString(3, expectedStatus);
            return ps.executeUpdate() == 1;
        }
    }

    // Runs a SELECT with at most one text parameter and builds the list.
    private List<WaterRequest> runQuery(String sql, String parameter) throws SQLException {
        List<WaterRequest> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (parameter != null) {
                ps.setString(1, parameter);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    WaterRequest r = new WaterRequest();
                    r.setId(rs.getInt("id"));
                    r.setSocietyId(rs.getInt("society_id"));
                    r.setSocietyName(rs.getString("society_name"));
                    r.setRequiredQuantity(rs.getInt("required_quantity"));
                    r.setPreferredDate(rs.getDate("preferred_date").toLocalDate());
                    r.setUrgency(rs.getString("urgency"));
                    r.setRemarks(rs.getString("remarks"));
                    r.setStatus(rs.getString("status"));
                    r.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(r);
                }
            }
        }
        return list;
    }
}