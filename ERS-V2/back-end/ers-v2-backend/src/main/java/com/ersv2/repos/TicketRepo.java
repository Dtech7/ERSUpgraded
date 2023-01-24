package com.ersv2.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ersv2.models.Ticket;
import com.ersv2.models.TicketStatus;
import com.ersv2.models.TicketType;
import com.ersv2.models.User;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {

	Ticket getTicketById(Long ticketId);
	List<Ticket> getTicketByCreator(User u);
	List<Ticket> getTicketByResolver(User u);
	List<Ticket> getTicketByStatus(TicketStatus s);
	List<Ticket> getTicketByType(TicketType t);
	
}
