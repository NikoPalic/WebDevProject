package eu.pracenjetroskova.app.controller;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import eu.pracenjetroskova.app.model.CustomUserDetails;
import eu.pracenjetroskova.app.model.Revenue;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.repository.RevenueRepository;
import eu.pracenjetroskova.app.repository.UserRepository;
import eu.pracenjetroskova.app.service.RevenueService;
import eu.pracenjetroskova.app.service.UserService;

@Controller
public class ProfilController {

	private final UserRepository userRepository;
	private final RevenueRepository revenueRepository;
	private final RevenueService revenueService;
	@Autowired
	public ProfilController(RevenueRepository revenueRepository, UserRepository userRepository, RevenueService revenueService) {
		super();
		this.revenueRepository=revenueRepository;
		this.userRepository=userRepository;
		this.revenueService=revenueService;
	}
	
	@GetMapping("/profil")
	public String mojProfil() {
		return "home";
	}
	
	@GetMapping("/profil/troskovi")
	public String pregledTroskova() {
		//TODO
		return "troskovi";
	}
	
	@GetMapping("/profil/prihodi")
	public @ResponseBody List<Revenue> pregledPrihoda(Principal principal) {
		Optional<User> user=userRepository.findByUsername(principal.getName());
		List<Revenue>prihodi=revenueRepository.findByUserID(user.get());
		return prihodi;
	}
	
	@GetMapping("/profil/stednje")
	public String pregledStednji() {
		//TODO
		return "stednje";
	}
	
	@GetMapping("/profil/stednje/zajednicke")
	public String pregledZajednickihStednji() {
		//TODO
		return "zajednickeStednje";
	}

	
	@PostMapping("/profil/troskovi/stvori")
	public void stvoriNoviTrosak() {
		//TODO
		
	}
	
	@GetMapping("/profil/prihodi/stvori")
	public String showRevenueForm(WebRequest request, Model model) {
		Revenue revenue = new Revenue();
		model.addAttribute("revenue", revenue);
		return "newrevenue";
	}
	
	@PostMapping("/profil/prihodi/stvori")
	public ModelAndView stvoriNoviPrihod(@ModelAttribute("revenue") Revenue newRevenue, 
			  BindingResult result, 
			  WebRequest request, 
			  Errors errors,Principal principal) {
		Optional<User> user=userRepository.findByUsername(principal.getName());
		newRevenue.setUserID(user.get());
		revenueService.createRevenue(newRevenue);
		return new ModelAndView ("successrevenue","revenue", newRevenue);
		
	}

	@PostMapping("/profil/stednje/stvori")
	public void stvoriNovuStednju() {
		//TODO
		
	}
	
	@PostMapping("/profil/stednje/zajednicke")
	public void stvoriNovuZajednickuStednju() {
		//TODO
		
	}
}
