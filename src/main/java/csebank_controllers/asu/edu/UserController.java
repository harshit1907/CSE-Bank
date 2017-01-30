package csebank_controllers.asu.edu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
//import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import csebank_database.asu.edu.UserService;
import csebank_objectmodel.asu.edu.User;
import csebank_utility.asu.edu.CaptchaCheck;
import csebank_utility.asu.edu.DbParamNams;
import csebank_utility.asu.edu.OTPValidator;
import exception.csebank_controllers.asu.edu.CustomInvalidArgumetException;
import exception.csebank_controllers.asu.edu.CustomerNotFoundException;
import exception.csebank_controllers.asu.edu.UserIDAlreadyExists;


@Controller
public class UserController {
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	//test method, todo: remove it	
	
	@RequestMapping(value = "/check",method = RequestMethod.GET)
	public @ResponseBody String user(){
		logger.info("Logging is working.");
		String userName= SecurityContextHolder.getContext().getAuthentication().getName();
		userName=userName==null?"":userName;
		return "Rest call is Working Fine.\n You are logged in as :"+userName;
	}
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/checktest",method = RequestMethod.GET)
	public @ResponseBody String use2r(){
		logger.info("Logging is working.");
		String userName= SecurityContextHolder.getContext().getAuthentication().getName();
		userName=userName==null?"":userName;
		return "Rest call is Working Fine.\n You are logged in as :"+userName;
	}
	
	//ObjectMapper objectMapper;
	//only captcha check
		
