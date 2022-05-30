package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Buffet;

public interface BuffetRepository extends CrudRepository<Buffet, Long> {
	
	public Buffet findByNome(String nome);
	
	public List<Buffet> findByChefNomeAndChefCognome(String nome, String cognome);
}
