package com.ggktech.poogleFormsSpring.service.DAO;

import com.ggktech.poogleFormsSpring.service.models.clients.Client;
import com.ggktech.poogleFormsSpring.service.models.forms.Answer;

public interface DB_Insertor {
	public Long insertAnswerInDB(Answer ans);
	public boolean insertClientSpecificDetatilsIntoDB(Client client);
}
