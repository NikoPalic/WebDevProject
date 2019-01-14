package eu.pracenjetroskova.app.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "users_common_balance")
public class UsersCommonBalance {
 
    @EmbeddedId
    private UsersCommonBalanceId id;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cbId")
    private CommonBalance commonbalance;
 
    @Column(name = "status")
    private String status;

    
    
	public UsersCommonBalance() {
	}



	public UsersCommonBalance(UsersCommonBalanceId id, User user, CommonBalance commonbalance, String status) {
		super();
		this.id = id;
		this.user = user;
		this.commonbalance = commonbalance;
		this.status = status;
	}



	public UsersCommonBalanceId getId() {
		return id;
	}



	public void setId(UsersCommonBalanceId id) {
		this.id = id;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public CommonBalance getCommonbalance() {
		return commonbalance;
	}



	public void setCommonbalance(CommonBalance commonbalance) {
		this.commonbalance = commonbalance;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}

	
	
	
    
}