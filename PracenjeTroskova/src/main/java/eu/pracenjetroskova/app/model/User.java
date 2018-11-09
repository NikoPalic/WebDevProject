package eu.pracenjetroskova.app.model;

import java.util.Collection;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Entity
@Table(name = "\"user\"")
public class User {

	@Id
	@GeneratedValue
	@Column(name="user_id")
	private Long id;
	
	
	@Column(name="user_username", unique=true)
	private String username;
	
	@Column(name="user_password")
	private String password;
	
	@Column(name="user_name")
	private String name;
	
	@Column(name="user_last_name")
	private String lastName;
	
	@Column(name="user_email",unique=true)
	private String email;
	
	@Column(name="user_active")
	private int active;
	
	
	
	

	public User() {
	}

	public User(User users) {
		this.active=users.getActive();
		this.email=users.getEmail();
		
		this.name=users.getName();
		this.lastName=users.getLastName();
		this.username=users.getUsername();
		this.id=users.getId();
		this.password=users.getPassword();
		
	}

	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = passwordEncoder().encode(password);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	
	
	
	
	
	
	
	
}
