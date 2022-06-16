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

import it.uniroma3.siw.controller.validator.IngredienteValidator;
import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.service.IngredienteService;

@Controller
public class IngredienteController {
	@Autowired
	private IngredienteService service;
	@Autowired
	private IngredienteValidator validator;
	
	@GetMapping("/admin/inserisciIngredienteForm")
	public String getInserisciIngredienteForm(Model model) {
		model.addAttribute("ingrediente", new Ingrediente());
		return "inserisciIngredienteForm.html";
	}
	
	@GetMapping("/admin/ingredientiPage")
	public String getBuffetPage(Model model) {
		model.addAttribute("ingredienti", service.getTuttiIngredienti());
		return "ingredienti.html";
	}
	
	@PostMapping("/admin/inserisciIngrediente")
	public String inserisciIngrediente(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, BindingResult bindingResult, Model model) {
		validator.validate(ingrediente, bindingResult);
		if(!bindingResult.hasErrors()) {
			service.salva(ingrediente);
			model.addAttribute("ingredienteNuovo", ingrediente);
			model.addAttribute("ingredienti", service.getTuttiIngredienti());
			return "ingredienti.html";
		}
		return "inserisciIngredienteForm.html"; 
	}
	
	@GetMapping("/admin/eliminaIngrediente/{id}")
	public String eliminaIngrediente(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = service.rimuoviBuffet(id);
		model.addAttribute("ingredienteRimosso", ingrediente);
		model.addAttribute("ingredienti", service.getTuttiIngredienti());
		return "ingredienti.html";
	}
}
