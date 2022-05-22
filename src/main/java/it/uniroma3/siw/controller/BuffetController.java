package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Buffet;
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
	public String getBuffet(@RequestParam(name = "nome") String nome, Model model) {
		Buffet buffet = buffServ.getBuffet(nome);
		if(buffet != null) {		// FIXME controllo da migliorare
			model.addAttribute("buffet", buffet);
			return "buffet.html";
		}
		else {
			return "buffetForm.html";
		}
	}

}