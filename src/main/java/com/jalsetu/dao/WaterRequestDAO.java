package com.jalsetu.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jalsetu.model.WaterRequest;
import com.jalsetu.util.DBConnection;

public class WaterRequestDAO {

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

    // Returns all requests, newest first, together with the society name.
    public List<WaterRequest> getAllRequests() throws SQLException {
        List<WaterRequest> list = new ArrayList<>();
        String sql = "SELECT r.id, r.society_id, s.society_name, r.required_quantity, "
                   + "r.preferred_date, r.urgency, r.remarks, r.status, r.created_at "
                   + "FROM water_request r "
                   + "JOIN society s ON r.society_id = s.id "
                   + "ORDER BY r.created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
        return list;
    }
}