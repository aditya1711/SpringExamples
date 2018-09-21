package poogleForms.model.form;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import poogleForms.model.form.MultipleChoiceTypeQuestion;
import poogleForms.model.form.Question;
import poogleForms.model.form.TYPES_OF_QUESTION;

public class MultipleChoiceTypeQuestion implements Question {
	
	public static final TYPES_OF_QUESTION QUESTION_TYPE = TYPES_OF_QUESTION.MULTIPLE_CHOICE_QUESTION;
	public Long ID;
	
	public static long idTemp= 5000;
	
	public long formID;

	public String prompt;
	public ArrayList<String> options = new ArrayList<String>();
	
	public static String handler = "MCQHandler.jsp";
	
	
	public MultipleChoiceTypeQuestion() {
		super();
		//to-do
		ID= ++idTemp;
	}
	
	public void setID(Long ID) {
		this.	ID = ID;
	}

	public MultipleChoiceTypeQuestion(long formID, String prompt) {
		super();
		this.formID = formID;
		this.prompt = prompt;
		//to-do
		ID= ++idTemp;
	}
	
	public MultipleChoiceTypeQuestion(String prompt, ArrayList<String> options, long formID) {
		super();
		this.prompt = prompt;
		this.options = options;
		this.formID = formID;
		//to-do
		ID= ++idTemp;
		
	}
	
	public MultipleChoiceTypeQuestion(String prompt, ArrayList<String> options) {
		super();
		this.prompt = prompt;
		this.options = options;
		//to-do
		ID= ++idTemp;
		
	}
	
	public MultipleChoiceTypeQuestion(String prompt, String[] options, long formID) {
		super();
		this.prompt = prompt;
		this.options = new ArrayList<String>(Arrays.asList(options));
		this.formID = formID;
		//to-do
		ID= ++idTemp;
		
	}
	
	public MultipleChoiceTypeQuestion(String prompt, String[] options) {
		super();
		this.prompt = prompt;
		this.options = new ArrayList<String>(Arrays.asList(options));
		//to-do
		ID= ++idTemp;
		
	}
	
	public long getFormID() {
		return formID;
	}

	public void setFormID(long formID) {
		this.formID = formID;
	}
	
	
	public ArrayList<String> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<String> options) {
		this.options = options;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public void setHandler(String handler) {
		MultipleChoiceTypeQuestion.handler = handler;
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
	
	@JsonIgnore
	@Override
	public TYPES_OF_QUESTION getType() {
		// TODO Auto-generated method stub
		return QUESTION_TYPE;
	}

	@Override
	public String getHandler() {
		// TODO Auto-generated method stub
		return handler;
	}
	
	public void addOption(String option){
		options.add(option);
	}
	
	public String toString(){
		return "FormId: " + getFormID() + "\nQuestion Type: " + QUESTION_TYPE +  " prompt: " + getPrompt() + " options: " + options.toString() + "\n";
	}
	
	public String toJSONString() throws JsonProcessingException{
		return ow.writeValueAsString(this);
	}
	
	@JsonIgnore
	public static Question getQuestionFromJSONString(String JSONString) throws JsonParseException, JsonMappingException, IOException{
		return mapper.readValue(JSONString, MultipleChoiceTypeQuestion.class);
	}
	
	@JsonIgnore
	public String getOptionsAsJSONString() throws JsonProcessingException{
		return ow.writeValueAsString(options);
	}
}
