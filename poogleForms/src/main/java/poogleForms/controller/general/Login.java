package poogleForms.controller.general;

import java.io.IOException;
import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import poogleForms.DAO.ClientsDAO;
import poogleForms.controller.configs.PoogleFormsContext;
import poogleForms.maintainance.logs.ControllerLogs;
import poogleForms.model.clients.Client;
import poogleForms.model.clients.Level1Clients;

@Controller
public class Login {
	private static final long serialVersionUID = 1L;
    

	public ClientsDAO clientsDAO;

	public ClientsDAO getClientsDAO() {
		return clientsDAO;
	}
	@Autowired
	public void setClientsDAO(ClientsDAO clientsDAO) {
		this.clientsDAO = clientsDAO;
	}
	
	public Login() {
    }
	
	@PostConstruct
	public void initForLogin(){
		System.out.println(clientsDAO);
	}

	@RequestMapping(value="/Login" , method = RequestMethod.GET)
	protected ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if(session.getAttribute("isClientValidationDone")!=null && session.getAttribute("isClientValidationDone").equals(true) && session.getAttribute("client")!=null){
			return new Dasboard().post("0", request, response);
		}else{
			session.setAttribute("isClientValidationDone", false);
			ModelAndView loginPage = new ModelAndView();
			loginPage.setViewName("login");
			return loginPage;
			//response.sendRedirect("login.jsp");
		}
		
	}

	@RequestMapping(value="/Login" , method = RequestMethod.POST)
	protected ModelAndView post(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			HttpSession session = request.getSession(false);
			if(session!=null){
				session.invalidate();
			}
			session= request.getSession();
			Client newClient = new Level1Clients();
			/*String username = request.getParameter("username");
			String password = request.getParameter("password");*/
			
			newClient = clientsDAO.getClientByUsername(username, password);
			session.setAttribute("client", newClient);
			session.setAttribute("isClientValidationDone", true);
			//response.sendRedirect("Dashboard");
			return new ModelAndView("forward:Dashboard.get");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ModelAndView developerErrorPage = new ModelAndView("DeveloperError");
			//response.sendRedirect("DeveloperError.jsp");
			return developerErrorPage;
		}
		
	}

}
