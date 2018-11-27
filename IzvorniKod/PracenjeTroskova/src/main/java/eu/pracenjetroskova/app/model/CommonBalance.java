package eu.pracenjetroskova.app.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "common_balance")
public class CommonBalance {
	
	@Id
	@GeneratedValue
	@Column(name="common_balance_id")
	private Long id;
	
	@Column(name="common_balance_funds")
	private Double funds;
	
	@Column(name="common_balance_name")
	private String name;
	
	@OneToMany(mappedBy = "cbID")
	private List<Log> log;
	
	@OneToMany(
	        mappedBy = "commonbalance",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	    private List<UsersCommonBalance> users = new ArrayList<>();

	
	public CommonBalance(Long id, Double funds, String name) {
		super();
		this.id = id;
		this.funds = funds;
		this.name = name;
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
	
	

}
