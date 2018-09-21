package poogleForms.controller.general;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import poogleForms.maintainance.logs.ControllerLogs;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet implements ControllerLogs{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			HttpSession session = request.getSession();
			if(request.getParameter("confirm")!=null && request.getParameter("confirm").equals("true")){
				
				ControllerLogs.createLog(Level.INFO,"User Loging out:\n" + request.getSession().getAttribute("client").toString());
				
				session.invalidate();
				request.getRequestDispatcher("Checkout.jsp").forward(request, response);
				return;
			}else{
				request.getRequestDispatcher("UnauthorizedAccess.jsp").forward(request, response);
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ControllerLogs.createLog(Level.SEVERE,"EXCEPTION in doPost():\n", e);
			request.getRequestDispatcher("DeveloperError.jsp").forward(request, response);;
		}
	}

}
