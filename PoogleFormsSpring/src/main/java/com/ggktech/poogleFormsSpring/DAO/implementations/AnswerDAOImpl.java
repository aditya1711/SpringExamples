package com.ggktech.poogleFormsSpring.DAO.implementations;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggktech.poogleFormsSpring.DAO.events.DbInsertEvent;
import com.ggktech.poogleFormsSpring.DAO.interfaces.AnswerDAO;
import com.ggktech.poogleFormsSpring.service.models.forms.Answer;
import com.ggktech.poogleFormsSpring.service.models.forms.FormModelTypes;

@Repository
@PropertySources({@PropertySource("classpath:experimental.properties")})
public class AnswerDAOImpl extends ParentDAO implements AnswerDAO,ApplicationEventPublisherAware{
	
	
	public ObjectMapper mapper;
	private ApplicationEventPublisher eventPublisher;
	
	public ObjectMapper getMapper() {
		return mapper;
	}
	//@Autowired
	@Resource
	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Autowired
	private DAOsHelper daosHelper;
	
	public DAOsHelper getDaosHelper() {
		return daosHelper;
	}

	public void setDaosHelper(DAOsHelper daosHelper) {
		this.daosHelper = daosHelper;
	}

	@Override
	public HashSet<Long> getAnswerIDsWithUsername(String username){
		String queryForGetingAnswerIDsWithUsername = "select ID as answerID from answers where json_value(answerJson, '$.username')  = ?;";
		List<Long> answerIDsList = getJdbcTemplate().queryForList(queryForGetingAnswerIDsWithUsername, new Object[]{username},Long.class);
		return new HashSet<Long>(answerIDsList);
	}

	@Override
	public Map<Long, Answer> getAnswersWithUsernameAndFormID(String username, Long formID) {
		// TODO Auto-generated method stub
		
		String queryForCheckingIfAnswerWithFormIDAndUsername ="select count(ID) as countAnswerID from answers where json_value(answerJson, '$.username')  = ? and json_value(answerJson, '$.questionID')  IN  "
				+ "(select ID from questions where formID = ?); ";
		int countOfAnswers = getJdbcTemplate().queryForObject(queryForCheckingIfAnswerWithFormIDAndUsername, new Object[]{username,formID}, Integer.class);
		
		Map<Long,Answer> answerMap = new HashMap<Long,Answer>();
		
		if(countOfAnswers>0){
			String queryForGetingAnswerWithFormIDAndUsername ="select ID as answerID, answerJson from answers where json_value(answerJson, '$.username')  = ? and json_value(answerJson, '$.questionID')  IN  "
					+ "(select ID from questions where formID = ?) order by json_value(answerJson, '$.questionID') ;";
			List<Answer> answersList = getJdbcTemplate().query(queryForGetingAnswerWithFormIDAndUsername,new Object[]{username, formID}, new AnswerMapper(mapper));
			for(Answer answer : answersList){
				answerMap.put(answer.getID(), answer);
			}
		}

		return answerMap;
	}

	@Override
	public HashSet<Long> getAnsweredFormIDsWithUsername(String username) {
		// TODO Auto-generated method stub
		
		String queryForGetingAnsweredFormIDsWithUsername ="select distinct formID " +
				"from questions where ID in " +
				"(select json_value(answerJson, '$.questionID') as questionID from answers where json_value(answerJson, '$.username') = ?) "
				+ "AND formID in (select ID from forms); " ;
		List<Long> formIDList = getJdbcTemplate().queryForList(queryForGetingAnsweredFormIDsWithUsername,new Object[]{username},Long.class);
		
		return new HashSet<Long>(formIDList);
	}
	
	@Override
	@Transactional
	public Long insertAnswerInDB(Answer ans) throws DataAccessException, JsonProcessingException {
		if(checkToUpdate(ans)){
			return (updateAnswer(ans)? (long)1 : null);
		}
		else{
			Long idTableInsertGeneratedKey = daosHelper.modelInsert(FormModelTypes.ANSWER);
			if(idTableInsertGeneratedKey==null){
				return (long)0;
			}
			else{
				String queryForInsertingAnswer = "insert into answers (ID,answerJson) " + 
						"values ((select ID from IDTable where ID=?),?); ";
				getJdbcTemplate().update(queryForInsertingAnswer, new Object[]{idTableInsertGeneratedKey,ans.toJSONString()});
				System.out.println();
				eventPublisher.publishEvent(new DbInsertEvent(ans));
				
				return idTableInsertGeneratedKey;
			}
		}
		
	}
	
	private boolean checkToUpdate(Answer ans){
		String queryForCheckAnswerExist="select ( " +
				"case when exists(select ID from answers where json_value(answerJson, '$.answers[0]') <> 'null' and json_value(answerJson, '$.questionID') = ? and json_value(answerJson, '$.username') = ?) "+
				"then 1 " +
				"else 0 " +
				"end) as result ;";
		if(getJdbcTemplate().queryForObject(queryForCheckAnswerExist,new Object[]{ans.getQuestionID(),ans.getUsername()} ,Integer.class)==1){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean updateAnswer(Answer ans) throws DataAccessException, JsonProcessingException {
		String queryForUpdateAnswer ="update answers " +
				"set answerjson = ? " +
				"where json_value(answerJson, '$.questionID') = ? and json_value(answerJson, '$.username') = ?";
		int rowsUpdated = getJdbcTemplate().update(queryForUpdateAnswer,new Object[]{ans.toJSONString(),ans.getQuestionID(),ans.getUsername()});
		if(rowsUpdated==1){
			eventPublisher.publishEvent(new DbInsertEvent(ans));
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public Long getCountOfUsersAnsweredAForm(Long formID) {
		// TODO Auto-generated method stub
		String sql ="select count(ID) as count from answers where json_value(answerJson, '$.questionID') = ?; ";
		return getJdbcTemplate().queryForObject(sql, new Object[]{formID} ,Long.class);
	}
	
	private static final class AnswerMapper implements RowMapper<Answer>{
		ObjectMapper mapper;
		
		public AnswerMapper(ObjectMapper mapper) {
			// TODO Auto-generated constructor stub
			this.mapper=mapper;
		}

		@Override
		public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			try {
				//System.out.println(mapper);
				return mapper.readValue(rs.getString("answerJson"), Answer.class);
				
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		// TODO Auto-generated method stub
		this.eventPublisher = applicationEventPublisher;
	}
}
