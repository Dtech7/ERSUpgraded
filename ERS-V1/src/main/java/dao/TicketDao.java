package dao;

import java.sql.SQLException;
import java.util.List;

//import exceptions.UnauthorizedException;
import user.Ticket;
import user.User;

public interface TicketDao {
	
	public void addTicket(Ticket t);
	public Ticket getTicketById(int i) throws SQLException;
	public void adjustTicket(Ticket t);
	public void changeTicketStatus(User u, int stat, int tickId)throws SQLException;
	public List<Ticket> getAllTickets(User u);
}
