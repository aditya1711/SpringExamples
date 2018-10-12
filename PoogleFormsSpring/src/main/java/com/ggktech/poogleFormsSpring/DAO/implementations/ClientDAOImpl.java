package com.ggktech.poogleFormsSpring.DAO.implementations;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggktech.poogleFormsSpring.DAO.interfaces.AnswerDAO;
import com.ggktech.poogleFormsSpring.DAO.interfaces.ClientDAO;
import com.ggktech.poogleFormsSpring.DAO.interfaces.FormDAO;
import com.ggktech.poogleFormsSpring.service.models.clients.Client;
import com.ggktech.poogleFormsSpring.service.models.clients.ClientTypes;
import com.ggktech.poogleFormsSpring.service.models.clients.Level1Clients;
import com.ggktech.poogleFormsSpring.service.models.clients.Level2Clients;
import com.ggktech.poogleFormsSpring.service.models.clients.LoginCredentials;



@Repository
public class ClientDAOImpl extends ParentDAO implements ClientDAO{
	
	FormDAO formDAO;
	AnswerDAO answerDAO;
	
	public FormDAO getFormDAO() {
		return formDAO;
	}
	@Autowired
	public void setFormDAO(FormDAO formDAO) {
		this.formDAO = formDAO;
	}
	public AnswerDAO getAnswersDAO() {
		return answerDAO;
	}
	@Autowired
	public void setAnswersDAO(AnswerDAO answersDAO) {
		this.answerDAO = answersDAO;
	}

	public boolean checkForUsernamePasswordPair(String username, String password){
		
		String queryToCheckForUsernamePasswordPair = "use poogleForms " +
				"if exists(select username from loginCredentials where username= ? and password= ?) " +
				"select 1; " +
				"else " +
				"select 0; ";
		
		int result = getJdbcTemplate().queryForObject(queryToCheckForUsernamePasswordPair,new Object[]{username,password},Integer.class);
		if(result==1){
			return true;
		}
		return false;
	}
	
	public Client getClientByUsernameAndPassword(String username, String password){
		
		String queryForAllClientDataForOneUser = "select lc.username, lc.password, lc.clientType, c.firstName, c.lastName from (loginCredentials as lc inner join client as c on c.username = lc.username) " + 
				"where lc.username = ? and lc.password = ?";
		
		Client client = getJdbcTemplate().queryForObject(queryForAllClientDataForOneUser, new Object[]{username,password} ,new clientMapper(formDAO,answerDAO));
		return client;
	}

	@Transactional
	public boolean insertClientSpecificDetatilsIntoDB(Client client){
		
		String queryToInsertClientSpecificDetails = "insert into client(username, firstName, lastname) " +
				"values  ((select username from loginCredentials where username = ?), ?,?); ";
		String queryToInsertLoginCredentials = "insert into loginCredentials(username, password, clientType) " +
				"values(?,?, (select clientType from clientTypes where clientType = ?)); ";
		
		int result1 = getJdbcTemplate().update(queryToInsertLoginCredentials, new Object[]{client.getLoginCredentials().getUsername(),client.getLoginCredentials().getPassword(),client.getLoginCredentials().getType().getDBName()});
		int result2 = getJdbcTemplate().update(queryToInsertClientSpecificDetails, new Object[]{client.getLoginCredentials().getUsername(), client.getFirstName(),client.getLastName()});
		if(result1 == 1 && result2 == 1){
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public boolean checkIfUsernameExists(String username) {
		// TODO Auto-generated method stub
		
		String queryToCheckIfUsernameExists = "use poogleForms " +
				"if exists(select username from loginCredentials where username= ?) " +
				"select 1; " +
				"else " +
				"select 0; ";
		if(getJdbcTemplate().queryForObject(queryToCheckIfUsernameExists, new Object[]{username}, Integer.class) == 1){
			return true;
		}
		
		return false;
	}
	
	
	
	
	private static final class clientMapper implements RowMapper<Client>{
		FormDAO formDAO;
		AnswerDAO answerDAO;
		public clientMapper(FormDAO formDAO2, AnswerDAO answerDAO2){
			this.formDAO=formDAO2;
			this.answerDAO = answerDAO2;
		}
		@Override
		public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Level1Clients client = new Level1Clients();
			if(ClientTypes.getClientType(rs.getString("clientType")).equals(ClientTypes.LEVEL2)){
				System.out.println("haha");
				client = new Level2Clients();
				client.setLoginCredentials(new LoginCredentials(rs.getString("username"),rs.getString("password"), ClientTypes.LEVEL2));
				((Level2Clients)(client)).setFormsCreatedIDs(formDAO.getFormIDsWithUsername(rs.getString("username")));
			}
			else if(ClientTypes.getClientType(rs.getString("clientType")).equals(ClientTypes.LEVEL1)){
				System.out.println("hoho");
				client.setLoginCredentials(new LoginCredentials(rs.getString("username"),rs.getString("password"), ClientTypes.LEVEL1));
				
			}
			client.setAnswerIDs(answerDAO.getAnswerIDsWithUsername(rs.getString("username")));
			client.setFirstName(rs.getString("firstName"));
			client.setLastName(rs.getString("lastName"));
			return client;
		}
		
	}
}
