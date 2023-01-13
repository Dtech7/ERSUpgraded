package user;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	
	private String firstName;
	private String lastName;
	private boolean manager = false;
	private String email;
	private String password;
	
	public User() {
		
	}
	
	public User(String fName, String lName, String email, String pass) {
		this(fName, lName, false, email, pass);
	}
	
	public User(String fName, String lName, boolean man, String email, String pass) {
		this.firstName = fName;
		this.lastName = lName;
		this.manager = man;
		this.email = email;
		this.password = pass;
	}
	
	
	
	//Getters & Setters
	//First Name
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	//Last Name
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	//Manager
	public boolean isManager() {
		return manager;
	}
	public void setManager(boolean manager) {
		this.manager = manager;
	}
	//email
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	//Password
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" 
	+ lastName + ", manager=" + manager + ", email=" + email
				+ ", password=" + password + "]";
	}
	


}
