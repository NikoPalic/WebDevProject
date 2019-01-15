package eu.pracenjetroskova.app.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class Transakcija {

	@NotNull(message="Polje ne može ostati prazno")
	@NumberFormat(style=Style.NUMBER)
	@DecimalMin(value="0.0",message="Iznos mora biti pozitivan broj")
	private Double amount;
	
	private Long id;

	public Transakcija() {
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
