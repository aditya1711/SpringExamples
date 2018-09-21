package poogleForms.controller.form;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import poogleForms.DAO.AnswersDAO;
import poogleForms.DAO.FormDAO;
import poogleForms.maintainance.logs.ControllerLogs;
import poogleForms.model.clients.Client;
import poogleForms.model.clients.ClientTypes;
import poogleForms.model.clients.Level1Clients;
import poogleForms.model.clients.Level2Clients;
import poogleForms.model.clients.LoginCredentials;
import poogleForms.model.form.Form;

/**
 * Servlet implementation class DisplayFormsByUser
 */
@WebServlet("/DisplayFormsByUser")
public class DisplayFormsByUser extends HttpServlet implements ControllerLogs{
	private static final long serialVersionUID = 1L;
	
	FormDAO formDAO ;
	AnswersDAO answersDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayFormsByUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    public void init(){
    	/*String DB_URL = "jdbc:sqlserver://ggku3ser2;instanceName=SQL2016;databaseName=poogleForms";
		String DB_User = "sa";
		String DB_Password = "Welcome@1234";
		try {
			formDAO = FormDAO.getFormDAO(DB_URL, DB_User, DB_Password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		formDAO = (FormDAO) getServletContext().getAttribute("formDAO");
		answersDAO = (AnswersDAO) getServletContext().getAttribute("answersDAO");
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			HttpSession session = (HttpSession)request.getSession();
			
			String displayIndexString =request.getParameter("displayIndex");
			if(displayIndexString==null){
				Object o  = request.getAttribute("displayIndex");
				if(o!=null){
					displayIndexString = o.toString();
				}
			}
			if(displayIndexString==null){
				displayIndexString= "1";
			}
			
			int displayIndex=0;
			displayIndex = Integer.parseInt(displayIndexString);
			
			
			Client client = (Client)session.getAttribute("client");
			ArrayList<Long> formIDsList =new ArrayList<Long>(formDAO.getFormIDsWithUsername(client.getLoginCredentials().getUsername()));
			System.out.println(formIDsList);
			Integer noOfPages = (int) (formIDsList.size())/10;
			if((formIDsList.size())%10>0){
				noOfPages++;
			}
			request.setAttribute("noOfPages", noOfPages.toString());
			request.setAttribute("displayIndex", displayIndexString);
			request.setAttribute("callingPage", "DisplayFormsByUser");
			
			ArrayList<Form> forms = new ArrayList<Form>();
			ArrayList<String> formReports = new ArrayList<String>();
			
			for(int i =(displayIndex-1)*10;i<formIDsList.size() && i < (displayIndex)*10;i++){
				Form form = formDAO.getForm(formIDsList.get(i));
				forms.add(form);
				
				String report = "";
				report += "users attempted this form: ";
				report += answersDAO.getCountOfUsersAnsweredAForm(form.getID());
				formReports.add(report);
			}
			
			
			
			
			
			request.setAttribute("forms", forms);
			request.setAttribute("formReports", formReports);
			request.setAttribute("callingPage", "DisplayFormsByUser");
			request.setAttribute("pageHeading", "Forms User Created:");
			request.getRequestDispatcher("displayForms.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("DeveloperError.jsp");
		}
		
	}

}
