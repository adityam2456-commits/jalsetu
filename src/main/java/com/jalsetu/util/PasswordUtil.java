package com.jalsetu.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Turns a plain password into a hash. Used when creating an admin.
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // Checks a typed password against the hash stored in the database.
    public static boolean checkPassword(String plainPassword, String storedHash) {
        if (plainPassword == null || storedHash == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainPassword, storedHash);
        } catch (IllegalArgumentException e) {
            return false;   // the stored hash was not a valid BCrypt hash
        }
    }
}