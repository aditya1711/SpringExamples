package poogleForms.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import poogleForms.maintainance.logs.DOAsLogs;
import poogleForms.model.form.Answer;

@Component
public class AnswersDAO extends DAO implements DOAsLogs{
	
	//private static final Logger logger = LogManager.getLogger("DAOsLogger");
	private static final Logger logger = LogManager.getLogger(AnswersDAO.class.getName());
	
	private static AnswersDAO answerDAO = new AnswersDAO();
	
	public static  ObjectMapper mapper = new ObjectMapper();
	
	private static final String queryForGetingAnswerIDsWithUsername = "select ID as answerID, answerJson from answers where json_value(answerJson, '$.username')  = ?;";
	private static final String queryForGetingAnswerWithID ="select ID as answerID, answerJson from answers where ID=?;";
	private static final String queryForGetingAnswerWithFormIDAndUsername ="select ID as answerID, answerJson from answers where json_value(answerJson, '$.username')  = ? and json_value(answerJson, '$.questionID')  IN  "
			+ "(select ID from questions where formID = ?) order by json_value(answerJson, '$.questionID') ;";
	
	private static final String queryForInsertingAnswer  =  "begin try " + 
			"begin transaction " + 
			"insert into IDTable " + 
			"values ('3'); " +
			"insert into answers (ID,answerJson) " + 
			"values (SCOPE_IDENTITY(),?); " +
			"commit; " +
			"end try " +
			"begin catch " +
			"rollback; " +
			"end catch; " ;
	
	private static final String queryForGetingAnsweredFormIDsWithUsername ="select distinct formID " +
			"from questions where ID in " +
			"(select json_value(answerJson, '$.questionID') as questionID from answers where json_value(answerJson, '$.username') = ?) "
			+ "AND formID in (select ID from forms); " ;
	
	private static final String queryForCheckAnswerExist="select ( " +
			"case when exists(select ID from answers where json_value(answerJson, '$.answers[0]') <> 'null' and json_value(answerJson, '$.questionID') = ? and json_value(answerJson, '$.username') = ?) then 1 " +
			"else 0 " +
			"end) as result ;";

	private static final String queryForUpdateAnswer ="update answers " +
			"set answerjson = ? " +
			"where json_value(answerJson, '$.questionID') = ? and json_value(answerJson, '$.username') = ?";
	
	private AnswersDAO(){
		
	}
	public static AnswersDAO getAnswersDAO(String dbURL, String userID, String password) throws SQLException{
		answerDAO.setDAO(dbURL, userID, password);
		return answerDAO;
	}
	
	public Map<Long,Answer> getAnswersWithUsernameAndFormID(String username, Long formID){
		Map<Long,Answer> answers = new HashMap<Long,Answer>();
		
		try(Connection conn = getConnection()){
			PreparedStatement ps = conn.prepareStatement(queryForGetingAnswerWithFormIDAndUsername);
			int i=1;
			ps.setString(i++,username);
			ps.setLong(i++,formID);
			ResultSet rs= ps.executeQuery();
	
			while(rs.next()){
				Answer answer = mapper.readValue(rs.getString("answerJson"), Answer.class);
				answer.setID(rs.getLong("answerID"));
				answers.put(answer.getQuestionID(), answer);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		if(answers.isEmpty()){
			return null;
		}
		return answers;
	}
	
	public HashSet<Long> getAnswerIDsWithUsername(String username){
		HashSet<Long> answerIDs = new HashSet<Long>();
		try(Connection conn = getConnection()){
			PreparedStatement ps = conn.prepareStatement(queryForGetingAnswerIDsWithUsername);
			int i=1;
			ps.setString(i++,username);
			ResultSet rs= ps.executeQuery();
			while(rs.next()){
				answerIDs.add(rs.getLong("answerID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return answerIDs;
	}
	
	public HashSet<Long> getAnsweredFormIDsWithUsername(String username){
		HashSet<Long> formIDs = new HashSet<Long>();
		try(Connection conn = getConnection()){
			PreparedStatement ps = conn.prepareStatement(queryForGetingAnsweredFormIDsWithUsername);
			int i=1;
			ps.setString(i++,username);
			ResultSet rs= ps.executeQuery();
			while(rs.next()){
				formIDs.add(rs.getLong("formID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return formIDs;
	}
	
	public Answer getAnswerWithID(Long ID){
		try(Connection conn = getConnection()){
			PreparedStatement ps = conn.prepareStatement(queryForGetingAnswerWithID);
			int i=1;
			ps.setLong(i++,ID);
			ResultSet rs= ps.executeQuery();
			rs.next();
			Answer answer = mapper.readValue(rs.getString("answerJson"), Answer.class);
			answer.setID(rs.getLong("answerID"));
			return answer;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public void addAnswerInDB(Answer ans){
		if(checkToUpdate(ans)){
			updateAnswer(ans);
		}
		else{
			try(Connection conn = getConnection()){
				PreparedStatement ps = conn.prepareStatement(queryForInsertingAnswer);
				int i=1;
				ps.setString(i++, ans.toJSONString());
				
				ps.executeUpdate();
				
				logger.info("A new ans added: " + ans);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("adding ans failed in DAO" + e);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				logger.error("adding ans failed in DAO" + e);
				e.printStackTrace();
			}
		}
	}
	private void updateAnswer(Answer ans) {
		// TODO Auto-generated method stub
		try(Connection conn = getConnection()){
			PreparedStatement ps = conn.prepareStatement(queryForUpdateAnswer);
			int i=1;
			ps.setString(i++, ans.toJSONString());
			ps.setLong(i++, ans.getQuestionID());
			ps.setString(i++, ans.getUsername());
			
			ps.executeUpdate();
			
			logger.info("Ans Updated: " + ans);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("updating ans failed in DAO" + e);
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("updating ans failed in DAO" + e);
			e.printStackTrace();
		}
	}
	private boolean checkToUpdate(Answer ans){
		try(Connection conn = getConnection()){
			PreparedStatement ps = conn.prepareStatement(queryForCheckAnswerExist);
			int i=1;
			ps.setLong(i++, ans.getQuestionID());
			ps.setString(i++, ans.getUsername());
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			int result =rs.getInt("result");
			if(result==0){
				return false;
			}
			else{
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public Long getCountOfUsersAnsweredAQuestion(Long questionID){
		Long count=(long) 0;
		try(Connection conn = getConnection()){
			PreparedStatement ps = conn.prepareStatement("select count(ID) as count from answers where json_value(answerJson, '$.questionID') = ?; ");
			int i=1;
			ps.setLong(i++, questionID);
			
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			count =rs.getLong("count");
			
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public Long getCountOfUsersAnsweredAForm(Long formID){
		Long count=(long) 0;
		try(Connection conn = getConnection()){
			PreparedStatement ps = conn.prepareStatement("select count(ID) as count from answers where json_value(answerJson, '$.questionID') IN (select ID from questions where formID = ?); ");
			int i=1;
			ps.setLong(i++, formID);
			
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			count =rs.getLong("count");
			
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
