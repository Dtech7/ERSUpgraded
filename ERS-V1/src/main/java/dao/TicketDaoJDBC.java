package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import utils.JdbcConnectUtil;
import java.util.ArrayList;
import java.util.List;

import exceptions.BadTicketException;
import exceptions.UnauthorizedException;
import user.Ticket;
import user.User;

public class TicketDaoJDBC implements TicketDao {
	
	private JdbcConnectUtil conUtil = JdbcConnectUtil.getInstance();
	private DaoUserSetter setter = new DaoUserSetter();
	
	@Override
	public void addTicket(Ticket t) {
		try {
			Connection connect = conUtil.getConnection();
		
			String sql = "INSERT INTO tickets(created_by, amount, details, status)"
						+ " VALUES (?,?,?,?)";
		
			PreparedStatement prepared = connect.prepareStatement(sql);
			prepared.setString(1, t.getCreatedBy().getEmail());
			prepared.setDouble(2, t.getAmount());
			prepared.setString(3, t.getInfo());
			prepared.setInt(4, t.getStat());
			prepared.execute();
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new BadTicketException("Failed");
		}
	}


	@Override
	public List<Ticket> getAllTickets(User u) {
		boolean man = u.isManager();
		List<Ticket> ticketList = new ArrayList<Ticket>();
		
		if(man) {
			try {
				Connection connect = conUtil.getConnection();
				String sql = "SELECT * FROM tickets WHERE status = 1";
				Statement stat = connect.createStatement();
				ResultSet result = stat.executeQuery(sql);
				ticketList = setter.ticketListSetter(result);
				
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}else {
			try {
				Connection connect = conUtil.getConnection();
				String sql = "SELECT * FROM tickets WHERE created_by = '" + u.getEmail() + "'";
				Statement stat = connect.createStatement();
				ResultSet result = stat.executeQuery(sql);
				ticketList = setter.ticketListSetter(result);
				
			}catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		
		return ticketList;
	}
	
	@Override
	public void adjustTicket(Ticket t) {}

	@Override
	public void changeTicketStatus(User u, int stat, int tickId) throws SQLException {
		boolean man = u.isManager();
		
		if(man) {
				Connection connect = conUtil.getConnection();
				String sql = "UPDATE tickets SET status = ? WHERE ticket_id = ? ";				
				PreparedStatement prep = connect.prepareStatement(sql);
				prep.setInt(1, stat);
				prep.setInt(2, tickId);
				prep.execute();

		}else {
			throw new UnauthorizedException();
		}		
	}


	@Override
	public Ticket getTicketById(int i)throws SQLException {
		Ticket t = null;
		
		Connection connect = conUtil.getConnection();
		String sql = "SELECT * FROM tickets WHERE ticket_id = " + i;
		Statement stat = connect.createStatement();
		ResultSet result = stat.executeQuery(sql);
		t = setter.ticketSetter(result);
		return t;
	}

}
