package csebank_database.asu.edu;

import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SessionService {
	private ConnectionClass connectionClass=null;
	private HashMap<String,String> parameterMap;
	private LinkedHashMap<String, String> parameterNameValueMap;
    public SessionService(HashMap<String,String> parameterMap)
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
    public boolean addSession()
    {
    	boolean result=false;
    	String insertQuery="insert into session(SessionKey,UserId,SessionStart,SessionEnd,SessionRequest,SessionTimeout,SessionOTP) values(?,?,?,?,?,?,?);";
    	 parameterNameValueMap.put("SessionKey",parameterMap.get("SessionKey"));
    	 parameterNameValueMap.put("UserId",parameterMap.get("UserId"));
    	 parameterNameValueMap.put("SessionStart",parameterMap.get("SessionStart"));
    	 parameterNameValueMap.put("SessionEnd",parameterMap.get("SessionEnd"));
    	 parameterNameValueMap.put("SessionRequest",parameterMap.get("SessionRequest"));    	 
    	 parameterNameValueMap.put("SessionTimeout",parameterMap.get("SessionTimeout"));
    	 parameterNameValueMap.put("SessionOTP",parameterMap.get("SessionOTP"));
    	 if(connectionClass.executeUpdateWithSQLQuery(insertQuery, parameterNameValueMap))
				result=true; 
			 parameterNameValueMap.clear();
	      return result;
    }
    public String getOtp()
    {
    	String getQuery="Select SessionOTP from session where SessionKey=?;";
    	parameterNameValueMap.put("SessionKey",parameterMap.get("SessionKey"));
    	List<HashMap<String,Object>> resultList=connectionClass.executeSelectQuery(getQuery, parameterNameValueMap);
        String otp=resultList.get(0).get("SessionOTP").toString();
        return otp;
    }
    public boolean setEndTime()
    {
    	boolean result=false;
    	String setQuery="update session set SessionEnd=Now() where SessionKey=?";	 
    	parameterNameValueMap.put("SessionKey",parameterMap.get("SessionKey"));
    	if(connectionClass.executeUpdateWithSQLQuery(setQuery, parameterNameValueMap))
			result=true; 
		 parameterNameValueMap.clear();
      return result;
     }
    public boolean setTimeOut()
    {
    
    	boolean result=false;
    	String setQuery="update session set SessionTimeout='true' where SessionKey=?";	 
    	parameterNameValueMap.put("SessionKey",parameterMap.get("SessionKey"));
    	if(connectionClass.executeUpdateWithSQLQuery(setQuery, parameterNameValueMap))
    	{
			result=true; 
			setEndTime();
    	}
    	parameterNameValueMap.clear();
      return result;
    
    }
    public boolean setOTP()
    {
    	boolean result=false;
    	String setQuery="update session set SessionOTP=? where SessionKey=?";
    	parameterNameValueMap.put("SessionOTP",parameterMap.get("SessionOTP"));
    	parameterNameValueMap.put("SessionKey",parameterMap.get("SessionKey"));
    	if(connectionClass.executeUpdateWithSQLQuery(setQuery, parameterNameValueMap))
    	{
			result=true; 
    	}
    	parameterNameValueMap.clear();
      return result;
    }
    
    
}
