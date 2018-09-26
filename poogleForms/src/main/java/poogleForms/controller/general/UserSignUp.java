package poogleForms.controller.general;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import poogleForms.DAO.ClientsDAO;
import poogleForms.maintainance.logs.ControllerLogs;
import poogleForms.model.clients.Client;
import poogleForms.model.clients.ClientTypes;
import poogleForms.model.clients.Level1Clients;
import poogleForms.model.clients.LoginCredentials;

/**
 * Servlet implementation class UserSignUp
 */
//@WebServlet("/UserSignUp")
@Controller
public class UserSignUp{
	private static final long serialVersionUID = 1L;
    private ClientsDAO  clientsDAO;  
    public ClientsDAO getClientsDAO() {
		return clientsDAO;
	}
    @Autowired
	public void setClientsDAO(ClientsDAO clientsDAO) {
		this.clientsDAO = clientsDAO;
	}

    public UserSignUp() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	public void init(){
	}
    
	@InitBinder
	public void clientTypeBinder(WebDataBinder binder){
		//binder.registerCustomEditor(ClientTypes.class,"clientType", new ClientTypePropertyEditor());
	}
	
	@RequestMapping(value = "/UserSignUp" ,  method = RequestMethod.GET)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*response.getWriter().append("Served at: ").append(request.getContextPath());*/
		request.getRequestDispatcher("SignUp.jsp").forward(request, response);
	}
	
	@RequestMapping(value = "/UserSignUp" ,  method = RequestMethod.POST)
	protected ModelAndView doPost(@ModelAttribute("userCreated") Level1Clients l1c, BindingResult bindResult , HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			/*String userType = request.getParameter("userType");
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			Level1Clients l1c = new Level1Clients();
			l1c.setClientType(ClientTypes.LEVEL1);
			l1c.setFirstName(firstName);
			l1c.setLastName(lastName);
			l1c.setLoginCredentials(new LoginCredentials(username, password, ClientTypes.LEVEL1));*/
			
			/*if(l1c.getLoginCredentials().getType().equals("LEVEL2")){
				l1c.getLoginCredentials().setType(ClientTypes.LEVEL2);
			}*/
			if(bindResult.hasErrors()){
				System.out.println(bindResult.getAllErrors());
				return new ModelAndView("SignUp");
				
			}
			
			System.out.println(l1c);
			//clientsDAO.addClientToDB(l1c);
			
			//request.getRequestDispatcher("Login").forward(request, response);
			//ControllerLogs.createLog(Level.INFO,"Creating New User " + l1c );
			return new ModelAndView("login");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ModelAndView("DeveloperError");
			
		}
	}

}
