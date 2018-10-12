package com.ggktech.poogleFormsSpring.service.models.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ggktech.poogleFormsSpring.service.models.misc.IDAble;

public class Form implements IDAble<Long> {
	Map<Long, Question> map;
	ArrayList<Question> list;
	long ID;
	
	String name="Untitled";
	String adminUsername;
	
	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}

	public Form(){
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String formName) {
		this.name = formName;
	}
	
	public Map<Long, Question> getMap() {
		return map;
	}

	public void setMap(Map<Long, Question> map) {
		this.map = map;
	}

	public ArrayList<Question> getList() {
		return list;
	}

	public void setList(ArrayList<Question> list) {
		this.list = list;
	}

	@Override
	public Long getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public void setID(Long ID) {
		// TODO Auto-generated method stub
		this.ID = ID;
	}

	public String getAdminUsername() {
		return adminUsername;
	}

	public void setAdminID(String username) {
		this.adminUsername = username;
	}
	
	public Form(String name, Map<Long, Question> questionMap, String adminUsername){
		this.name = name;
		map = questionMap;
		list = new ArrayList<Question>();
		for(Entry<Long,Question> e : map.entrySet()){
			list.add(e.getValue());
		}
		this.adminUsername = adminUsername;
		//TO-DO
		ID = 0;
	}
	
	public Form(String name, ArrayList<Question> list, String adminUsername){
		this.name = name;
		this.list = list;
		map = new HashMap<Long, Question>();
		for(int i=0;i<list.size();i++){
			map.put(list.get(i).getID(), list.get(i));
		}
		this.adminUsername = adminUsername;
		//TO-DO
		ID = 0;
	}
	
	public String toString(){
		String s = new String();
		s= "\nFormID: " + getID() + "\n"
				+ "FormName: " + getName() + "\n";
		for(int i=0;i<list.size();i++){
			s = s + list.get(i).toString();
		}
		return s;
	}
	
}
