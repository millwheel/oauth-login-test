package com.example.loginback.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordEncoder {

    public static String encodePasswordBySHA256(String password) throws NoSuchAlgorithmException {
        byte[] salt = generateSalt();
        String encodedPassword = encodePasswordWithSalt(password, salt);
        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        return saltBase64 + ":" + encodedPassword;
    }

    public static boolean verifyPassword(String inputPassword, String storedPassword) throws NoSuchAlgorithmException {
        String[] parts = storedPassword.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        String storedHashedPassword = parts[1];

        String inputHashedPassword = encodePasswordWithSalt(inputPassword, salt);
        return storedHashedPassword.equals(inputHashedPassword);
    }

    private static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private static String encodePasswordWithSalt(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
}
