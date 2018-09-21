package poogleForms.controller.form;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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
import poogleForms.model.form.Form;
import poogleForms.model.form.MultipleChoiceTypeQuestion;
import poogleForms.model.form.Question;
import poogleForms.model.form.TextTypeQuestion;

/**
 * Servlet implementation class CreateQuestion
 */
@WebServlet("/CreateQuestion")
public class CreateQuestion extends HttpServlet implements ControllerLogs{
	private static final long serialVersionUID = 1L;
	private FormDAO formDAO;  
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuestion() {
        super();
        // TODO Auto-generated constructor stub
    }
    
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			HttpSession session = request.getSession();
			
			

			
			
			if(request.getParameter("command").equals("createQuestion")){
				
				if(((Client)(session.getAttribute("client"))).getLoginCredentials().getType() == ClientTypes.LEVEL1 ){
					response.setStatus(401);
					request.getRequestDispatcher("UnauthorizedAccess.jsp").forward(request, response);
					return;
				}else if(!formDAO.checkForLevel2UsernameAndFormIDPair(((Client)(session.getAttribute("client"))).getLoginCredentials().getUsername(), Long.parseLong(request.getParameter("formID")))){
					response.setStatus(401);
					request.getRequestDispatcher("UnauthorizedAccess.jsp").forward(request, response);
					return;
				}
				
				String questionPrompt = request.getParameter("questionPrompt");
				String optionsString = request.getParameter("options");
				Long formID = Long.parseLong(request.getParameter("formID"));
				String[] options = optionsString.split(";");
				Question q = null;
								
				if(request.getParameter("questionType").equals("MCQ")){
					q= new MultipleChoiceTypeQuestion();
					q.setPrompt(questionPrompt);
					q.setFormID(formID);
					((MultipleChoiceTypeQuestion)(q)).setOptions(new ArrayList<String>(Arrays.asList(options)));
				}
				else if(request.getParameter("questionType").equals("TextTypeQuestion")){
					q= new TextTypeQuestion();
					q.setPrompt(questionPrompt);
					q.setFormID(formID);
				}
				
				Long questionID = formDAO.addQuestionToDB(q);
				if(questionID==0){
					response.setStatus(504);
					response.getWriter().write("Question Adding failed, Try again");
					return;
				}
				request.setAttribute("currQuestion", formDAO.getQuestion(questionID));
				request.setAttribute("callingPage", "CreateQuestionServlet");
				request.setAttribute("formAdminUsername", formDAO.getForm(formID).getAdminUsername());
				
				request.getRequestDispatcher(q.getHandler()).forward(request, response);
			}
			else if(request.getParameter("command").equals("deleteQuestion")){
				Question quesToDelete = formDAO.getQuestion(Long.parseLong(request.getParameter("questionID")));
				if(formDAO.checkForLevel2UsernameAndFormIDPair(((Client)(session.getAttribute("client"))).getLoginCredentials().getUsername(), quesToDelete.getFormID())){
					Long result = formDAO.deleteQuestion(Long.parseLong(request.getParameter("questionID")));
					if(result== 0){
						response.setStatus(504);
						response.getWriter().println("delete Question FAILED. TRY AGAIN");
						return;
					}
					else{
						System.out.println("delete Question Success");
						response.getWriter().println("delete Question Success");
					}
					response.getWriter().flush();
					response.flushBuffer();
				}
				else{
					System.out.println("unauthorized delete question");
					response.setStatus(401);
					response.getWriter().println("unauthorized delete question");
					response.getWriter().flush();
					response.flushBuffer();
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setStatus(500);
		}
		
		
		
	}

}
