/*
 * 1. Generate key pair
 * 2. Generate digital signature
 * 3. Verify the digital signature
 * 
 * */
package com.keystore.asymmetrickeyencrypt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;

public class Demo1 {
	public static void main(String args[]) throws Exception {
		
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		kpg.initialize(1024, random);

		//generating private-public keys
		KeyPair pair = kpg.generateKeyPair();
		PrivateKey priv = pair.getPrivate();
		PublicKey pub = pair.getPublic();
		
		System.out.println("Private Key: "+priv.getEncoded());
		System.out.println("Public Key: "+pub.getEncoded());
		
		System.out.println("***************************Encryption***************************");
		
		Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
		
		dsa.initSign(priv);
		//pass the message to generate sign
		dsa.update("Hello".getBytes());
		byte[] dsaSign = dsa.sign();	//digital signature
		
		System.out.println("Encrypted Hello:"+dsaSign);
//		dsa = null;
		
		System.out.println("***************************Decryption***************************");
		
//		dsa = Signature.getInstance("SHA1withDSA", "SUN");
		
		dsa.initVerify(pub);
		//pass the message to be verify sign
		dsa.update("Hello".getBytes());
		System.out.println("Verification success? "+dsa.verify(dsaSign));
	}
}
