package poogleForms.maintainance.logs;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface DOAsLogs {
	Logger logger = Logger.getLogger("PoogleFormsDAOs");
	
	FileHandler fileHandler =  FileHandlers.PoogleFormsDAOsFileHandler;
	
	public static void setupLogger(){
		fileHandler.setLevel(Level.FINE);
		logger.addHandler(fileHandler);
	}
	
	public static Logger getLogger(){
		return logger;
	}
	public static void log(Level level, String log){
		logger.log(level, log);
	}
	public static void log(Level level, String log, Exception e){
		logger.log(level, log, e);
	}
}
