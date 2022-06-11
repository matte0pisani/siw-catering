package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.service.BuffetService;

@Component
public class BuffetValidator implements Validator {
	@Autowired
	private BuffetService service;

	@Override
	public boolean supports(Class<?> clazz) {
		return Buffet.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Buffet buff = (Buffet) target;
		if(service.verificaEsistenzaBuffet(buff.getNome()))
			errors.reject("duplicato");
	}

}
