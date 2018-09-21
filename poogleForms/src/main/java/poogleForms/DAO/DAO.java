package poogleForms.DAO;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.PooledConnection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.microsoft.sqlserver.jdbc.SQLServerConnectionPoolDataSource;

public class DAO {
	
	private static final Logger logger = LogManager.getLogger(DAO.class.getName());
	
	/*private DatabaseLoginCredentials databaseLoginCredentials;
	
	public DatabaseLoginCredentials getDatabaseLoginCredentials() {
		return databaseLoginCredentials;
	}

	public void setDatabaseLoginCredentials(DatabaseLoginCredentials databaseLoginCredentials) {
		this.databaseLoginCredentials = databaseLoginCredentials;
	}*/

	private String dbURL, password, userID;
	private String connectionTemp;
	private SQLServerConnectionPoolDataSource ds;
	public PooledConnection pc;
	
	protected DAO(){
		
	}
	
	public String getConnectionTemp() {
		return connectionTemp;
	}
	
	@PostConstruct
	public void postInitializeSetConnection() throws SQLException{
		if(dbURL!=null && !dbURL.equals("") && password!=null && !password.equals("") && userID!=null && !userID.equals("")){
			logger.info("setting Up Connection in post contruct method");
			setUpDB();
		}
		else{
			logger.warn("required parameteres not set while initializing in post construct method");
		}
	}

	public void setConnectionTemp(String toDo) throws SQLException{
		logger.info(toDo + dbURL + userID +password);
		if(toDo.equals("true") && dbURL!=null && !dbURL.equals("") && password!=null && !password.equals("") && userID!=null && !userID.equals("")){
			System.out.println("Setting connection temp");
			setUpDB();
		}
	}
	
	public void setDAO(String dbURL, String userID, String password) throws SQLException{
		if (this.dbURL==null || this.password== null || this.userID==null) {
			System.out.println("setting DAO");
			setDbURL(dbURL);
			setPassword(password);
			setUserID(userID);
			setUpDB();
		}
	}

	private void setUpDB() throws SQLException {
		// TODO Auto-generated method stub
		if(pc == null){
			System.out.println("PC is null");
			 ds = new SQLServerConnectionPoolDataSource();
			 ds.setURL(getDbURL());
			 ds.setUser(getUserID());
			 ds.setPassword(getPassword());
			 
			 pc = ds.getPooledConnection();
		}
	}

	public String getDbURL() {
		return dbURL;
	}
	
	@Autowired
	public void setDbURL(String dbURL) {
		logger.info("Seting DBURL");
		this.dbURL = dbURL;
	}

	public String getPassword() {
		return password;
	}
	@Autowired
	public void setPassword(String password) {
		logger.info("Seting passoerd");
		this.password = password;
	}

	public String getUserID() {
		return userID;
	}
	@Autowired
	public void setUserID(String userID) {
		logger.info("Seting USERID");
		this.userID = userID;
	}
	
	public Connection getConnection() throws SQLException{
		if(pc==null){
			System.out.println("PC IS NULL IN getConnection()");
		}
		return pc.getConnection();
	}
}
