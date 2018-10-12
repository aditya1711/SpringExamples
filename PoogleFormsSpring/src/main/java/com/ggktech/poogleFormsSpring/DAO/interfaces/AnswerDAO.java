package com.ggktech.poogleFormsSpring.DAO.interfaces;

import java.util.HashSet;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ggktech.poogleFormsSpring.service.models.forms.Answer;

public interface AnswerDAO {
	public Map<Long,Answer> getAnswersWithUsernameAndFormID(String username, Long formID);
	public HashSet<Long> getAnswerIDsWithUsername(String username);
	public HashSet<Long> getAnsweredFormIDsWithUsername(String username);
	public Long insertAnswerInDB(Answer ans) throws DataAccessException, JsonProcessingException;
	public Long getCountOfUsersAnsweredAForm(Long formID);
}
