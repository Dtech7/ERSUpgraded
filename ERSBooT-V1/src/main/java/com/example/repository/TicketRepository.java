package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Employee;
import com.example.models.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer>{
	
	List<Ticket> getTicketsBySubmitter(Employee e);
	//List<Ticket> getTicketsBySubmitterAndType(Employee e, TicketType t);
	//List<Ticket> getTicketsBy
}
