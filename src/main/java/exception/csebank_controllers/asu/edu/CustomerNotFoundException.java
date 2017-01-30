package exception.csebank_controllers.asu.edu;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="User ID Not found.")
public class CustomerNotFoundException extends IllegalArgumentException{
	private static final long serialVersionUID = 102L;
	public CustomerNotFoundException(String error){
		super(error);
	}
	public CustomerNotFoundException() {
		super("User ID Not found");
	}
}
