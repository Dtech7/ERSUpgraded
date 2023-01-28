package com.ersv2.SecurityConfig;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ersv2.models.UserDetailer;
import com.ersv2.services.UserDetailerService;
import com.ersv2.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserDetailerService uDet;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String header = request.getHeader("Auth");
		String username = null;
		String jwt = null;
		
		if(header != null && header.startsWith("Token ")) {
			jwt = header.substring(6);
			username = jwtUtil.extractUserName(jwt);
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetailer userDet = uDet.loadUserByUsername(username);
			
			if(jwtUtil.validateToken(jwt, userDet)) {
				UsernamePasswordAuthenticationToken unpt = new UsernamePasswordAuthenticationToken(userDet, userDet.getRoles());
				unpt.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(unpt);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
