package utils;


import org.junit.jupiter.api.Assertions;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Encryption {
    private static final byte[] test = new byte[] {-13, -10, -126, 70, -28, 121, -52, -107, 60, -94, 65, 72, -128, -124, 18, -94, 50, 9, 104, 35, -10, 12, -15, -45, -18, -74, -95, 73, 9, -66, 52, -22};
    private static final SecretKeySpec superSecretKeyOfDoomAndGloom = new SecretKeySpec(test, "AES");
    private static final byte[] iV = new byte[] {29, -15, -68, 59, -65, -27, 110, -43, -98, 94, -103, -67, 112, -120, 127, 92};


    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        /*
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
         */
        return superSecretKeyOfDoomAndGloom;
    }

    public static IvParameterSpec generateIv() {
        /*
        byte[] iv = new byte[16];

        new SecureRandom().nextBytes(iv);
        System.out.println(new IvParameterSpec(iv));
         */
        return new IvParameterSpec(iV);
    }

    public static String encrypt(String algorithm, String input, SecretKey key,IvParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        System.out.println(algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    public static String decrypt(String algorithm, String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    public static String encryptString(String input)
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        SecretKey key = Encryption.generateKey(256);
        System.out.println(key);
        IvParameterSpec ivParameterSpec = Encryption.generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";
        String cipherText = Encryption.encrypt(algorithm, input, key, ivParameterSpec);
        return cipherText.toString();
        //String plainText = Encryption.decrypt(algorithm, cipherText, key, ivParameterSpec);
        //Assertions.assertEquals(input, plainText);
    }

    public static String decryptString(String cipherString) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        SecretKey key = Encryption.generateKey(256);
        System.out.println(key);
        IvParameterSpec ivParameterSpec = Encryption.generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";
        String plainText = Encryption.decrypt(algorithm, cipherString, key, ivParameterSpec);
        return plainText;//.toString();

        //Assertions.assertEquals(input, plainText);
    }
}
