package eu.pracenjetroskova.app.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

@Entity
@Table(name = "revenue")
public class Revenue {
	

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="amount")
	private Float amount;
	
	@Column(name="info")
	private String info;
	
	@Column(name="cat_id")
	private Long catId;
	
	
	@Column(name="user_id")
	private Long userId;

	
	
	public Revenue(Long id, String name, Date date, Float amount, String info, Long catId, Long userId) {
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


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
