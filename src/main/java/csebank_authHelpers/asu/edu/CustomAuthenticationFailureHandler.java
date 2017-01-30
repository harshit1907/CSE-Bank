package csebank_authHelpers.asu.edu;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import csebank_controllers.asu.edu.UserController;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	UserController userController;
	
    private static final Logger log = Logger.getLogger(CustomAuthenticationFailureHandler.class);

    public CustomAuthenticationFailureHandler(UserController userController) {
    	this.userController=userController;
        log.info(this.getClass().toString() + " has Started.");
    	
    }

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {		
		String userId= request.getParameter("userId");
		if(exception instanceof LockedException){
			log.error(String.format("Authentication failure for UserID : %s. User account is locked, number of tries exceeded", userId));
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "Error: User Account is locked, number of tries exceeded. Contact Bank employees to unlock the account.");
		} else if(exception instanceof DisabledException){
			log.error(String.format("Authentication failure for UserID : %s. User account is locked, number of tries exceeded", userId));
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "Error: User Account is Disabled. Please contact admin to enable your account.");
		}else{
			userController.updateLoginAttempt(userId);
			log.info("Authentication failure for UserID : "+userId);
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "Error: Wrong credentials.");
		}
		
	}

}