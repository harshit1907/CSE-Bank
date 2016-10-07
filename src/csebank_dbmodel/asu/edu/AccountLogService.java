package csebank_dbmodel.asu.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class AccountLogService {

	ConnectionClass connectionClass=null;
	private HashMap<String,String> parameterMap;
	private LinkedHashMap<String, String> parameterNameValueMap;
	
	public AccountService(HashMap<String,String> parameterMap)
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
		String insertQuery="INSERT INTO `AccountLog` SELECT T.TransId, A.AccBalance, A.AccountId FROM Account A INNER JOIN Transaction T ON A.AccountId=T.TransDestAccNo OR A.AccountId=T.TransSrcAccNo WHERE A.AccountId = ? AND T.TransId = ?";
		Utility utility=new Utility();
		parameterNameValueMap.put("transId", transId);
		parameterNameValueMap.put("accountId", accountId);
		
		connectionClass=new ConnectionClass();
		if(connectionClass.executeUpdateWithSQLQuery(insertQuery, parameterNameValueMap))
		{	
			result=true;
		}	
		return result;	
	}
	
	public AccountLog getAccountLogEntries()
	{
		String selectQuery="SELECT * FROM AccountLog where accountId=?";
		AccountLog log=null;
		parameterNameValueMap.put("accountId", parameterMap.get("accountId"));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		log=this.createAccountLogObject(resultlist).get(0);
			return log;
	}
}