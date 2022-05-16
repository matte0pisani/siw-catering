package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.repository.BuffetRepository;

@Service
public class BuffetService {	
	@Autowired
	private BuffetRepository repo;
	
	private Buffet buffetCorrente;
	
	public Buffet getBuffet(String nome) {
		buffetCorrente = repo.findByNome(nome);
		return buffetCorrente;
	}
	
	public void terminaVisualizzazione() {	}

}
