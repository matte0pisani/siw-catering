package it.uniroma3.siw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validator.IngredienteValidator;
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
	
	@PostMapping("/admin/inserisciIngrediente")
	public String inserisciIngrediente(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, BindingResult bindingResult, Model model) {
		validator.validate(ingrediente, bindingResult);
		if(!bindingResult.hasErrors()) {
			service.salva(ingrediente);
			return "confermaInserimentoIngrediente.html";
		}
		return "inserisciIngredienteForm.html"; 
	}
}
