package poogleForms.controller.configs;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PoogleFormsContext extends AnnotationConfigApplicationContext{
	private static PoogleFormsContext context;
	private static Class configurerClass;
	private PoogleFormsContext(Class configurerClass){
		super(configurerClass);
	}
	public static PoogleFormsContext getPoogleFormsContext(Class configurerClass){
		
		if(PoogleFormsContext.configurerClass==null || !PoogleFormsContext.configurerClass.equals(configurerClass) || context==null){
			System.out.println("No context found");
			PoogleFormsContext.configurerClass=configurerClass;
			context = new PoogleFormsContext(PoogleFormsContext.configurerClass);
			return PoogleFormsContext.context;
		}
		return context;
	}
}
