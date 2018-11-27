package eu.pracenjetroskova.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("stednje")
public class SavingsController {

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
	
	@PostMapping
	public void povuciNovacSaStednje() {
		//TODO
	}

	@PostMapping
	public void prebaciNovacSBlagaje() {
		//TODO
	}

	
	
}
