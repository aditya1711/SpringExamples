package com.ggktech.poogleFormsSpring.DAO.implementations;


import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ggktech.poogleFormsSpring.service.models.forms.FormModelTypes;

@Repository
public class DAOsHelper extends ParentDAO {

	public Long modelInsert(FormModelTypes fmt){
		
		
		KeyHolder generatedKey = new GeneratedKeyHolder();
		int result =  getJdbcTemplate().update(connection -> {
			String sql = "insert into IDTable values (?); ";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int dbValue = 0;
			if(fmt == FormModelTypes.ANSWER){
				dbValue = 3;
			}
			else if(fmt == FormModelTypes.QUESTION){
				dbValue = 2;
			}
			else if(fmt == FormModelTypes.FORM){
				dbValue = 1;
			}
			
			ps.setInt(1, dbValue);
			//ps.executeUpdate();
			return ps;
		}, generatedKey);
		
		if(result!=1){
			return null;
		}
		return generatedKey.getKey().longValue();
	}
}
