package eu.pracenjetroskova.app.model;

import java.util.Date;
import java.util.UUID;
import eu.pracenjetroskova.app.model.User;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;


@Entity
@Table(name = "savings")
public class Savings {
	
	@Id
	@GeneratedValue
	@Column(name="savings_id")
	private Long id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="savings_starttdate")
	private Date startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="savings_enddate")
	private Date endDate;
	
	@NotNull(message="Polje ne može ostati prazno")
	@NumberFormat(style=Style.NUMBER)
	@DecimalMin(value="0.0",message="Iznos mora biti pozitivan broj")
	@Column(name="savings_goal")
	private Double goal;
	
	@NotBlank(message="Polje ne može ostati prazno")
	@Column(name="savings_info")
	private String info;
	
	@NotNull(message="Polje ne može ostati prazno")
	@NumberFormat(style=Style.NUMBER)
	@DecimalMin(value="0.0",message="Iznos mora biti pozitivan broj")
	@Column(name="savings_funds")
	private Double funds;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User userID;
	

	public Savings() {}
	
	
	public Savings(Long id, Date startDate, Date endDate, Double goal, String info, Double funds) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.goal = goal;
		this.info = info;
		this.funds = funds;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public Double getGoal() {
		return goal;
	}


	public void setGoal(Double goal) {
		this.goal = goal;
	}


	public String getInfo() {
		return info;
	}


	public User getUserID() {
		return userID;
	}


	public void setInfo(String info) {
		this.info = info;
	}


	public Double getFunds() {
		return funds;
	}


	public void setFunds(Double funds) {
		this.funds = funds;
	}


	public void setUserID(User userID) {
		this.userID = userID;
	}

}
