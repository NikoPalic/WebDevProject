package eu.pracenjetroskova.app.dto;

import javax.validation.constraints.NotBlank;

public class Korisnik {

	@NotBlank(message="Polje ne može ostati prazno")
	private String name;
	
	private Long id;

	public Korisnik() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
