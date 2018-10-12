package com.ggktech.poogleFormsSpring.service.models.clients;

public interface Client {
	public LoginCredentials getLoginCredentials();
	public void setLoginCredentials(LoginCredentials lc);
	
	public String getFirstName();
	public void setFirstName(String firstname);
	public String getLastName();
	public void setLastName(String lastName);
}
