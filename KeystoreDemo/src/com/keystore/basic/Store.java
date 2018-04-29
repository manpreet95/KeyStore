/*
 * Store the keys into keystore
 */
package com.keystore.basic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyStore;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Store {

	public static void main(String[] args) throws Exception {
		
		System.out.println("Execution Started");
		
		KeyStore ks = KeyStore.getInstance("JCEKS");

		char[] password = "123456789".toCharArray();

		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream("D:\\Work\\Keystore\\keystore.jks");
			ks.load(fis, password);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		
		byte[] yourBytes = new BigInteger("5A5A5A5A5A5A5A5A", 16).toByteArray();

		SecretKey mySecretKey = new SecretKeySpec(yourBytes, 0, yourBytes.length, "DES");

		KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(mySecretKey);

		ks.setEntry("test", skEntry, new KeyStore.PasswordProtection(password));

		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream("D:\\Work\\Keystore\\keystore.jks");
			ks.store(fos, password);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
		
		System.out.println("Execution completed");
	}
}