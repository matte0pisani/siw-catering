package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.repository.BuffetRepository;

@Service
public class BuffetService {	
	@Autowired
	private BuffetRepository repo;
	
	public Buffet getBuffet(String nome) {
		return repo.findByNome(nome);
	}
	
	public List<Buffet> getTuttiBuffetPerNomeChef(String nomeCognomeChef) {
		String[] splitString = nomeCognomeChef.split(" ");
		List<Buffet> buffets = repo.findByChefNomeAndChefCognome(splitString[0], splitString[1]);
		if(buffets == null)
			buffets = repo.findByChefNomeAndChefCognome(splitString[0], splitString[1]);	// permetto all'utente l'inserimento in qualsiasi ordine
		return buffets;
	}

}
