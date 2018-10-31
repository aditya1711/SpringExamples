package com.ggktech.poogleFormsSpring.DAO.implementations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggktech.poogleFormsSpring.DAO.interfaces.FormDAO;
import com.ggktech.poogleFormsSpring.service.models.forms.Form;
import com.ggktech.poogleFormsSpring.service.models.forms.FormModelTypes;
import com.ggktech.poogleFormsSpring.service.models.forms.MultipleChoiceTypeQuestion;
import com.ggktech.poogleFormsSpring.service.models.forms.Question;
import com.ggktech.poogleFormsSpring.service.models.forms.TYPES_OF_QUESTION;
import com.ggktech.poogleFormsSpring.service.models.forms.TextTypeQuestion;

@Repository
public class FormDAOImpl extends ParentDAO implements FormDAO {

	@Autowired
	private DAOsHelper daosHelper;

	public DAOsHelper getDaosHelper() {
		return daosHelper;
	}

	public void setDaosHelper(DAOsHelper daosHelper) {
		this.daosHelper = daosHelper;
	}

	@Override
	public HashSet<Long> getFormIDsWithUsername(String username){
		String queryForGetingFormIDsWithUsername = "select ID as formID from forms where username=?;";
		List<Long> formIDs = getJdbcTemplate().queryForList(queryForGetingFormIDsWithUsername,new Object[]{"username"},Long.class);
		return new HashSet<Long>(formIDs);
	}
	@Override
	public boolean checkForLevel2UsernameAndFormIDPair(String username, Long formID) {
		// TODO Auto-generated method stub
		String queryForCheckForLevel2UsernameAndFormIDPair ="select case when (select username from  forms where ID = ?) = ? then 1 else 0 end; ";

		return (getJdbcTemplate().queryForObject(queryForCheckForLevel2UsernameAndFormIDPair, new Object[]{formID,username} ,Integer.class))==1 ? true : false;
	}
	@Override
	public HashSet<Long> getAllFormIDs() {
		// TODO Auto-generated method stub
		String queryForGetingAllFormIDs ="select ID as formID from forms; ";
		return new HashSet<Long>(getJdbcTemplate().queryForList(queryForGetingAllFormIDs,Long.class));
	}
	@Override
	public Form getFormWithFormID(Long ID) {
		// TODO Auto-generated method stub
		String queryForGetingFormSpecificDetailsWithID = "select name as formName,username,ID as formID from forms where id = ?;";
		
		return getJdbcTemplate().queryForObject(queryForGetingFormSpecificDetailsWithID, new Object[]{ID}, new FormMapper(this));
	}
	
	public ArrayList<Question> getQuestionsWithFormID(Long formID){
		String queryForGetingQuestionsWithFormID = "select prompt, type as questionType, formID, ID as questionID, options from questions where formID = ?";
		return new ArrayList<Question>(getJdbcTemplate().query(queryForGetingQuestionsWithFormID,new Object[]{formID} ,new QuestionMapper()));
	}
	
	@Override
	public Question getQuestionWithQuestionID(Long questionID) {
		// TODO Auto-generated method stub
		String queryForGetingQuestionWithID = "select prompt, type as questionType,formID, ID as questionID, options from questions where id = ?;";
		return getJdbcTemplate().queryForObject(queryForGetingQuestionWithID,new Object[]{questionID} ,new QuestionMapper());
	}
	