	@RequestMapping(value = {"/anonymous/signup"},method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)//
	public @ResponseBody String addUser(@RequestBody HashMap<String, String> userMap)throws Exception{
		String captcha=userMap.remove("g-recaptcha-response");
		ObjectMapper mapper = new ObjectMapper();
		
		User user=mapper.convertValue(userMap, User.class);
		UserService userService;
		logger.info("User is signing up:"+user.getUserId());
		
		if(!CaptchaCheck.verify(captcha)){//check captcha
			throw new CustomInvalidArgumetException("Invalid captcha.");
		}
		ControllerUtility.validateUser(user);
		//check for duplicate phone number and email
		HashMap<String, String> parameterNameValueMap=ControllerUtility.getUserMap(user);		
		userService=new UserService(parameterNameValueMap);
		if(userService.getEmailandPhone()){
			throw new CustomInvalidArgumetException("Email and(or) phone number not valid. Please try other value.");
		}
		
		userService=new UserService(parameterNameValueMap);
		
		
		String result=userService.addNewUser(), msg="No output.";
		logger.info("User is sign up:"+user.getUserId() +"Successfull.");
		if(result.equals(user.getUserId())){
			msg="Congratulations ! You have successfully signedup";			
		}else
			throw new Exception("Some error occured in server while while signing up.");
		return msg;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER', 'ROLE_ADMIN')")
	@RequestMapping(value={"/unlockUser/{userId}"},method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void unlockUser(@PathVariable String userId) {		
		updateLoginAttempt(userId, 0);
		logger.info("User -"+userId+"- has been unlocked by user -" + getCurrentUserId());
	}
	
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER', 'ROLE_ADMIN')")
	@RequestMapping(value={"/getUser/{userId}"},method = RequestMethod.GET)//
	public @ResponseBody User getUserEndPonit(@PathVariable String userId) {
		User user=getUser(userId);
		ControllerUtility.sensorUserObject(user);
		return user;
	}
	
	public User getUser(String userId) {
		HashMap<String, String> param=new HashMap<String, String>();
		ControllerUtility.validateUserID(userId);
		param.put(DbParamNams.USER_ID,userId);
		UserService userService=new UserService(param);
		User user =userService.getUserDetails();
		if(user==null){
			throw new CustomerNotFoundException();
		}
		return user;
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value={"/getUserDetails",},method = RequestMethod.GET)
	public @ResponseBody User getUser() {
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();		
		HashMap<String, String> param=new HashMap<String, String>();
		param.put(DbParamNams.USER_ID,userId);
		UserService userService=new UserService(param);
		User user =userService.getUserDetails();
		if(user==null){
			throw new CustomerNotFoundException();
		}
		ControllerUtility.sensorUserObject(user);
		return user;
	}
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value={"/authenticated/getUserRole"},method = RequestMethod.GET)//
	public @ResponseBody String getUserRole() {
		String userName= SecurityContextHolder.getContext().getAuthentication().getName();
		userName=userName==null?"":userName;
		return getUser(userName).getUserRole();
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value={"/getUserRole"},method = RequestMethod.GET)//will be deprecated
	public @ResponseBody String getUserRole(String userId) {
		//String userName= SecurityContextHolder.getContext().getAuthentication().getName();
		//userName=userName==null?"":userName;
		return getUser(userId).getUserRole();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value={"/updateUserStatus"}, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateUserStatus(@RequestBody User user) throws SQLException{
		ControllerUtility.validateUserStatus(user.getUserStatus());
		ControllerUtility.validateUserID(user.getUserId());
		User userDB=getUser(user.getUserId());//check and throws exception is the user is not in the DB		
		ControllerUtility.validateUserPermission(userDB.getUserRole(), getUserRole());
		if(user.getUserStatus().equals(DbParamNams.ACCOUNT_STATUS_ACTIVE)){
			unlockUser(user.getUserId());
		}
		UserService userService=new UserService(ControllerUtility.getUserMap(user));
		return userService.setUserStatus();
	}
	
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER', 'ROLE_ADMIN')")
	@RequestMapping(value={"/getUserList"}, method = RequestMethod.GET)
	public @ResponseBody List<User> getUserList(){
		String currentRole=getUserRole();
		ArrayList <String>role=new ArrayList<>();
		switch(currentRole){
			case DbParamNams.USER_ROLE_ADMIN:
				role.add(DbParamNams.USER_ROLE_MANANEGR);
			case DbParamNams.USER_ROLE_MANANEGR:
				role.add(DbParamNams.USER_ROLE_EMPLOYEE);
			case DbParamNams.USER_ROLE_EMPLOYEE:
				role.add(DbParamNams.USER_ROLE_CUSTOMER);
				role.add(DbParamNams.USER_ROLE_MERCHANT);
		}
		List <User> users =new ArrayList<>();
		role.forEach(role1->{
			users.addAll(getUserList(role1));
		});				
		//users.forEach((user)->ControllerUtility.sensorUserObject(user));				
		return users;
	}
	
	public @ResponseBody List<User> getUserList(@PathVariable String userRole){
		HashMap<String, String> param=new HashMap<String, String>();
		ControllerUtility.validateUserRole(userRole);
		param.put(DbParamNams.USER_ROLE,userRole);
		UserService userService=new UserService(param);		
		List <User> users=userService.getUserListOnUserRole();		
		if(users!=null){
			users.forEach((user)->ControllerUtility.sensorUserObject(user));
		} else{
			users =new ArrayList<>();
		}
		return users;
	}
	
	@RequestMapping(value="/anonymous/getsecurityqn/{userId}", method = RequestMethod.GET)//
	public @ResponseBody User getSecurityqn(@PathVariable String userId) throws SQLException{
		ControllerUtility.validateUserID(userId);
		getUser(userId);//check and throws exception is the user is not in the DB
		checkLoginAttempt(userId);
		HashMap<String, String> param=new HashMap<String, String>();
		param.put(DbParamNams.USER_ID,userId);
		UserService userService=new UserService(param);
		return userService.getSecurityqn();
	}
	
	@RequestMapping(value="/anonymous/sendOtp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String forgotpwd(@RequestBody User user) throws Exception{		
		ControllerUtility.validateUserID(user.getUserId());
		ControllerUtility.validateUserID(user.getSecurityAns2());
		ControllerUtility.validateUserID(user.getSecurityAns1());
		checkLoginAttempt(user.getUserId());
		User user_=getUser(user.getUserId());//check and throws exception is the user is not in the DB	
		UserService userService=new UserService(ControllerUtility.getUserMap(user));
		HashMap<String, String> param=ControllerUtility.getUserMap(user);		
		if(userService.checkSecurityAns()){			
			OTPValidator otpsend=new OTPValidator();
			otpsend.sendOTP(user_.getUserId(),user_.getPhoneNumber());
		} else {
			throw new CustomInvalidArgumetException("Security does not match.");
		}
		return "Success! OTP is sent your registered phone number.";
	}
	void checkLoginAttempt(String userId){
		User user=getUser(userId);
		if(Integer.parseInt(user.getLoginAttemptNumber())>ControllerUtility.LOGIN_ATTEMPT_MAX_NUM){
			logger.error(String.format("Authentication failure for UserID : %s. User account is locked, number of tries exceeded", user.getUserId()));
			throw new CustomInvalidArgumetException("User Account is locked, number of tries exceeded. Contact Bank employees to unlock the account.");
		}
	}
	@RequestMapping(value="/anonymous/otpAndNewPassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String validate( @RequestBody HashMap<String, String> values) throws Exception{
		
		String password=values.get(DbParamNams.USER_PASSWORD);
		if(!password.equals(values.get("confirm_"+DbParamNams.USER_PASSWORD))){
			throw new CustomInvalidArgumetException("Password not matching. Please enter a matching password");
		}
		checkLoginAttempt(values.get(DbParamNams.USER_ID));
		String otp=values.remove(DbParamNams.OTP);
		ControllerUtility.validateOTP(otp);
		ControllerUtility.validateUserPassword(password);		
		ObjectMapper mapper=new ObjectMapper();		
		ControllerUtility.validateUserID(values.get(DbParamNams.USER_ID));		
		OTPValidator otpsend=new OTPValidator();
		try{
			otpsend.validateOTP(values.get(DbParamNams.USER_ID),otp);
		} catch(CustomInvalidArgumetException e){
			updateLoginAttempt(values.get(DbParamNams.USER_ID));
			throw e;
		}
		UserService userService=new UserService(values);		
		userService.modifyUserDetails();
		return "Awesome! Password successfully changed.";
		
	}
	@Deprecated
	public @ResponseBody boolean checkLogin(@RequestBody User user){
        //to avoid NPE
        ControllerUtility.validateUserID(user.getUserId());
        ControllerUtility.validateUserPassword(user.getPassword());
		UserService usr=new UserService(ControllerUtility.getUserMap(user));
		return usr.validateLoginCredentials();
	}

	public void updateLoginAttempt(String userId){
		User user=null;
		try{
			user=getUser(userId);
		} catch(Exception e){
			logger.error("User does not exists in the system.");
		}
		if(user!=null){
			HashMap<String, String> param=new HashMap<String, String>();
			param.put(DbParamNams.USER_ID,userId);
			UserService userService=new UserService(param);
			userService.updateLoginAttempt(Integer.parseInt(user.getLoginAttemptNumber())+1);
		}
	}
	
	public void updateLoginAttempt(String userId, int attempt){
		User user=null;
		try{
			user=getUser(userId);
		} catch(Exception e){
			logger.error("User does not exists in the system.");
		}
		if(user!=null){
			HashMap<String, String> param=new HashMap<String, String>();
			param.put(DbParamNams.USER_ID,userId);
			UserService userService=new UserService(param);
			userService.updateLoginAttempt(attempt);
		}
	}
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public String getCurrentUserId(){
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}