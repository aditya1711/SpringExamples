package poogleForms.DAO;

import java.util.HashMap;
import java.util.Map;

public class ObjectToDB_Mapper {
	public static final Map<String, String> FORM_MAP = new HashMap<String,String>();
	public static final Map<String, String> MULTIPLE_TYPE_QUESTION_MAP = new HashMap<String,String>();
	public static final Map<String, String> TEXT_TYPE_QUESTION_MAP = new HashMap<String,String>();
	public static final Map<String, String> ANSWER_MAP = new HashMap<String,String>();
	
	public static final Map<String, String> CLIENT_LEVEL1_MAP = new HashMap<String,String>();
	public static final Map<String, String> CLIENT_LEVEL2_MAP = new HashMap<String,String>();
	
	
	static{
		setFormMap();
		setMultipleTypeQuestionMap();
		setTextTypeQuestionMap();
		setAnswerMap();
		setClientLevel1Map();
		setClientLevel2Map();
	}
	
	
	public static Map<String, String> getFormMap() {
		return FORM_MAP;
	}
	public static Map<String, String> getMultipleTypeQuestionMap() {
		return MULTIPLE_TYPE_QUESTION_MAP;
	}
	public static Map<String, String> getTextTypeQuestionMap() {
		return TEXT_TYPE_QUESTION_MAP;
	}
	public static Map<String, String> getAnswerMap() {
		return ANSWER_MAP;
	}
	public static Map<String, String> getClientLevel1Map() {
		return CLIENT_LEVEL1_MAP;
	}
	public static Map<String, String> getClientLevel2Map() {
		return CLIENT_LEVEL2_MAP;
	}
	
	
	private static void setFormMap() {
		
	}
	private static void setMultipleTypeQuestionMap() {

	}
	private static void setTextTypeQuestionMap() {

	}
	private static void setAnswerMap() {

	}
	private static void setClientLevel1Map() {

	}
	private static void setClientLevel2Map() {

	}
	
	
	
	
}
