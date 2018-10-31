package poogleForms.controller.general;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import poogleForms.DAO.ClientsDAO;
import poogleForms.maintainance.logs.ControllerLogs;
import poogleForms.model.clients.Level1Clients;
import poogleForms.model.clients.LoginCredentials;
import poogleForms.model.misc.JSONConvertible;

/**
 * Servlet implementation class UsernameCheck
 */
//@WebServlet("/UsernameCheck")
@Controller
public class UsernameCheck {
	private static final long serialVersionUID = 1L;
	ClientsDAO clientsDAO;
	public ClientsDAO getClientsDAO() {
		return clientsDAO;
	}

	@Autowired
	public void setClientsDAO(ClientsDAO clientsDAO) {
		this.clientsDAO = clientsDAO;
	}

	public UsernameCheck() {
		super();
		// TODO Auto-generated constructor stub
	}



	public class UsernameCheckResult implements JSONConvertible{

		private  String message;
		private boolean result;

		public UsernameCheckResult() {
			super();
		}
		public UsernameCheckResult(String message, boolean result) {
			super();
			this.message = message;
			this.result = result;
		}
		public UsernameCheckResult(boolean b) {
			// TODO Auto-generated constructor stub
			result=b;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public boolean isResult() {
			return result;
		}
		public void setResult(boolean result) {
			this.result = result;
		}

		@Override
		public String toJSONString() throws JsonProcessingException {
			// TODO Auto-generated method stub
			return new ObjectMapper().writer().writeValueAsString(this);
		}
	}

	/*@ResponseBody
	@RequestMapping(value="checkForUsernamePasswordPair" , method = RequestMethod.POST)
	public ResponseEntity<String> checkForUsernamePasswordPair(@RequestParam(value="username" , required=false) String username,@RequestParam(value="password" , required=false) String password ,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {

			if (clientsDAO.checkForUsernamePasswordPair(username, password)) {
				System.out.println("username password pair exists");
				return ResponseEntity.ok().body("true");
			} else {
				System.out.println("username password pair donot exists");
				return ResponseEntity.ok().body("false");
			} 

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(504).body("forward:DeveloperError.get");
		}
	}
*/
	@ResponseBody
	@RequestMapping(value="validateUser" , method = RequestMethod.POST)
	public UsernameCheckResult validateUser(@Valid @ModelAttribute Level1Clients lc1, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("VALIDATING USER Dinding result has erros? " + bindingResult.hasErrors());
		
		try{
			if(bindingResult.hasErrors()){
				System.out.println("BINDING ERRORS: "+bindingResult.getAllErrors());
				List<ObjectError> errorList =  bindingResult.getAllErrors();
				Iterator<ObjectError> listIT = errorList.iterator();
				String message = "";
				while(listIT.hasNext()){
					message = message + listIT.next().getDefaultMessage() + "\n";
				}
				return new UsernameCheckResult(message, false);
			}
			else{
				return new UsernameCheckResult(true);
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			/*response.setStatus(504);
			response.sendRedirect("DeveloperError.jsp");*/
			throw e;
		}
	}
	/*@ResponseBody
	@RequestMapping(value="checkForUsernamePasswordPair" , method = RequestMethod.POST)
	protected ResponseEntity<String> checkForUsernamePasswordPair(@RequestParam(value="username" , required=false) String username,@RequestParam(value="password" , required=false) String password ,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {

			if (clientsDAO.checkForUsernamePasswordPair(username, password)) {
				System.out.println("username password pair exists");
				return ResponseEntity.ok().body("true");
			} else {
				System.out.println("username password pair donot exists");
				return ResponseEntity.ok().body("false");
			} 

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(504).body("forward:DeveloperError.get");
		}
	}

	@ResponseBody
	@RequestMapping(value="checkForExistingUsername" , method = RequestMethod.POST)
	protected ResponseEntity<String> checkForExistingUsername(@Valid @ModelAttribute LoginCredentials lc ,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try{
			if (clientsDAO.checkIfUsernameExists(lc.getUsername())) {
				System.out.println("username exists");
				response.getWriter().write("true");
				response.getWriter().flush();
				response.flushBuffer();
				return ResponseEntity.ok().body("true");
			} else {
				System.out.println("username donot exists");
				response.getWriter().write("false");
				response.getWriter().flush();
				response.flushBuffer();
				return ResponseEntity.ok().body("false");
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setStatus(504);
			response.sendRedirect("DeveloperError.jsp");
			return ResponseEntity.status(504).body("forward:DeveloperError.get");
		}*/



}
