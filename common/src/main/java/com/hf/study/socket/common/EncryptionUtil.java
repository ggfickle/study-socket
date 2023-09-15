package com.hf.study.socket.common;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class EncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String CHARSET = "UTF-8";

    public static String encrypt(String cardNumber, String secretKey) throws Exception {
        Key key = generateKey(secretKey);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(cardNumber.getBytes(CHARSET));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedCardNumber, String secretKey) throws Exception {
        Key key = generateKey(secretKey);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedCardNumber);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, CHARSET);
    }

    private static Key generateKey(String secretKey) throws Exception {
        byte[] keyData = secretKey.getBytes(CHARSET);
        return new SecretKeySpec(keyData, ALGORITHM);
    }

    public static void main(String[] args) {
        try {
            String cardNumber = "340824199707240619"; // 用户的银行卡号
            String secretKey = "0H6iY6fH8TS79CmZOQEmehg54+H0dF7I"; // 配置的盐值

            // 加密
            String encryptedCardNumber = encrypt(cardNumber, secretKey);
            System.out.println("加密后的银行卡号：" + encryptedCardNumber);

            // 解密
            String decryptedCardNumber = decrypt(encryptedCardNumber, secretKey);
            System.out.println("解密后的银行卡号：" + decryptedCardNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
