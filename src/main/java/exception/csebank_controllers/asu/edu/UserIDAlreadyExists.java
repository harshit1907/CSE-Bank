package exception.csebank_controllers.asu.edu;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.EXPECTATION_FAILED, 
		/*lets not give the signing up user whether the user is already there in DB or not */
		reason="Invalid User ID.")
public class UserIDAlreadyExists extends IllegalArgumentException {
	private static final long serialVersionUID = 101L;
	public UserIDAlreadyExists(String error){
		super(error);
	}
	public UserIDAlreadyExists() {
		super("Invalid User ID");
	}
}
