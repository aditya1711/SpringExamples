package com.ggktech.poogleFormsSpring.service.models.forms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggktech.poogleFormsSpring.service.models.misc.IDAble;
import com.ggktech.poogleFormsSpring.service.models.misc.JSONConvertible;

public interface Question extends IDAble<Long>, JSONConvertible{
	
	public static  ObjectMapper mapper = new ObjectMapper();
	public long getFormID();
	public void setFormID(long formID);
	public String getPrompt();
	public void setPrompt(String prompt);
	public TYPES_OF_QUESTION getType();
	public String getHandler();
	
}
