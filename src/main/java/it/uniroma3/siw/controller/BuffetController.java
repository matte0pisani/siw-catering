package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.service.BuffetService;

@Controller
public class BuffetController {
	@Autowired
	private BuffetService buffServ;
	
	@GetMapping("/buffetForm")
	public String getBuffetForm(Model model) {
		model.addAttribute("buffet", new Buffet());
		return "buffetForm.html";
	}
	
	@GetMapping("/buffet")
	public String getBuffet(Buffet buffet, Model model) {	// FIXME per ora non facciamo un controllo sul contenuto di nome
		Buffet buffetRiempito = buffServ.getBuffet(buffet.getNome());	// FIXME forse meglio aggiungere metodo "riempi buffet"
		model.addAttribute("buffet", buffetRiempito);
		return "buffet.html";
	}
	
}