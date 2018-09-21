package poogleForms.model.form;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class TextTypeQuestion implements Question{

	public static final TYPES_OF_QUESTION QUESTION_TYPE = TYPES_OF_QUESTION.TEXT_QUESTIONS;
	public Long ID;
	
	public static long tempID=5000;
	
	public long formID;
	
	public String prompt;
	
	public static String handler = "TextTypeQuestionHandler.jsp";
	public static ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	
	public TextTypeQuestion() {
		super();
	}
	
	public TextTypeQuestion(String prompt) {
		super();
		this.prompt = prompt;
	}
	
	public TextTypeQuestion(String prompt, long formID) {
		super();
		this.prompt = prompt;
		this.formID= formID;
		//to-do
		ID = --tempID;
	}

	public long getFormID() {
		return formID;
	}

	public void setFormID(long formID) {
		this.formID = formID;
	}

	@Override
	public void setID(Long t) {
		// TODO Auto-generated method stub
		ID= t;
	}
	
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public void setHandler(String handler) {
		TextTypeQuestion.handler = handler;
	}
	
	@Override
	public Long getID() {
		return ID;
	}

	@Override
	public String getPrompt() {
		// TODO Auto-generated method stub
		return prompt;
	}

	@Override @JsonIgnore
	public TYPES_OF_QUESTION getType() {
		// TODO Auto-generated method stub
		return QUESTION_TYPE;
	}

	@Override
	public String getHandler() {
		// TODO Auto-generated method stub
		return handler;
	}
	
	public String toString(){
		return "\nQuestion Type: " + QUESTION_TYPE +  " prompt: " + getPrompt() + "\n";
	}
	
	public static Question getQuestionFromJSONString(String JSONString) throws JsonParseException, JsonMappingException, IOException{
		return mapper.readValue(JSONString, TextTypeQuestion.class);
	}
	
	public String toJSONString() throws JsonProcessingException{
		return ow.writeValueAsString(this);
	}

	
}
