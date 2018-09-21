package poogleForms.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter(
		filterName = "AuthenticationFilter",
        urlPatterns = {
        		"/CreateForm.jsp",
        		"/CreateMCQ.jsp",
        		"/displayForms.jsp",
        		"/FormHandler.jsp",
        		"/level2page.jsp",
        		"/viewAnswers.jsp",
        		"/viewForms.jsp",
        },
        servletNames = {
        		"/CreateForm",
        		"/CreateQuestion",
        		"/DisplayAllForms",
        		"/DisplayAllFormsByUser",
        		"/FormHandler",
        		"/Dashboard",
        		"/ViewAnsweredForms",
        },
        dispatcherTypes = {javax.servlet.DispatcherType.REQUEST, javax.servlet.DispatcherType.FORWARD}
)
public class AuthenticationFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthenticationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		if(!(req.getSession().getAttribute("isClientValidationDone")!= null && req.getSession().getAttribute("isClientValidationDone").equals(true)) ){
			req.getRequestDispatcher("UnauthorizedAccess.jsp").forward(request, response);
			return;
		}
		// pass the request along the filter chain
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
