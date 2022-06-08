package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.controller.beans.InserisciBuffetBean;
import it.uniroma3.siw.service.BuffetService;

@Component
public class InserisciBuffetBeanValidator implements Validator {
	@Autowired
	private BuffetService service;

	@Override
	public boolean supports(Class<?> clazz) {
		return InserisciBuffetBean.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(service.verificaEsistenzaBuffet(((InserisciBuffetBean) target).getNome())) {
			errors.reject("duplicato");
		}
	}

}
