package poogleForms.controller.form;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

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
import poogleForms.model.form.*;

/**
 * Servlet implementation class Form
 */
@WebServlet("/FormHandler")
public class FormHandler extends HttpServlet implements ControllerLogs{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    FormDAO formDAO ;
    AnswersDAO answersDAO;
    
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
			answersDAO = AnswersDAO.getAnswersDAO(DB_URL, DB_User, DB_Password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	 formDAO = (FormDAO) getServletContext().getAttribute("formDAO"); 
    	 answersDAO = (AnswersDAO) getServletContext().getAttribute("answersDAO");
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			if(request.getParameter("formID")==null){
				request.getRequestDispatcher("wrongAccess.jsp").forward(request, response);
			}
			HttpSession session = request.getSession();
			
			Form f = formDAO.getForm(Long.parseLong(request.getParameter("formID")));
			Map<Long,Answer> answers = answersDAO.getAnswersWithUsernameAndFormID(((Client)(session.getAttribute("client"))).getLoginCredentials().getUsername(), f.getID());
			
			request.setAttribute("form", f);
			request.setAttribute("answersMap", answers);
			
			System.out.println(answers);
			
			request.getRequestDispatcher("viewForms.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("DeveloperError.jsp");
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			HttpSession session = (HttpSession)request.getSession();
			
			Form f = formDAO.getForm(Long.parseLong(request.getParameter("formID")));
			ArrayList<Question> qs = f.getList();
			
			for(int i=0;i<qs.size();i++){
				System.out.println("question ID: " + qs.get(i).getID());
				String answer = request.getParameter(Long.toString(qs.get(i).getID()));
				System.out.println("answer string: " + answer);
				if(!(answer==null || answer=="")){
					System.out.println("from forms handler------> "+ answer);
					ArrayList<String> answers = new ArrayList<String>();
					answers.add(answer);
					Answer ans = new Answer();
					ans.setAnswers(answers);
					ans.setUsername(((Client)(session.getAttribute("client"))).getLoginCredentials().getUsername());
					ans.setQuestionID(qs.get(i).getID());
					
					answersDAO.addAnswerInDB(ans);
				}
				
			}
			response.sendRedirect("Dashboard");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("DeveloperError.jsp");
		}
	}

}
