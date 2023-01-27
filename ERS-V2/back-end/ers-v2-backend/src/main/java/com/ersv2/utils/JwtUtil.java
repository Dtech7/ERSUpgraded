package com.ersv2.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ersv2.models.UserDetailer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	
	//obviously going to be changed
	private JwtKey SECRET_KEY;
	
	
	public String extractUsername(String token) {
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
	
	@SuppressWarnings("unused")
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public String generateToken(UserDetailer uDet) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, uDet.getUsername());
	}

	private String createToken(Map<String, Object> claims, String sub) {
		return Jwts.builder().setClaims(claims).setSubject(sub).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SECRET_KEY,SignatureAlgorithm.HS256).compact();
	}
	
	
}
