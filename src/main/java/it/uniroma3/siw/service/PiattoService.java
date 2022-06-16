package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.repository.PiattoRepository;

@Service
public class PiattoService {
	@Autowired
	private PiattoRepository repo;
	
	public Piatto getPiattoPerId(Long id) {
		return repo.findById(id).get();
	}
	
	public List<Piatto> getTuttiPiatti() {
		return (List<Piatto>) repo.findAll();
	}

	public boolean verificaEsistenzaPiattoPerNome(String nome) {
		return repo.findByNome(nome) != null;
	}
	
	public void salva(Piatto p) {
		repo.save(p);
	}

	public List<Piatto> getTuttiPiattiMeno(List<Piatto> piatti) {
		List<Long> ids = new ArrayList<>();
		for(Piatto piatto : piatti) { ids.add(piatto.getId()); }
		return repo.findByIdNotIn(ids);
	}

	public Piatto rimuoviPiatto(Long id) {
		Piatto result = repo.findById(id).get();
		repo.deleteById(id);
		return result;
	}

}
