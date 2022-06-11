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
				model.addAttribute("chef", nomeCognome);
				return "buffets.html";
			}
			else {
				model.addAttribute("isBuffetsInesistenti", true); 	
				return "cercaBuffetForm";
			}
		}
		else {
			model.addAttribute("isNomeCognomeBlank", true);
			return "cercaBuffetForm";
		}
	}

	@PostMapping("/admin/inserisciBuffet")
	public String insertBuffet(@Valid @ModelAttribute("buffet") Buffet buffet, BindingResult bindingResult, Model model) {
		buffValidator.validate(buffet, bindingResult);
		if(!bindingResult.hasErrors()) {
			buffService.save(buffet);
			return "confermaInserimentoBuffet";
		}
		model.addAttribute("allChefs", chefService.getTuttiChef());
		model.addAttribute("allPiatti", piattoService.getTuttiPiatti());
		return "inserisciBuffetForm.html";
	}

	@PostMapping("/admin/eliminaBuffet")
	public String eliminaBuffet(@Valid @ModelAttribute("bean") EliminaBuffetBean bean, BindingResult bindingResult, Model model) {
		if(!bindingResult.hasErrors()) {
			model.addAttribute("buffets", buffService.deleteTuttiBuffetConIds(bean.getBuffetIds()));
			return "confermaEliminazioneBuffet.html";				
		}
		model.addAttribute("buffets", buffService.getTuttiBuffet());
		return "eliminaBuffetForm";
	}

	// FIXME temporaneo
	@GetMapping("/admin/getAmministratorePage") 
	public String getAmministratorePage(Model model) {
		return "amministratore";
	}
}