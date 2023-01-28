package com.ersv2.controllers;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ersv2.models.Address;
import com.ersv2.models.User;
import com.ersv2.models.UserDetailer;
import com.ersv2.services.UserService;
import com.ersv2.utils.JwtUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
	
	private UserService uServ;
	private JwtUtil jwt;
	private AuthenticationManager auth;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody LinkedHashMap<String, String> body){
		String firstName = body.get("firstName");
		String lastName = body.get("lastName");
		String email = body.get("email");
		String password = body.get("password");
		String phoneNumber = body.get("phoneNumber");
		
		User reg = new User(firstName, lastName, email, password, phoneNumber);
		uServ.registerUser(reg);
		return new ResponseEntity<>("User has been successfully registered.", HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody LinkedHashMap<String, Object> body){
		String firstName = (String)body.get("firstName");
		String lastName = (String)body.get("lastName");
		String phoneNumber = (String)body.get("phoneNumber");
		Address add = null;
		if(body.get("address")!= null) {
			add = (Address) body.get("address");
		}
		
		User u = new User();
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setPhoneNumber(phoneNumber);
		u.setAddress(add);
		uServ.updateUser(u);
		
		return new ResponseEntity<>("User has been successfully updated.", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LinkedHashMap<String, String> body){
		String email = body.get("email");
		String password = body.get("password");
		
		User u = uServ.loginUser(email, password);
		auth.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		
		UserDetailer uDet = new UserDetailer(u);
		String uToken = jwt.generateToken(uDet);

		return new ResponseEntity<>(uToken, HttpStatus.OK);
	}
	
	@PutMapping("/logout")
	public ResponseEntity<String> logout(@RequestBody LinkedHashMap<String, String> body) {
		
		@SuppressWarnings("unused")
		String email = body.get("email");
		/*
		 *When spring security is implemented, this will invalidate the session 
		 */
		//uServ.logout(email);
		return new ResponseEntity<>("Logged out Successfully", HttpStatus.OK);
	}

	@PutMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestBody LinkedHashMap<String, String> body) {
	  String email = body.get("email");
	  String oldPass = body.get("oldPass");
	  String newPass = body.get("newPass");
	  uServ.resetPassword(email, oldPass, newPass);
	  return new ResponseEntity<>("Password successfully reset.", HttpStatus.OK);
	}

	@PutMapping("/update-email")
	public ResponseEntity<String> updateEmail(@RequestBody LinkedHashMap<String, String> body) {
		String oldEmail = body.get("oldEmail");
		String newEmail = body.get("newEmail");
		uServ.updateEmail(newEmail, oldEmail);
		
		return new ResponseEntity<>("Email successfully updated.", HttpStatus.OK);
	}
}
