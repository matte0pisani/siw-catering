package it.uniroma3.siw.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.beans.EliminaBuffetBean;
import it.uniroma3.siw.controller.beans.InserisciBuffetBean;
import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.service.BuffetService;
import it.uniroma3.siw.service.ChefService;
import it.uniroma3.siw.service.PiattoService;

@Controller
public class BuffetController {
	@Autowired
	private BuffetService buffService;
	@Autowired
	private ChefService chefService;
	@Autowired
	private PiattoService piattoService;

	@GetMapping("/cercaBuffetForm")
	public String getCercaBuffetForm(Model model) {
		return "cercaBuffetForm.html";
	}

	@GetMapping("/inserisciBuffetForm")
	public String getInserisciBuffetForm(Model model) {
		model.addAttribute("bean", new InserisciBuffetBean());
		model.addAttribute("chefs", chefService.getTuttiChef());
		model.addAttribute("piatti", piattoService.getTuttiPiatti());
		return "inserisciBuffetForm.html";
	}

	@GetMapping("/eliminaBuffetForm")
	public String getEliminaBuffetForm(Model model) {
		model.addAttribute("bean", new EliminaBuffetBean());
		model.addAttribute("buffets", buffService.getTuttiBuffet());
		return "eliminaBuffetForm.html";
	}

	@GetMapping("/allBuffets")
	public String getTuttiBuffet(Model model) {
		model.addAttribute("buffets", buffService.getTuttiBuffet());
		return "buffets.html";
	}

	@GetMapping("/buffet")
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

	@GetMapping("/buffets")
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

	@PostMapping("/inserisciBuffet")
	public String insertBuffet(@Valid InserisciBuffetBean bean, Model model, BindingResult bindingResult) {
		if(!bindingResult.hasErrors()) {
			Buffet buffet = new Buffet(bean.getNome(), bean.getDescrizione());
			buffet.setChef(chefService.getChefPerId(bean.getPrimoChefId()));
			for(Long id : bean.getPiatti()) {
				buffet.addPiatto(piattoService.getPiattoPerId(id));
			}
			model.addAttribute("buffet", buffet);
			buffService.save(buffet);
			return "confermaInserimento.html";
		}
		return "inserisciBuffetForm";
	}

	@PostMapping("/eliminaBuffet")
	public String eliminaBuffet(EliminaBuffetBean bean, Model model) {
		// FIXME per ora saltiamo controllo di validità (se utente ha selezionato almento un buffet)
		if(bean != null) {
			model.addAttribute("buffets", buffService.deletePerId(bean.getBuffetIds()));
			return "confermaEliminazione.html";				
		}
		return "eliminaBuffetForm";
	}

	// FIXME temporaneo
	@GetMapping("/getAmministratorePage") 
	public String getAmministratorePage(Model model) {
		return "amministratore";
	}
}