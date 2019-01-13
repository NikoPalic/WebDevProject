package eu.pracenjetroskova.app.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	public String deleteTrosak(@PathVariable Long id,Principal principal) {
		User user=userService.findByUsername(principal.getName()).get();
		user.setFunds(user.getFunds()+expenditureService.findById(id).get().getAmount());
		userService.updateUser(user);
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
	public String updateTrosak(@ModelAttribute("edittrosak") @Valid Expenditure expenditure, BindingResult bindingResult,Principal principal,Model model) {
		User user = userService.findByUsername(principal.getName()).get();
		
		try {
			expenditure.setDate(formatiranjeDatuma(expenditure.getDate()));
		} catch (ParseException e) {
		}
		if (bindingResult.hasErrors()) {
			List<Category>kategorije=user.getCategories();
			model.addAttribute("edittrosak", expenditure);
			model.addAttribute("kategorije", kategorije);
			return "updateexpenditure";
		}
		user.setFunds(user.getFunds()+expenditureService.findById(expenditure.getId()).get().getAmount()-expenditure.getAmount());
		expenditureService.saveExpenditure(expenditure);
		userService.updateUser(user);
		return "redirect:/profil/troskovi";
	}
	
	private Date formatiranjeDatuma(Date trenutniDatum) throws ParseException {
		SimpleDateFormat vrijeme = new SimpleDateFormat("HH:mm:ss");
	    SimpleDateFormat datum = new SimpleDateFormat("yyyy-MM-dd");
	    DateFormat noviDatum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String formattedTime = vrijeme.format(Calendar.getInstance().getTime());
	    String formattedDate = datum.format(trenutniDatum);
	    return noviDatum.parse(formattedDate+" "+formattedTime);
	}
}
