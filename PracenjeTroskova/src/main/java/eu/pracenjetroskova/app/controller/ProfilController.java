package eu.pracenjetroskova.app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfilController {

	
	
	@GetMapping("/profil")
	public String mojProfil() {
		return "home";
	}
	
	

}
