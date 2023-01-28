package com.ersv2.utils;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;




public class JwtKey{

	public static SecretKey generateKey() throws NoSuchAlgorithmException {

		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(128);
		SecretKey secret = generator.generateKey();
		return secret;
	}

}
