package exception.csebank_controllers.asu.edu;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Insufficient privileges to perform the operation.")
public class OperationNotPermitted extends IllegalArgumentException{
	private static final long serialVersionUID = 103L;

	public OperationNotPermitted(String error) {
		super(error);		
	}

	public OperationNotPermitted() {
		super("Insufficient privileges to perform the operation.");		// 
	}
	
	
}
