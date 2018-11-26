package eu.pracenjetroskova.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("prihodi")
public class RevenueController {

	@GetMapping
	public String ispisPrihoda() {
		//TODO
		return "ispis";
	}
	
	@PostMapping
	public void deletePrihod() {
		//TODO
	}
	
	@PostMapping
	public void updatePrihod() {
		//TODO
	}
}
