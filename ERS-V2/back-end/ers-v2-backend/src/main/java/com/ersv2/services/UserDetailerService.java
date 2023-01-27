package com.ersv2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ersv2.exceptions.ICException;
import com.ersv2.models.User;
import com.ersv2.models.UserDetailer;
import com.ersv2.repos.UserRepo;

@Service
public class UserDetailerService implements UserDetailsService{

	@Autowired
    UserRepo uRepo;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws ICException {
		User u = uRepo.getByEmail(username).orElseThrow(ICException::new);
		return new UserDetailer(u);
	}

}
