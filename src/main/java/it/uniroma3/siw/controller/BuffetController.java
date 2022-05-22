package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.service.BuffetService;

@Controller
public class BuffetController {
	@Autowired
	private BuffetService buffServ;
	
	@GetMapping("/buffetForm")
	public String getBuffetForm(Model model) {
		return "buffetForm.html";
	}
	
	@GetMapping("/buffet")
	public String getBuffet(@RequestParam(name = "nome") String nome, Model model) {	// FIXME per ora non facciamo un controllo sul contenuto di nome
		model.addAttribute("buffet", buffServ.getBuffet(nome));
		return "buffet.html";
	}
	
}