package it.uniroma3.siw.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.beans.EliminaBuffetBean;
import it.uniroma3.siw.controller.validator.BuffetValidator;
import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.service.BuffetService;
import it.uniroma3.siw.service.ChefService;
import it.uniroma3.siw.service.PiattoService;

@Controller
public class BuffetController {
	@Autowired
	private BuffetService buffService;
	@Autowired
	private BuffetValidator buffValidator;
	@Autowired
	private ChefService chefService;
	@Autowired
	private PiattoService piattoService;

	@GetMapping("/buffet/cerca")
	public String getCercaBuffetForm(Model model) {
		return "cercaBuffetForm.html";
	}

	@GetMapping("/admin/inserisciBuffetForm")
	public String getInserisciBuffetForm(Model model) {
		model.addAttribute("buffet", new Buffet());
		model.addAttribute("allChefs", chefService.getTuttiChef());
		model.addAttribute("allPiatti", piattoService.getTuttiPiatti());
		return "inserisciBuffetForm.html";
	}

	@GetMapping("/admin/eliminaBuffetForm")
	public String getEliminaBuffetForm(Model model) {
		model.addAttribute("bean", new EliminaBuffetBean());
		model.addAttribute("buffets", buffService.getTuttiBuffet());
		return "eliminaBuffetForm.html";
	}
	
	@GetMapping("/admin/modificaBuffet/{id}")
	public String modificaBuffetForm(@PathVariable("id") Long id, Model model) {
		Buffet buffet = buffService.getBuffetPerId(id);
		model.addAttribute("buffet", buffet);
		model.addAttribute("allChefs", chefService.getTuttiChefMeno(buffet.getChef()));
		model.addAttribute("allPiatti", piattoService.getTuttiPiattiMeno(buffet.getPiatti()));
		return "modificaBuffetForm.html";
	}
	
	@GetMapping("/admin/buffetPage")
	public String getBuffetPage(Model model) {
		model.addAttribute("admin", true);
		model.addAttribute("buffets", buffService.getTuttiBuffet());
		return "buffets.html";
	}

	@GetMapping("/buffet/all")
	public String getTuttiBuffet(Model model) {
		model.addAttribute("buffets", buffService.getTuttiBuffet());
		return "buffets.html";
	}

	@GetMapping("/buffet/nome")
	public String getBuffetByNome(@RequestParam(name = "nome") String nome, Model model) {
		if(!(nome == null || nome.isBlank())) {
			Buffet buffet = buffService.getBuffetPerNome(nome);
			if(buffet != null) {
				model.addAttribute("buffet", buffet);
				return "buffet.html";
			}
			else {
				// il buffet non esiste
				model.addAttribute("isBuffetInesistente", true);
				model.addAttribute("namePlaceholder", nome);
				return "cercaBuffetForm.html";
			}
		}
		else {
			// la stringa è vuota, o soli spazi
			model.addAttribute("isNomeBlank", true);
			return "cercaBuffetForm.html";
		}
	}

	@GetMapping("/buffet/{id}")
	public String getBuffetById(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", buffService.getBuffetPerId(id));
		return "buffet.html";
	}

	@GetMapping("/buffet/chef")
	public String getBuffetsByChef(@RequestParam(name = "nomeCognome") String nomeCognome, Model model) {
		if(nomeCognome != null && !nomeCognome.isBlank()) {
			List<Buffet> buffets = buffService.getTuttiBuffetPerNomeChef(nomeCognome);
			if(buffets != null && !buffets.isEmpty()) {
				model.addAttribute("buffets", buffets);
				model.addAttribute("chef", chefService.getChefPerNomeCognome(nomeCognome));
				return "buffets.html";
			}
			else {
				model.addAttribute("isBuffetsInesistenti", true); 
				model.addAttribute("chefPlaceholder", nomeCognome);
				return "cercaBuffetForm";
			}
		}
		else {
			model.addAttribute("isNomeCognomeBlank", true);
			return "cercaBuffetForm";
		}
	}

	@PostMapping("/admin/inserisciBuffet")
	public String insertBuffet(@Valid @ModelAttribute("buffetNuovo") Buffet buffet, BindingResult bindingResult, Model model) {
		buffValidator.validate(buffet, bindingResult);
		if(!bindingResult.hasErrors()) {
			buffService.save(buffet);
			model.addAttribute("buffets", buffService.getTuttiBuffet());
			model.addAttribute("admin", true);
			return "buffets.html";
		}
		model.addAttribute("allChefs", chefService.getTuttiChef());
		model.addAttribute("allPiatti", piattoService.getTuttiPiatti());
		return "inserisciBuffetForm.html";
	}
	
	@PostMapping("/admin/modificaBuffet/{id}")
	public String modificaBuffet(@PathVariable("id") Long id, @Valid Buffet buffet,
								BindingResult bindingResult, Model model) {
		Buffet oldBuffet = buffService.getBuffetPerId(id);
		if(!bindingResult.hasFieldErrors("nome") && !bindingResult.hasFieldErrors("descrizione")) {
			oldBuffet.setId(buffet.getId());
			oldBuffet.setNome(buffet.getNome());
			oldBuffet.setDescrizione(buffet.getDescrizione());
			oldBuffet.setChef(buffet.getChef());
			buffService.save(oldBuffet);
			
			model.addAttribute("buffetModificato", oldBuffet);
			model.addAttribute("buffets", buffService.getTuttiBuffet());
			model.addAttribute("admin", true);
			return "buffets.html";
		}
		model.addAttribute("buffet", oldBuffet);
		model.addAttribute("modificaNonValida", true);
		model.addAttribute("allChefs", chefService.getTuttiChefMeno(oldBuffet.getChef()));
		model.addAttribute("allPiatti", piattoService.getTuttiPiattiMeno(oldBuffet.getPiatti()));
		return "modificaBuffetForm.html";
	}

	@PostMapping("/admin/eliminaBuffet")
	public String eliminaBuffet(@Valid @ModelAttribute("bean") EliminaBuffetBean bean, BindingResult bindingResult, Model model) {
		if(!bindingResult.hasErrors()) {
			model.addAttribute("buffets", buffService.rimuoviTuttiBuffetConIds(bean.getBuffetIds()));
			return "confermaEliminazioneBuffet.html";
		}
		model.addAttribute("buffets", buffService.getTuttiBuffet());
		return "eliminaBuffetForm";
	}
	
	@GetMapping("/admin/eliminaBuffet/{id}")
	public String eliminaUnBuffet(@PathVariable("id") Long id, Model model) {
		Buffet buffet = buffService.rimuoviBuffet(id);
		model.addAttribute("buffetRimosso", buffet);
		model.addAttribute("buffets", buffService.getTuttiBuffet());
		model.addAttribute("admin", true);
		return "buffets.html";
	}

}