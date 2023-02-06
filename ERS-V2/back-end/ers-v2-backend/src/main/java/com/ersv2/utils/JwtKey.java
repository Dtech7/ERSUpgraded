package com.ersv2.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;




public class JwtKey{

	public static KeyPair generateKeyPair() throws Exception{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(1024);
		KeyPair kp = kpg.genKeyPair();
		return kp;
	}
}
