package com.ersv2.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
//import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tickets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", updatable = false)
	private User creator;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resolved_by", insertable = false)
	private User resolver;
	
	@Enumerated(EnumType.STRING)
	private TicketType type;
	
	@Enumerated(EnumType.STRING)
	private TicketStatus status;
	
	private String memo;
	
	@Column(name = "date_created", updatable = false)
	private LocalDate dateCreated;
	
	@Column(name = "date_resolved", insertable = false)
	private LocalDate dateResolved;
	
	@Column(nullable = false)
	private Double amount;
	
	public Ticket(User creator, String type, Double amount, String memo) {
		this.creator = creator;
		this.type = TicketType.valueOf(type.toUpperCase());
		this.status = TicketStatus.PENDING;
		this.amount = amount;
		this.memo = memo;
		this.dateCreated = LocalDate.now();
	}
}
