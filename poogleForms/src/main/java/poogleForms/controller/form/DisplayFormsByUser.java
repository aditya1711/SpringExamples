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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
@Controller
public class DisplayFormsByUser {
	private static final long serialVersionUID = 1L;
	
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
	

    public DisplayFormsByUser() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @RequestMapping(value={"DisplayFormsByUser"}, method=RequestMethod.GET)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doPost(request, response);
	}

    @RequestMapping(value={"DisplayFormsByUser"}, method=RequestMethod.POST)
	protected ModelAndView doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			HttpSession session = (HttpSession)request.getSession();
			
			ModelAndView mv = new ModelAndView("displayForms");
			
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
			
			mv.addObject("noOfPages", noOfPages.toString());
			mv.addObject("displayIndex", displayIndexString);
			mv.addObject("callingPage", "DisplayFormsByUser");
			
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
			
			
			
			mv.addObject("forms", forms);
			mv.addObject("formReports", formReports);
			mv.addObject("callingPage", "DisplayFormsByUser");
			mv.addObject("pageHeading", "Forms User Created:");
			
			return mv;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ModelAndView("DeveloperError");
		}
		
	}

}
