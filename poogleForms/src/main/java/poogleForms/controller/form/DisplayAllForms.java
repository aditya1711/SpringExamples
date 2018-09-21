package poogleForms.controller.form;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import poogleForms.DAO.AnswersDAO;
import poogleForms.DAO.FormDAO;
import poogleForms.maintainance.logs.ControllerLogs;
import poogleForms.model.form.Form;

/**
 * Servlet implementation class DisplayAllForms
 */
@WebServlet("/DisplayAllForms.NOACCESS")
public class DisplayAllForms extends HttpServlet implements ControllerLogs {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	FormDAO formDAO ;
	
    public DisplayAllForms() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
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
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stud

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
			request.getRequestDispatcher("displayAllForms.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("DeveloperError.jsp");
		}
	}

}
