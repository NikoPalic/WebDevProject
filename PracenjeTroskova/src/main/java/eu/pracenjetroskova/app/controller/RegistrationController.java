package eu.pracenjetroskova.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import eu.pracenjetroskova.app.model.Users;
import eu.pracenjetroskova.app.repository.UsersRepository;
import eu.pracenjetroskova.app.service.UserService;


@RestController
public class RegistrationController {
	
	private final UserService userService;
	
	
	@Autowired
	public RegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/signup")
	public String registration() {
		return "registracija je jos u izradi";
	}
	
	@PostMapping("/signup")
	public Users register(@RequestBody Users user) {
		userService.insertUser(user);
		return user;
	}
	
//	@PreAuthorize("hasRole('ADMIN')")
//	@GetMapping("/signup/all")
//	@ResponseBody
//	public List<Users> getAll(){
//		return usersRepository.findAll();
//	}
	
	
}
