package poogleForms.controller.form;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import poogleForms.DAO.FormDAO;
import poogleForms.maintainance.logs.ControllerLogs;
import poogleForms.model.clients.Client;
import poogleForms.model.clients.ClientTypes;
import poogleForms.model.form.*;

/**
 * Servlet implementation class CreateForm
 */
@WebServlet("/CreateForm")
public class CreateForm extends HttpServlet implements ControllerLogs {
	private static final long serialVersionUID = 1L;
	private FormDAO formDAO;        
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(){
		formDAO = (FormDAO) getServletContext().getAttribute("formDAO");

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		try {
			
			HttpSession session = request.getSession();
			
			ControllerLogs.createLog(Level.INFO,"doGet() request by " + ((Client)(session.getAttribute("client"))).getLoginCredentials().getUsername());
			
			if(((Client)(session.getAttribute("client"))).getLoginCredentials().getType() == ClientTypes.LEVEL1 ){
				request.getRequestDispatcher("UnauthorizedAccess.jsp").forward(request, response);
				ControllerLogs.createLog(Level.INFO,"UNAUTHORIZED doGet() request by " + ((Client)(session.getAttribute("client"))).getLoginCredentials().getUsername());
				return;
			}
			else{
				Form form;
				if(request.getParameter("formID")!=null &&
						formDAO.checkForLevel2UsernameAndFormIDPair(((Client)(session.getAttribute("client"))).getLoginCredentials().getUsername(), Long.parseLong(request.getParameter("formID")))){
					
					ControllerLogs.createLog(Level.INFO,"edit form attempt by " + ((Client)(session.getAttribute("client"))).getLoginCredentials().getUsername() + " formID: " + request.getParameter("formID"));
					
					form = formDAO.getForm(Long.parseLong(request.getParameter("formID")));
				}
				else if(request.getParameter("formID")!=null && 
						!formDAO.checkForLevel2UsernameAndFormIDPair(((Client)(session.getAttribute("client"))).getLoginCredentials().getUsername(), Long.parseLong(request.getParameter("formID")))){
					
					ControllerLogs.createLog(Level.INFO,"UNAUTHORIZED edit form attempt by " + ((Client)(session.getAttribute("client"))).getLoginCredentials().getUsername());
					
					request.getRequestDispatcher("UnauthorizedAccess.jsp").forward(request, response);
					return;
				}
				else{
					form = (Form) request.getAttribute("form");
				}
				Long formID = (long) 0;
				if(form==null){
					form = new Form();
					form.setAdminUsername(((Client)(session.getAttribute("client"))).getLoginCredentials().getUsername());
					formID =formDAO.addFormPrototypeToDB(form);
					
					if(formID==0){
						
						ControllerLogs.createLog(Level.INFO,"FORM_DAO ERROR: Creating form attempt by " + ((Client)(session.getAttribute("client"))).getLoginCredentials().getUsername());
						
						request.getRequestDispatcher("DeveloperError.jsp").forward(request, response);
						return;
					}
					
					form.setID(formID);
					
					ControllerLogs.createLog(Level.INFO,"Creating form attempt by " + ((Client)(session.getAttribute("client"))).getLoginCredentials().getUsername() + " formID: " + formID);
					
					System.out.println("Form not found in request in create form, Creating a new one");
				}else{
					formID = form.getID();
				}

				request.setAttribute("form", form);
				request.getRequestDispatcher("CreateForm.jsp").forward(request, response);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ControllerLogs.createLog(Level.SEVERE, "EXCEPTION", e);
			response.sendRedirect("DeveloperError.jsp");
		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			HttpSession session = request.getSession();
			
			if(((Client)(session.getAttribute("client"))).getLoginCredentials().getType() == ClientTypes.LEVEL1 ){
				response.setStatus(401);
				request.getRequestDispatcher("UnauthorizedAccess.jsp");
				return;
			}else if(!formDAO.checkForLevel2UsernameAndFormIDPair(((Client)(session.getAttribute("client"))).getLoginCredentials().getUsername(), Long.parseLong(request.getParameter("formID")))){
				response.setStatus(401);
				request.getRequestDispatcher("UnauthorizedAccess.jsp").forward(request, response);
				return;
			}
			
			
			if(request.getParameter("command").equals("createForm")){
				Long result = formDAO.updateFormName(Long.parseLong(request.getParameter("formID")), request.getParameter("formName"));
				if(result == 0){
					response.getWriter().println("Updating Form Name FAILED, Try AGAIN");
					response.setStatus(504);
					return;
				}
				//response.sendRedirect("Dashboard");
			}
			else if(request.getParameter("command").equals("deleteForm")){
				Long result = formDAO.deleteForm(Long.parseLong(request.getParameter("formID")));
				if(result==0){
					//response.setStatus(arg0);
					response.setStatus(504);
					response.getWriter().println("Delete Form FAILED, Try AGAIN");
					return;
				}
				System.out.println("Sucess delete form");
				response.getWriter().println("Delete Form Success");
				response.getWriter().flush();
				response.flushBuffer();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setStatus(500);
			response.sendRedirect("DeveloperError.jsp");
		}


	}

}
