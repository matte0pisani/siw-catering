package it.uniroma3.siw.controller.beans;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

@Component
public class InserisciBuffetBean {
	@NotBlank
	private String nome;
	
	@NotBlank
	private String descrizione;
	
	@NotEmpty
	private List<Long> chefs;
	
	@NotEmpty
	private List<Long> piatti;
	
	public InserisciBuffetBean() {
		chefs = new ArrayList<>();
		piatti = new ArrayList<>();
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
	
	public List<Long> getChefs() {
		return chefs;
	}
	
	public void setChefs(List<Long> chefs) {
		this.chefs = chefs;
	}
	
	public List<Long> getPiatti() {
		return piatti;
	}
	
	public void setPiatti(List<Long> piatti) {
		this.piatti = piatti;
	}
	
	public Long getPrimoChefId() {
		return this.getChefs().get(0);
	}
	
	
}
