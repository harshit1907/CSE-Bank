package exception.csebank_controllers.asu.edu;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Log File Not Found.")
public class LogFileNotFoundException extends FileNotFoundException{
	private static final long serialVersionUID = 102L;
	public LogFileNotFoundException(String error){
		super(error);
	}
	public LogFileNotFoundException() {
		super("Log File Not Found");
	}
}