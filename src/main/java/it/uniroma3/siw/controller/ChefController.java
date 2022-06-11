package it.uniroma3.siw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validator.ChefValidator;
import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.service.ChefService;

@Controller
public class ChefController {
	@Autowired
	private ChefService service;
	@Autowired
	private ChefValidator validator;
	
	@GetMapping("/admin/inserisciChefForm")
	public String getInserisciChefForm(Model model) {
		model.addAttribute("chef", new Chef());
		return "inserisciChefForm.html";
	}
	
	@GetMapping("/chef/all")
	public String getTuttiChef(Model model) {
		model.addAttribute("chefs", service.getTuttiChef());
		return "chefs.html";
	}
	
	@GetMapping("/chef/{id}")
	public String getChefById(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", service.getChefPerId(id));
		return "chef.html";
	}
	
	@PostMapping("/admin/inserisciChef")
	public String inserisciChef(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult, Model model) {
		validator.validate(chef, bindingResult);
		if(!bindingResult.hasErrors()) {
			service.salva(chef);
			return "confermaInserimentoChef.html";
		}
		return "inserisciChefForm.html"; 
	}
	
}
