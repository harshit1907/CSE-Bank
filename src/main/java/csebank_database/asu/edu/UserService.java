package csebank_database.asu.edu;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import csebank_objectmodel.asu.edu.User;
import csebank_utility.asu.edu.DBQueries;
import csebank_utility.asu.edu.DbParamNams;
import exception.csebank_controllers.asu.edu.CustomerNotFoundException;
import exception.csebank_controllers.asu.edu.UserIDAlreadyExists;

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
		boolean userexists=getCheckUser();
		if(userexists)
			throw new UserIDAlreadyExists();
		else
		{
		String result=null;
		String insertQuery=DBQueries.addNewUser;
		parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
		parameterNameValueMap.put(DbParamNams.USER_ROLE,parameterMap.get(DbParamNams.USER_ROLE));
		parameterNameValueMap.put(DbParamNams.USER_FIRST_NAME,parameterMap.get(DbParamNams.USER_FIRST_NAME));
		parameterNameValueMap.put(DbParamNams.USER_LAST_NAME, parameterMap.get(DbParamNams.USER_LAST_NAME));
		parameterNameValueMap.put(DbParamNams.USER_PHONE,parameterMap.get(DbParamNams.USER_PHONE));
		parameterNameValueMap.put(DbParamNams.USER_EMAIL,parameterMap.get(DbParamNams.USER_EMAIL));
		parameterNameValueMap.put(DbParamNams.USER_ADDRESS,parameterMap.get(DbParamNams.USER_ADDRESS));
		parameterNameValueMap.put(DbParamNams.USER_SSN,parameterMap.get(DbParamNams.USER_SSN));
		parameterNameValueMap.put(DbParamNams.USER_DOB,parameterMap.get(DbParamNams.USER_DOB));
		parameterNameValueMap.put(DbParamNams.USER_PASSWORD,parameterMap.get(DbParamNams.USER_PASSWORD));
		parameterNameValueMap.put(DbParamNams.USER_STATUS,DbParamNams.USER_STATUS_ACTIVE);
		parameterNameValueMap.put(DbParamNams.USER_LOGIN_ATTEMPT,0+"");
		parameterNameValueMap.put(DbParamNams.USER_SEC_QN1,parameterMap.get(DbParamNams.USER_SEC_QN1));
		parameterNameValueMap.put(DbParamNams.USER_SEC_ANS1,parameterMap.get(DbParamNams.USER_SEC_ANS1));
		parameterNameValueMap.put(DbParamNams.USER_SEC_QN2,parameterMap.get(DbParamNams.USER_SEC_QN2));
		parameterNameValueMap.put(DbParamNams.USER_SEC_ANS2,parameterMap.get(DbParamNams.USER_SEC_ANS2));
		parameterNameValueMap.put(DbParamNams.USER_ORGANISATION,parameterMap.get(DbParamNams.USER_ORGANISATION));


		connectionClass=new ConnectionClass();
		if(connectionClass.executeUpdateWithSQLQuery(insertQuery, parameterNameValueMap))
		{	result=parameterMap.get(DbParamNams.USER_ID);
		
		}
		return result;	
		}
	}

	///////////////

	public boolean getCheckUser()
	{
		String selectQuery=DBQueries.getUserDetails;
		parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
        if(resultlist.size()==0)
        	return false;
        return true;
	}
	public List<User> getUserListOnUserRole()
	{
		String selectQuery=DBQueries.getUserListOnUserRole;
		parameterNameValueMap.put(DbParamNams.USER_ROLE, parameterMap.get(DbParamNams.USER_ROLE));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		List<User> usersList=this.createUserObject(resultlist);
		return usersList;
	}

	///////////////


	public List<User> createUserObject(List<HashMap<String,Object>> resultlist){
		List<User> usersList=null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date parsed=null;
		java.sql.Date sqldate=null;
		if(resultlist.size()>0)
		{
			usersList=new ArrayList<User>();
			for(int i=0;i<resultlist.size();i++)
			{
				User user=new User();
				HashMap<String,Object> userMap=resultlist.get(i);
				user.setUserId((String)userMap.get(DbParamNams.USER_ID));
				user.setUserRole((String)userMap.get(DbParamNams.USER_ROLE));
				user.setFirstName((String)userMap.get(DbParamNams.USER_FIRST_NAME));
				user.setLastName((String)userMap.get(DbParamNams.USER_LAST_NAME));
				user.setPhoneNumber((String)userMap.get(DbParamNams.USER_PHONE));
				user.setEmail((String)userMap.get(DbParamNams.USER_EMAIL));
				user.setAddress((String)userMap.get(DbParamNams.USER_ADDRESS));
				user.setPassword((String)userMap.get(DbParamNams.USER_PASSWORD));
				user.setLoginAttemptNumber((String)userMap.get(DbParamNams.USER_LOGIN_ATTEMPT));
				if(userMap.get(DbParamNams.USER_SSN) != null)
					user.setSSN(Integer.parseInt((String) userMap.get(DbParamNams.USER_SSN)));
				if(userMap.get(DbParamNams.USER_DOB) != null)
				{
					try {
						parsed = format.parse((String) userMap.get(DbParamNams.USER_DOB));
					} catch (ParseException e) {
						e.getMessage();
					}
					sqldate= new java.sql.Date(parsed.getTime());
				}
				user.setDOB(sqldate);
				user.setUserStatus((String)userMap.get(DbParamNams.USER_STATUS));
				user.setSecurityQn1((String)userMap.get(DbParamNams.USER_SEC_QN1));
				user.setSecurityAns1((String)userMap.get(DbParamNams.USER_SEC_ANS1));
				user.setSecurityQn2((String)userMap.get(DbParamNams.USER_SEC_QN2));
				user.setSecurityAns2((String)userMap.get(DbParamNams.USER_SEC_ANS2));
				user.setOrganization((String)userMap.get(DbParamNams.USER_ORGANISATION));

				usersList.add(user);
			}
		}

		return usersList;
	}
