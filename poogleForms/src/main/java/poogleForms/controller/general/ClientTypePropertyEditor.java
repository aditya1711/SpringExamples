package poogleForms.controller.general;

import java.beans.PropertyEditorSupport;

import poogleForms.model.clients.ClientTypes;

public class ClientTypePropertyEditor extends PropertyEditorSupport{
	@Override
	public void setAsText(String clientType) throws IllegalArgumentException{
		if(clientType.equals("LEVEL1")){
			setValue(ClientTypes.LEVEL1);
		}
		else if(clientType.equals("LEVEL2")){
			setValue(ClientTypes.LEVEL2);
		}
	}
}
