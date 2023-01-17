package com.ersv2.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ersv2.models.Ticket;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Integer> {

}
