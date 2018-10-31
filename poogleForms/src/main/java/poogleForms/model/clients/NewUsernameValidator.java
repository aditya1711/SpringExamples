package poogleForms.model.clients;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import poogleForms.DAO.ClientsDAO;

@Component
public class NewUsernameValidator implements ConstraintValidator<IsValidNewUsername, String> {
	
	@Autowired
	ClientsDAO clientsDAO;
	
	@Override
	public void initialize(IsValidNewUsername constraintAnnotation) {
		// TODO Auto-generated method stub
		ConstraintValidator.super.initialize(constraintAnnotation);
	}
	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return !clientsDAO.checkIfUsernameExists(username);
	}

}
