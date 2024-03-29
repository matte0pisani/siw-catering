package it.uniroma3.siw.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.repository.BuffetRepository;

@Service
public class BuffetService {	
	@Autowired
	private BuffetRepository repo;

	public Buffet getBuffetPerId(Long id) {
		return repo.findById(id).get();
	}

	public Buffet getBuffetPerNome(String nome) {
		return repo.findByNome(nome);
	}

	public List<Buffet> getTuttiBuffetPerNomeChef(String nomeCognomeChef) {
		String[] splitString = nomeCognomeChef.split(" ");
		if(splitString.length > 1) {
			List<Buffet> buffets = repo.findByChefNomeAndChefCognome(splitString[0], splitString[1]);
			if(buffets == null)
				buffets = repo.findByChefNomeAndChefCognome(splitString[1], splitString[0]);	// FIXME non funziona, perché?
			return buffets;
		}
		else return null;
	}

	public List<Buffet> getTuttiBuffet() {
		return (List<Buffet>) repo.findAll();
	}

	@Transactional
	public void save(Buffet buffet) {
		repo.save(buffet);
	}

	@Transactional
	public List<Buffet> rimuoviTuttiBuffetConIds(Collection<Long> ids) {	// FIXME nome più evocativo?
		List<Buffet> result = (List<Buffet>) repo.findAllById(ids);
		repo.deleteAllById(ids);
		return result;
	}

	@Transactional
	public Buffet rimuoviBuffet(Long id) {
		Buffet result = repo.findById(id).get();
		repo.deleteById(id);
		return result;
	}
	

	public boolean verificaEsistenzaBuffet(String nome) {
		return getBuffetPerNome(nome) != null;
	}
	
}
