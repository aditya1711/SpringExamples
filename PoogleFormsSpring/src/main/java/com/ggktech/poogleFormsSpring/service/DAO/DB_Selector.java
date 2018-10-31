package com.ggktech.poogleFormsSpring.service.DAO;

import java.util.HashSet;
import java.util.Map;

import com.ggktech.poogleFormsSpring.service.models.clients.Client;
import com.ggktech.poogleFormsSpring.service.models.forms.Answer;

public interface DB_Selector {
	public HashSet<Long> getAnswerIDsWithUsername(String username);
	public Map<Long, Answer> getAnswersWithUsernameAndFormID(String username, Long formID);
	public HashSet<Long> getAnsweredFormIDsWithUsername(String username);
	public Long getCountOfUsersAnsweredAForm(Long formID);
	public Client getClientByUsernameAndPassword(String username, String password);
}
