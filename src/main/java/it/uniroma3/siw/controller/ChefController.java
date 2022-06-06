package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.ChefService;

@Controller
public class ChefController {
	@Autowired
	private ChefService service;
	
	@GetMapping("/allChefs")
	public String getTuttiChef(Model model) {
		model.addAttribute("chefs", service.getTuttiChef());
		return "chefs.html";
	}
}
