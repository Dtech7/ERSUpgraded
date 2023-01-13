package user;

import java.text.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Ticket {
	
	private User createdBy;
	private String email;
	private double amount;
	private String info;
	private int stat;
	private String status;
	private int ticketId;
	

	
	//Constructors 
	public Ticket() {}
	
	public Ticket(User cBy, String info, double amnt) {
		this(cBy, 1, info, amnt);
	}
	


	public Ticket(User cBy, int status, String info, double amnt) {
		this.createdBy = cBy;
		this.stat = status;
		this.info = info;
		this.amount = amnt;	
	}
	
	public Ticket(User cBy, int status, String info, double amnt, int tId) {
		this.createdBy = cBy;
		this.stat = status;
		this.info = info;
		this.amount = amnt;	
		this.ticketId = tId;
	}
	
	//getters & setters
	@JsonIgnore
	public int getStat() {
		return stat;
	}

	public void setStat(int status) {
		this.stat = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	@JsonIgnore
	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
		this.email = createdBy.getEmail();
	}
	
	
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	//Used to turn Status into a string
	public String getStatus () {
		String s;
		if(stat == 3)
			s = "DENIED";
		else if(stat == 2)
			s = "APPROVED";
		else
			s = "PENDING";
		
		return s;
	}
	public void setStatus() {
		String s;
		if(stat == 3)
			s = "DENIED";
		else if(stat == 2)
			s = "APPROVED";
		else
			s = "PENDING";
		this.status = s;
	}
	
	public int statToInt(String stat) {
		String s = stat.toLowerCase();
		if(s.equals("denied"))
			return 3;
		else if(s.equals("approved"))
			return 2;
		else
			return 1;
	}
	
	//toString
	@Override
	public String toString() {
		NumberFormat fmt = NumberFormat.getCurrencyInstance();
		String a = fmt.format(amount);
		//String stat = statToString(status);
		
		return "Ticket [Status: " + status + " || Info: " + info 
				+ " || Amount: " + a + " Created By: "
				+ email + "]";
	}
	
}
