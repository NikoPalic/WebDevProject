package eu.pracenjetroskova.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("troskovi")
public class ExpenditureController {

	@GetMapping
	public String ispisTroskova() {
		//TODO
		return "ispis";
	}
	
	@PostMapping
	public void deleteTrosak() {
		//TODO
	}
	
	@PostMapping
	public void updateTrosak() {
		//TODO
	}
}
