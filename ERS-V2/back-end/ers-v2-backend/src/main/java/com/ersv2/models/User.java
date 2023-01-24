package com.ersv2.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;


@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer dbId;
	
	/*
	 * @Column(name = "employee_id", unique= true) private String uId;
	 */
	
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(unique = true)
	private String email;
	
	@Column(name = "phone")
	private String phoneNumber;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;
	
	@OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Ticket> userTickets;
	
	@OneToMany(mappedBy = "resolver", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Ticket> resolvedTickets;
	
	
	/*
	 * To be used if 2FA is implemented
		@Column(name = "auth_token")
  		private Integer authToken;

  		@Column(name = "first_login")
  		private Boolean firstLogin; 
	 */
	
	public User(String firstName, String lastName, String email, String password, String phoneNumber) {
		this.role = UserRole.EMPLOYEE;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password; //will be changed to a randomizer if 2FA is implmented
		this.phoneNumber = phoneNumber;
	}
}
