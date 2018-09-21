package poogleForms.maintainance.logs;

import java.io.IOException;
import java.util.logging.FileHandler;

public class FileHandlers {
	
	public static FileHandler PoogleFormsControllersFileHandler;
	public static FileHandler PoogleFormsDAOsFileHandler;
	static{
		try {
			PoogleFormsControllersFileHandler = new FileHandler("E:\\gitLocal\\poogleForms\\poogleForms\\PoogleFormsControllers.log",true);
			PoogleFormsDAOsFileHandler = new FileHandler("PoogleFormsDAOs.log",true);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
