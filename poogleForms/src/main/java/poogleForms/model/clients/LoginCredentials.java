package poogleForms.model.clients;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:ValidationMessages.properties")
public class LoginCredentials {
	
	@NotNull @IsValidNewUsername
	private String username;
	
	@Size(min=6, max=15, message= "#{${Size.password}}")
	private String  password;
	@NotNull
	private ClientTypes type;
	
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public ClientTypes getType() {
		return type;
	}


	public void setType(ClientTypes type) {
		this.type = type;
	}
	
	public LoginCredentials() {
		super();
	}

	public LoginCredentials(String username, String password, ClientTypes t){
		setUsername(username);
		setPassword(password);
		setType(t);
	}
	
	
	public String toString(){
		return "username: " + getUsername() + " Password: " + getPassword() + " clientType: " + getType() ;
	}
	
}
