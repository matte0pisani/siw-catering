package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.beans.InserisciBuffetBean;
import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.repository.ChefRepository;
import it.uniroma3.siw.repository.PiattoRepository;
import it.uniroma3.siw.service.BuffetService;

@Controller
public class BuffetController {
	@Autowired
	private BuffetService buffServ;
	@Autowired
	private ChefRepository chefRepo;	//FIXME solo per velocità
	@Autowired
	private PiattoRepository piattoRepo;	//FIXME solo per velocità

	@GetMapping("/cercaBuffetForm")
	public String getCercaBuffetForm(Model model) {
		return "cercaBuffetForm.html";
	}
	
	@GetMapping("/inserisciBuffetForm")
	public String getInserisciBuffetForm(Model model) {
		model.addAttribute("buffet", new Buffet());
		model.addAttribute("chefs", chefRepo.findAll());
		model.addAttribute("piatti", piattoRepo.findAll());
		return "inserisciBuffetForm.html";
	}

	@GetMapping("/buffet")
	public String getBuffetByNome(@RequestParam(name = "nome") String nome, Model model) {
		Buffet buffet = buffServ.getBuffetPerNome(nome);
		if(buffet != null) {		// FIXME controllo da migliorare; include se nome == null o blank
			model.addAttribute("buffet", buffet);
			return "buffet.html";
		}
		else
			return "cercaBuffetForm.html";
	}

	@GetMapping("/buffets")
	public String getBuffetsByChef(@RequestParam(name = "nomeCognome") String nomeCognome, Model model) {
		if(nomeCognome != null && !nomeCognome.isBlank()) {
			List<Buffet> buffets = buffServ.getTuttiBuffetPerNomeChef(nomeCognome);
			if(buffets != null) {
				model.addAttribute("buffets", buffets);
				model.addAttribute("chef", nomeCognome);
				return "buffets.html";
			}
		}
		return "cercaBuffetForm";
	}
	
	@PostMapping("/inserisciBuffet")
	public String insertBuffet(Buffet buffet, Model model) {
		// FIXME per ora saltiamo il controllo sulla validità del contenuto del bean
		if(buffet != null) {
//			Buffet buffet = new Buffet(buffet.getNome(), buffet.getDescrizione());
////			Iterable<Chef> allChefs = (Iterable<Chef>) model.getAttribute("chefs");	// model viene "ricreato" ad ogni sessione
////			for(Chef c : allChefs) {
////				if(c.getId() == bean.getChefs().get(0))
////					buffet.setChef(c);
////			}
//			buffet.setChef(chefRepo.findById(buffet.getChefs().get(0)).get());
////			Iterable<Piatto> allPiatti = (Iterable<Piatto>) model.getAttribute("piatti");
////			for(Piatto p : allPiatti) {
////				if(bean.getPiatti().contains(p.getId()))
////					buffet.getPiatti().add(p);
////			}
//			for(Long id : buffet.getPiatti()) {
//				buffet.getPiatti().add(piattoRepo.findById(id).get());
//			}
			model.addAttribute("buffet", buffet);
			buffServ.save(buffet);
			return "confermaInserimento.html";
		}
		return "inserisciBuffetForm";
	}

}