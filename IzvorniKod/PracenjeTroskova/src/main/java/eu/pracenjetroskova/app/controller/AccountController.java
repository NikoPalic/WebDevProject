package eu.pracenjetroskova.app.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

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
	
	@PostMapping("/kategorije/izbrisi/{id}")
	public String deleteKategorija(@PathVariable Long id,Principal principal) {
		Optional<User> user=userService.findByUsername(principal.getName());
		List<Category>kategorije=user.get().getCategories();
		try {
			kategorije.remove(categoryService.findById(id));
			user.get().setCategories(kategorije);
			categoryService.deleteCategory(id);
		} catch (Exception e) {
			return "zabranabrisanja";
		}
		
		return "redirect:/profil/kategorije";
	}
	
	@GetMapping("/kategorije/osvjezi/{id}")
	public String showUpdateKategorija(@PathVariable Long id, Model model, Principal principal) {
		model.addAttribute("editkategorija", categoryService.findById(id));
		return "updatecategory";
	}
	
	@PostMapping("/kategorije/osvjezi")
	public String updateKategorija(@ModelAttribute("editkategorija") Category category, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "updatecategory";
		}
		categoryService.createCategory(category);
		return "redirect:/profil/kategorije";
	}
		
	
}
