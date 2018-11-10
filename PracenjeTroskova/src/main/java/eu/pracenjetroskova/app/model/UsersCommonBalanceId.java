package eu.pracenjetroskova.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UsersCommonBalanceId
    implements Serializable {
 
    @Column(name = "user_id")
    private Long userId;
 
    @Column(name = "cb_id")
    private Long cbId;

	public UsersCommonBalanceId(Long userId, Long cbId) {
		super();
		this.userId = userId;
		this.cbId = cbId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCbId() {
		return cbId;
	}

	public void setCbId(Long cbId) {
		this.cbId = cbId;
	}
    
}