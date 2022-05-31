package it.uniroma3.siw.controller.beans;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class InserisciBuffetBean {
	private String nome;
	private String descrizione;
	private List<Long> chefs;
	private List<Long> piatti;
	
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
	
	
}
