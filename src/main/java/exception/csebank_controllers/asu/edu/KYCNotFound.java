package exception.csebank_controllers.asu.edu;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="KYC ID Not found.")
public class KYCNotFound extends IllegalArgumentException{
	private static final long serialVersionUID = 110L;
	public KYCNotFound(String error){
		super(error);
	}
	public KYCNotFound() {
		super("KYC ID Not found");
	}
}