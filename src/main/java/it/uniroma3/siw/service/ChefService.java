package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.repository.ChefRepository;

@Service
public class ChefService {
	@Autowired
	private ChefRepository repo;
	
	public List<Chef> getTuttiChef() {
		return (List<Chef>) repo.findAll();
	}

	public Chef getChefPerId(Long id) {
		return repo.findById(id).get();
	}
	
	public boolean verificaEsisteChefPerNomeCognome(String nome, String cognome) {
		return repo.findByNomeAndCognome(nome, cognome) != null;
	}
	
	public void salva(Chef c) {
		repo.save(c);
	}

	public Chef getChefPerNomeCognome(String nomeCognomeChef) {
		String[] splitString = nomeCognomeChef.split(" ");
		return repo.findByNomeAndCognome(splitString[0], splitString[1]);
	}
}
