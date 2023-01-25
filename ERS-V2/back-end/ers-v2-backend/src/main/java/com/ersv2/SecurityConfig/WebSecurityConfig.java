package com.ersv2.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;



@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfiguration{
	
	@Autowired
	UserDetailsService userDetailsService;
	
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/**").permitAll();
        
        //.and().formLogin()
        //.requestMatchers("/admin").hasRole("ADMIN")
        //.requestMatchers("/user").hasAnyRole("ADMIN", "USER")
    }
	
	
	@Bean
    public PasswordEncoder getPasswordEncoder() {
        return	PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //NoOpPasswordEncoder.getInstance()
    }
}
