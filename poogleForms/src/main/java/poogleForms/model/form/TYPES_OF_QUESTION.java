package poogleForms.model.form;

public enum TYPES_OF_QUESTION {
	MULTIPLE_CHOICE_QUESTION, TEXT_QUESTIONS;
	
	public String getDBName(){
		if(this.equals(MULTIPLE_CHOICE_QUESTION)){
			return "mcq";
		}
		else if(this.equals(TEXT_QUESTIONS)){
			return "textType";
		}
		else return null;
	}
	
}
