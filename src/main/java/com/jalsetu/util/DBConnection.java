package com.jalsetu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Read settings from environment variables.
    // If a variable is not set, the value after the comma is used.
    private static final String URL = getSetting("DB_URL",
            "jdbc:mysql://localhost:3306/jalsetu");
    private static final String USER = getSetting("DB_USER", "root");
    private static final String PASSWORD = getSetting("DB_PASSWORD", "");

    private static String getSetting(String name, String defaultValue) {
        String value = System.getenv(name);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        return value;
    }

    // Opens a new connection to the database.
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL driver not found", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}