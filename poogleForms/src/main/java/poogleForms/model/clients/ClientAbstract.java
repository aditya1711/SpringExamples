package poogleForms.model.clients;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public abstract class ClientAbstract implements Client {
	
	@Valid @NotNull
	private LoginCredentials loginCredentials;
	
	@Pattern(regexp="[^0-9]*" , message = "Name must not contain numbers")
	private String firstName, lastName;
	//ClientTypes clientType;
	
	public LoginCredentials getLoginCredentials() {
		return loginCredentials;
	}
	public void setLoginCredentials(LoginCredentials loginCredentials) {
		this.loginCredentials = loginCredentials;
	}
	
	@Override
	public String getFirstName() {
		return firstName;
	}
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Override
	public String getLastName() {
		return lastName;
	}
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
