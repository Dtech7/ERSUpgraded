package com.ersv2.models;


import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class UserDetailer implements UserDetails{
	
	private String firstName;
	private String lastName;
	private String userName;//this is email
	private String password;
	private String phoneNumber;
	private Boolean active;
	private String roles;
	
	public UserDetailer(User u) {
		this.userName = u.getEmail();
		this.password = u.getPassword();
		this.active = u.getActive();
		this.roles = u.getRole().toString();
		this.firstName = u.getFirstName();
		this.lastName = u.getLastName();
		this.phoneNumber = u.getPhoneNumber();
	} 
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

}
