package poogleForms.controller.general;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import poogleForms.DAO.ClientsDAO;
import poogleForms.maintainance.logs.ControllerLogs;
import poogleForms.model.clients.Client;
import poogleForms.model.clients.ClientTypes;
import poogleForms.model.clients.Level1Clients;
import poogleForms.model.clients.LoginCredentials;

/**
 * Servlet implementation class UserSignUp
 */
@WebServlet("/UserSignUp")
public class UserSignUp extends HttpServlet implements ControllerLogs{
	private static final long serialVersionUID = 1L;
    private ClientsDAO  clientsDAO;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init(){
		clientsDAO = (ClientsDAO) getServletContext().getAttribute("clientsDAO");
	}
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*response.getWriter().append("Served at: ").append(request.getContextPath());*/
		request.getRequestDispatcher("SignUp.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String userType = request.getParameter("userType");
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			Level1Clients l1c = new Level1Clients();
			l1c.setClientType(ClientTypes.LEVEL1);
			l1c.setFirstName(firstName);
			l1c.setLastName(lastName);
			l1c.setLoginCredentials(new LoginCredentials(username, password, ClientTypes.LEVEL1));
			if(userType.equals("LEVEL2")){
				l1c.setLoginCredentials(new LoginCredentials(username, password, ClientTypes.LEVEL2));
			}
			clientsDAO.addClientToDB(l1c);
			//request.getRequestDispatcher("Login").forward(request, response);
			ControllerLogs.createLog(Level.INFO,"Creating New User " + l1c );
			response.sendRedirect("Login");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ControllerLogs.createLog(Level.SEVERE,"Creating New User EXCEPTION", e);
			response.sendRedirect("DeveloperError.jsp");
			
		}
	}

}
