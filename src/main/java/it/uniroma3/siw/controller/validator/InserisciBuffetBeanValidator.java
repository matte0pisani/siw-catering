package it.uniroma3.siw.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.controller.beans.InserisciBuffetBean;

public class InserisciBuffetBeanValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return InserisciBuffetBean.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		

	}

}
