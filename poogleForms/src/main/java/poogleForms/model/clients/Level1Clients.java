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
	
	public void addAnsweredForm(HashSet<Long> answerIDs){
		Iterator<Long> it = answerIDs.iterator();
		while(it.hasNext()){
			this.answerIDs.add(it.next());
		}
	}
	
	public void addAnswer(Long answerID){
		answerIDs.add(answerID);
	}
	
	/*public Answer getAnswer(Long answerID){
		if(answerIDs.contains(answerID)){
			return AnswersDAO.getDAO().getAnswer(answerID);
		}
		return null;
	}*/
	public String toString(){
		String s ="";
		s = getLoginCredentials().toString() + "\n"
				+ "Name " + getFirstName() + " " + getLastName() + "\n"
						+ "AnswerIDs: ";
		s= s + answerIDs.toString();
		/*Iterator<Long> it = answerIDs.iterator();
		while(it.hasNext()){
			
		}*/
		return s;
	}
}
