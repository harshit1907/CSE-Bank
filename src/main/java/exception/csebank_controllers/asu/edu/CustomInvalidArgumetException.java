package exception.csebank_controllers.asu.edu;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.EXPECTATION_FAILED)//,reason="Invalid or Mising argument.")
public class CustomInvalidArgumetException extends IllegalArgumentException{
	private static final long serialVersionUID = 100L;
	public CustomInvalidArgumetException(String error){
		super(error);
	}
}