package com.ggktech.poogleFormsSpring.service.DAO;

import com.ggktech.poogleFormsSpring.service.models.forms.Answer;

public interface DB_Checker {
	public boolean checkToUpdateAnswer(Answer ans);
	public boolean checkForUsernamePasswordPair(String username, String password);
	public boolean checkIfUsernameExists(String username);
}
