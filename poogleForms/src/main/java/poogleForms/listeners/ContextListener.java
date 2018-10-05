package poogleForms.listeners;

import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import poogleForms.DAO.AnswersDAO;
import poogleForms.DAO.ClientsDAO;
import poogleForms.DAO.FormDAO;
import poogleForms.controller.configs.ServletConfigurer;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener, ServletContextAttributeListener {

	private FormDAO formDAO;
	private ClientsDAO clientsDAO;
	private AnswersDAO answersDAO;
	
	public FormDAO getFormDAO() {
		return formDAO;
	}
	
	@Autowired
	public void setFormDAO(FormDAO formDAO) {
		this.formDAO = formDAO;
	}

	public ClientsDAO getClientsDAO() {
		return clientsDAO;
	}
	@Autowired
	public void setClientsDAO(ClientsDAO clientsDAO) {
		this.clientsDAO = clientsDAO;
	}

	public AnswersDAO getAnswersDAO() {
		return answersDAO;
	}
	@Autowired
	public void setAnswersDAO(AnswersDAO answersDAO) {
		this.answersDAO = answersDAO;
	}


	/**
	 * Default constructor. 
	 */
	public ContextListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
	 */
	public void attributeAdded(ServletContextAttributeEvent arg0)  { 
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
	 */
	public void attributeRemoved(ServletContextAttributeEvent arg0)  { 
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0)  { 
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
	 */
	public void attributeReplaced(ServletContextAttributeEvent arg0)  { 
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0)  { 
		// TODO Auto-generated method stub
		/*   	DB_URL =  arg0.getServletContext().getInitParameter("DB_URL");
    	DB_User =  arg0.getServletContext().getInitParameter("DB_User");
    	DB_Password =  arg0.getServletContext().getInitParameter("DB_Password");
    	//ApplicationContext springAppCtx  = new ClassPathXmlApplicationContext("spring.xml")*/

    	ServletContext ctx = arg0.getServletContext();

    	/*PoogleFormsContext springAppContext = PoogleFormsContext.getPoogleFormsContext(ServletConfigurer.class);
    	
    	FormDAO formDAO = (FormDAO) springAppContext.getBean("formDAO");
    	AnswersDAO answersDAO = (AnswersDAO) springAppContext.getBean("answersDAO");
    	ClientsDAO clientsDAO = (ClientsDAO) springAppContext.getBean("clientsDAO");
    	
    	System.out.println("DAOs condition:   " +formDAO + " " + clientsDAO + " " + answersDAO);
    	
    	ctx.setAttribute("formDAO", springAppContext.getBean("formDAO"));
		ctx.setAttribute("clientsDAO", springAppContext.getBean("clientsDAO"));
		ctx.setAttribute("answersDAO", springAppContext.getBean("answersDAO"));
    	*/
    	System.out.println("DAOs condition:   " +formDAO + " " + clientsDAO + " " + answersDAO);
    	ctx.setAttribute("formDAO", formDAO);
		ctx.setAttribute("clientsDAO", clientsDAO);
		ctx.setAttribute("answersDAO", answersDAO);
		

    	

/*
    	System.out.println("DAOs condition:   " +formDAO + " " + clientsDAO + " " + answersDAO);
    	System.out.println(clientsDAO.getClientByUsername("RajuChacha1811", "RajuChacha1811"));

*/
		

		/*Logger logger = LogManager.getLogger(ContextListener.class.getName());
		System.out.println(ContextListener.class.getName());


		logger.info("Ans Updated: ");
		logger.debug("Ans Updated: ");
		logger.error("Ans Updated: ");
		logger.fatal("Ans Updated: ");
		logger.warn("Ans Updated: ");

		System.out.println("enab;ed d" +logger.isDebugEnabled());
		System.out.println("enab;ed I" +logger.isInfoEnabled());*/



	}

}
