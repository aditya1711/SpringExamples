package com.ggktech.poogleFormsSpring.service.models.forms;

//import java.sql.SQLException;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ggktech.poogleFormsSpring.service.models.misc.IDAble;
import com.ggktech.poogleFormsSpring.service.models.misc.JSONConvertible;

public class Answer implements IDAble<Long>, JSONConvertible {
	
	public Long questionID;
	//private Question belongedQuestion;
	public String username;
	
	/*static String DB_URL = "jdbc:sqlserver://ggku3ser2;instanceName=SQL2016;databaseName=poogleForms";
	static String DB_User = "sa";
	static String DB_Password = "Welcome@1234";*/
	
	@JsonIgnore
	public Long ID;
	ArrayList<String> answers;
	
	/*private static FormDAO formDAO;
	
	static{
		try {
			formDAO = FormDAO.getFormDAO(DB_URL, DB_User, DB_Password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	
	
	public Long getQuestionID() {
		return questionID;
	}

	public void setQuestionID(Long questionID) {
		this.questionID = questionID;
		//belongedQuestion = formDAO.getQuestion(questionID);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<String> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}

	
	@Override
	public String toJSONString() throws JsonProcessingException {
		// TODO Auto-generated method stub
		return ow.writeValueAsString(this);
	}

	@Override @JsonIgnore
	public Long getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override @JsonIgnore
	public void setID(Long t) {
		// TODO Auto-generated method stub
		ID=t;
	}
	
	public String toString(){
		return "QuestionID: " + getQuestionID() + "\n"
				//+ "Question: " + belongedQuestion.getPrompt() + "\n"
						+ "Username: " + getUsername() + "\n"
							+ "AnswerID: " + getID() + "\n"
								+ "Answer: " + answers.toString() + "\n";
	}
	
}
