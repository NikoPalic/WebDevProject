package eu.pracenjetroskova.app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@Entity
@Table(name = "common_balance")
public class CommonBalance {
	
	@Id
	@GeneratedValue
	@Column(name="common_balance_id")
	private Long id;
	
	@NotNull(message="Polje ne može ostati prazno")
	@NumberFormat(style=Style.NUMBER)
	@DecimalMin(value="0.0",message="Iznos mora biti pozitivan broj")
	@Column(name="common_balance_funds")
	private Double funds;
	
	@NotBlank(message="Polje ne može ostati prazno")
	@Column(name="common_balance_name")
	private String name;
	
	@NotBlank(message="Polje ne može ostati prazno")
	@Column(name="common_balance_info")
	private String info;
	
	@NotNull(message="Polje ne može ostati prazno")
	@NumberFormat(style=Style.NUMBER)
	@DecimalMin(value="0.0",message="Iznos mora biti pozitivan broj")
	@Column(name="common_balance_goal")
	private Double goal;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="common_balance_date")
	private Date date;
	
	@OneToMany(mappedBy = "cbID")
	private List<Log> log;
	
	@OneToMany(
	        mappedBy = "commonbalance",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	    private List<UsersCommonBalance> users = new ArrayList<>();

	
	
	public CommonBalance() {
		
	}

	

	public CommonBalance(Long id, Double funds, String name, String info, Double goal, Date date) {
		super();
		this.id = id;
		this.funds = funds;
		this.name = name;
		this.info = info;
		this.goal = goal;
		this.date = date;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getFunds() {
		return funds;
	}

	public void setFunds(Double funds) {
		this.funds = funds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Double getGoal() {
		return goal;
	}

	public void setGoal(Double goal) {
		this.goal = goal;
	}

	public List<Log> getLog() {
		return log;
	}

	public void setLog(List<Log> log) {
		this.log = log;
	}

	public List<UsersCommonBalance> getUsers() {
		return users;
	}

	public void setUsers(List<UsersCommonBalance> users) {
		this.users = users;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void deleteMember(UsersCommonBalance user) {
		this.users.remove(user);
	}

}
