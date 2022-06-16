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
	
	@GetMapping("/admin/modificaChef/{id}")
	public String modificaChefForm(@PathVariable("id") Long id, Model model) {
		Chef chef = service.getChefPerId(id);
		model.addAttribute("chef", chef);
		return "modificaChefForm.html";
	}
	
	@GetMapping("/admin/chefPage")
	public String getBuffetPage(Model model) {
		model.addAttribute("admin", true);
		model.addAttribute("chefs", service.getTuttiChef());
		return "chefs.html";
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
			model.addAttribute("chefNuovo", chef);
			model.addAttribute("chefs", service.getTuttiChef());
			model.addAttribute("admin", true);
			return "chefs.html";
		}
		return "inserisciChefForm.html"; 
	}
	
	@PostMapping("/admin/modificaChef/{id}")
	public String modificaChef(@PathVariable("id") Long id, @Valid Chef chef,
								BindingResult bindingResult, Model model) {
		Chef oldChef = service.getChefPerId(id);
		if(!bindingResult.hasErrors()) {
			// no id
			oldChef.setNome(chef.getNome());
			oldChef.setCognome(chef.getCognome());
			oldChef.setNazionalita(chef.getNazionalita());
			service.save(oldChef);
			
			model.addAttribute("chefModificato", oldChef);
			model.addAttribute("chefs", service.getTuttiChef());
			model.addAttribute("admin", true);
			return "chefs.html";
		}
		model.addAttribute("modificaNonValida", true);
		model.addAttribute("chef", oldChef);
		return "modificaChefForm.html";
	}
		
	@GetMapping("/admin/eliminaChef/{id}")
	public String eliminaUnoChef(@PathVariable("id") Long id, Model model) {
		Chef chef = service.rimuoviPerId(id);
		model.addAttribute("chefRimosso", chef);
		model.addAttribute("chefs", service.getTuttiChef());
		model.addAttribute("admin", true);
		return "chefs.html";
	}
	
	
}
