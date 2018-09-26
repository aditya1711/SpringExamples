package poogleForms.controller.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan({"poogleForms"})
public class ServletConfigurer {
	@Bean
	public InternalResourceViewResolver viewResolver(){
		InternalResourceViewResolver vr = new InternalResourceViewResolver();
		vr.setSuffix(".jsp");
		return vr;
	}
	@Bean
	public String dbURL(){
		return "jdbc:sqlserver://ggku3ser2;instanceName=SQL2016;databaseName=poogleForms";
	}
	@Bean
	public String userID(){
		return "sa";
	}
	@Bean
	public String password(){
		return "Welcome@1234";
	}
}