	@Override
	@Transactional
	public Long addQuestionToDB(Question q) {
		// TODO Auto-generated method stub
		Long idTableGeneratedId = daosHelper.modelInsert(FormModelTypes.QUESTION);

		String queryForInsertingQuestion = "insert into questions (prompt, type, formID, options, ID) " + 
				"values (?,(select questionType from questionTypes where questionType = ?),(select ID from forms where ID = ?),?, (select ID from IDTable where ID=?)); ";
		
		String options="";
		if(q.getType().equals(TYPES_OF_QUESTION.MULTIPLE_CHOICE_QUESTION)){
			System.out.println("mcq found in form: " + q.getFormID());
			MultipleChoiceTypeQuestion mcq =(MultipleChoiceTypeQuestion)q;

			for(int index = 0;index<mcq.getOptions().size();index++){
				options = options + mcq.getOptions().get(index) + ";";
			}
		}
		else if(q.getType().equals(TYPES_OF_QUESTION.TEXT_QUESTIONS)){
			options = "";
		}
		
		int rowsUpdated =  getJdbcTemplate().update(queryForInsertingQuestion, new Object[]{q.getPrompt(),q.getType(),q.getFormID(), options ,idTableGeneratedId});
		
		return (rowsUpdated==1) ? idTableGeneratedId : null;
		
	}
	
	@Override
	@Transactional
	public Long addFormPrototypeToDB(Form f) {
		// TODO Auto-generated method stub
		Long idTableGeneratedId;
		int rowsUpdate;
		try {
			idTableGeneratedId = daosHelper.modelInsert(FormModelTypes.FORM);
			
			int i = 0/0;
			
			String queryForInsertingFormSpecificDetatils = "insert into forms (name, username, ID) " +
					"values (?,(select username from client where username=?), (select ID from IDTable where ID=?)); ";
			rowsUpdate = getJdbcTemplate().update(queryForInsertingFormSpecificDetatils, new Object[]{f.getName(), f.getAdminUsername(), idTableGeneratedId});
			return (rowsUpdate==1) ? idTableGeneratedId : null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("?||||||||||||||||||||||||");
			e.printStackTrace();
		}
		return null;
		
	}
	@Override
	public Long updateFormName(Long formID, String formName) {
		// TODO Auto-generated method stub
		String queryForUpdatingFormName ="update forms set name= ? where ID = ?";
		return (long) getJdbcTemplate().update(queryForUpdatingFormName, new Object[]{formName, formID});
	}
	@Override
	public Long deleteQuestion(Long questionID) {
		// TODO Auto-generated method stub
		String queryForDeletingQuestion = "delete from questions where ID = ? ";
		return (long) getJdbcTemplate().update(queryForDeletingQuestion, new Object[]{questionID});
	}
	@Override
	public Long deleteForm(Long formID) {
		// TODO Auto-generated method stub
		String queryForDeletingForm = "alter table questions " +
				"nocheck constraint [FK__questions__formI__6754599E]; " +
				"delete from forms where ID =?; " +
				"alter table questions " +
				"check constraint [FK__questions__formI__6754599E];";
		
		return (long) getJdbcTemplate().update(queryForDeletingForm, new Object[]{formID});
	}



	public static final class FormMapper implements RowMapper<Form>{
		FormDAOImpl formDAO;
		public FormMapper(FormDAOImpl formDAO) {
			// TODO Auto-generated constructor stub
			this.formDAO = formDAO;
		}
		
		@Override
		public Form mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Form form = new Form();
			form.setAdminUsername(rs.getString("username"));
			form.setName(rs.getString("formName"));
			form.setID(rs.getLong("formID"));
			form.setList(formDAO.getQuestionsWithFormID(form.getID()));
			return form;
		}

	}
	public static final class QuestionMapper implements RowMapper<Question>{

		@Override
		public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Question q;
			if(rs.getString("questionType").equals("mcq")){
				q = new MultipleChoiceTypeQuestion();
				MultipleChoiceTypeQuestion mcq = (MultipleChoiceTypeQuestion)q;
				mcq.setOptions(new ArrayList<String>(Arrays.asList(rs.getString("options").split(";"))));

			}
			else if(rs.getString("questionType").equals("text type")){
				q = new TextTypeQuestion();
				TextTypeQuestion tq = (TextTypeQuestion)q;
			}
			else{
				q = new TextTypeQuestion();
			}
			q.setFormID(rs.getLong("formID"));
			q.setID(rs.getLong("questionID"));
			q.setPrompt(rs.getString("prompt"));
			return q;
		}

	}
}
