package eu.pracenjetroskova.app.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eu.pracenjetroskova.app.dto.Korisnik;
import eu.pracenjetroskova.app.enumeration.Status;
import eu.pracenjetroskova.app.model.CommonBalance;
import eu.pracenjetroskova.app.model.Savings;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.model.UsersCommonBalance;
import eu.pracenjetroskova.app.model.UsersCommonBalanceId;
import eu.pracenjetroskova.app.service.CommonBalanceService;
import eu.pracenjetroskova.app.service.SavingsService;
import eu.pracenjetroskova.app.service.UserService;
import eu.pracenjetroskova.app.service.UsersCommonBalanceService;


@Controller
@RequestMapping("stednje")
public class SavingsController {

	private final SavingsService savingsService;
	private final UserService userService;
	private final UsersCommonBalanceService uCBService;
	private final CommonBalanceService commonBalanceService;
	
	@Autowired
	public SavingsController(SavingsService savingsService, UserService userService,
			UsersCommonBalanceService uCBService, CommonBalanceService commonBalanceService) {
		super();
		this.savingsService = savingsService;
		this.userService = userService;
		this.uCBService = uCBService;
		this.commonBalanceService = commonBalanceService;
	}



	@GetMapping
	public String prikazSvihUnosa() {
		//TODO
		return "unosi";
	}
	@GetMapping("/zajednicke")
	public List<String> prikazLogaZaZajednickuStednju(){
		//TODO
		return new ArrayList<String>();
	}
	
	@PostMapping("/povuci")
	public void povuciNovacSaStednje() {
		//TODO
	}

	@PostMapping("/prebaci")
	public void prebaciNovacSBlagaje() {
		//TODO
	}
	
	@PostMapping("/zajednicke/prihvati/{id}")
	public String prihvatiZahtjev(@PathVariable Long id, Principal principal,RedirectAttributes redir) {
		User user=userService.findByUsername(principal.getName()).get();
		CommonBalance zajednicka=commonBalanceService.findById(id).get();
		List<UsersCommonBalance> zapisi=uCBService.findByUser(user);
		UsersCommonBalance mojZapis=zapisi.stream().filter(e->e.getCommonbalance().getId().equals(id)).findFirst().get();
		mojZapis.setStatus(Status.ACCEPTED.name());
		uCBService.updateUCB(mojZapis);
		List<UsersCommonBalance> korisnici=zajednicka.getUsers();
		korisnici.add(mojZapis);
		zajednicka.setUsers(korisnici);
		commonBalanceService.updateCommon(zajednicka);
		redir.addFlashAttribute("successMsg", "Zahtjev uspješno prihvaćen");
		return "redirect:/profil/zajednicke/zahtjevi";
	}
	
	@PostMapping("/zajednicke/odbij/{id}")
	public String podbijZahtjev(@PathVariable Long id, Principal principal,RedirectAttributes redir) {
		User user=userService.findByUsername(principal.getName()).get();
		List<UsersCommonBalance> zapisi=uCBService.findByUser(user);
		UsersCommonBalance mojZapis=zapisi.stream().filter(e->e.getCommonbalance().getId().equals(id)).findFirst().get();
		mojZapis.setStatus(Status.DECLIEND.name());
		uCBService.updateUCB(mojZapis);
		redir.addFlashAttribute("successMsg", "Zahtjev uspješno odbijen");
		return "redirect:/profil/zajednicke/zahtjevi";
	}
	
	@GetMapping("/zajednicke/pozovi/{id}")
	public String pozoviKorisnikaForm(@PathVariable Long id, Model model, Principal principal) {
		Korisnik korisnik=new Korisnik();
		korisnik.setId(id);
		model.addAttribute("korisnik", korisnik);
		return "adduser";
	}
	
	@PostMapping("/zajednicke/pozovi")
	public String pozoviKorisnika(@ModelAttribute("korisnik") @Valid  Korisnik korisnik, BindingResult result, Principal principal, Model model, RedirectAttributes redir) {
		
		if(!korisnik.getName().equals("") && !userService.findByUsername(korisnik.getName()).isPresent()) {
			result.rejectValue("name", "adduser.korisnik.name");
		}
		if(result.hasErrors()) {
			model.addAttribute("korisnik", korisnik);
			return "adduser";
		}else {
			User user=userService.findByUsername(korisnik.getName()).get();
			UsersCommonBalanceId uCbId=new UsersCommonBalanceId(user.getId(), korisnik.getId());
			CommonBalance zajednicka=commonBalanceService.findById(korisnik.getId()).get();
			uCBService.createUCB(new UsersCommonBalance(uCbId, user, zajednicka, Status.WAITING.name()));
			redir.addFlashAttribute("successMsg", "Zahtjev uspješno poslan");
			return "redirect:/profil/zajednicke";
		}
		
		
	}

	@PostMapping("/izbrisi/{id}")
	public String deleteStednja(@PathVariable Long id, Principal principal) {
		User user=userService.findByUsername(principal.getName()).get();
		user.setFunds(user.getFunds()+savingsService.findById(id).get().getFunds());
		userService.updateUser(user);
		savingsService.deleteSavings(id);
		return "redirect:/profil/stednje";
	}
	
	@GetMapping("/osvjezi/{id}")
	public String showUpdateStednja(@PathVariable Long id, Model model, Principal principal) {
		model.addAttribute("editstednja", savingsService.findById(id).get());
		return "updatesavings";
	}
	
	@PostMapping("/osvjezi")
	public String updateStednja(@ModelAttribute("editstednja") @Valid Savings savings, BindingResult bindingResult, Principal principal, Model model) {
		User user=userService.findByUsername(principal.getName()).get();
		try {
			savings.setStartDate(formatiranjeDatuma(savings.getStartDate()));
			savings.setEndDate(formatiranjeDatuma(savings.getEndDate()));
		} catch (ParseException e) {
		}
		if(savings.getEndDate().before(savings.getStartDate())) {
			bindingResult.rejectValue("endDate", "savings.endDate");
		}
		if(savings.getFunds()!=null){
			if(savings.getFunds()>user.getFunds()+savingsService.findById(savings.getId()).get().getFunds()) {
				bindingResult.rejectValue("funds", "savings.funds");
			}
		}
		if(savings.getFunds()!=null && savings.getGoal()!=null){
			if(savings.getGoal()<savings.getFunds()) {
				bindingResult.rejectValue("goal", "savings.goal");
			}
		}
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("editstednja", savings);
			return "updatesavings";
		}
		
		user.setFunds(user.getFunds()+savingsService.findById(savings.getId()).get().getFunds()-savings.getFunds());
		savingsService.saveRevenue(savings);
		
		userService.updateUser(user);
		return "redirect:/profil/stednje";
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
