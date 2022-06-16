package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.repository.IngredienteRepository;

@Service
public class IngredienteService {
	@Autowired
	private IngredienteRepository repo;

	public boolean verificaEsisteIngredientePerNome(String nome) {
		return repo.findByNome(nome) != null;
	}

	public void salva(Ingrediente ingrediente) {
		repo.save(ingrediente);	
	}

	public Object getTuttiIngredienti() {
		return (List<Ingrediente>) repo.findAll();
	}

	public Ingrediente rimuoviBuffet(Long id) {
		Ingrediente result = repo.findById(id).get();
		repo.deleteById(id);
		return result;
	}

}
