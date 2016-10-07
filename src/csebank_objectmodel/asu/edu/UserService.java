package csebank_dbmodel.asu.edu;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class UserService {
	private ConnectionClass connectionClass=null;
	private HashMap<String,String> parameterMap;
	private LinkedHashMap<String, String> parameterNameValueMap;
	
	public UserService(HashMap<String,String> parameterMap)
	{
		connectionClass=new ConnectionClass();
		parameterNameValueMap=new LinkedHashMap<String,String>();
		try{
		if(parameterMap!=null)
					this.parameterMap=parameterMap;
		else
					throw new ParameterMapNullException("The Paramter Map is null..Plase insert values");
		}
		catch (ParameterMapNullException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public String addNewUser()
	{
		String result=null;
		String insertQuery="INSERT INTO User(UserId,UserRole,FirstName,LastName,Phone,Email,Address,SSN,DOB,Password, UserStatus, LoginAttempt, SecurityQn, SecurityAns, Organization) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String userId = parameterMap.get("UserId");
		String userRole = parameterMap.get("UserRole");
		String firstName = parameterMap.get("FirstName");
		String lastName = parameterMap.get("LastName");
		String phone = parameterMap.get("Phone");
		String email = parameterMap.get("Email");
		String address = parameterMap.get("Address");
		String ssn = parameterMap.get("SSN");
		//date format should be noted
		//DateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
		//Date dob = (Date) format.parse(parameterMap.get("DOB"));
		String dob = parameterMap.get("DOB");
		String password = parameterMap.get("Password");
		String userStatus = parameterMap.get("UserStatus");
		String loginAttempt = parameterMap.get("LoginAttempt");
		String securityQn = parameterMap.get("SecurityQn");
		String securityAns = parameterMap.get("SecurityAns");
		String organization = parameterMap.get("Organization");
	
		parameterNameValueMap.put("UserId", userId);
		parameterNameValueMap.put("UserRole",userRole);
		parameterNameValueMap.put("FirstName",firstName);
		parameterNameValueMap.put("LastName",lastName);
		parameterNameValueMap.put("Phone",phone);
		parameterNameValueMap.put("Email",email);
		parameterNameValueMap.put("Address",address);
		parameterNameValueMap.put("SSN",ssn);
		parameterNameValueMap.put("DOB",dob);
		parameterNameValueMap.put("Password",password);
		parameterNameValueMap.put("UserStatus", userStatus);
		parameterNameValueMap.put("LoginAttempt",loginAttempt);
		parameterNameValueMap.put("SecurityQn",securityQn);
		parameterNameValueMap.put("SecurityAns",securityAns);
		parameterNameValueMap.put("Organization",organization);
		
		
		connectionClass=new ConnectionClass();
		if(connectionClass.executeUpdateWithSQLQuery(insertQuery, parameterNameValueMap))
		{	result=userId;
		//deduct in src;
		}	
		return result;	
}
	
	///////////////
	

	public List<User> getUserListOnUserRole()
	{
		String selectQuery="SELECT * FROM User where UserRole=?";
		parameterNameValueMap.put("UserRole", parameterMap.get("UserRole"));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		List<User> usersList=this.createUserObject(resultlist);
			return usersList;
		}
	
	///////////////
	
	public List<User> createUserObject(List<HashMap<String,Object>> resultlist){
		List<User> usersList=null;
		if(resultlist.size()>0)
		{
			usersList=new ArrayList<User>();
			for(int i=0;i<resultlist.size();i++)
			{
				User user=new User();
				HashMap<String,Object> userMap=resultlist.get(i);
				user.setUserId((String)userMap.get("UserId"));
				user.setUserRole((String)userMap.get("UserRole"));
				user.setFirstName((String)userMap.get("FirstName"));
				user.setLastName((String)userMap.get("LastName"));
				user.setPhone((String)userMap.get("Phone"));
				user.setEmail((String)userMap.get("Email"));
				user.setAddress((String)userMap.get("Address"));
				user.setSSN((Integer)userMap.get("SSN"));
				user.setDOB((Date)userMap.get("DOB"));
				user.setPassword((String)userMap.get("Password"));
				user.setUserStatus((String)userMap.get("UserStatus"));
				user.setLoginAttempt((String)userMap.get("LoginAttempt"));
				user.setSecurityQn((String)userMap.get("SecurityQn"));
				user.setSecurityAns((String)userMap.get("SecurityAns"));
				user.setOrganization((String)userMap.get("Organization"));
		
				usersList.add(user);
			}
		}
		
		return usersList;
	}
	
	public boolean modifyUserDetails()
	{
		boolean result=false;
		String transStatus=parameterMap.get("TransStatus");
		String updateQuery="UPDATE User SET UserRole=?,FirstName=?,LastName=?,Address=?,Password=?, UserStatus=?, LoginAttempt=?, SecurityQn=?, SecurityAns=?, Organization=? where UserId=?";
		
		String userId = parameterMap.get("UserId");
		String userRole = parameterMap.get("UserRole");
		String firstName = parameterMap.get("FirstName");
		String lastName = parameterMap.get("LastName");
		String address = parameterMap.get("Address");
		String password = parameterMap.get("Password");
		String userStatus = parameterMap.get("UserStatus");
		String loginAttempt = parameterMap.get("LoginAttempt");
		String securityQn = parameterMap.get("SecurityQn");
		String securityAns = parameterMap.get("SecurityAns");
		String organization = parameterMap.get("Organization");
	
		parameterNameValueMap.put("UserId", userId);
		parameterNameValueMap.put("UserRole",userRole);
		parameterNameValueMap.put("FirstName",firstName);
		parameterNameValueMap.put("LastName",lastName);
		parameterNameValueMap.put("Address",address);
		parameterNameValueMap.put("Password",password);
		parameterNameValueMap.put("UserStatus", userStatus);
		parameterNameValueMap.put("LoginAttempt",loginAttempt);
		parameterNameValueMap.put("SecurityQn",securityQn);
		parameterNameValueMap.put("SecurityAns",securityAns);
		parameterNameValueMap.put("Organization",organization);
		
		if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap))
			result=true;
		
		return result;
	}
	
	public boolean validateLoginCredentials(){
		boolean result=false;
		String username = parameterMap.get("UserId");
		String password = parameterMap.get("Password");
		
		String selectQuery="SELECT UserId, Password FROM User where UserId=?";
		User user=null;
		parameterNameValueMap.put("UserId", parameterMap.get("UserId"));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		user=this.createUserObject(resultlist).get(0);
		
		//incrementing login attempt, session end set to false(0)
		updateLoginAttempt(0);
		
		//validating login credentials
		if(username.equals(user.getUserId())&&password.equals(user.getPassword())){
			result = true;
		}
		return result;
	}
	
	public boolean checkSecurityAns(){
		boolean result=false;
		String secAns = parameterMap.get("SecurityAns");
		
		String selectQuery="SELECT SecurityAns FROM User where UserId=?";
		User user=null;
		parameterNameValueMap.put("UserId", parameterMap.get("UserId"));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		user=this.createUserObject(resultlist).get(0);
		
		//checking security answer
		if(secAns.equals(user.getSecurityAns())){
			result = true;
		}
		return result;
	}
	
	public boolean updateLoginAttempt(int sessionEnd){
		
		String updateQuery="UPDATE User SET LoginAttempt=? where UserId=?";
		boolean update = false;
		String loginAttemptstr = null ;
		boolean result = false;
		
		//if session does not end, increment login attempt
		if(sessionEnd==0){
			String loginAttempt = parameterMap.get("LoginAttempt");
			int logAttempt = Integer.parseInt(loginAttempt);
			logAttempt = logAttempt + 1;
			update = true;
			loginAttemptstr = Integer.toString(logAttempt);
		}
				
		//if session ends, set login attempt to 0
		else if(sessionEnd==1){
			loginAttemptstr = Integer.toString(0);
			update = true;
		}
				
		else{
			update = false;
		}
		
		if(update){
			parameterNameValueMap.put("LoginAttempt", loginAttemptstr);
		}
		else{
			parameterNameValueMap.put("LoginAttempt", parameterMap.get("LoginAttempt"));
		}

		parameterNameValueMap.put("UserId", parameterMap.get("UserId"));
		if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap))
			result=true;
		
		return result;
		
		
	}
	
	public boolean setUserStatus(){
		
		String updateQuery="UPDATE User SET UserStatus=? where UserId=?";
		boolean update=false;
		String us = parameterMap.get("UserStatus");
		parameterNameValueMap.put("UserStatus", us);
		parameterNameValueMap.put("UserId", parameterMap.get("UserId"));
		if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap)){
			update = true;
		}
		return update;
		
	}
	
	public boolean KYCmodfn(){
		String kycstatus = parameterMap.get("Status");
		boolean update = false;
		if(kycstatus.equals("Yes")){
			String updateQuery="UPDATE User SET Phone=?, Email=?, DOB=? where UserId=?";
			parameterNameValueMap.put("Phone", parameterMap.get("Phone"));
			parameterNameValueMap.put("Email", parameterMap.get("Email"));
			parameterNameValueMap.put("DOB", parameterMap.get("DOB"));
			parameterNameValueMap.put("UserId", parameterMap.get("UserId"));
			if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap)){
				update = true;
			}
			
		}
		return update;
	}
	
	
	/*
	 * KYC (allow email, phone number, DOB)
	setUserStatus
	 */
	
}
