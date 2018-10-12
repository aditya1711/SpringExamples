package com.ggktech.poogleFormsSpring.service.springConfigs;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ComponentScan(basePackages = {"com.ggktech.poogleFormsSpring"})
@EnableTransactionManagement
public class SpringConfiguration {
	@Bean
	public DataSource dataSource(){
		BasicDataSource ds =  new BasicDataSource();
		
		ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		ds.setUrl(dbUrl());
		ds.setUsername(dbUserID());
		ds.setPassword(dbPassword());
		ds.setInitialSize(5);
		return ds;
	}
	
	 @Bean
     public org.springframework.jdbc.datasource.DataSourceTransactionManager txManager() {
         return new org.springframework.jdbc.datasource.DataSourceTransactionManager(dataSource());
     }
	
	 @Bean
	 public ObjectMapper mapper(){
		 return new ObjectMapper();
	 }
	
	@Bean
	public String dbUrl(){
		return "jdbc:sqlserver://ggku3ser2;instanceName=SQL2016;databaseName=poogleForms";
	}
	@Bean
	public String dbUserID(){
		return "sa";
	}
	@Bean
	public String dbPassword(){
		return "Welcome@1234";
	}
}