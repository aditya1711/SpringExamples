package com.ggktech.poogleFormsSpring.DAO.implementations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public abstract class ParentDAO extends JdbcDaoSupport{
	@Autowired
	public void setDatasource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
	
}
