package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.PiattoService;

@Controller
public class PiattoController {
	@Autowired
	private PiattoService service;
	
	@GetMapping("/piatto/{id}")
	public String getPiattoById(@PathVariable("id") Long id, Model model) {
		model.addAttribute("piatto", service.getPiattoPerId(id));
		return "piatto.html";
	}

}
