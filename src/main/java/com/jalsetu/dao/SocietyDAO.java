package com.jalsetu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jalsetu.model.Society;
import com.jalsetu.util.DBConnection;

public class SocietyDAO {

    // Saves a new society and returns the id MySQL gave it.
    public int addSociety(Society society) throws SQLException {
        String sql = "INSERT INTO society (society_name, contact_person, contact_number, address) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, society.getSocietyName());
            ps.setString(2, society.getContactPerson());
            ps.setString(3, society.getContactNumber());
            ps.setString(4, society.getAddress());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return -1;
    }

    // Looks for a society with the same name and contact number.
    // Returns its id, or -1 if it does not exist yet.
    public int findSocietyId(String societyName, String contactNumber) throws SQLException {
        String sql = "SELECT id FROM society WHERE society_name = ? AND contact_number = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, societyName);
            ps.setString(2, contactNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }

    // Returns every society in the table.
    public List<Society> getAllSocieties() throws SQLException {
        List<Society> list = new ArrayList<>();
        String sql = "SELECT id, society_name, contact_person, contact_number, address "
                   + "FROM society ORDER BY society_name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Society s = new Society();
                s.setId(rs.getInt("id"));
                s.setSocietyName(rs.getString("society_name"));
                s.setContactPerson(rs.getString("contact_person"));
                s.setContactNumber(rs.getString("contact_number"));
                s.setAddress(rs.getString("address"));
                list.add(s);
            }
        }
        return list;
    }
}