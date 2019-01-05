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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import eu.pracenjetroskova.app.model.Category;
import eu.pracenjetroskova.app.model.Revenue;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.service.RevenueService;
import eu.pracenjetroskova.app.service.UserService;

@Controller
@RequestMapping("prihodi")
public class RevenueController {

	
	private final RevenueService revenueService;
	private final UserService userService;
	
	@Autowired
	public RevenueController(RevenueService revenueService, UserService userService) {
		super();
		this.revenueService = revenueService;
		this.userService = userService;
	}

	@GetMapping
	public String ispisPrihoda() {
		//TODO
		return "ispis";
	}
	
	@PostMapping("/izbrisi/{id}")
	public String deletePrihod(@PathVariable Long id) {
		revenueService.deleteRevenue(id);
		return "redirect:/profil/prihodi";
	}
	
	@GetMapping("/osvjezi/{id}")
	public String showUpdatePrihod(@PathVariable Long id, Model model, Principal principal) {
		Optional<User> user=userService.findByUsername(principal.getName());
		List<Category>kategorije=user.get().getCategories();
		model.addAttribute("editprihod", revenueService.findById(id).get());
		model.addAttribute("kategorije", kategorije);
		return "updaterevenue";
	}
	@PostMapping("/osvjezi")
	public String updatePrihod(@ModelAttribute("editprihod") Revenue prihod, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "updaterevenue";
		}
		revenueService.saveRevenue(prihod);
		return "redirect:/profil/prihodi";
	}
}
