package eu.pracenjetroskova.app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfilController {

	
	
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
	public String pregledPrihoda() {
		//TODO
		return "prihodi";
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
	
	@PostMapping("/profil/prihodi/stvori")
	public void stvoriNoviPrihod() {
		//TODO
		
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
