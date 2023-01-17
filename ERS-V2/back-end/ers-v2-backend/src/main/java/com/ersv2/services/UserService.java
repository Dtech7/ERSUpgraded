package com.ersv2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ersv2.exceptions.AEException;
import com.ersv2.exceptions.ICException;
import com.ersv2.models.User;
import com.ersv2.repos.UserRepo;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
	
	private UserRepo uRepo;
	
	public User registerUser(User u) throws AEException{
		try {
			return uRepo.save(u);
		} catch(Exception e) {
			throw new AEException(u);
		}
	}
	
	public User loginUser(String email, String password) {
		User u = uRepo.getByEmail(email).orElseThrow(ICException::new);
		if(!u.getPassword().equals(password)) {
			throw new ICException();
		}
		return u;
	}
	
	public User updateUser(User u) {
		User oUser = uRepo.getByEmail(u.getEmail()).get();
		
		if(u.getFirstName() != null) {oUser.setFirstName(u.getFirstName());}
		if(u.getLastName() != null) {oUser.setLastName(u.getLastName());}
		if(u.getPhoneNumber() != null) {oUser.setPhoneNumber(u.getPhoneNumber());}
		
		
		return uRepo.save(oUser);
	}
}
