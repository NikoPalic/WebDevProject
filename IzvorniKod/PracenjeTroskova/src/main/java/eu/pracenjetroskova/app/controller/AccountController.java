package eu.pracenjetroskova.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {

	@PostMapping("profil/postavke/korisnickoime")
	public void changeUsername() {
		//TODO
	}
	
	@PostMapping("profil/postavke/email")
	public void changeEmail() {
		//TODO
	}
	
	@PostMapping("profil/postavke/ime")
	public void changeName() {
		//TODO
	}
	
	@PostMapping("profil/postavke/prezime")
	public void changeLastName() {
		//TODO
	}
	
	@PostMapping("profil/postavke/lozinka")
	public void changePassword() {
		//TODO
	}
}
