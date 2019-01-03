package eu.pracenjetroskova.app.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "expenditure")
public class Expenditure {
	
	@Id
	@GeneratedValue
	@Column(name="expenditure_id")
	private Long id;
	
	@Column(name="expenditure_name")
	private String name;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="expenditure_date")
	private Date date;
	
	@Column(name="expenditure_amount")
	private Float amount;
	
	@Column(name="expenditure_info")
	private String info;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User userID;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="category_id")
	private Category categoryID;

	
	public Expenditure() {}

	public Expenditure(Long id, String name, Date date, Float amount, String info) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.amount = amount;
		this.info = info;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public Float getAmount() {
		return amount;
	}


	public void setAmount(Float amount) {
		this.amount = amount;
	}


	public String getInfo() {
		return info;
	}


	public void setInfo(String info) {
		this.info = info;
	}
	
	public void setUserID(User userID) {
		this.userID = userID;
	}

	public Category getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Category categoryID) {
		this.categoryID = categoryID;
	}

	public User getUserID() {
		return userID;
	}

}
