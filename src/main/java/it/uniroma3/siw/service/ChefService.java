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
}
