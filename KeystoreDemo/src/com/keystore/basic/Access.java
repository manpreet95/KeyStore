/*
 * Access the keys from keystore
 */
package com.keystore.basic;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.util.Base64;

public class Access {

	public static void main(String[] args) throws Exception {
		
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

		  Key key = ks.getKey("mykey", password);

		  System.out.println("-----BEGIN PRIVATE KEY-----");
		  System.out.println(Base64.getEncoder().encode(key.getEncoded()));
		  System.out.println("-----END PRIVATE KEY-----");
		  
	}

}
