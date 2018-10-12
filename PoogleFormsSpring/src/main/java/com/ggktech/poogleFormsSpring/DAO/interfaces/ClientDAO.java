package com.ggktech.poogleFormsSpring.DAO.interfaces;

import com.ggktech.poogleFormsSpring.service.models.clients.Client;

public interface ClientDAO {
	public Client getClientByUsernameAndPassword(String username, String password);
	public boolean checkForUsernamePasswordPair(String username, String password);
	public boolean insertClientSpecificDetatilsIntoDB(Client client);
	public boolean checkIfUsernameExists(String username);
}
