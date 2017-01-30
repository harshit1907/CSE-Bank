package csebank_authHelpers.asu.edu;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import csebank_controllers.asu.edu.ControllerUtility;
import csebank_controllers.asu.edu.UserController;
import csebank_database.asu.edu.UserService;
import csebank_objectmodel.asu.edu.User;
import csebank_utility.asu.edu.DbParamNams;

public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired 
	UserController userController;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		HashMap <String, String> param=new HashMap<>();
		ControllerUtility.validateUserID(username);
		param.put(DbParamNams.USER_ID, username);
		UserService userService=new UserService(param);
		User user = userService.getUserDetails();
		return new SpringUserDetail(user);
	}
}
