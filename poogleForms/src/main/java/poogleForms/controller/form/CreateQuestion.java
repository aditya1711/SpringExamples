package poogleForms.controller.form;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

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
//@WebServlet("/CreateQuestion")
@Controller
public class CreateQuestion {
	private static final long serialVersionUID = 1L;
	private FormDAO formDAO;  

	public FormDAO getFormDAO() {
		return formDAO;
	}

	@Autowired
	public void setFormDAO(FormDAO formDAO) {
		this.formDAO = formDAO;
	}


	public CreateQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init(){ 
	}

	@RequestMapping(value="DeleteQuestion", method = RequestMethod.POST)
	protected void deleteQuestion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@RequestMapping(value="CreateQuestionTQ", method = RequestMethod.POST)
	protected ModelAndView createQuestion(@ModelAttribute("CreatedQuestionTQ") TextTypeQuestion tq, @SessionAttribute("client") Client currentClient ,@RequestParam("formID") Long formID,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {

			if(currentClient.getLoginCredentials().getType() == ClientTypes.LEVEL1 ){
				
				ModelAndView mv = new ModelAndView("UnauthorizedAccess");
				mv.setStatus(HttpStatus.UNAUTHORIZED);
				
				return mv;
			}else if(!formDAO.checkForLevel2UsernameAndFormIDPair(currentClient.getLoginCredentials().getUsername(), formID)){
				ModelAndView mv = new ModelAndView("UnauthorizedAccess");
				mv.setStatus(HttpStatus.UNAUTHORIZED);
				
				return mv;
			}

			Long questionID = formDAO.addQuestionToDB(tq);
			if(questionID==0){
				ModelAndView mv = new ModelAndView();
				mv.setStatus(HttpStatus.EXPECTATION_FAILED);
				
				return mv;
			}else{
				ModelAndView mv = new ModelAndView(tq.getHandler());
				mv.addObject("currQuestion", formDAO.getQuestion(questionID));
				mv.addObject("callingPage", "CreateQuestionServlet");
				mv.addObject("formAdminUsername", formDAO.getForm(formID).getAdminUsername());
				return mv;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ModelAndView mv = new ModelAndView();
			mv.setStatus(HttpStatus.EXPECTATION_FAILED);
			
			return mv;
		}



	}
	
	
	@RequestMapping(value="CreateQuestionMCQ", method = RequestMethod.POST)
	protected ModelAndView createQuestion(@ModelAttribute("CreatedQuestionMCQ") MultipleChoiceTypeQuestion mcq, @SessionAttribute("client") Client currentClient ,@RequestParam("formID") Long formID,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {

			if(currentClient.getLoginCredentials().getType() == ClientTypes.LEVEL1 ){
				
				ModelAndView mv = new ModelAndView("UnauthorizedAccess.jsp");
				mv.setStatus(HttpStatus.UNAUTHORIZED);
				
				return mv;
			}else if(!formDAO.checkForLevel2UsernameAndFormIDPair(currentClient.getLoginCredentials().getUsername(), formID)){
				ModelAndView mv = new ModelAndView("UnauthorizedAccess.jsp");
				mv.setStatus(HttpStatus.UNAUTHORIZED);
				
				return mv;
			}
/*
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
*/
			Long questionID = formDAO.addQuestionToDB(mcq);
			if(questionID==0){
				/*response.setStatus(504);
				response.getWriter().write("Question Adding failed, Try again");
				return;*/
				ModelAndView mv = new ModelAndView();
				mv.setStatus(HttpStatus.EXPECTATION_FAILED);
				
				return mv;
			}
			request.setAttribute("currQuestion", formDAO.getQuestion(questionID));
			/*request.setAttribute("callingPage", "CreateQuestionServlet");
			request.setAttribute("formAdminUsername", formDAO.getForm(formID).getAdminUsername());

			request.getRequestDispatcher(tq.getHandler()).forward(request, response);*/
			
			ModelAndView mv = new ModelAndView(mcq.getHandler());
			mv.addObject("currQuestion", formDAO.getQuestion(questionID));
			mv.addObject("callingPage", "CreateQuestionServlet");
			mv.addObject("formAdminUsername", formDAO.getForm(formID).getAdminUsername());
			
			return mv;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ModelAndView mv = new ModelAndView();
			mv.setStatus(HttpStatus.EXPECTATION_FAILED);
			
			return mv;
		}



	}

}
