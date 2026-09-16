package utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        //creates object with hashing
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        //convert password to bytes and apply hashing
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

        //convert the previous bytes into a big number
        BigInteger number = new BigInteger(1, hash);

        //convert the number to base 16
        StringBuilder hexString= new StringBuilder(number.toString(16));

        // add leading zeros until the hash is 64 characters
        while (hexString.length()<64){
            hexString.insert(0,"0");
        }
        return hexString.toString();

    }
}


