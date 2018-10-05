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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import poogleForms.DAO.AnswersDAO;
import poogleForms.DAO.FormDAO;
import poogleForms.maintainance.logs.ControllerLogs;
import poogleForms.model.form.Form;

@Controller
public class DisplayAllForms {
	private static final long serialVersionUID = 1L;

	FormDAO formDAO ;
	
    public FormDAO getFormDAO() {
		return formDAO;
	}
    @Autowired
	public void setFormDAO(FormDAO formDAO) {
		this.formDAO = formDAO;
	}

	public DisplayAllForms() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	@RequestMapping(value={"DisplayAllForms"}, method=RequestMethod.GET)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stud

		doPost(request, response);
	}

	@RequestMapping(value={"DisplayAllForms"}, method=RequestMethod.POST)
	protected ModelAndView doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			ModelAndView mv = new ModelAndView();
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
			
			mv.addObject("noOfPages", noOfPages.toString());
			mv.addObject("displayIndex", displayIndexString);
			mv.addObject("callingPage", "DisplayAllForms");
			
			ArrayList<Form> forms = new ArrayList<Form>();
			
			for(int i =(displayIndex-1)*10 ;i<formIDsList.size() && i < (displayIndex)*10;i++){
				forms.add(formDAO.getForm(formIDsList.get(i)));
			}
			
			
			mv.addObject("forms", forms);
			mv.setViewName("displayAllForms");
			return mv;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ModelAndView("DeveloperError");
		}
	}

}
