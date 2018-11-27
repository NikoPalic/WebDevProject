package eu.pracenjetroskova.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "log")
public class Log {

	@Id
	@GeneratedValue
	@Column(name="log_id")
	private Long id;
	
	@Column(name="log_action")
	private String action;
	
	@Column(name="log_date")
	private Date date;
	
	@Column(name="log_amount")
	private Double amount;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User userID;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="common_balance_id")
	private CommonBalance cbID;

	public Log(Long id, String action, Date date, Double amount) {
		super();
		this.id = id;
		this.action = action;
		this.date = date;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
