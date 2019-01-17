package eu.pracenjetroskova.app.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import eu.pracenjetroskova.app.validation.ValidPassword;

import eu.pracenjetroskova.app.validation.PasswordMatches;
import eu.pracenjetroskova.app.validation.ValidEmail;

@PasswordMatches
public class UserDto {

	@NotNull(message="Polje ne može ostati prazno")
	@NotEmpty(message="Polje ne može ostati prazno")
	private String username;
	
	@NotNull(message="Polje ne može ostati prazno")
	@NotEmpty(message="Polje ne može ostati prazno")
	private String name;
	
	@NotNull(message="Polje ne može ostati prazno")
	@NotEmpty(message="Polje ne može ostati prazno")
	private String lastName;
	
	@ValidEmail
	@NotNull(message="Polje ne može ostati prazno")
	@NotEmpty(message="Polje ne može ostati prazno")
	private String email;
	
	@NotNull(message="Polje ne može ostati prazno")
	@NotEmpty(message="Polje ne može ostati prazno")
	@ValidPassword
	private String password;
	@NotNull
    @Size(min = 1,message="Minimalna duljina potrebna iznosi 1 znak")
	private String matchingPassword;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMatchingPassword() {
		return matchingPassword;
	}
	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
	
	
	
}
