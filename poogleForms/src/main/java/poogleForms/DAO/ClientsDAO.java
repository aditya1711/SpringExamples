package poogleForms.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import poogleForms.maintainance.logs.DOAsLogs;
import poogleForms.model.clients.Client;
import poogleForms.model.clients.ClientAbstract;
import poogleForms.model.clients.ClientTypes;
import poogleForms.model.clients.Level1Clients;
import poogleForms.model.clients.Level2Clients;
import poogleForms.model.clients.LoginCredentials;

@Component
public class ClientsDAO extends DAO implements DOAsLogs{
	private static final Logger logger = LogManager.getLogger(ClientsDAO.class.getName());
	
	private static ClientsDAO clientsDAO = new ClientsDAO();
	
	private static final String queryForAllClientData = "select lc.username, lc.password, lc.clientType, c.firstName, c.lastName from loginCredentials as lc inner join client as c on c.username = lc.username;"; 
	
	private static final String queryForAllClientDataForOneUser = "select lc.username, lc.password, lc.clientType, c.firstName, c.lastName from (loginCredentials as lc inner join client as c on c.username = lc.username) " + 
																		"where lc.username = ? and lc.password = ?";
	
	private static final String queryToCheckForUsernamePasswordPair = "use poogleForms " +
																			"if exists(select username from loginCredentials where username= ? and password= ?) " +
																				"select 1; " +
																					"else " +
																						"select 0; ";
	private static final String queryToCheckIfUsernameExists = "use poogleForms " +
			"if exists(select username from loginCredentials where username= ?) " +
				"select 1; " +
					"else " +
						"select 0; ";
	
	
	private static final String queryToInsertClientSpecificDetails = "begin try " + 
			"begin transaction " +
			"insert into client(username, firstName, lastname) " +
			"values  ((select username from loginCredentials where username = ?), ?,?); " +
			"commit; " +
			"end try " +
			"begin catch " +
			"rollback; " +
			"end catch; " ;
	private static final String queryToInsertLoginCredentials = "begin try " + 
			"begin transaction " +
			"insert into loginCredentials(username, password, clientType) " +
			"values(?,?, (select clientType from clientTypes where clientType = ?)); " +
			"commit; " +
			"end try " +
			"begin catch " +
			"rollback; " +
			"end catch; " ;
	
	private ClientsDAO(){
		
	}
	
	private FormDAO formDAO;
	private AnswersDAO answersDAO;
	
	public AnswersDAO getAnswersDAO() {
		return answersDAO;
	}
	
	@Autowired
	public void setAnswersDAO(AnswersDAO answersDAO) {
		this.answersDAO = answersDAO;
	}

	public FormDAO getFormDAO() {
		return formDAO;
	}
	@Autowired
	public void setFormDAO(FormDAO formDAO) {
		this.formDAO = formDAO;
	}

	
	
	public static ClientsDAO getClientDAO(String dbURL, String userID, String password) throws SQLException{
		clientsDAO.setDAO(dbURL, userID, password);
		clientsDAO.setFormDAO(FormDAO.getFormDAO(dbURL, userID, password));
		clientsDAO.setAnswersDAO(AnswersDAO.getAnswersDAO(dbURL, userID, password));
		return clientsDAO;
	}
	
	public Client getClientByUsername(String username, String password){
		return addClientData(username, password);
	}
	
	private Client addClientData(String username, String password){
		Client c = new Level1Clients();
		PreparedStatement ps;
		try(Connection conn = getConnection();) {
			ps = conn.prepareStatement(queryForAllClientDataForOneUser);
			int i=1;
			ps.setString(i++, username);
			ps.setString(i++, password);
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			if(ClientTypes.getClientType(rs.getString("clientType")).equals(ClientTypes.LEVEL2)){
				c = new Level2Clients();
				addLevel2Data(c,username);
			}
			c.setLoginCredentials(new LoginCredentials(rs.getString("username"),rs.getString("password"), ClientTypes.getClientType(rs.getString("clientType"))));
			c.setFirstName(rs.getString("firstName"));
			c.setLastName(rs.getString("lastName"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addLevel1Data(c, username);
		return c;
	}
	private void addLevel1Data(Client client, String username){
		Level1Clients l1c = (Level1Clients)(client);
		l1c.setAnswersIDs(answersDAO.getAnswerIDsWithUsername(username));
	}
	private void addLevel2Data(Client client, String username){
		Level2Clients l2c = (Level2Clients)(client);
		l2c.setFormsCreatedIDs(formDAO.getFormIDsWithUsername(username));
		
	}

	
	public boolean checkForUsernamePasswordPair(String username, String password){
		try(Connection conn = pc.getConnection();) {
			PreparedStatement ps = conn.prepareStatement(queryToCheckForUsernamePasswordPair);
			int i=1;
			ps.setString(i++, username);
			ps.setString(i++, password);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			if(rs.getInt(1) == 1)
				return true;
			else
				return false;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	
	public void addClientToDB(ClientAbstract client){
		addLoginCredentialsToDB(client.getLoginCredentials());
		
		try(Connection conn = getConnection()){
			PreparedStatement ps = conn.prepareStatement(queryToInsertClientSpecificDetails);
			int i=1;
			ps.setString(i++, client.getLoginCredentials().getUsername());
			ps.setString(i++, client.getFirstName());
			ps.setString(i++, client.getLastName());
			
			ps.execute();
			
			logger.info("Adding Client to DAO: " + client);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("adding Client failed in DAO" + e);
		}
	}
	
	public void addLoginCredentialsToDB(LoginCredentials lc){
		try(Connection conn = getConnection()){
			PreparedStatement ps = conn.prepareStatement(queryToInsertLoginCredentials);
			int i=1;
			ps.setString(i++, lc.getUsername());
			ps.setString(i++, lc.getPassword());
			ps.setString(i++, lc.getType().getDBName());
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("adding LC failed in DAO" + e);
			e.printStackTrace();
			
		}
	}

	public boolean checkIfUsernameExists(String username) {
		// TODO Auto-generated method stub
		try(Connection conn = pc.getConnection();) {
			PreparedStatement ps = conn.prepareStatement(queryToCheckIfUsernameExists);
			int i=1;
			ps.setString(i++, username);
			ResultSet rs = ps.executeQuery();
			rs.next();
			if(rs.getInt(1) == 1)
				return true;
			else
				return false;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
