package poogleForms.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class JspIncludesFilter extends HandlerInterceptorAdapter {

    
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		System.out.println("Got request to save data : name:"+request.getParameter("name"));
		if(request.getRequestURI().contains("header.jsp")){
			response.sendRedirect("UnauthorizedAccess.jsp");
			return false;
		}
		else return true;
	}
	 

}
