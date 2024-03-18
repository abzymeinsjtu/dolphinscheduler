package org.apache.dolphinscheduler.api.utils;

import org.apache.dolphinscheduler.api.exceptions.ServiceException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class PaginationUtils {

    private static final String algorithm = "AES/CBC/PKCS5Padding";

    private static final IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[16]);

    public static String encodeNextToken(String uniqueId) {
        try {
            SecretKey key = generateKey(128);
            String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
            return encodedKey.substring(0, 22) + encrypt(algorithm, uniqueId, key, ivParameterSpec);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public static String decodeNextToken(String nextToken) {
        byte[] encodedKeyNew = Base64.getDecoder().decode(nextToken.substring(0, 22) + "==");
        SecretKey decryptKey = new SecretKeySpec(
                encodedKeyNew,
                0,
                encodedKeyNew.length,
                "AES");

        try {
            return decrypt(algorithm, nextToken.substring(22), decryptKey, ivParameterSpec);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        return keyGenerator.generateKey();
    }

    public static String encrypt(String algorithm, String input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());

        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    public static String decrypt(String algorithm, String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }
}
