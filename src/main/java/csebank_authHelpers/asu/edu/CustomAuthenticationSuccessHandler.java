package csebank_authHelpers.asu.edu;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import csebank_controllers.asu.edu.UserController;
import csebank_objectmodel.asu.edu.User;
import csebank_utility.asu.edu.DbParamNams;
import csebank_utility.asu.edu.Encryption;


public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	private static final Logger log = Logger.getLogger(CustomAuthenticationSuccessHandler.class);
	UserController userController;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {		
		log.info("Authentication success for UserID:"+authentication.getName());
		User user=userController.getUser();
		if(user.getUserRole().equalsIgnoreCase(DbParamNams.USER_ROLE_ADMIN)){
			Encryption.setPublicKeyPrivateKey();
		}
		
	}
	public CustomAuthenticationSuccessHandler(UserController userController) {
		this.userController = userController;
	}

}
