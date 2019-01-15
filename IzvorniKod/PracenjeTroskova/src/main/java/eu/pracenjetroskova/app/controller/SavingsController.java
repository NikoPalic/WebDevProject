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
import eu.pracenjetroskova.app.dto.Transakcija;
import eu.pracenjetroskova.app.enumeration.Action;
import eu.pracenjetroskova.app.enumeration.Status;
import eu.pracenjetroskova.app.model.CommonBalance;
import eu.pracenjetroskova.app.model.Log;
import eu.pracenjetroskova.app.model.Savings;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.model.UsersCommonBalance;
import eu.pracenjetroskova.app.model.UsersCommonBalanceId;
import eu.pracenjetroskova.app.service.CommonBalanceService;
import eu.pracenjetroskova.app.service.LogService;
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
	private final LogService logService;
	
	@Autowired
	public SavingsController(SavingsService savingsService, UserService userService,
			UsersCommonBalanceService uCBService, CommonBalanceService commonBalanceService, LogService logService) {
		super();
		this.savingsService = savingsService;
		this.userService = userService;
		this.uCBService = uCBService;
		this.commonBalanceService = commonBalanceService;
		this.logService = logService;
	}




	@GetMapping("/zajednicke/{id}")
	public String infoForm(@PathVariable Long id, Principal principal, Model model) {
		CommonBalance zajednicka=commonBalanceService.findById(id).get();
		List<UsersCommonBalance> zapisi=zajednicka.getUsers();
		List<User> korisnici=zapisi.stream().map(e->e.getUser()).collect(Collectors.toList());
		List<Log> logovi=zajednicka.getLog();
		model.addAttribute("logovi", logovi);
		model.addAttribute("korisnici", korisnici);
		return "commoninfo";
	}
	
	@GetMapping("/zajednicke/prebaci/{id}")
	public String prebaciZajednickaForm(@PathVariable Long id, Principal principal,Model model) {
		Transakcija transaction=new Transakcija();
		transaction.setId(id);
		model.addAttribute("transaction", transaction);
		return "newcommontransaction";
	}
	
	@PostMapping("/zajednicke/prebaci")
	public String prebaciNaZajednicku(@ModelAttribute("transaction") @Valid Transakcija transaction, BindingResult result, Principal principal, Model model,RedirectAttributes redir) {
		User user=userService.findByUsername(principal.getName()).get();
		if(transaction.getAmount()!=null) {
			if(transaction.getAmount()>user.getFunds()) {
				result.rejectValue("amount", "newtransaction.transaction.amount");
			}
			
		}
		if(result.hasErrors()) {
			model.addAttribute("transaction", transaction);
			return "newcommontransaction";
		}else {
			CommonBalance stednja=commonBalanceService.findById(transaction.getId()).get();
			Double uplaceniIznos=0.0;
			if(stednja.getFunds()+transaction.getAmount()>stednja.getGoal()) {
				stednja.setFunds(stednja.getGoal());
				user.setFunds(user.getFunds()-(stednja.getGoal()-stednja.getFunds()));
				uplaceniIznos=stednja.getGoal()-stednja.getFunds();
				redir.addFlashAttribute("successMsg", "Uspješno ste uplatili novce u zajedničku štednju "+stednja.getInfo()+"! Višak novaca je vraćen u blagajnu!");
			}else {
				stednja.setFunds(stednja.getFunds()+transaction.getAmount());
				user.setFunds(user.getFunds()-transaction.getAmount());
				uplaceniIznos=transaction.getAmount();
				redir.addFlashAttribute("successMsg", "Uspješno ste uplatili "+transaction.getAmount()+" u zajedničku štednju: "+stednja.getInfo());
			}
			Log log=new Log(null, Action.UPLATA.name(), Calendar.getInstance().getTime(), uplaceniIznos);
			log.setCbID(stednja);
			log.setUserID(user);
			log=logService.createLog(log);
			List<Log>logovi=stednja.getLog();
			logovi.add(log);
			stednja.setLog(logovi);
			commonBalanceService.updateCommon(stednja);
			userService.updateUser(user);
			return "redirect:/profil/zajednicke";
		}
	}
	
	@GetMapping("/zajednicke/povuci/{id}")
	public String povuciZajednickeForm(@PathVariable Long id, Principal principal,Model model) {
		Transakcija transaction=new Transakcija();
		transaction.setId(id);
		model.addAttribute("transaction", transaction);
		return "newcommontrans";
	}
	
	@PostMapping("/zajednicke/povuci")
	public String povuciNovacZajednicka(@ModelAttribute("transaction") @Valid Transakcija transaction, BindingResult result, Principal principal, Model model,RedirectAttributes redir) {
		User user=userService.findByUsername(principal.getName()).get();
		CommonBalance stednja=commonBalanceService.findById(transaction.getId()).get();
		Double isplaceniIznos=0.0;
		if(transaction.getAmount()!=null) {
			if(transaction.getAmount()>stednja.getFunds()) {
				result.rejectValue("amount", "newtransaction.transaction.funds");
			}
			
		}
		
		if(result.hasErrors()) {
			model.addAttribute("transaction", transaction);
			return "newcommontrans";
		}else {
			stednja.setFunds(stednja.getFunds()-transaction.getAmount());
			user.setFunds(user.getFunds()+transaction.getAmount());
			redir.addFlashAttribute("successMsg", "Uspješno ste isplatili "+transaction.getAmount()+" sa zajedničke štednje: "+stednja.getInfo());
			isplaceniIznos=transaction.getAmount();
			Log log=new Log(null, Action.ISPLATA.name(), Calendar.getInstance().getTime(), isplaceniIznos);
			log.setCbID(stednja);
			log.setUserID(user);
			log=logService.createLog(log);
			List<Log>logovi=stednja.getLog();
			logovi.add(log);
			stednja.setLog(logovi);
			commonBalanceService.updateCommon(stednja);
			userService.updateUser(user);
			return "redirect:/profil/zajednicke";
		}
	}
	
	@GetMapping("/povuci/{id}")
	public String povuciForm(@PathVariable Long id, Principal principal,Model model) {
		Transakcija transaction=new Transakcija();
		transaction.setId(id);
		model.addAttribute("transaction", transaction);
		return "newtrans";
	}
	
	@PostMapping("/povuci")
	public String povuciNovacSaStednje(@ModelAttribute("transaction") @Valid Transakcija transaction, BindingResult result, Principal principal, Model model,RedirectAttributes redir) {
		User user=userService.findByUsername(principal.getName()).get();
		Savings stednja=savingsService.findById(transaction.getId()).get();
		if(transaction.getAmount()!=null) {
			if(transaction.getAmount()>stednja.getFunds()) {
				result.rejectValue("amount", "newtransaction.transaction.funds");
			}
			
		}
		
		if(result.hasErrors()) {
			model.addAttribute("transaction", transaction);
			return "newtrans";
		}else {
			stednja.setFunds(stednja.getFunds()-transaction.getAmount());
			user.setFunds(user.getFunds()+transaction.getAmount());
			redir.addFlashAttribute("successMsg", "Uspješno ste isplatili "+transaction.getAmount()+" sa štednje: "+stednja.getInfo());
			savingsService.saveRevenue(stednja);
			userService.updateUser(user);
			return "redirect:/profil/stednje";
		}
	}

	@GetMapping("/prebaci/{id}")
	public String prebaciNovacSBlagajneForm(@PathVariable Long id, Principal principal,Model model) {
		Transakcija transaction=new Transakcija();
		transaction.setId(id);
		model.addAttribute("transaction", transaction);
		return "newtransaction";
	}
	
	@PostMapping("/prebaci")
	public String prebaciNaStednju(@ModelAttribute("transaction") @Valid Transakcija transaction, BindingResult result, Principal principal, Model model,RedirectAttributes redir) {
		User user=userService.findByUsername(principal.getName()).get();
		if(transaction.getAmount()!=null) {
			if(transaction.getAmount()>user.getFunds()) {
				result.rejectValue("amount", "newtransaction.transaction.amount");
			}
			
		}
		if(result.hasErrors()) {
			model.addAttribute("transaction", transaction);
			return "newtransaction";
		}else {
			Savings stednja=savingsService.findById(transaction.getId()).get();
			if(stednja.getFunds()+transaction.getAmount()>stednja.getGoal()) {
				stednja.setFunds(stednja.getGoal());
				user.setFunds(user.getFunds()-(stednja.getGoal()-stednja.getFunds()));
				redir.addFlashAttribute("successMsg", "Uspješno ste uplatili novce u štednju "+stednja.getInfo()+"! Višak novaca je vraćen u blagajnu!");
			}else {
				stednja.setFunds(stednja.getFunds()+transaction.getAmount());
				user.setFunds(user.getFunds()-transaction.getAmount());
				redir.addFlashAttribute("successMsg", "Uspješno ste uplatili "+transaction.getAmount()+" u štednju: "+stednja.getInfo());
			}
			savingsService.saveRevenue(stednja);
			userService.updateUser(user);
			return "redirect:/profil/stednje";
		}
		
		
		
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
