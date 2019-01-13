package eu.pracenjetroskova.app.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eu.pracenjetroskova.app.dto.UserDto;
import eu.pracenjetroskova.app.model.Category;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.service.CategoryService;
import eu.pracenjetroskova.app.service.UserService;

@Controller
public class AccountController {

	private final UserService userService;
	private final CategoryService categoryService;
	
	@Autowired
	public AccountController(UserService userService, CategoryService categoryService) {
		super();
		this.userService = userService;
		this.categoryService = categoryService;
	}


	@GetMapping("/postavke")
	public String mojePostavke(Principal principal,WebRequest request, Model model) {
		Optional<User> user=userService.findByUsername(principal.getName());
		model.addAttribute("korisnik", user.get());
		return "userdata";
	}
	
	

	@PostMapping("/postavke/osvjezi")
	public String updateUser(@ModelAttribute("korisnik") @Valid User user, BindingResult bindingResult, Principal principal,Model model) {
		if (bindingResult.hasErrors()) {
			return "userdata";
		}else if(userService.emailExist(user.getEmail())) {
			if(!userService.findByEmail(user.getEmail()).getId().equals(user.getId())) {
				Optional<User> user2=userService.findByUsername(principal.getName());
				model.addAttribute("korisnik", user2.get());
				return "userdata";
			}
			
		}
		user.setCategories(userService.findByUsername(principal.getName()).get().getCategories());
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
		if(user.getName().equals("")) {
			bindingResult.rejectValue("name", "lozinka.nije.upisana");
		}
		if(user.getPassword().equals("")) {
			bindingResult.rejectValue("password", "lozinka.nije.upisana");
		}
		if(user.getMatchingPassword().equals("")) {
			bindingResult.rejectValue("matchingPassword", "lozinka.nije.upisana");
		}
		if(!user.getPassword().equals(user.getMatchingPassword())) {
			bindingResult.rejectValue("matchingPassword", "lozinka.promijena.potvrda");
		}
		if(!userService.passwordEncoder().matches(user.getName(), userO.get().getPassword()) && !user.getName().equals("")) {
			bindingResult.rejectValue("name", "lozinka.promijena.stari");
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("lozinka", user);
			return "passwordchange";
		}
		userO.get().setPassword(userService.passwordEncoder().encode(user.getPassword()));
		userService.updateUser(userO.get());
		return "redirect:/profil";
	}
	
	@PostMapping("/kategorije/izbrisi/{id}")
	public String deleteKategorija(@PathVariable Long id,Principal principal, RedirectAttributes redir) {
		Optional<User> user=userService.findByUsername(principal.getName());
		List<Category>kategorije=user.get().getCategories();
		try {
			kategorije.remove(categoryService.findById(id));
			user.get().setCategories(kategorije);
			categoryService.deleteCategory(id);
		} catch (Exception e) {
			redir.addFlashAttribute("zabrana", "Zabrana brisanja! Kategorija se koristi!");
			return "redirect:/profil/kategorije";
		}
		
		return "redirect:/profil/kategorije";
	}
	
	@GetMapping("/kategorije/osvjezi/{id}")
	public String showUpdateKategorija(@PathVariable Long id, Model model, Principal principal) {
		model.addAttribute("editkategorija", categoryService.findById(id));
		return "updatecategory";
	}
	
	@PostMapping("/kategorije/osvjezi")
	public String updateKategorija(@ModelAttribute("editkategorija") @Valid Category category, BindingResult bindingResult,Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("editkategorija", category);
			return "updatecategory";
		}
		categoryService.createCategory(category);
		return "redirect:/profil/kategorije";
	}
		
	
}
