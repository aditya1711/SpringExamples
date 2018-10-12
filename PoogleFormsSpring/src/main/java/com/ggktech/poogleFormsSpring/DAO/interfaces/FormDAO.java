package com.ggktech.poogleFormsSpring.DAO.interfaces;

import java.util.HashSet;

import com.ggktech.poogleFormsSpring.service.models.forms.Form;
import com.ggktech.poogleFormsSpring.service.models.forms.Question;



public interface FormDAO {
	public boolean checkForLevel2UsernameAndFormIDPair(String username, Long formID);
	public HashSet<Long> getFormIDsWithUsername(String username);
	public HashSet<Long> getAllFormIDs();
	public Form getFormWithFormID(Long ID);
	public Question getQuestionWithQuestionID(Long ID);
	public Long addQuestionToDB(Question q);
	public Long addFormPrototypeToDB(Form f);
	public Long updateFormName(Long formID, String formName);
	public Long deleteQuestion(Long questionID);
	public Long deleteForm(Long formID);
}
