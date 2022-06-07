package it.uniroma3.siw.service;

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
}
