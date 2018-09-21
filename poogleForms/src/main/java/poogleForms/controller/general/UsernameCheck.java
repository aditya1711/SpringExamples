package poogleForms.controller.general;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import poogleForms.DAO.ClientsDAO;
import poogleForms.maintainance.logs.ControllerLogs;

/**
 * Servlet implementation class UsernameCheck
 */
@WebServlet("/UsernameCheck")
public class UsernameCheck extends HttpServlet implements ControllerLogs{
	private static final long serialVersionUID = 1L;
    ClientsDAO clientsDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsernameCheck() {
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
		//response.getWriter().write("usernameCheck called by get");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			System.out.println("username check servlet called");
			if (request.getParameter("command").equals("checkForExistingUsername")) {
				if (clientsDAO.checkIfUsernameExists(request.getParameter("username"))) {
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
			}else if(request.getParameter("command").equals("checkForUsernameAndPasswordPair")){
				if (clientsDAO.checkForUsernamePasswordPair(request.getParameter("username"), request.getParameter("password"))) {
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
			response.sendRedirect("DeveloperError.jsp");
		}
	}

}
