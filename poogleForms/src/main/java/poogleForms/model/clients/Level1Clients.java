package poogleForms.model.clients;

import java.util.HashSet;
import java.util.Iterator;

import poogleForms.DAO.AnswersDAO;
import poogleForms.model.form.Answer;

public class Level1Clients extends ClientAbstract {
	
	private HashSet<Long> answerIDs;
	

	public HashSet<Long> getAnswersIDs() {
		return answerIDs;
	}
	public void setAnswersIDs(HashSet<Long> answerIDs) {
		this.answerIDs = answerIDs;
	}
	
	public HashSet<Long> getAnswerIDs() {
		return answerIDs;
	}
	public void setAnswerIDs(HashSet<Long> answerIDs) {
		this.answerIDs = answerIDs;
	}
	public void addAnsweredForm(HashSet<Long> answerIDs){
		Iterator<Long> it = answerIDs.iterator();
		while(it.hasNext()){
			this.answerIDs.add(it.next());
		}
	}
	
	public void addAnswer(Long answerID){
		answerIDs.add(answerID);
	}
	
	public String toString(){
		String s ="";
		s = getLoginCredentials().toString() + "\n"
				+ "Name " + getFirstName() + " " + getLastName() + "\n"
						+ "AnswerIDs: ";
		s= s + "ClientType: " + getLoginCredentials().getType();
		s= s + ((answerIDs==null)? "none" : answerIDs.toString());
		
		return s;
	}
}
