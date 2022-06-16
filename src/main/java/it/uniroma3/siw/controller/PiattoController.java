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
import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.service.BuffetService;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.PiattoService;

@Controller
public class PiattoController {
	@Autowired
	private PiattoService piattoService;
	@Autowired
	private BuffetService buffetService;
	@Autowired
	private PiattoValidator piattoValidator;
	@Autowired
	private IngredienteService ingredienteService;
	
	@GetMapping("/admin/inserisciPiattoForm")
	public String getInserisciPiattoForm(Model model) {
		model.addAttribute("piatto", new Piatto());
		model.addAttribute("allIngredienti", ingredienteService.getTuttiIngredienti());
		return "inserisciPiattoForm.html";
	}
	
	@GetMapping("/admin/piattiPage")
	public String getPiattiPage(Model model) {
		model.addAttribute("piatti", piattoService.getTuttiPiatti());
		return "piatti.html";
	}
	
	@GetMapping("/piatto/{id}")
	public String getPiattoById(@PathVariable("id") Long id, Model model) {
		model.addAttribute("piatto", piattoService.getPiattoPerId(id));
		return "piatto.html";
	}
	
	@PostMapping("/admin/inserisciPiatto")
	public String inserisciPiatto(@Valid @ModelAttribute("piatto") Piatto piatto, BindingResult bindingResult, Model model) {
		piattoValidator.validate(piatto, bindingResult);
		if(!bindingResult.hasErrors()) {
			piattoService.salva(piatto);
			model.addAttribute("piattoNuovo", piatto);
			model.addAttribute("piatti", piattoService.getTuttiPiatti());
			return "piatti.html";
		}
		model.addAttribute("allIngredienti", ingredienteService.getTuttiIngredienti());
		return "inserisciPiattoForm";
	}
	
	@GetMapping("/admin/aggiungiABuffet/{idb}/{idp}")
	public String aggiungiPiattoABuffet(@PathVariable("idb") Long idBuffet, @PathVariable("idp") Long idPiatto) {
		Buffet buffet = buffetService.getBuffetPerId(idBuffet);
		buffet.addPiatto(piattoService.getPiattoPerId(idPiatto));
		buffetService.save(buffet);
		return "redirect:/admin/modificaBuffet/" + idBuffet;
	}
	
	@GetMapping("/admin/rimuoviDaBuffet/{idb}/{idp}")
	public String rimuoviPiattoDaBuffet(@PathVariable("idb") Long idBuffet, @PathVariable("idp") Long idPiatto) {
		Buffet buffet = buffetService.getBuffetPerId(idBuffet);
		buffet.removePiatto(piattoService.getPiattoPerId(idPiatto));
		buffetService.save(buffet);
		return "redirect:/admin/modificaBuffet/" + idBuffet;
	}
	
	@GetMapping("/admin/eliminaPiatto/{id}")
	public String eliminaUnPiatto(@PathVariable("id") Long id, Model model) {
		Piatto piatto = piattoService.rimuoviPiatto(id);
		model.addAttribute("piattoRimosso", piatto);
		model.addAttribute("piatti", piattoService.getTuttiPiatti());
		return "piatti.html";
	}

}
