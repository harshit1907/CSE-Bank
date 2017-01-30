package csebank_database.asu.edu;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.EXPECTATION_FAILED, reason="Exception:Parameter Map Null in DB service")
public class ParameterMapNullException extends Exception{
	public ParameterMapNullException(){
		
	}
	public ParameterMapNullException(String message)
	{
		super(message);
	}
}
