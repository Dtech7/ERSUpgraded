package com.ersv2.utils;



import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.ersv2.models.UserDetailer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	public JwtUtil() throws NoSuchAlgorithmException{
		
	}
	//obviously going to be changed
	private SecretKey SECRET_KEY = JwtKey.generateKey() ;
	
	
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
		return Jwts.builder().setClaims(claims).setSubject(sub).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SECRET_KEY,SignatureAlgorithm.ES256).compact();
	}
	
	public Boolean validateToken(String token, UserDetailer uDet) {
		String uName = extractUserName(token);
		return(uName.equals(uDet.getUsername()) && !isTokenExpired(token));
	}
	
}
