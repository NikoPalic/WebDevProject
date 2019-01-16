package eu.pracenjetroskova.app.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import eu.pracenjetroskova.app.validation.ValidEmail;



@Entity
@Table(name = "\"user\"")
public class User {

	@Id
	@GeneratedValue
	@Column(name="user_id")
	private Long id;
	
	@NotBlank(message="Polje ne može ostati prazno")
	@Column(name="user_username", unique=true)
	private String username;
	
	@Column(name="user_password")
	private String password;
	
	@NotBlank(message="Polje ne može ostati prazno")
	@Column(name="user_name")
	private String name;
	@NotBlank(message="Polje ne može ostati prazno")
	@Column(name="user_last_name")
	private String lastName;
	
	@ValidEmail(message="Neispravan email")
	@Column(name="user_email",unique=true)
	private String email;
	
	@Column(name="user_active")
	private boolean active;
	
	@Column(name="user_funds")
	private Double funds;
	
	@OneToMany(mappedBy = "userID")
	private List<Savings> savings;
	
	@OneToMany(mappedBy = "userID")
	private List<Revenue> revenue;
	
	@OneToMany(mappedBy = "userID")
	private List<Expenditure> expenditure;
	
	@OneToMany(mappedBy = "userID")
	private List<Log> log;
	
	@ManyToMany(cascade = { 
	        CascadeType.PERSIST, 
	        CascadeType.MERGE
	    })
	    @JoinTable(name = "users_category",
	        joinColumns = @JoinColumn(name = "user_id"),
	        inverseJoinColumns = @JoinColumn(name = "category_id")
	    )
	    private List<Category> categories = new ArrayList<>();
	
	@OneToMany(
	        mappedBy = "user",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	    private List<UsersCommonBalance> commonbalances = new ArrayList<>();
	
	

	public User() {
	}

	public User(User users) {
		this.active=users.getActive();
		this.email=users.getEmail();
		this.funds=users.getFunds();
		this.name=users.getName();
		this.lastName=users.getLastName();
		this.username=users.getUsername();
		this.id=users.getId();
		this.password=users.getPassword();
		
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
		this.password = password;
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

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Double getFunds() {
		return funds;
	}

	public void setFunds(Double funds) {
		this.funds = funds;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Savings> getSavings() {
		return savings;
	}

	public void setSavings(List<Savings> savings) {
		this.savings = savings;
	}
	
	
	

	
	
	
	
	
	
	
	
}
