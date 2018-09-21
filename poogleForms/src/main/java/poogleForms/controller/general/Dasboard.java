package poogleForms.controller.general;

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

import poogleForms.DAO.FormDAO;
import poogleForms.maintainance.logs.ControllerLogs;
import poogleForms.model.clients.ClientTypes;
import poogleForms.model.clients.Level2Clients;
import poogleForms.model.clients.LoginCredentials;
import poogleForms.model.form.Form;

/**
 * Servlet implementation class Dasboard
 */
@WebServlet("/Dashboard")
public class Dasboard extends HttpServlet implements ControllerLogs{
	private static final long serialVersionUID = 1L;
    private FormDAO formDAO;   
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	 public void init(){
	    	/*ServletContext ctx = getServletContext();
	    	try {
				formDAO = FormDAO.getFormDAO(ctx.getAttribute("DB_URL").toString(), ctx.getAttribute("DB_Username").toString(), ctx.getAttribute("DB_Password").toString());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("DB initialzters errors");
				e.printStackTrace();
			}*/
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
	    }
	
    public Dasboard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
/*		response.getWriter().append("Served at: ").append(request.getContextPath());
		Level2Clients l2c =new Level2Clients();
		l2c.setFirstName("hasan");
		l2c.setLastName("faraz");
		l2c.setLoginCredentials(new LoginCredentials("hasan123", "hasan123", ClientTypes.LEVEL2));
		l2c.setAnswersIDs(new HashSet<Long>());
		l2c.setFormsCreatedIDs(new HashSet<Long>());
		
		
		HttpSession session = (HttpSession)request.getSession();
		
		session.setAttribute("client", l2c);*/
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
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
			
			ArrayList<Long> formIDsList =new ArrayList<Long>(formDAO.getAllFormIDs());
			Integer noOfPages = (int) (formIDsList.size())/10;
			if((formIDsList.size())%10>0){
				noOfPages++;
			}
			
			request.setAttribute("noOfPages", noOfPages.toString());
			request.setAttribute("displayIndex", displayIndexString);
			request.setAttribute("callingPage", "DisplayAllForms");
			
			ArrayList<Form> forms = new ArrayList<Form>();
			
			for(int i =(displayIndex-1)*10 ;i<formIDsList.size() && i < (displayIndex)*10;i++){
				forms.add(formDAO.getForm(formIDsList.get(i)));
			}
			
			request.setAttribute("forms", forms);
			request.setAttribute("callingPage", "Dashboard");
			request.setAttribute("pageHeading", "Forms Available To Answer");
			request.getRequestDispatcher("displayForms.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("DeveloperError.jsp");
		}
	}

}
