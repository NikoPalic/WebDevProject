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

import eu.pracenjetroskova.app.model.Category;
import eu.pracenjetroskova.app.model.Expenditure;
import eu.pracenjetroskova.app.model.Revenue;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.service.ExpenditureService;
import eu.pracenjetroskova.app.service.UserService;

@Controller
@RequestMapping("troskovi")
public class ExpenditureController {

	private final ExpenditureService expenditureService;
	private final UserService userService;
	
	
	@Autowired
	public ExpenditureController(ExpenditureService expenditureService, UserService userService) {
		super();
		this.expenditureService = expenditureService;
		this.userService = userService;
	}


	
	@PostMapping("/izbrisi/{id}")
	public String deleteTrosak(@PathVariable Long id) {
		expenditureService.deleteExpenditure(id);
		return "redirect:/profil/troskovi";
	}
	
	@GetMapping("/osvjezi/{id}")
	public String showUpdateTrosak(@PathVariable Long id, Model model, Principal principal) {
		Optional<User> user=userService.findByUsername(principal.getName());
		List<Category>kategorije=user.get().getCategories();
		model.addAttribute("edittrosak", expenditureService.findById(id).get());
		model.addAttribute("kategorije", kategorije);
		return "updateexpenditure";
	}
	
	@PostMapping("/osvjezi")
	public String updateTrosak(@ModelAttribute("edittrosak") Expenditure expenditure, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "updateexpenditure";
		}
		expenditureService.saveExpenditure(expenditure);
		return "redirect:/profil/troskovi";
	}
}
