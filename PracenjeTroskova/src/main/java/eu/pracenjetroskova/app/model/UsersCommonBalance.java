package eu.pracenjetroskova.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users_common_balance")
public class UsersCommonBalance {

	@Id
	@Column(name="users_common_balance_user_id")
	private Long userId;
	
	@Id
	@Column(name="users_common_balance_cb_id")
	private Long commonBalanceId;

	@Column(name="users_common_balance_status")
	private String Status;

	public UsersCommonBalance(Long userId, Long commonBalanceId, String status) {
		super();
		this.userId = userId;
		this.commonBalanceId = commonBalanceId;
		Status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCommonBalanceId() {
		return commonBalanceId;
	}

	public void setCommonBalanceId(Long commonBalanceId) {
		this.commonBalanceId = commonBalanceId;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	
	
}
