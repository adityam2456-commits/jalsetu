
package com.jalsetu.dao;

import com.jalsetu.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    // Saves a new admin. The password must already be hashed.
    public void addAdmin(String username, String passwordHash) throws SQLException {
        String sql = "INSERT INTO admin (username, password_hash) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.executeUpdate();
        }
    }

    // Returns the stored hash for a username, or null if there is no such admin.
    public String getPasswordHash(String username) throws SQLException {
        String sql = "SELECT password_hash FROM admin WHERE username = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password_hash");
                }
            }
        }
        return null;
    }
}