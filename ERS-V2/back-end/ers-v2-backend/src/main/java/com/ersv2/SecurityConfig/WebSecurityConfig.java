package com.ersv2.SecurityConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.ersv2.services.UserDetailerService;



@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfiguration{

	@Autowired
	private UserDetailerService userDS;
	
	@Autowired
	private RequestFilter reqFilter;
	
	//creating base public request matchers
	private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
		    new AntPathRequestMatcher("/"), new AntPathRequestMatcher("/users/register"),
		    new AntPathRequestMatcher("/users/login"), new AntPathRequestMatcher("/users/forgot"));
	//will protect other routes  but ignore the ones defined in PUBLIC_URLS
	private static final RequestMatcher PROTECTED_USER_URLS = new OrRequestMatcher(new AntPathRequestMatcher("/tickets/create"), 
			new AntPathRequestMatcher("/tickets/user"));
	
	private static final RequestMatcher PROTECTED_MAN_URLS = new OrRequestMatcher(new AntPathRequestMatcher("/tickets/resolve"), 
			new AntPathRequestMatcher("/tickets/{status}"));

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDS)
			.passwordEncoder(getPasswordEncoder());
	}
	
	
	protected void configure(HttpSecurity http) throws Exception {
        http
        	.sessionManagement()
        	.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http
        	.authorizeHttpRequests()
        	.requestMatchers(PUBLIC_URLS).permitAll()
        	.requestMatchers(PROTECTED_USER_URLS).hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")
        	.requestMatchers(PROTECTED_MAN_URLS).hasAnyRole("MANAGER", "ADMIN");
        
        http.addFilterBefore(reqFilter, UsernamePasswordAuthenticationFilter.class);
        
        http
        .csrf().disable()
        .formLogin().disable()
        .logout().disable();
    }
	
	
	@Bean
    public PasswordEncoder getPasswordEncoder() {
        return	PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //NoOpPasswordEncoder.getInstance()
    }
}
