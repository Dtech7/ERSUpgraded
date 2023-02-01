package com.ersv2.SecurityConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ersv2.services.UserDetailerService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

	@Autowired
	private UserDetailerService userDS;
	
	@Autowired
	private RequestFilter reqFilter;
	

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDS)
			.passwordEncoder(getPasswordEncoder());
	}
	
	@Bean
	public WebSecurityCustomizer webSC() {
		return (web)-> web.ignoring()
				.requestMatchers("/css/**", "/js/**", 
						"/img/**", "/lib/**", "/favicon.ico",
						"/users/login", "/users/register");
	   } 
	 @Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 
		 http
		 .csrf().disable()
		 .authorizeHttpRequests()
		 .requestMatchers("/","/users/register").permitAll()
		 .requestMatchers("/users/login", "/users/logout").permitAll()
		 .requestMatchers("tickets/create", "tickets/user")
		 .hasAnyRole("EMPLOYEE", "MANAGER","ADMIN")
		 .requestMatchers("/tickets/resolve", "/tickets/{status}" )
		 .hasAnyRole("MANAGER","ADMIN")
		 .and()
		 .authenticationManager(authenticationManager(http))
		 .formLogin().disable()
		 .sessionManagement()
		 .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 http.addFilterBefore(reqFilter, UsernamePasswordAuthenticationFilter.class);
		 return http.build();
		 
	 }
	
	
	@Bean
    public PasswordEncoder getPasswordEncoder() {
        return	PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
	
	
	@Bean 
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{ 
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDS)
				.passwordEncoder(getPasswordEncoder())
				.and().build();
	}
	 
}
