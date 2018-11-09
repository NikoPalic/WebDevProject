package eu.pracenjetroskova.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users_category")
public class UsersCategory {

	@Id
	@Column(name="users_category_user_id")
	private Long userId;
	
	@Column(name="users_category_cat_id")
	private Long categoryId;

	public UsersCategory(Long userId, Long categoryId) {
		super();
		this.userId = userId;
		this.categoryId = categoryId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	
	
}
