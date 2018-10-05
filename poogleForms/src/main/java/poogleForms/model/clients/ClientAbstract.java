package poogleForms.model.clients;

public abstract class ClientAbstract implements Client {
	LoginCredentials loginCredentials;
	String firstName, lastName;
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
