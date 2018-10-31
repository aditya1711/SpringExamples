package poogleForms.controller.general;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import poogleForms.DAO.ClientsDAO;
import poogleForms.maintainance.logs.ControllerLogs;
import poogleForms.model.clients.Client;
import poogleForms.model.clients.ClientTypes;
import poogleForms.model.clients.Level1Clients;
import poogleForms.model.clients.LoginCredentials;

/**
 * Servlet implementation class UserSignUp
 */
//@WebServlet("/UserSignUp")
@Controller
public class UserSignUp{
	private static final long serialVersionUID = 1L;
	private ClientsDAO  clientsDAO;  
	public ClientsDAO getClientsDAO() {
		return clientsDAO;
	}
	@Autowired
	public void setClientsDAO(ClientsDAO clientsDAO) {
		this.clientsDAO = clientsDAO;
	}

	public UserSignUp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(){
	}

	@InitBinder
	public void clientTypeBinder(WebDataBinder binder){
		//binder.registerCustomEditor(ClientTypes.class,"clientType", new ClientTypePropertyEditor());
	}

	@RequestMapping(value = "/UserSignUp" ,  method = RequestMethod.GET)
	protected String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		return "SignUp";
	}

	@RequestMapping(value = "/UserSignUp" ,  method = RequestMethod.POST)
	protected ModelAndView doPost(@Valid @ModelAttribute Level1Clients l1c, BindingResult bindResult) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			if(l1c==null){
				ModelAndView mv = new ModelAndView("/UserSignUp");
				mv.setStatus(HttpStatus.BAD_REQUEST);
				return mv;
			}
			if(bindResult.hasErrors()){
				System.out.println(bindResult.getAllErrors());
				return new ModelAndView("SignUp");
			}

			System.out.println(l1c);
			clientsDAO.addClientToDB(l1c);


			return new ModelAndView("login");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ModelAndView("DeveloperError");

		}
	}

}
