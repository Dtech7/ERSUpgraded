package dao;

import java.util.List;
import user.User;

public interface UserDao {
	
	public void addUser(User u)throws Exception;
	public List<User> getAllUsers();
	public User getUserByName(String uname);
	public boolean findIfManager(String uname);
	public void LogIn(User u);
	public void logOut(User u);
	public void removeUser(User u)throws Exception;
	//public void updatePerson(User u);

}