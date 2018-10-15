package com.ggktech.poogleFormsSpring.DAO.events;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources({@PropertySource("classpath:experimental.properties")})
public class DbInsertEvent extends ApplicationEvent {
	
	Object addedObject;
	
	@Value("${DB.answers.insert.event.success}")
	public String insertMessage;
	
	public DbInsertEvent(Object source) {
		super(source);
		addedObject = source;
		// TODO Auto-generated constructor stub
	}
	public String toString(){
		return insertMessage +   "\nof Object: " + addedObject.getClass() + " accured  at Time: " + getTimestamp() + " with values: \n" + addedObject.toString();
	}

}
