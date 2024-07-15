package com.bciexercise.exercise.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class EncriptDecrypt {

	private static final String ALGORITHM = "AES";
	private static SecretKey secretKey;
	
    public static void generateSecretKey() throws Exception {
    	if(secretKey == null) {
			KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
	        keyGen.init(128);
	        secretKey = keyGen.generateKey();
    	}
        
    }
    
    public static String encrypt(String data) throws Exception {
    	generateSecretKey();
    	
    	Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    
    public static String decrypt(String encryptedData) throws Exception {
        generateSecretKey();

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }
	
}
