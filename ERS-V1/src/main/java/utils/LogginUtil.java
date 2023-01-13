package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogginUtil {
	private static final Logger logger = LoggerFactory.getLogger(LogginUtil.class);
	 //Logger logger2 = Logger.getLogger(WriteLogToFile.class.getName());
	 
	public static Logger getLogger() {
		return logger;
	}
}
