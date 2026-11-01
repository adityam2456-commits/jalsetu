package com.jalsetu.util;

import com.jalsetu.dao.AdminDAO;

import java.io.Console;
import java.sql.SQLException;
import java.util.Scanner;

public class CreateAdmin {

    public static void main(String[] args) {
        String username;
        String password;

        Console console = System.console();
        if (console != null) {
            username = console.readLine("Admin username: ");
            char[] chars = console.readPassword("Admin password (min 8 characters): ");
            password = new String(chars);
        } else {
            // Fallback: the password will be visible while typing.
            Scanner sc = new Scanner(System.in);
            System.out.print("Admin username: ");
            username = sc.nextLine();
            System.out.print("Admin password (min 8 characters): ");
            password = sc.nextLine();
        }

        username = username.trim();

        if (username.length() < 3 || username.length() > 50) {
            System.out.println("Username must be 3 to 50 characters.");
            return;
        }
        if (password.length() < 8) {
            System.out.println("Password must be at least 8 characters.");
            return;
        }

        try {
            String hash = PasswordUtil.hashPassword(password);
            new AdminDAO().addAdmin(username, hash);
            System.out.println("Admin '" + username + "' created successfully.");
        } catch (SQLException e) {
            System.out.println("Could not create admin: " + e.getMessage());
        }
    }
}