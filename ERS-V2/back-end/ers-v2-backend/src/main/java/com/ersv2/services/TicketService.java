package com.ersv2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ersv2.exceptions.AEException;
import com.ersv2.models.Ticket;
import com.ersv2.models.TicketStatus;
import com.ersv2.models.TicketType;
import com.ersv2.models.User;
import com.ersv2.repos.TicketRepo;
import com.ersv2.repos.UserRepo;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TicketService {
	
	private TicketRepo tRepo;
	private UserRepo uRepo;
	
	public Ticket createTicket(Ticket t) throws AEException {
		try {
			return tRepo.save(t);
		}  catch(Exception e) {
			throw new AEException(t);
		} 
	}
	
	public Ticket resolveTicket(String email, Long tId, TicketStatus newStatus) {
		User u = uRepo.getByEmail(email).get();
		Ticket t = tRepo.getReferenceById(tId);
		t.setResolver(u);
		t.setStatus(newStatus);
		
		return tRepo.save(t);
	}
	
	public Ticket resolveTicket(String email, Long tId, TicketStatus newStatus, String memo) {
		User u = uRepo.getByEmail(email).get();
		Ticket t = tRepo.getReferenceById(tId);
		t.setResolver(u);
		t.setStatus(newStatus);
		t.setMemo(memo);
		return tRepo.save(t);
	}
	
	public List<Ticket> getTicketsByCreator(String email){
		User u = uRepo.getByEmail(email).get();
		return tRepo.getTicketByCreator(u);
	}
	
	public List<Ticket> getTicketsByStatus(TicketStatus s){
		return tRepo.getTicketByStatus(s);
	}
	
	public List<Ticket> getTicketsByType(TicketType tt){
		return tRepo.getTicketByType(tt);
	}
}
