package poogleForms.controller.general;

import java.io.IOException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import poogleForms.DAO.FormDAO;
import poogleForms.model.form.Form;


@Controller
public class Dasboard {
	private static final long serialVersionUID = 1L;
    public FormDAO formDAO;
	
	public FormDAO getFormDAO() {
		return formDAO;
	}
	@Autowired
	public void setFormDAO(FormDAO formDAO) {
		this.formDAO = formDAO;
	}
	
    @PostConstruct
	public void initForLogin(){
    	System.out.println(formDAO);
	}
	
    public Dasboard() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @RequestMapping(value={"Dashboard"}, method=RequestMethod.GET)
	protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		post("0",request, response);
	}

	@RequestMapping(value={"Dashboard"}, method=RequestMethod.POST)
	protected ModelAndView post(@RequestParam(value = "displayIndex" , defaultValue = "1") @RequestAttribute("displayIndex") String displayIndexString, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			Integer displayIndex = Integer.parseInt(displayIndexString);
			System.out.println("formDAO in post of DAshboard: " + formDAO);
			ArrayList<Long> formIDsList =new ArrayList<Long>(formDAO.getAllFormIDs());
			Integer noOfPages = (int) (formIDsList.size())/10;
			if((formIDsList.size())%10>0){
				noOfPages++;
			}
			ModelAndView mv = new ModelAndView();
			mv.setViewName("displayForms");
			mv.addObject("displayIndex",displayIndex);
			mv.addObject("callingPage","DisplayAllForms");
			mv.addObject("noOfPages",noOfPages);
			

			ArrayList<Form> forms = new ArrayList<Form>();
			
			for(int i =(displayIndex-1)*10 ;i<formIDsList.size() && i < (displayIndex)*10;i++){
				forms.add(formDAO.getForm(formIDsList.get(i)));
			}
		
			mv.addObject("pageHeading", "Forms Available To Answer");
			mv.addObject("callingPage","Dashboard");
			mv.addObject("forms", forms);
			return mv;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ModelAndView loginPage = new ModelAndView();
			loginPage.setViewName("DeveloperError");
			return loginPage;
		}
	}

}
