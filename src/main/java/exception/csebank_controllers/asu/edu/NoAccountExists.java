package exception.csebank_controllers.asu.edu;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.BAD_REQUEST, 
		/*lets not give the signing up user whether the user is already there in DB or not */
		reason="Account number does not exists")
public class NoAccountExists extends IllegalArgumentException {
	private static final long serialVersionUID = 101L;
	public NoAccountExists(String error){
		super(error);
	}
	public NoAccountExists() {
		super("Account number does not exists");
	}
}
