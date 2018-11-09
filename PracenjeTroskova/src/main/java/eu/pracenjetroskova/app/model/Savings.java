package eu.pracenjetroskova.app.model;

import java.util.Date;
import java.util.UUID;
import eu.pracenjetroskova.app.model.User;

import javax.persistence.*;


@Entity
@Table(name = "savings")
public class Savings {
	
	@Id
	@GeneratedValue
	@Column(name="savings_id")
	private Long id;
	
	@Column(name="savings_startdate")
	private Date starDate;
	
	@Column(name="savings_enddate")
	private Date endDate;
	
	@Column(name="savings_goal")
	private Float goal;
	
	@Column(name="savings_info")
	private String info;
	
	@Column(name="savings_funds")
	private Float funds;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User userID;
	

	public Savings() {}
	
	
	public Savings(Long id, Date starDate, Date endDate, Float goal, String info, Float funds) {
		super();
		this.id = id;
		this.starDate = starDate;
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


	public Date getStarDate() {
		return starDate;
	}


	public void setStarDate(Date starDate) {
		this.starDate = starDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public Float getGoal() {
		return goal;
	}


	public void setGoal(Float goal) {
		this.goal = goal;
	}


	public String getInfo() {
		return info;
	}


	public void setInfo(String info) {
		this.info = info;
	}


	public Float getFunds() {
		return funds;
	}


	public void setFunds(Float funds) {
		this.funds = funds;
	}

}
