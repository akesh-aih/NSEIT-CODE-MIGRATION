//package com.nseit.generic.util;
//
//import java.security.MessageDigest;
//import javax.crypto.Cipher;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import com.nseit.users.action.LoginAction;
//
//import javax.crypto.SecretKey;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import java.util.Base64;
//import java.nio.charset.StandardCharsets;
//import java.util.Arrays;
//
//public class LoginEncryptor {
//	private static final Logger logger = LogManager.getLogger(LoginEncryptor.class);
//
//    private static final String PASS_PHRASE = "Pas5pr@se";
//    private static final String SALT_VALUE = "s@1tValue";
//    private static final String HASH_ALGORITHM = "SHA1";
//    private static final int PASSWORD_ITERATIONS = 2;
//    private static final String INIT_VECTOR = "@1B2c3D4e5F6g7H8";
//    private static final int KEY_SIZE = 256;
//
//    // Custom key derivation function to mimic C# PasswordDeriveBytes (PBKDF1)
//    private static byte[] deriveKey(String passPhrase, byte[] saltValue, String hashAlgorithm, int passwordIterations, int keySize) throws Exception {
//        MessageDigest digest = MessageDigest.getInstance(hashAlgorithm);
//        byte[] passwordBytes = passPhrase.getBytes(StandardCharsets.UTF_8);
//        
//        byte[] key = new byte[keySize / 8];
//        int currentDerivedBytes = 0;
//        int block = 0;
//
//        while (currentDerivedBytes < (keySize / 8)) {
//            // Concatenate password, salt, and block number (as a byte)
//            // PasswordDeriveBytes appends a single byte counter for each block
//            byte[] data = new byte[passwordBytes.length + saltValue.length + 1];
//            System.arraycopy(passwordBytes, 0, data, 0, passwordBytes.length);
//            System.arraycopy(saltValue, 0, data, passwordBytes.length, saltValue.length);
//            data[data.length - 1] = (byte) (block + 1); // Changed to start from 1
//
//            // Hash repeatedly
//            byte[] hashed = digest.digest(data);
//            for (int i = 1; i < passwordIterations; i++) {
//                hashed = digest.digest(hashed);
//            }
//
//            // Copy derived bytes to the key array
//            int bytesToCopy = Math.min(hashed.length, key.length - currentDerivedBytes);
//            System.arraycopy(hashed, 0, key, currentDerivedBytes, bytesToCopy);
//            currentDerivedBytes += bytesToCopy;
//            block++;
//        }
//        logger.debug("Derived Key (Base64): {}", Base64.getEncoder().encodeToString(key));
//        return key;
//    }
//
//    public static String encrypt(String plainText) throws Exception {
//        byte[] initVectorBytes = INIT_VECTOR.getBytes(StandardCharsets.US_ASCII);
//        byte[] saltValueBytes = SALT_VALUE.getBytes(StandardCharsets.US_ASCII);
//        byte[] plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);
//
//        // Use the provided C# derived key directly for testing
//        byte[] keyBytes = Base64.getDecoder().decode("fMy7Bz78Zh57zHDMdnxgJxgq6u0xt0tudJpN6BP3CXk=");
//        SecretKey secret = new SecretKeySpec(keyBytes, "AES");
//
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(initVectorBytes));
//
//        byte[] encrypted = cipher.doFinal(plainTextBytes);
//
//        return Base64.getEncoder().encodeToString(encrypted);
//    }
//
//    public static String decrypt(String cipherText) throws Exception {
//        byte[] initVectorBytes = INIT_VECTOR.getBytes(StandardCharsets.US_ASCII);
//        byte[] saltValueBytes = SALT_VALUE.getBytes(StandardCharsets.US_ASCII);
//        byte[] cipherTextBytes = Base64.getDecoder().decode(cipherText);
//
//        // Use the provided C# derived key directly for testing
//        byte[] keyBytes = Base64.getDecoder().decode("fMy7Bz78Zh57zHDMdnxgJxgq6u0xt0tudJpN6BP3CXk=");
//        SecretKey secret = new SecretKeySpec(keyBytes, "AES");
//
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(initVectorBytes));
//
//        byte[] decrypted = cipher.doFinal(cipherTextBytes);
//
//        return new String(decrypted, StandardCharsets.UTF_8);
//    }
//
//    
//}