package csebank_database.asu.edu;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import csebank_objectmodel.asu.edu.AccountLog;
import csebank_utility.asu.edu.DBQueries;
import csebank_utility.asu.edu.DbParamNams;

public class AccountLogService {

	ConnectionClass connectionClass=null;
	private HashMap<String,String> parameterMap;
	private LinkedHashMap<String, String> parameterNameValueMap;
	
	public AccountLogService(HashMap<String,String> parameterMap)
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
	
	public boolean addAccountLogEntry()
	{
		boolean result=false;
		String insertQuery=DBQueries.addAccountLogEntry;
		parameterNameValueMap.put(DbParamNams.ACCOUNT_ID,parameterMap.get(DbParamNams.ACCOUNT_ID));
		parameterNameValueMap.put(DbParamNams.TRANS_ID, parameterMap.get(DbParamNams.TRANS_ID));
		
		connectionClass=new ConnectionClass();
		if(connectionClass.executeUpdateWithSQLQuery(insertQuery, parameterNameValueMap))
		{	
			result=true;
		}	
		return result;	
	}
	
	public AccountLog getAccountLogEntries()
	{
		String selectQuery=DBQueries.getAccountLogEntries;
		AccountLog log=null;
		parameterNameValueMap.put(DbParamNams.ACCOUNT_ID, parameterMap.get(DbParamNams.ACCOUNT_ID));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		log=this.createAccountLogObject(resultlist);
			return log;
	}

	public AccountLog createAccountLogObject(List<HashMap<String, Object>> resultlist) {
		AccountLog accountlog=null;
		if(resultlist.size()>0)
		{
			accountlog=new AccountLog();
			HashMap<String,Object> accMap=resultlist.get(0);	
			accountlog.setAccountBalance((int) accMap.get(DbParamNams.ACCOUNT_BALANCE));
			accountlog.setAccountId((String) accMap.get(DbParamNams.ACCOUNT_ID));
		    accountlog.setTransId((String) accMap.get(DbParamNams.TRANS_ID));
		}
		return accountlog;
		
	}
}