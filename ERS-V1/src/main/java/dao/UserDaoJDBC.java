package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import user.User;
import utils.JdbcConnectUtil;

public class UserDaoJDBC implements UserDao {

	private JdbcConnectUtil conUtil = JdbcConnectUtil.getInstance();
	private DaoUserSetter setter = new DaoUserSetter();
	
	@Override
	public void addUser(User u) throws SQLException{
			Connection connect = conUtil.getConnection();
			String sql = "INSERT INTO users(first_name, last_name, is_manager, email, password)"
						+ "VALUES (?,?,?,?,?)";
		
			PreparedStatement prepared = connect.prepareStatement(sql);
			prepared.setString(1, u.getFirstName());
			prepared.setString(2, u.getLastName());
			prepared.setBoolean(3, u.isManager());
			prepared.setString(4, u.getEmail());
			prepared.setString(5, u.getPassword());
			prepared.execute();
	}

	@Override
	public List<User> getAllUsers() {
		List<User> uList = new ArrayList<User>();
		
		try {
			Connection connect = conUtil.getConnection();
			String sql = "SELECT * FROM users";
			Statement stat = connect.createStatement();
			ResultSet result = stat.executeQuery(sql);
			uList = setter.UserListSetter(result);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return uList;
	}

	@Override
	public User getUserByName(String email) {
		User u = null;
		
		try {
			Connection connect = conUtil.getConnection();
			String sql = "SELECT * FROM users WHERE email= '" + email +"'";
			Statement stat = connect.createStatement();
			ResultSet result = stat.executeQuery(sql);
			u = setter.userSetter(result);
			
		}catch(SQLException e) {
			e.printStackTrace();			
		}
		
		return u;
	}

	@Override
	public boolean findIfManager(String uname) {
		User u = null;
		boolean uBool = false;
		
		try {
			Connection connect = conUtil.getConnection();
			String sql = "SELECT * FROM users WHERE email='" + uname +"'";
			Statement stat = connect.createStatement();
			ResultSet result = stat.executeQuery(sql);
			u = setter.userSetter(result);
			uBool = u.isManager();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return uBool;
	}

	@Override
	public void LogIn(User u) {
		/*Used to set someone to the loggedIn table
		 * before I learned about session
		 * no longer in use, to be deleted*/
		try {
			Connection connect = conUtil.getConnection();
		
			String sql = "INSERT INTO loggedIn(uname, utype)"
						+ "VALUES (?,?)";
		
			PreparedStatement prepared = connect.prepareStatement(sql);
			prepared.setString(1, u.getEmail());
			prepared.setBoolean(2, u.isManager());
			prepared.execute();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void removeUser(User u) throws SQLException{
		Connection connect = conUtil.getConnection();
		String sql = "DELETE FROM users CASCADE WHERE email = ?";
		PreparedStatement prepared = connect.prepareStatement(sql);
		prepared.setString(1, u.getEmail());
		prepared.execute();
}
	


	/*Used to remove someone from the loggedIn table
	 * before I learned about session
	 * no longer in use, to be deleted*/
	@Override
	public void logOut(User u) {
		String uname = u.getEmail();
		try {
			Connection connect = conUtil.getConnection();
		
			String sql = "DELETE FROM loggedIn where uname = '" + uname + "'";
			Statement stat = connect.createStatement();
			stat.execute(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

}
