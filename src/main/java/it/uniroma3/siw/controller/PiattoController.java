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

import it.uniroma3.siw.controller.validator.PiattoValidator;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.PiattoService;

@Controller
public class PiattoController {
	@Autowired
	private PiattoService piattoService;
	@Autowired
	private PiattoValidator piattoValidator;
	@Autowired
	private IngredienteService ingredienteService;
	
	@GetMapping("/piatto/{id}")
	public String getPiattoById(@PathVariable("id") Long id, Model model) {
		model.addAttribute("piatto", piattoService.getPiattoPerId(id));
		return "piatto.html";
	}
	
	@GetMapping("/inserisciPiattoForm")
	public String getInserisciPiattoForm(Model model) {
		model.addAttribute("piatto", new Piatto());
		model.addAttribute("allIngredienti", ingredienteService.getTuttiIngredienti());
		return "inserisciPiattoForm.html";
	}
	
	@PostMapping("/inserisciPiatto")
	public String inserisciPiatto(@Valid @ModelAttribute("piatto") Piatto piatto, BindingResult bindingResult, Model model) {
		piattoValidator.validate(piatto, bindingResult);
		if(!bindingResult.hasErrors()) {
			piattoService.salva(piatto);
			return "confermaInserimentoPiatto";
		}
		model.addAttribute("allIngredienti", ingredienteService.getTuttiIngredienti());
		return "inserisciPiattoForm";
	}

}
