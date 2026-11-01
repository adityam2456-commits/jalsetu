package com.jalsetu.dao;

import com.jalsetu.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DashboardDAO {

    // Returns all dashboard numbers in one Map, for example stats.get("pending").
    public Map<String, Integer> getStats() throws SQLException {
        Map<String, Integer> stats = new LinkedHashMap<>();

        try (Connection con = DBConnection.getConnection()) {
            stats.put("societies", count(con, "SELECT COUNT(*) FROM society"));
            stats.put("suppliers", count(con, "SELECT COUNT(*) FROM supplier"));
            stats.put("requests",  count(con, "SELECT COUNT(*) FROM water_request"));
            stats.put("pending",   count(con, "SELECT COUNT(*) FROM water_request WHERE status = 'Pending'"));
            stats.put("approved",  count(con, "SELECT COUNT(*) FROM water_request WHERE status = 'Approved'"));
            stats.put("completed", count(con, "SELECT COUNT(*) FROM water_request WHERE status = 'Completed'"));
            stats.put("rejected",  count(con, "SELECT COUNT(*) FROM water_request WHERE status = 'Rejected'"));
        }
        return stats;
    }

    // Runs one COUNT query and returns the number.
    private int count(Connection con, String sql) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}