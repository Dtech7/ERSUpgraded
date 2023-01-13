package services;

import user.Ticket;
import user.User;
import utils.LogginUtil;

import java.sql.SQLException;
import java.util.List;

import dao.TicketDao;
import dao.UserDao;
import exceptions.BadTicketException;
import exceptions.UnauthorizedException;
import exceptions.UserAlreadyExistException;
import exceptions.UserDoesNotExistException;

public class Service {
	
	//User user = new User();
	private UserDao uDao;
	private TicketDao tDao;
	
	
	//constructors
	public Service(TicketDao tDao) {
		this.tDao = tDao;
	}
	public Service(UserDao uDao) {
		this.uDao = uDao;		
	}
	public Service(UserDao uDao, TicketDao tDao) {
		this.uDao = uDao;
		this.tDao = tDao;
	}
	
	//User Services
	public void register(User u) {
		try {
			uDao.addUser(u);
			LogginUtil.getLogger().info("User: " + u.getEmail() 
			+" was registered.");
		}catch(Exception e) {
			String errMsg = "User: " + u.getEmail() + " is already in system.";
			LogginUtil.getLogger().warn(errMsg);
			throw new UserAlreadyExistException(errMsg);
			
		}
		
	}
	
	public void fired(String m, String f) {
		boolean man = uDao.findIfManager(m);
		User fired = uDao.getUserByName(f);
		if(man) {
			try {
				uDao.removeUser(fired);
				LogginUtil.getLogger().info("User: " + fired.getEmail() 
				+" was fired.");
			}catch(Exception e) {
				throw new UnauthorizedException();
			}
		}else 
			throw new UnauthorizedException();
	}
	
	public User getUserByEmail(String email) {
		return uDao.getUserByName(email);
	}
	
	
	public User logIn(String e, String p) {
		User u = uDao.getUserByName(e);
		String errMsg = "User: " + e + " failed to logIn";
		
		if(u == null) {
			LogginUtil.getLogger().warn(errMsg);
			throw new UserDoesNotExistException(errMsg);
		
		}else if(u.getPassword().equals(p)) {
			
			LogginUtil.getLogger().info("User: " + e + " sucessfully Logged in." );
			return u;
		
		}else {
			
			LogginUtil.getLogger().warn(errMsg);
			throw new UserDoesNotExistException(errMsg);
		}	
	}
	
	public List<User> getAllUsers(String email){
		User ucheck = uDao.getUserByName(email);
		List<User> uList = null;
		if(ucheck.isManager()) {
			uList = uDao.getAllUsers();
			
		}else {
			throw new UnauthorizedException();
		}
		return uList;
	}
	
	//peviusly used to logout not currently bein used
	public void logOut(User u) {
		uDao.logOut(u);
		LogginUtil.getLogger().info("User logged out.");
	}
	//------------------------------------------------
	
	//Ticket Services
	public void createTicket(Ticket t) {
		//String s = null;
		if(t.getInfo()== null) {
			throw new BadTicketException("Bad Ticket");
		}
		if(t.getAmount() == 0) {
			throw new BadTicketException("Bad Ticket");
		}
		tDao.addTicket(t);
		LogginUtil.getLogger().info("New ticket created.");

	}
	
	public Ticket getTicket(int i) throws SQLException {
		Ticket t = tDao.getTicketById(i);
		return t;
	}
	
	public void reviewTicket(User u, int nstat, int tId){
		
		try {
			Ticket t = tDao.getTicketById(tId);
			if(t.getStat()!= 1) {
				throw new UnauthorizedException();
			}
		}catch (Exception e) {
			String errMsg = "Ticket not found";
			throw new BadTicketException(errMsg);
		}
		
		try {
			tDao.changeTicketStatus(u, nstat, tId);
		} catch (Exception e) {
			throw new UnauthorizedException();
			
		}
		
		if(nstat == 2)
			LogginUtil.getLogger().info("Ticket was Approved");
		else if(nstat == 3)
			LogginUtil.getLogger().info("Ticket was Denied");
	}
		
	public List<Ticket> getAllTickets(User u){
		/*might have to add loop that adds the user that created the ticket*/ 
		List<Ticket> tList = tDao.getAllTickets(u);
		return tList;
	}
}
