package eu.pracenjetroskova.app.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue
	@Column(name="category_id")
	private Long id;
	
	@NotBlank(message="Polje ne može ostati prazno")
	@Column(name="category_name")
	private String name;
	
	@NotBlank(message="Polje ne može ostati prazno")
	@Column(name="category_info")
	private String info;
	
	@OneToMany(mappedBy = "categoryID")
	private List<Expenditure> expenditure;
	
	@OneToMany(mappedBy = "categoryID")
	private List<Revenue> revenue;
	
    @ManyToMany(mappedBy = "categories")
    private List<User> users = new ArrayList<>();
	
	
    
    
	public Category() {
		
	}

	public Category(Long id, String name, String info) {
		super();
		this.id = id;
		this.name = name;
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	

}
