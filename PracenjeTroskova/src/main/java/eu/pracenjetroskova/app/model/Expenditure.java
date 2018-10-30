package eu.pracenjetroskova.app.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "expenditure")
public class Expenditure {
	
	@Id
	@GeneratedValue
	@Column(name="exp_id", nullable = false)
	private Long id;
	
	@Column(name="name", nullable = false)
	private String name;
	
	@Column(name="date", nullable = false)
	private Date date;
	
	@Column(name="amount", nullable = false)
	private Float amount;
	
	@Column(name="info", nullable = true)
	private String info;
	
	@Column(name="cat_id", nullable = false)
	private Long catId;
	
	
	@Column(name="user_id", nullable = false)
	private UUID userId;


	public Expenditure(Long id, String name, Date date, Float amount, String info, Long catId, UUID userId) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.amount = amount;
		this.info = info;
		this.catId = catId;
		this.userId = userId;
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


	public Long getCatId() {
		return catId;
	}


	public void setCatId(Long catId) {
		this.catId = catId;
	}


	public UUID getUserId() {
		return userId;
	}


	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	
	


}
