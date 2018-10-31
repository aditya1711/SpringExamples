package com.ggktech.poogleFormsSpring.DAO.implementations;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.jdbc.core.RowMapper;
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
	public Map<Long, FormModelTypes> getIDTable(){
		
		String sql = "select id from IDTable order by id";
		//Map<Long, FormModelTypes> map = new HashMap<Long,FormModelTypes>(getJdbcTemplate().query(sql, new idMapper()));
		
		ArrayList<Long> list = new ArrayList<Long>(getJdbcTemplate().queryForList(sql, Long.class));
		//Collections.sort(list);
		Map<Long, FormModelTypes> map = new HashMap<Long,FormModelTypes>();
		for(int i=0;i< list.size();i++){
			map.put(list.get(i), FormModelTypes.FORM);
		}
		return map ;
	}
	/*class idMapper implements RowMapper<Entry>{

		@Override
		public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Entry e = new Entry<Long, FormModelTypes>() {
				private final Long key;
				private FormModelTypes value;
				@Override
				public Long getKey() {
					// TODO Auto-generated method stub
					try {
						return rs.getLong("");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}

				@Override
				public FormModelTypes getValue() {
					// TODO Auto-generated method stub
					try {
						return FormModelTypes(rs.getString("type"));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}

				@Override
				public FormModelTypes setValue(FormModelTypes value) {
					// TODO Auto-generated method stub
					return null;
				}
			};
			return null;
		}

	}*/
}
