package eu.pracenjetroskova.app.controller;


import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.LogRecord;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import eu.pracenjetroskova.app.model.Category;
import eu.pracenjetroskova.app.model.CommonBalance;
import eu.pracenjetroskova.app.model.CustomUserDetails;
import eu.pracenjetroskova.app.model.Expenditure;
import eu.pracenjetroskova.app.model.Log;
import eu.pracenjetroskova.app.model.Revenue;
import eu.pracenjetroskova.app.model.Savings;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.model.UsersCommonBalance;
import eu.pracenjetroskova.app.model.UsersCommonBalanceId;
import eu.pracenjetroskova.app.repository.CategoryRepository;
import eu.pracenjetroskova.app.repository.ExpenditureRepository;
import eu.pracenjetroskova.app.repository.LogRepository;
import eu.pracenjetroskova.app.repository.RevenueRepository;
import eu.pracenjetroskova.app.repository.SavingsRepository;
import eu.pracenjetroskova.app.repository.UserRepository;
import eu.pracenjetroskova.app.service.RevenueService;
import eu.pracenjetroskova.app.service.SavingsService;
import eu.pracenjetroskova.app.service.UserService;
import eu.pracenjetroskova.app.service.UsersCommonBalanceService;
import eu.pracenjetroskova.app.service.CategoryService;
import eu.pracenjetroskova.app.service.CommonBalanceService;
import eu.pracenjetroskova.app.service.ExpenditureService;
import eu.pracenjetroskova.app.service.LogService;
import eu.pracenjetroskova.app.enumeration.Action;
import eu.pracenjetroskova.app.enumeration.Status;

@Controller
public class ProfilController {

	private final UserService userService; 
	private final RevenueService revenueService;
	private final ExpenditureService expenditureService;
	private final SavingsService savingsService;
	private final CategoryService categoryService;
	private final CommonBalanceService commonBalanceService;
	private final LogService logService;
	private final UsersCommonBalanceService uCBService;
	
	
	
	@Autowired
	public ProfilController(UserService userService, RevenueService revenueService,
			ExpenditureService expenditureService, SavingsService savingsService, CategoryService categoryService,
			CommonBalanceService commonBalanceService, LogService logService, UsersCommonBalanceService uCBService) {
		super();
		this.userService = userService;
		this.revenueService = revenueService;
		this.expenditureService = expenditureService;
		this.savingsService = savingsService;
		this.categoryService = categoryService;
		this.commonBalanceService = commonBalanceService;
		this.logService = logService;
		this.uCBService = uCBService;
	}


