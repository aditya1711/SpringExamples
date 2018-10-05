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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import poogleForms.DAO.AnswersDAO;
import poogleForms.DAO.FormDAO;
import poogleForms.maintainance.logs.ControllerLogs;
import poogleForms.model.clients.Client;
import poogleForms.model.form.Form;

/**
 * Servlet implementation class ViewAnsweredForms
 */
@Controller
public class ViewAnsweredForms{
	private static final long serialVersionUID = 1L;
	public AnswersDAO getAnswersDAO() {
		return answersDAO;
	}

	@Autowired
	public void setAnswersDAO(AnswersDAO answersDAO) {
		this.answersDAO = answersDAO;
	}


	public FormDAO getFormDAO() {
		return formDAO;
	}

	@Autowired
	public void setFormDAO(FormDAO formDAO) {
		this.formDAO = formDAO;
	}

	private AnswersDAO answersDAO;
	private FormDAO formDAO;
       
    public ViewAnsweredForms() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @RequestMapping(value="/ViewAnsweredForms" , method = RequestMethod.GET)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
		
		
	}
	@RequestMapping(value="/ViewAnsweredForms" , method = RequestMethod.POST)
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
			
			ArrayList<Long> formIDsList =new ArrayList<Long>(answersDAO.getAnsweredFormIDsWithUsername(((Client)(session.getAttribute("client"))).getLoginCredentials().getUsername()));
			Integer noOfPages = (int) (formIDsList.size())/10;
			if((formIDsList.size())%10>0){
				noOfPages++;
			}
			
			request.setAttribute("noOfPages", noOfPages.toString());
			request.setAttribute("displayIndex", displayIndexString);
			request.setAttribute("callingPage", "ViewAnsweredForms");
			
			ArrayList<Form> forms = new ArrayList<Form>();
			
			for(int i =(displayIndex-1)*10 ;i<formIDsList.size() && i < (displayIndex)*10;i++){
				forms.add(formDAO.getForm(formIDsList.get(i)));
			}
			
			request.setAttribute("forms", forms);
			request.setAttribute("callingPage", "ViewAnsweredForms");
			request.setAttribute("pageHeading", "Forms you Answered");
			request.getRequestDispatcher("displayForms.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("DeveloperError.jsp");
		}
	}

}
