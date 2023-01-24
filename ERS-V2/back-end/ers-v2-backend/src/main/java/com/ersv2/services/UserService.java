package com.ersv2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ersv2.exceptions.AEException;
import com.ersv2.exceptions.ICException;
import com.ersv2.models.Address;
import com.ersv2.models.User;
import com.ersv2.repos.AddressRepo;
import com.ersv2.repos.UserRepo;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
	
	private UserRepo uRepo;
	private AddressRepo aRepo;
	
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
		if(u.getAddress() != null) {updateAddress(u.getAddress(), oUser.getAddress().getId());}
		return uRepo.save(oUser);
	}
	
	public void updateAddress(Address newAdd, Long addId) {
		Address oAdd = aRepo.getReferenceById(addId);
		oAdd.setStreet(newAdd.getStreet());
		oAdd.setAptNum(newAdd.getAptNum());
		oAdd.setCity(newAdd.getCity());
		oAdd.setState(newAdd.getState());
		oAdd.setZip(newAdd.getZip());
		
		aRepo.save(oAdd);
	}
	
	public User updateEmail(String newEmail, String oldEmail) {
		User oUser = uRepo.getByEmail(oldEmail).get();
		/*
		 * When Spring mail is implemented send confirmation message to both emails
		 * Extra: ask reason for changing email
		 * if reason is unable to access old email or old email hacked then donot send confirmation to old email
		 * if reason is simply updating send confirmation
		 */
		oUser.setEmail(newEmail);
		return uRepo.save(oUser);
	}
	
	public User resetPassword(String email, String oldPassword, String newPassword) {
		User oUser = uRepo.getByEmail(email).get();
		if(oUser.getPassword().equals(oldPassword)) {
			oUser.setPassword(newPassword);
			return uRepo.save(oUser);
		}else {
			/* 
			 * When Spring mail is implemented send send email notication
			 */
			throw new ICException();
		}
	}
	
	public void logout(User u) {
		/*
		 * clear sessins and others
		 */
	}
}
