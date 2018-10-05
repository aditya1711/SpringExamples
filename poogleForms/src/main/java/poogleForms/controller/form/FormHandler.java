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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import poogleForms.DAO.AnswersDAO;
import poogleForms.DAO.FormDAO;
import poogleForms.maintainance.logs.ControllerLogs;
import poogleForms.model.clients.Client;
import poogleForms.model.form.*;

/**
 * Servlet implementation class Form
 */
@Controller
public class FormHandler {
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
    public FormDAO getFormDAO() {
		return formDAO;
	}
    @Autowired
	public void setFormDAO(FormDAO formDAO) {
		this.formDAO = formDAO;
	}

	public AnswersDAO getAnswersDAO() {
		return answersDAO;
	}
	
	@Autowired
	public void setAnswersDAO(AnswersDAO answersDAO) {
		this.answersDAO = answersDAO;
	}

	AnswersDAO answersDAO;
    
	@RequestMapping(value={"FormHandler"}, method=RequestMethod.GET)
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

	
	@RequestMapping(value={"FormHandler"}, method=RequestMethod.POST)
	protected ModelAndView doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			return new ModelAndView("forward:Dashboard.get");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ModelAndView("DeveloperError");
		}
	}

}
