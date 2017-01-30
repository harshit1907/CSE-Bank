package csebank_database.asu.edu;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import csebank_utility.asu.edu.DBQueries;
import csebank_utility.asu.edu.DbParamNams;

public class OtpTabService {
	private ConnectionClass connectionClass=null;
	private HashMap<String,String> parameterMap;
	private LinkedHashMap<String, String> parameterNameValueMap;
	
	public OtpTabService(HashMap<String,String> parameterMap)
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
	
	public boolean addOtp()
	{
		String insertQuery=DBQueries.addOtp;
		boolean result=false;
		parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
		parameterNameValueMap.put(DbParamNams.OTP, parameterMap.get(DbParamNams.OTP));
		if(connectionClass.executeUpdateWithSQLQuery(insertQuery, parameterNameValueMap));
		result=true;
		
		return result;
	}
	public boolean validateOtp()
	{
		String validateQuery=DBQueries.isOTPValid;
		boolean result=false;
		parameterNameValueMap.put(DbParamNams.OTP, parameterMap.get(DbParamNams.OTP));
		parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
		List<HashMap<String,Object>> resultList=connectionClass.executeSelectQuery(validateQuery, parameterNameValueMap);
		if(resultList.size()==1)
		{
		String currentOtp=resultList.get(0).get(DbParamNams.OTP).toString();
		if(currentOtp.equalsIgnoreCase(parameterMap.get(DbParamNams.OTP)));
		result=true;
		}
		return result;
	}
}
