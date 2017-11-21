package utilities;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.enums.Messages;

public class LogWriter {
	private Logger log;
	private static LogWriter instance;

	private LogWriter() {
		log = Logger.getLogger("log");
		log.setUseParentHandlers(false);
		try {
			log.addHandler(new FileHandler("log.txt"));
		} catch (SecurityException e) {
			Printer.print(e.getMessage());
		} catch (IOException e) {
			Printer.print(Messages.NO_LOG_FILE_FOUND.toString());
		}
	}

	public static LogWriter getInstance() {
		if (instance == null) {
			instance = new LogWriter();
		}
		return instance;
	}

	public void log(Exception exception, String className) {
		log.log(Level.SEVERE, className + " " + exception);
		Printer.println(Messages.ERROR.toString());
	}
}
