package com.ggktech.poogleFormsSpring.service.models.clients;

import java.util.HashSet;

public class Level2Clients extends Level1Clients {
	private HashSet<Long> formsCreatedIDs;

	public HashSet<Long> getFormsCreatedIDs() {
		return formsCreatedIDs;
	}

	public void setFormsCreatedIDs(HashSet<Long> formsCreatedIDs) {
		this.formsCreatedIDs = formsCreatedIDs;
	}
	
	public void addCreatedForm(Long formID){
		formsCreatedIDs.add(formID);
	}
	/*public Form getForm(Long ID){
		if(formsCreatedIDs.contains(ID)){
			return FormDAO.getDAO.getForm(ID);
		}
	}*/
	public String toString(){
		return super.toString() + "\nFormsCreated: " + formsCreatedIDs.toString();
	}
}
 