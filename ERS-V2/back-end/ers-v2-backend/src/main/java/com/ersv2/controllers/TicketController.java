package com.ersv2.controllers;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ersv2.models.Ticket;
import com.ersv2.models.TicketStatus;
import com.ersv2.services.TicketService;


import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/tickets")
@CrossOrigin("*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TicketController {
	
	private TicketService tServ;
	
	@PostMapping("/create")
	public ResponseEntity<String> createTicket(@RequestBody LinkedHashMap<String, Object> body){
		String email = (String) body.get("email");
		String type = (String) body.get("type");
		Double amount = (Double) body.get("amount");
		String memo = (String) body.get("memo");
	
		tServ.createTicket(email, type, amount, memo);
		return new ResponseEntity<>("Ticket created", HttpStatus.OK);
	}
	
	@PutMapping("/resolve")
	public ResponseEntity<String> resolveTicket(@RequestBody LinkedHashMap<String, Object> body){
		String email = (String) body.get("email");
		Long ticketId = (Long) body.get("ticketId");
		String newStatus = (String) body.get("newStatus");
		
		tServ.resolveTicket(email, ticketId, newStatus);
		return new ResponseEntity<>("Ticket resolved", HttpStatus.OK);
	}
	
	@GetMapping("/{status}")
	public List<Ticket> getTicketsByStatus(@PathVariable("status")TicketStatus status){
		return tServ.getTicketsByStatus(status);
	}
	
	@GetMapping("/user")
	public List<Ticket> getTicketsByCreator(@RequestBody LinkedHashMap<String, String> body){
		String email = body.get("email");
		return tServ.getTicketsByCreator(email);
	}
}
