package eu.pracenjetroskova.app.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import eu.pracenjetroskova.app.dto.UserDto;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.service.UserService;

@Controller
public class AccountController {

	private final UserService userService;
	
	@Autowired
	public AccountController(UserService userService) {
		super();
		this.userService = userService;
	}


	@GetMapping("/postavke")
	public String mojePostavke(Principal principal,WebRequest request, Model model) {
		Optional<User> user=userService.findByUsername(principal.getName());
		model.addAttribute("korisnik", user.get());
		return "userdata";
	}
	
	@PostMapping("/postavke/osvjezi")
	public String updateUser(@ModelAttribute("korisnik") User user, BindingResult bindingResult, Principal principal,Model model) {
		if (bindingResult.hasErrors()) {
			return "userdata";
		}else if(userService.emailExist(user.getEmail())) {
			if(!userService.findByEmail(user.getEmail()).getId().equals(user.getId())) {
				Optional<User> user2=userService.findByUsername(principal.getName());
				model.addAttribute("korisnik", user2.get());
				return "userdata";
			}
			
		}
		userService.updateUser(user);
		return "redirect:/profil";
	}
	
	@GetMapping("/postavke/password")
	public String changePasswordForm(Principal principal,WebRequest request, Model model) {
		model.addAttribute("lozinka", new UserDto());
		
		return "passwordchange";
	}
	
	@PostMapping("/postavke/password")
	public String changePassword(@ModelAttribute("lozinka") UserDto user, BindingResult bindingResult, Principal principal,Model model) {
		Optional<User> userO=userService.findByUsername(principal.getName());
		if (bindingResult.hasErrors()) {
			return "passwordchange";
		}else if(!user.getPassword().equals(user.getMatchingPassword())) {
			model.addAttribute("lozinka", new UserDto());
			return "passwordchange";
		}else if(!userService.passwordEncoder().matches(user.getName(), userO.get().getPassword())) {
			model.addAttribute("lozinka", new UserDto());
			return "passwordchange";
		}
		userO.get().setPassword(userService.passwordEncoder().encode(user.getPassword()));
		userService.updateUser(userO.get());
		return "redirect:/profil";
	}
}
