package poogleForms.model.form;

import java.util.HashMap;
import java.util.Map;

public class QuestionTypeMapper {
	Map<TYPES_OF_QUESTION, String> typeToDisplay;
	public QuestionTypeMapper(){
		typeToDisplay = new HashMap<TYPES_OF_QUESTION, String>();
		typeToDisplay.put(TYPES_OF_QUESTION.MULTIPLE_CHOICE_QUESTION, "");
		typeToDisplay.put(TYPES_OF_QUESTION.TEXT_QUESTIONS, "");
	}
	public String get(TYPES_OF_QUESTION e){
		return typeToDisplay.get(e);
	}
}
