package it.uniroma3.siw.controller.beans;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class EliminaBuffetBean {
	private List<Long> buffetIds;

	public List<Long> getBuffetIds() {
		return buffetIds;
	}

	public void setBuffetIds(List<Long> buffetIds) {
		this.buffetIds = buffetIds;
	}
	
	
}
