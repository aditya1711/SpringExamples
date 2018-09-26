package poogleForms.controller.general;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import poogleForms.DAO.ClientsDAO;
import poogleForms.maintainance.logs.ControllerLogs;

/**
 * Servlet implementation class UsernameCheck
 */
//@WebServlet("/UsernameCheck")
@Controller
public class UsernameCheck {
	private static final long serialVersionUID = 1L;
    ClientsDAO clientsDAO;
    public ClientsDAO getClientsDAO() {
		return clientsDAO;
	}

    @Autowired
	public void setClientsDAO(ClientsDAO clientsDAO) {
		this.clientsDAO = clientsDAO;
	}


	/**
     * @see HttpServlet#HttpServlet()
     */
    public UsernameCheck() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().write("usernameCheck called by get");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@RequestMapping(value="UsernameCheck" , method = RequestMethod.POST)
	protected void doPost(@RequestParam("command") String clientCommand, @RequestParam(value="username" , required=false) String username,@RequestParam(value="password" , required=false) String password ,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			System.out.println("username check servlet called");
			if (clientCommand.equals("checkForExistingUsername")) {
				if (clientsDAO.checkIfUsernameExists(username)) {
					System.out.println("username exists");
					response.getWriter().write("true");
					response.getWriter().flush();
					response.flushBuffer();
				} else {
					System.out.println("username donot exists");
					response.getWriter().write("false");
					response.getWriter().flush();
					response.flushBuffer();
				} 
			}else if(clientCommand.equals("checkForUsernameAndPasswordPair")){
				if (clientsDAO.checkForUsernamePasswordPair(username, password)) {
					System.out.println("username password pair exists");
					response.getWriter().write("true");
					response.getWriter().flush();
					response.flushBuffer();
				} else {
					System.out.println("username password pair donot exists");
					response.getWriter().write("false");
					response.getWriter().flush();
					response.flushBuffer();
				} 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setStatus(504);
			response.sendRedirect("DeveloperError.jsp");
		}
	}

}
