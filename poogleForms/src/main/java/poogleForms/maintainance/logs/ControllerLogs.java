package poogleForms.maintainance.logs;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public interface ControllerLogs {
	
	Logger logger = Logger.getLogger("PoogleFormsControllers");
	
	FileHandler fileHandler =  FileHandlers.PoogleFormsControllersFileHandler;
	
	public static void setupLogger(){
		LogManager.getLogManager().reset();
		logger.setLevel(Level.ALL);
		
		fileHandler.setLevel(Level.FINE);
		logger.addHandler(fileHandler);
		logger.addHandler(new ConsoleHandler());
	}
	
	public static Logger getLogger(){
		return logger;
	}
	public static void createLog(Level level, String log){
		logger.log(level, log);
	}
	public static void createLog(Level level, String log, Exception e){
		logger.log(level, log, e);
	}
}
