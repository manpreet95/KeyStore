/*
 * fetch the key from keystore
 * encrypt the data
 * decrypt the data
 * 
 * */
package com.keystore.symmetrickeyencrypt;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

/**
 * This example program shows how AES encryption and decryption can be done in Java.
 * Please note that secret key and encrypted text is unreadable binary and hence 
 * in the following program we display it in hexadecimal format of the underlying bytes.
 * @author Jayson
 */
public class CustomEncryptDecrypt {
 
    /**
     * 1. Generate a plain text for encryption
     * 2. Get a secret key (printed in hexadecimal form). In actual use this must 
     * by encrypted and kept safe. The same key is required for decryption.
     * 3. 
     */
    public static void main(String[] args) throws Exception {
        String plainText = "Hello World";
        Key secKey = getSecretEncryptionKey();
        byte[] cipherText = encryptText(plainText, secKey);
        String decryptedText = decryptText(cipherText, secKey);
        
        System.out.println("Original Text:" + plainText);
        System.out.println("AES Key (Hex Form):"+bytesToHex(secKey.getEncoded()));
        System.out.println("Encrypted Text (Hex Form):"+bytesToHex(cipherText));
        System.out.println("Descrypted Text:"+decryptedText);
        
    }
    
    /**
     * gets the AES encryption key. In your actual programs, this should be safely
     * stored.
     * @return
     * @throws Exception 
     */
    public static Key getSecretEncryptionKey() throws Exception{
    	char[] password = "123456789".toCharArray();
		FileInputStream fis = null;
		KeyStore ks = KeyStore.getInstance("JCEKS");
		try {
			fis = new FileInputStream("D:\\Work\\Keystore\\keystore.jks");
			ks.load(fis, password);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}

		Key key = ks.getKey("test", password);

		System.out.println("-----BEGIN PRIVATE KEY-----");
		System.out.println(Base64.getEncoder().encode(key.getEncoded()));
		System.out.println("-----END PRIVATE KEY-----");
		return key;
    }
    
    /**
     * Encrypts plainText in AES using the secret key
     * @param plainText
     * @param secKey
     * @return
     * @throws Exception 
     */
    public static byte[] encryptText(String plainText,Key secKey) throws Exception{
		// AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("DES");
        
        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
        return byteCipherText;
    }
    
    /**
     * Decrypts encrypted byte array using the key used for encryption.
     * @param byteCipherText
     * @param secKey
     * @return
     * @throws Exception 
     */
    public static String decryptText(byte[] byteCipherText, Key secKey) throws Exception {
		// AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("DES");
        
        aesCipher.init(Cipher.DECRYPT_MODE, secKey);
        byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
        return new String(bytePlainText);
    }
    
    /**
     * Convert a binary byte array into readable hex form
     * @param hash
     * @return 
     */
    private static String  bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }
}