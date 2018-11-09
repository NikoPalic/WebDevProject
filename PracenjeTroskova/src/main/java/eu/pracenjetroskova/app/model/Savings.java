package eu.pracenjetroskova.app.model;

import java.util.Date;
import java.util.UUID;
import eu.pracenjetroskova.app.model.User;

import javax.persistence.*;


@Entity
@Table(name = "savings")
@SecondaryTable(name = "\"user\"")
public class Savings {
	
	@Id
	@GeneratedValue
	@Column(name="savings_id", nullable = false)
	private Long id;
	
	@Column(name="savings_startdate", nullable = false)
	private Date starDate;
	
	@Column(name="savings_enddate", nullable = false)
	private Date endDate;
	
	@Column(name="savings_goal", nullable = false)
	private Float goal;
	
	@Column(name="savings_info", nullable = true)
	private String info;
	
	@Column(name="savings_funds", nullable = false)
	private Float funds;
	
	
	private Long userId;

	public Savings() {}
	
	
	public Savings(Long id, Date starDate, Date endDate, Float goal, String info, Float funds, Long userId) {
		super();
		this.id = id;
		this.starDate = starDate;
		this.endDate = endDate;
		this.goal = goal;
		this.info = info;
		this.funds = funds;
		this.userId = userId;
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

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id", table ="\"user\"")
	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
