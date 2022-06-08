package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Buffet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	@NotBlank
	private String nome;
	
	@Column(length = 512)
	private String descrizione;
	
	@NotNull
	@ManyToOne
	private Chef chef;
	
	@NotEmpty
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Piatto> piatti;	// FIXME sarebbe meglio mappa
	
	public Buffet(String nome, String descrizione) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.piatti = new ArrayList<Piatto>();
	}
	
	public Buffet() {
		this(null, null);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public List<Piatto> getPiatti() {
		return piatti;
	}

	public void setPiatti(List<Piatto> piatti) {
		this.piatti = piatti;
	}
	
	/**
	 * @param nome nome del piatto
	 * @return un oggetto Piatto se tale nome esiste, null altrimenti
	 */
	public Piatto getPiatto(String nome) {
		for(Piatto p : this.piatti) {
			if(p.getNome() == nome) 
				return p;
		}
		return null;
	}
	
	public void addPiatto(Piatto p) {
		this.piatti.add(p);
	}

}
