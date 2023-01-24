package com.ersv2.exceptions;

import com.ersv2.models.Ticket;
import com.ersv2.models.User;

public class AEException extends RuntimeException {

	/**
	 * User Already Exist Exception
	 */
	private static final long serialVersionUID = 1L;
	
	public AEException(User u) {
		super("Emial: "+ u.getEmail() + " already in our system.");
	}
	
	public AEException(Ticket t) {
		super("Ticket id: " + t.getId()+ "has already in system.");
	}
}
