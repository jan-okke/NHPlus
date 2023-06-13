package utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class Hashing {

    /**
     * Generates a salt with the length of a given size.
     * @param size The length of the salt.
     * @return The generated salt.
     */
    public static String CreateSalt(int size) {
        SecureRandom random = new SecureRandom();

        byte[] salt = new byte[size];
        random.nextBytes(salt);
        return new String(salt);
    }

    /**
     * Calculates a hash for a given password and a given salt.
     * @param password The password to hash.
     * @param salt The salt for the password.
     * @return The hash value for password + salt.
     */
    public static String CalculateHash(String password, String salt) {
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();
        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, 10000, 256);
        Arrays.fill(passwordChars, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return new String(skf.generateSecret(spec).getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }
}
