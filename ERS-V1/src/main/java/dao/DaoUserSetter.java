package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import user.Ticket;
import user.User;

public class DaoUserSetter {
	
	public User userSetter(ResultSet set)throws SQLException {
		User u = null;
		while(set.next()) {
			u = new User();
			u.setFirstName(set.getString(1));
			u.setLastName(set.getString(2));
			u.setManager(set.getBoolean(3));
			u.setEmail(set.getString(4));
			u.setPassword(set.getString(5));
		}
		
		
		return u;
	}
	
	public Ticket ticketSetter(ResultSet set)throws SQLException {
		Ticket t = null;
		while(set.next()) {
			t = new Ticket();
			t.setEmail(set.getString(1));
			t.setAmount(set.getDouble(2));
			t.setInfo(set.getString(3));
			t.setStat(set.getInt(4));
			t.setTicketId(set.getInt(5));
		}
		return t;
	}
	
	public List<User> UserListSetter(ResultSet set) throws SQLException {
		List<User> uList = new ArrayList<User>();
		while(set.next()) {
			User nUser = new User();
			
			nUser.setFirstName(set.getString(1));
			nUser.setLastName(set.getString(2));
			nUser.setManager(set.getBoolean(3));
			nUser.setEmail(set.getString(4));
			//nUser.setPassword(set.getString(5));
			
			uList.add(nUser);
		}
		return uList;
		
	}
	
	public List<Ticket> ticketListSetter(ResultSet set) throws SQLException{
		List<Ticket> nList = new ArrayList<Ticket>();
		Ticket t = null;
		while(set.next()) {
			t = new Ticket();
			t.setEmail(set.getString(1));
			t.setAmount(set.getDouble(2));
			t.setInfo(set.getString(3));
			t.setStat(set.getInt(4));
			t.setTicketId(set.getInt(5));
			nList.add(t);
		}
		return nList;
	}
	
	
	
}
