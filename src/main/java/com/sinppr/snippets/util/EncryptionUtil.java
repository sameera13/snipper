package com.sinppr.snippets.util;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtil {
    private static final String ALGORITHM = "AES";

    public static String encrypt(String plainText, String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during encryption", e);
        }
    }

    public static String decrypt(String cipherText, String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during decryption", e);
        }
    }
}
