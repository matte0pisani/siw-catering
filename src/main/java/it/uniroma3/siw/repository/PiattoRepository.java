package it.uniroma3.siw.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Piatto;

public interface PiattoRepository extends CrudRepository<Piatto, Long> {
	public Piatto findByNome(String nome);
	
	public List<Piatto> findByIdNotIn(Collection<Long> ids);
	
//	@Modifying
////	@Query("delete from buffet_piatti bp where bp.buffet_id = ?1 and bp.piatti_id = ?2")
//	public void rimuoviDaBuffet(Long idBuffet, Long idPiatto);
}
