package eu.pracenjetroskova.app.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import eu.pracenjetroskova.app.model.Users;
import eu.pracenjetroskova.app.repository.UsersRepository;


@Controller
public class ProfilController {

	
	
	@GetMapping("/profil")
	public String mojProfil() {
		return "home";
	}
	
	

}