	@GetMapping("/")
	public String homepage(Principal principal,Model model) {
		User user = userService.findByUsername(principal.getName()).get();
		List<Expenditure> expenditures=expenditureService.findByUserID(user);
		List<Revenue> revenues=revenueService.findByUserID(user);
		List<Savings> stednje=savingsService.findByUserID(user);
		List<Savings> istekleStednje=stednje.stream().filter(e->e.getEndDate().before(Calendar.getInstance().getTime())).collect(Collectors.toList());
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		List<Expenditure> dobriTroskovi=expenditures.stream().filter(e->e.getDate().after(calendar.getTime())).collect(Collectors.toList());
		List<Revenue> dobriPrihodi=revenues.stream().filter(e->e.getDate().after(calendar.getTime())).collect(Collectors.toList());
		Double iznosTroskova=dobriTroskovi.stream().mapToDouble(e->e.getAmount()).sum();
		Double iznosPrihoda=dobriPrihodi.stream().mapToDouble(e->e.getAmount()).sum();
		boolean flagPotrosnja;
		String poruka="";
		if(iznosTroskova>=iznosPrihoda && iznosTroskova!=0.0) {
			flagPotrosnja=true;
			poruka="Previ??e tro??ite! U zadnjih mjesec dana va??i prihodi iznose "+iznosPrihoda + ", a tro??kovi "+iznosTroskova;
		}else if (iznosTroskova>=iznosPrihoda*0.85 && iznosTroskova!=0.0) {
			flagPotrosnja=true;
			poruka="Razlika izme??u va??ih prihoda i tro??kova u zadnjih mjesec dana manja je ili jednaka 15%!";
		}else {
			flagPotrosnja=false;
		}
		List<Savings> gotoveStednje=new ArrayList<>();
		for (Savings savings : stednje) {
			Double iznos=savings.getFunds();
			Double cilj=savings.getGoal();
			
			if(!(iznos<cilj)) {
				gotoveStednje.add(savings);
			}
		}
		
		boolean istSteZas;
		boolean gotSteZas;
		if(istekleStednje.isEmpty()) {
			istSteZas=false;
		}else {
			istSteZas=true;
		}
		if(gotoveStednje.isEmpty()) {
			gotSteZas=false;
		}else {
			gotSteZas=true;
		}
		String istekle=istekleStednje.stream().map(e->e.getInfo()).collect(Collectors.joining(" ; "));
		String gotove=gotoveStednje.stream().map(e->e.getInfo()).collect(Collectors.joining(" ; "));
		List<UsersCommonBalance> useroveStednje=uCBService.findByUser(user);
		List<CommonBalance> zajednicke=useroveStednje.stream().filter(e->e.getStatus().equals(Status.ACCEPTED.name())).map(e->e.getCommonbalance()).collect(Collectors.toList());
		List<CommonBalance> gotoveZajedncike=new ArrayList<>();
		for(CommonBalance common: zajednicke) {
			Double iznos=common.getFunds();
			Double cilj=common.getGoal();
			if(!(iznos<cilj)) {
				gotoveZajedncike.add(common);
			}
		}
		String dostigleCilj=gotoveZajedncike.stream().map(e->e.getName()).collect(Collectors.joining(" ; "));
		boolean flagZajednickeGotove;
		if(gotoveZajedncike.isEmpty()) {
			flagZajednickeGotove=false;
		}else {
			flagZajednickeGotove=true;
		}
		List<CommonBalance> zahtjevi = useroveStednje.stream().filter(t->t.getStatus().equals(Status.WAITING.name())).map(e->e.getCommonbalance()).collect(Collectors.toList());
		int brojZahtjeva=zahtjevi.size();
		boolean flagZahtjevi;
		if(brojZahtjeva>0) {
			flagZahtjevi=true;
		}else {
			flagZahtjevi=false;
		}
		model.addAttribute("flagZahtjevi", flagZahtjevi);
		model.addAttribute("flagZajednickeGotove", flagZajednickeGotove);
		model.addAttribute("dostigleCilj","Zajedni??ke ??tednje koje su dostigle cilj: "+dostigleCilj);
		model.addAttribute("brojZahtjeva", "Imate nove zahtjeve za pridru??ivanje zajedni??kim ??tednjama ("+brojZahtjeva+")!");
		model.addAttribute("expenditures", expenditures);
		model.addAttribute("revenues", revenues);
		model.addAttribute("flagStednje", istSteZas);
		model.addAttribute("flagStednjeGotove", gotSteZas);
		model.addAttribute("flagPotrosnja", flagPotrosnja);
		model.addAttribute("poruka", poruka);
		model.addAttribute("istekle","??tednje koje su istekle s trenutnim vremenom : "+istekle);
		model.addAttribute("gotove","??tednje koje su dostigle cilj: "+gotove);
		return "home";
	}
	
	@RequestMapping(value = "/metric-graph-data", method = RequestMethod.GET)
	@ResponseBody
	public Object [][] getGraphData(Principal principal){
		User user = userService.findByUsername(principal.getName()).get();
		List<Expenditure> expenditures=expenditureService.findByUserID(user);
		Object [][] result = new Object [expenditures.size()+1][2];
		result[0][0]="Ime";
		result[0][1]="Iznos";
		
		int i = 1;
		for(Expenditure e : expenditures) {
			result[i][0] = e.getName();
			result[i][1] = e.getAmount();
			i++;
		}
		return result;
	}
	
	@RequestMapping(value = "/revenue-graph-data", method = RequestMethod.GET)
	@ResponseBody
	public Object [][] getGraphDataRevenue(Principal principal){
		User user = userService.findByUsername(principal.getName()).get();
		List<Revenue> prihodi=revenueService.findByUserID(user);
		Object [][] result = new Object [prihodi.size()+1][2];
		result[0][0]="Ime";
		result[0][1]="Iznos";
		
		int i = 1;
		for(Revenue e : prihodi) {
			result[i][0] = e.getName();
			result[i][1] = e.getAmount();
			i++;
		}
		return result;
	}
	
	@RequestMapping(value = "/expenditure-graph-data", method = RequestMethod.GET)
	@ResponseBody
	public Object [][] getGraphDataForExpenditureLineChart(Principal principal){
		User user = userService.findByUsername(principal.getName()).get();
		List<Expenditure> expenditures=expenditureService.findByUserID(user);
		Collections.sort(expenditures,(s1,s2)-> s1.getDate().compareTo(s2.getDate()));
		Object [][] result = new Object [expenditures.size()+1][2];
		result[0][0]="Datum";
		result[0][1]="Potro??nja";
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		int i = 1;
		for(Expenditure e : expenditures) {
			
			result[i][0] = dateFormatter.format(e.getDate());
			result[i][1] = e.getAmount();
			i++;
		}
		return result;
	}

