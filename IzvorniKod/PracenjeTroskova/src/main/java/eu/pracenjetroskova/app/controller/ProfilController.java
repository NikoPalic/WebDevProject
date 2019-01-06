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

import eu.pracenjetroskova.app.model.Category;
import eu.pracenjetroskova.app.model.CustomUserDetails;
import eu.pracenjetroskova.app.model.Expenditure;
import eu.pracenjetroskova.app.model.Revenue;
import eu.pracenjetroskova.app.model.Savings;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.repository.CategoryRepository;
import eu.pracenjetroskova.app.repository.ExpenditureRepository;
import eu.pracenjetroskova.app.repository.RevenueRepository;
import eu.pracenjetroskova.app.repository.SavingsRepository;
import eu.pracenjetroskova.app.repository.UserRepository;
import eu.pracenjetroskova.app.service.RevenueService;
import eu.pracenjetroskova.app.service.SavingsService;
import eu.pracenjetroskova.app.service.UserService;
import eu.pracenjetroskova.app.service.CategoryService;
import eu.pracenjetroskova.app.service.ExpenditureService;

@Controller
public class ProfilController {

	private final UserService userService; 
	private final RevenueService revenueService;
	private final ExpenditureService expenditureService;
	private final SavingsService savingsService;
	private final CategoryService categoryService;
	private final CategoryRepository categoryRepository;
	
	
	
	
	@Autowired
	public ProfilController(RevenueService revenueService, ExpenditureService
			expenditureService, SavingsService savingsService,  CategoryRepository categoryRepository,UserService userService, CategoryService categoryService) {
		super();
		
		this.revenueService=revenueService;
		this.expenditureService=expenditureService;
		this.savingsService=savingsService;
		this.categoryRepository=categoryRepository;
		this.userService=userService;
		this.categoryService=categoryService;
	}
	
	@GetMapping("/profil")
	public String mojProfil(Principal principal,WebRequest request, Model model) {
		Optional<User> user=userService.findByUsername(principal.getName());
		model.addAttribute("funds",user.get().getFunds());
		return "profil";
	}
	
	@GetMapping("/profil/kategorije")
	public String pregledKategorija(Principal principal,WebRequest request, Model model) {
		Optional<User> user=userService.findByUsername(principal.getName());
		List<Category>kategorije=user.get().getCategories();
		model.addAttribute("kategorije", kategorije);
		return "kategorije";
	}
	
	@GetMapping("/profil/troskovi")
	public String pregledTroskova(Principal principal,WebRequest request, Model model) {
		Optional<User> user=userService.findByUsername(principal.getName());
		List<Expenditure>troskovi=expenditureService.findByUserID(user.get());
		model.addAttribute("troskovi", troskovi);
		return "troskovi";
	}
	
	@GetMapping("/profil/prihodi")
	public String pregledPrihoda(Principal principal,WebRequest request, Model model) {
		Optional<User> user=userService.findByUsername(principal.getName());
		List<Revenue>prihodi=revenueService.findByUserID(user.get());
		
		model.addAttribute("prihodi", prihodi);
		
		return "prihodi";
	}
	
	@GetMapping("/profil/stednje")
	public String pregledStednji(Principal principal,WebRequest request, Model model) {
		Optional<User> user=userService.findByUsername(principal.getName());
		List<Savings>stednje=savingsService.findByUserID(user.get());
		model.addAttribute("stednje", stednje);
		return "stednje";
	}
	
	@GetMapping("/profil/stednje/zajednicke")
	public String pregledZajednickihStednji() {
		//TODO
		return "zajednickeStednje";
	}
	
	@GetMapping("/profil/kategorije/stvori")
	public String showCategoryForm(WebRequest request, Model model, Principal principal) {
		Optional<User> user=userService.findByUsername(principal.getName());
		model.addAttribute("category", new Category());
		return "newcategory";
	}
	
	@GetMapping("/profil/troskovi/stvori")
	public String showExpenditureForm(WebRequest request, Model model, Principal principal) {
		Expenditure expenditure = new Expenditure();
		Optional<User> user=userService.findByUsername(principal.getName());
		List<Category>kategorije=user.get().getCategories();
		model.addAttribute("expenditure", expenditure);
		model.addAttribute("kategorije", kategorije);
		return "newexpenditure";
	}

	@PostMapping("/profil/kategorije/stvori")
	public ModelAndView stvoriNoviTrosak(@ModelAttribute("category") Category category, 
			  BindingResult result, 
			  WebRequest request, 
			  Errors errors,Principal principal) {
		Optional<User> user=userService.findByUsername(principal.getName());
		List<Category> listaKategorija=user.get().getCategories();
		listaKategorija.add(category);
		categoryService.createCategory(category);
		user.get().setCategories(listaKategorija);
		return new ModelAndView ("successcategory","category", category);
		
	}
	
	@PostMapping("/profil/troskovi/stvori")
	public ModelAndView stvoriNoviTrosak(@ModelAttribute("expenditure") Expenditure newExpenditure, 
			  BindingResult result, 
			  WebRequest request, 
			  Errors errors,Principal principal) {
		Optional<User> user=userService.findByUsername(principal.getName());
		newExpenditure.setUserID(user.get());
		expenditureService.createExpenditure(newExpenditure);
		return new ModelAndView ("successexpenditure","expenditure", newExpenditure);
		
	}
	
	@GetMapping("/profil/prihodi/stvori")
	public String showRevenueForm(WebRequest request, Model model, Principal principal) {
		Revenue revenue = new Revenue();
		Optional<User> user=userService.findByUsername(principal.getName());
		List<Category>kategorije=user.get().getCategories();
		model.addAttribute("revenue", revenue);
		model.addAttribute("kategorije", kategorije);
		return "newrevenue";
	}
	
	@PostMapping("/profil/prihodi/stvori")
	public ModelAndView stvoriNoviPrihod(@ModelAttribute("revenue") Revenue newRevenue, 
			  BindingResult result, 
			  WebRequest request, 
			  Errors errors,Principal principal) {
		Optional<User> user=userService.findByUsername(principal.getName());
		newRevenue.setUserID(user.get());
		revenueService.createRevenue(newRevenue);
		user.get().setFunds(user.get().getFunds()+newRevenue.getAmount());
		userService.updateUser(user.get());
		return new ModelAndView ("successrevenue","revenue", newRevenue);
		
	}
	
	@GetMapping("/profil/stednje/stvori")
	public String showSavingsForm(WebRequest request, Model model) {
		Savings savings = new Savings();
		model.addAttribute("savings", savings);
		return "newsavings";
	}

	@PostMapping("/profil/stednje/stvori")
	public ModelAndView stvoriNovuStednju(@ModelAttribute("savings") Savings newSavings, 
			  BindingResult result, 
			  WebRequest request, 
			  Errors errors,Principal principal) {
		Optional<User> user=userService.findByUsername(principal.getName());
		newSavings.setUserID(user.get());
		savingsService.createSavings(newSavings);
		return new ModelAndView ("successsavings","savings", newSavings);
		
	}
	
	
	
	@PostMapping("/profil/stednje/zajednicke")
	public void stvoriNovuZajednickuStednju() {
		//TODO
		
	}
}
