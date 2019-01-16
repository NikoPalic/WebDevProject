package eu.pracenjetroskova.app.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.google.common.base.Optional;

import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.service.UserService;

@ControllerAdvice
public class GlobalController {

	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public void  getUserData(Principal principal, Model model) {
		User user= userService.findByUsername(principal.getName()).get();
		model.addAttribute("currentUser", user);
		
	}
	
}