	@RequestMapping(value = "/revenue-graph-data-line", method = RequestMethod.GET)
	@ResponseBody
	public Object [][] getGraphDataRevenueLine(Principal principal){
		User user = userService.findByUsername(principal.getName()).get();
		List<Revenue> prihodi=revenueService.findByUserID(user);
		Collections.sort(prihodi,(s1,s2)-> s1.getDate().compareTo(s2.getDate()));
		Object [][] result = new Object [prihodi.size()+1][2];
		result[0][0]="Datum";
		result[0][1]="Prihodi";
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		int i = 1;
		for(Revenue e : prihodi) {
			result[i][0] = dateFormatter.format(e.getDate());
			result[i][1] = e.getAmount();
			i++;
		}
		return result;
	}
	
	@GetMapping("/profil")
	public String mojProfil(Principal principal,WebRequest request, Model model) {
		Optional<User> user=userService.findByUsername(principal.getName());
		List<UsersCommonBalance> useroveStednje=uCBService.findByUser(user.get());
		List<CommonBalance> zajednicke = useroveStednje.stream().filter(t->t.getStatus().equals(Status.ACCEPTED.name())).map(e->e.getCommonbalance()).collect(Collectors.toList());
		model.addAttribute("zajednicke", zajednicke);
		model.addAttribute("funds",user.get().getFunds());
		model.addAttribute("stednje", user.get().getSavings());
	
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
	
	@GetMapping("/profil/zajednicke")
	public String pregledZajednickihStednji(Principal principal, Model model) {
		User user=userService.findByUsername(principal.getName()).get();
		List<UsersCommonBalance> useroveStednje=uCBService.findByUser(user);
		List<CommonBalance> zajednicke = useroveStednje.stream().filter(t->t.getStatus().equals(Status.ACCEPTED.name())).map(e->e.getCommonbalance()).collect(Collectors.toList());
		
		model.addAttribute("zajednicke", zajednicke);
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
	public ModelAndView stvoriNoviTrosak(@ModelAttribute("category") @Valid Category category, 
			  BindingResult result, 
			  WebRequest request, 
			  Errors errors,Principal principal,RedirectAttributes redir) {
		if(result.hasErrors()) {
			return new ModelAndView("newcategory","category",category);
		}
		Optional<User> user=userService.findByUsername(principal.getName());
		List<Category> listaKategorija=user.get().getCategories();
		listaKategorija.add(category);
		categoryService.createCategory(category);
		user.get().setCategories(listaKategorija);
		redir.addFlashAttribute("successMsg", "Uspje??no ste stvorili novu kategoriju!");
		return new ModelAndView("redirect:/profil/kategorije");
		
	}
	
	@PostMapping("/profil/troskovi/stvori")
	public ModelAndView stvoriNoviTrosak(@ModelAttribute("expenditure") @Valid Expenditure newExpenditure, 
			  BindingResult result, 
			  WebRequest request, 
			  Errors errors,Principal principal,RedirectAttributes redir) {
		Optional<User> user=userService.findByUsername(principal.getName());
		try {
			newExpenditure.setDate(formatiranjeDatuma(newExpenditure.getDate()));
		} catch (ParseException e) {
		}
		
		if(result.hasErrors()) {
			List<Category>kategorije=user.get().getCategories();
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("expenditure", newExpenditure);
			model.put("kategorije", kategorije);
			return new ModelAndView("newexpenditure",model);
		}
		newExpenditure.setUserID(user.get());
		expenditureService.createExpenditure(newExpenditure);
		user.get().setFunds(user.get().getFunds()-newExpenditure.getAmount());
		userService.updateUser(user.get());
		redir.addFlashAttribute("successMsg", "Uspje??no ste stvorili novi tro??ak!");
		return new ModelAndView("redirect:/profil/troskovi");
		
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
	public ModelAndView stvoriNoviPrihod(@ModelAttribute("revenue") @Valid Revenue newRevenue, 
			  BindingResult result, 
			  WebRequest request, 
			  Errors errors,Principal principal,RedirectAttributes redir) {
		Optional<User> user=userService.findByUsername(principal.getName());
		try {
			newRevenue.setDate(formatiranjeDatuma(newRevenue.getDate()));
		} catch (ParseException e) {
		}
		if(result.hasErrors()) {
			List<Category>kategorije=user.get().getCategories();
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("revenue", newRevenue);
			model.put("kategorije", kategorije);
			return new ModelAndView("newrevenue",model);
		}
		newRevenue.setUserID(user.get());
		revenueService.createRevenue(newRevenue);
		user.get().setFunds(user.get().getFunds()+newRevenue.getAmount());
		userService.updateUser(user.get());
		redir.addFlashAttribute("successMsg", "Uspje??no ste stvorili novi prihod!");
		return new ModelAndView("redirect:/profil/prihodi");
	}
	
	@GetMapping("/profil/stednje/stvori")
	public String showSavingsForm(WebRequest request, Model model) {
		Savings savings = new Savings();
		model.addAttribute("savings", savings);
		return "newsavings";
	}

	@PostMapping("/profil/stednje/stvori")
	public ModelAndView stvoriNovuStednju(@ModelAttribute("savings") @Valid Savings newSavings, 
			  BindingResult result, 
			  WebRequest request, 
			  Errors errors,Principal principal,RedirectAttributes redir) {
		Optional<User> user=userService.findByUsername(principal.getName());
		try {
			newSavings.setStartDate(formatiranjeDatuma(newSavings.getStartDate()));
			newSavings.setEndDate(formatiranjeDatuma(newSavings.getEndDate()));
		} catch (ParseException e) {
		}
		if(newSavings.getEndDate().before(newSavings.getStartDate())) {
			result.rejectValue("endDate", "savings.endDate");
		}
		if(newSavings.getFunds()!=null){
			if(newSavings.getFunds()>user.get().getFunds()) {
				result.rejectValue("funds", "savings.funds");
			}
		}
		if(newSavings.getFunds()!=null && newSavings.getGoal()!=null) {
			if(newSavings.getGoal()<newSavings.getFunds()) {
				result.rejectValue("goal", "savings.goal");
			}
		}
		if (result.hasErrors()) {
			return new ModelAndView("newsavings","savings", newSavings);
		}
		newSavings.setUserID(user.get());
		savingsService.createSavings(newSavings);
		user.get().setFunds(user.get().getFunds()-newSavings.getFunds());
		userService.updateUser(user.get());
		redir.addFlashAttribute("successMsg", "Uspje??no ste stvorili novu ??tednju!");
		return new ModelAndView("redirect:/profil/stednje");
		
	}
	
	
	
	@GetMapping("/profil/zajednicke/stvori")
	public String showCommonBalanceForm(WebRequest request, Model model) {
		CommonBalance common = new CommonBalance();
		model.addAttribute("common", common);
		return "newcommon";
	}
	
	@PostMapping("/profil/zajednicke/stvori")
	public ModelAndView stvoriNovuZajednicku(@ModelAttribute("common") @Valid CommonBalance common, BindingResult result,Principal principal,RedirectAttributes redir) {
		
		User user=userService.findByUsername(principal.getName()).get();
		try {
			common.setDate(formatiranjeDatuma(common.getDate()));
		} catch (ParseException e) {
		}
		
		if(common.getFunds()!=null){
			if(common.getFunds()>user.getFunds()) {
				result.rejectValue("funds", "savings.funds");
			}
		}
		if(common.getFunds()!=null && common.getGoal()!=null) {
			if(common.getGoal()<common.getFunds()) {
				result.rejectValue("goal", "savings.goal");
			}
		}
		
		if(result.hasErrors()) {
			return new ModelAndView("newcommon","common",common);
		}else {

			CommonBalance stvorena=commonBalanceService.createCommon(common);
			Log log=new Log(null, Action.STVARANJE.name(), Calendar.getInstance().getTime(), common.getFunds());
			log.setCbID(stvorena);
			log.setUserID(user);
			List<Log> logovi=new ArrayList<>();
			logovi.add(logService.createLog(log));
			stvorena.setLog(logovi);
			UsersCommonBalanceId uCbId=new UsersCommonBalanceId(user.getId(), stvorena.getId());
			UsersCommonBalance pomocni=uCBService.createUCB(new UsersCommonBalance(uCbId, user,stvorena, Status.ACCEPTED.name()));
			List<UsersCommonBalance> korisnici=stvorena.getUsers();
			korisnici.add(pomocni);
			stvorena.setUsers(korisnici);
			commonBalanceService.updateCommon(stvorena);
			
			user.setFunds(user.getFunds()-common.getFunds());
			userService.updateUser(user);
			redir.addFlashAttribute("successMsg","Uspje??no ste stvorili novu zajedni??ku ??tednju");
			return new ModelAndView("redirect:/profil/zajednicke");
		}
	}
	
	@GetMapping("/profil/zajednicke/zahtjevi")
	public String dohvatiZahtjeve(Principal principal, Model model) {
		User user=userService.findByUsername(principal.getName()).get();
		List<UsersCommonBalance> useroveStednje=uCBService.findByUser(user);
		List<CommonBalance> zahtjevi = useroveStednje.stream().filter(t->t.getStatus().equals(Status.WAITING.name())).map(e->e.getCommonbalance()).collect(Collectors.toList());
		model.addAttribute("zahtjevi", zahtjevi);
		return "zahtjevi";
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
