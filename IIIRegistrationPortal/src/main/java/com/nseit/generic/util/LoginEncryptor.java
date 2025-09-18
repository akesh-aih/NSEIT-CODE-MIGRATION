package com.nseit.generic.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginEncryptor {

    private static final Logger logger = LogManager.getLogger(LoginEncryptor.class);

    private static final String PASS_PHRASE = "Pas5pr@se";
    private static final String SALT_VALUE = "s@1tValue";
    private static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int PASSWORD_ITERATIONS = 2;
    private static final String INIT_VECTOR = "@1B2c3D4e5F6g7H8";
    private static final int KEY_SIZE = 256;

    public static String encrypt(String plainText) {
        try {
            logger.debug("Plain text to encrypt: {}", plainText);
            byte[] initVectorBytes = INIT_VECTOR.getBytes(StandardCharsets.US_ASCII);
            byte[] saltValueBytes = SALT_VALUE.getBytes(StandardCharsets.US_ASCII);
            byte[] plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(HASH_ALGORITHM);
            KeySpec spec = new PBEKeySpec(PASS_PHRASE.toCharArray(), saltValueBytes, PASSWORD_ITERATIONS, KEY_SIZE);
            byte[] keyBytes = factory.generateSecret(spec).getEncoded();

            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(initVectorBytes));

            byte[] cipherTextBytes = cipher.doFinal(plainTextBytes);

            String cipherText = Base64.getEncoder().encodeToString(cipherTextBytes);
            logger.debug("Encrypted (Base64) text: {}", cipherText);
            return cipherText;
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }

    public static String decrypt(String cipherText) {
        try {
            byte[] initVectorBytes = INIT_VECTOR.getBytes(StandardCharsets.US_ASCII);
            byte[] saltValueBytes = SALT_VALUE.getBytes(StandardCharsets.US_ASCII);
            byte[] cipherTextBytes = Base64.getDecoder().decode(cipherText);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(HASH_ALGORITHM);
            KeySpec spec = new PBEKeySpec(PASS_PHRASE.toCharArray(), saltValueBytes, PASSWORD_ITERATIONS, KEY_SIZE);
            byte[] keyBytes = factory.generateSecret(spec).getEncoded();

            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(initVectorBytes));

            byte[] plainTextBytes = cipher.doFinal(cipherTextBytes);

            return new String(plainTextBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting password", e);
        }
    }
}
