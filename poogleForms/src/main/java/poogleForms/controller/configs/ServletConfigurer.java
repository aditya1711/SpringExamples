package poogleForms.controller.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import poogleForms.filters.JspIncludesFilter;

@Configuration
@ComponentScan({"poogleForms"})
@EnableWebMvc
public class ServletConfigurer implements WebMvcConfigurer {
	@Bean
	public InternalResourceViewResolver viewResolver(){
		InternalResourceViewResolver vr = new InternalResourceViewResolver();
		vr.setSuffix(".jsp");
		return vr;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(new JspIncludesFilter()).addPathPatterns("/allPageFunctionalities.jsp",
        		"/displayAllForms.jsp",
        		"/header.jsp",
        		"/MCQHandler.jsp",
        		"/TextTypeQuestionHandler.jsp",
        		"/userHeader.jsp",
        		"/Checkout.jsp");
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