public boolean modifyUserDetails()
	{
		boolean result=false;
		String updateQuery=null;
		
		if(parameterMap.containsKey(DbParamNams.USER_EMAIL))
		{
			updateQuery=DBQueries.modifyUserDetails_email;
			parameterNameValueMap.put(DbParamNams.USER_EMAIL, parameterMap.get(DbParamNams.USER_EMAIL));
		}
		else if(parameterMap.containsKey(DbParamNams.USER_PHONE))
		{
			updateQuery=DBQueries.modifyUserDetails_phoneNumber;
			parameterNameValueMap.put(DbParamNams.USER_PHONE, parameterMap.get(DbParamNams.USER_PHONE));

		}
		else if(parameterMap.containsKey(DbParamNams.USER_PASSWORD))
		{
			updateQuery=DBQueries.modifyUserDetails_password;
			parameterNameValueMap.put(DbParamNams.USER_PASSWORD, parameterMap.get(DbParamNams.USER_PASSWORD));

		}
		parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
		
		if(updateQuery!=null)
		{
			if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap))
				result=true;
		}
		return result;
	}
	public User getUserDetails()
	{
		String selectQuery=DBQueries.getUserDetails;
		User user=null;
		parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		if(resultlist.isEmpty())
		{
			throw new CustomerNotFoundException();
		}
		user=this.createUserObject(resultlist).get(0);
		return user;
	}
	public boolean validateLoginCredentials(){
		boolean result=false;
		String username = parameterMap.get(DbParamNams.USER_ID);
		String password = parameterMap.get(DbParamNams.USER_PASSWORD);

		String selectQuery=DBQueries.validateLoginCredentials;
		parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		connectionClass=new  ConnectionClass();
		String currentUserId=resultlist.get(0).get(DbParamNams.USER_ID).toString();
		String currentPass=resultlist.get(0).get(DbParamNams.USER_PASSWORD).toString();

		if(username.equalsIgnoreCase(currentUserId)&&password.equalsIgnoreCase(currentPass))
		{
			updateLoginAttempt(0);
			result = true;
		}
		else{
			String attempts=getLoginAttempt();
			System.out.println(attempts);
			int attemptint=Integer.parseInt(attempts)+1;
			updateLoginAttempt(attemptint);
		}

		return result;
	}

	public boolean checkSecurityAns(){
		boolean result=false;
		String secAns1 = parameterMap.get(DbParamNams.USER_SEC_ANS1);
		String secAns2 = parameterMap.get(DbParamNams.USER_SEC_ANS2);
		String selectQuery1=DBQueries.checkSecurityAns1;
		String selectQuery2=DBQueries.checkSecurityAns2;
		parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery1, parameterNameValueMap);
		User user1=this.createUserObject(resultlist).get(0);
		connectionClass=new ConnectionClass();
		List<HashMap<String,Object>> resultlist2=connectionClass.executeSelectQuery(selectQuery2, parameterNameValueMap);
		User user2=this.createUserObject(resultlist2).get(0);
		//checking security answer
		if(secAns1.equals(user1.getSecurityAns1())&&secAns2.equals(user2.getSecurityAns2())){
			result = true;
		}
		return result;
	}
	public String getLoginAttempt()
	{

		String getQuery=DBQueries.getLoginAttempt;
		parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(getQuery, parameterNameValueMap);
		;
		return resultlist.get(0).get(DbParamNams.USER_LOGIN_ATTEMPT).toString();
	}

	public boolean updateLoginAttempt(int attempts){

		String updateQuery=DBQueries.updateLoginAttempt;
		boolean update=false;
		parameterNameValueMap.clear();
		parameterNameValueMap.put(DbParamNams.USER_LOGIN_ATTEMPT, attempts+"");
		parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
		System.out.println(attempts+parameterMap.get(DbParamNams.USER_ID));
		connectionClass=new  ConnectionClass();
		if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap)){
			//System.out.println("hii");
			update = true;
		}
		if(attempts>=3)
		{
			HashMap<String,String> statusMap=new HashMap<String,String>();
			statusMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
			statusMap.put(DbParamNams.USER_STATUS,DbParamNams.USER_STATUS_SUSPENDED);
			UserService userservice=new UserService(statusMap);
			userservice.setUserStatus();
			return false;
		}
	
		return update;

	}

	public boolean setUserStatus(){

		String updateQuery=DBQueries.setUserStatus;
		boolean update=false;
		parameterNameValueMap.put(DbParamNams.USER_STATUS, parameterMap.get(DbParamNams.USER_STATUS));
		parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
		if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap)){
			update = true;
		}
		return update;

	}
	public User getSecurityqn()
	{
		User user=new User();
		String selectQuery=DBQueries.getSecurityqn;
		parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		if(resultlist.size()>0){			
			HashMap<String, Object> userMap=resultlist.get(0);
			user.setSecurityQn1((String)userMap.get(DbParamNams.USER_SEC_QN1));
			user.setSecurityQn2((String)userMap.get(DbParamNams.USER_SEC_QN2));			
		}
		return user;


	}
	public boolean getEmailandPhone()
	{
		String selectQuery=DBQueries.getEmailandPhone;
		parameterNameValueMap.put(DbParamNams.USER_EMAIL, parameterMap.get(DbParamNams.USER_EMAIL));
		parameterNameValueMap.put(DbParamNams.USER_PHONE, parameterMap.get(DbParamNams.USER_PHONE));

		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		if(resultlist.size()>0){			
			return true;		
		}
		else
		return false;


	}



}