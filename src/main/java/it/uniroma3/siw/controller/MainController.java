package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	@RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
	public String index(Model model) {
			return "index";
	}
	
	@GetMapping("/admin/getAmministratorePage") 
	public String getAmministratorePage(Model model) {
		return "amministratore";
	}
	
	@GetMapping("/info") 
	public String getInfoPage(Model model) {
		return "info";
	}
}