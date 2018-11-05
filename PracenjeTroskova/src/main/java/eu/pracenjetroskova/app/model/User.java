package eu.pracenjetroskova.app.model;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

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
	private UUID id;
	
	
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
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name="user_role", joinColumns= @JoinColumn(name ="user_id"))
	private Collection<Role> roles;
	
	

	public User() {
	}

	public User(User users) {
		this.active=users.getActive();
		this.email=users.getEmail();
		this.roles=users.getRoles();
		this.name=users.getName();
		this.lastName=users.getLastName();
		this.username=users.getUsername();
		this.id=users.getId();
		this.password=users.getPassword();
		
	}

	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
	
	
	
	
	
	
}