package com.ersv2.utils;


import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import com.ersv2.models.UserDetailer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
	
	public JwtUtil() throws Exception{
		
	}
	private KeyPair KEY_PAIR = JwtKey.generateKeyPair();
	private RSAPublicKey pubKey = (RSAPublicKey) KEY_PAIR.getPublic();
	private RSAPrivateKey pvtKey = (RSAPrivateKey) KEY_PAIR.getPrivate();
	Algorithm algo = Algorithm.RSA256(pubKey, pvtKey);
	
	public String extractUserName(String token) {
		Claims c = extractAllClaims(token);
		return c.getSubject();
	}
	
	public Date extractExpiration(String token) {
		Claims c = extractAllClaims(token);
		return c.getExpiration();
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().build().parseClaimsJws(token).getBody();
	}
	
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public String generateToken(UserDetailer uDet) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("Roles", uDet.getRoles());
		return createToken(claims, uDet.getUsername());
	}
	
	private String createToken(Map<String, Object> claims, String sub) {

		try{
			return JWT.create()
			.withIssuer("auth0")
			.withPayload(claims)
			.withSubject(sub)
			.withIssuedAt(new Date(System.currentTimeMillis()))
			.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
			.sign(algo);
			
		}catch(Exception e) {
			throw new JWTCreationException(sub, e);
		}
	}
	
	public Boolean validateToken(String token, UserDetailer uDet) {
		String uName = extractUserName(token);
		return(uName.equals(uDet.getUsername()) && !isTokenExpired(token));
	}
	
}
