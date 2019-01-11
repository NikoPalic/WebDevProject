package eu.pracenjetroskova.app.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

//	@GetMapping
//	public String ispisPrihoda() {
//		return "prihodi";
//	}
	
	@PostMapping("/izbrisi/{id}")
	public String deletePrihod(@PathVariable Long id,Principal principal) {
		User user=userService.findByUsername(principal.getName()).get();
		user.setFunds(user.getFunds()-revenueService.findById(id).get().getAmount());
		userService.updateUser(user);
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
		try {
			prihod.setDate(formatiranjeDatuma(prihod.getDate()));
		} catch (ParseException e) {
		}
		revenueService.saveRevenue(prihod);
		return "redirect:/profil/prihodi";
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
