package csebank_dbmodel.asu.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;



public class AccountService {

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
	
	public String addAccount()
	{
		boolean result=false;
		Utility utility=new Utility();
		String accountId=utility.loadRandomNumber(16);
		String insertQuery="INSERT INTO `Account` (`AccountId`, `UserId`, `AccType`, `AccOpenDate`, `AccBalance`, `AccStatus`) VALUES (?, ?, ?, NOW(), ?, 'active');";
		parameterNameValueMap.put("AccountId", accountId);
		parameterNameValueMap.put("UserId", parameterMap.get("UserId"));
		parameterNameValueMap.put("AccType", parameterMap.get("AccType"));
		parameterNameValueMap.put("AccBalance", parameterMap.get("AccBalance"));
		if(connectionClass.executeUpdateWithSQLQuery(insertQuery, parameterNameValueMap))
			result=true;
		parameterNameValueMap.clear();
		if(result)
			return accountId;
		else 
			return "";
	}	
	
	public int getAccountBalance() 
	{
		String getBalanceQuery="SELECT AccBalance FROM Account WHERE AccountId=?";
		parameterNameValueMap.put("AccountId", parameterMap.get("AccountId"));
		List<HashMap<String,Object>> resultList = connectionClass.executeSelectQuery(getBalanceQuery, parameterNameValueMap);
		int balance=((Integer)resultList.get(0).get("AccBalance"));
		return balance;
	}
	
	public boolean updateAccount()
	{
		boolean result=false;
		String transStatus = parameterMap.get("TransStatus");
		HashMap<String, String> param=new HashMap<>();
		param.put("AccountId",parameterMap.get("AccountId"));
		int currentBalance = this.getAccountBalance();
		int transAmount = Integer.parseInt(parameterMap.get("TransAmount"));
		
		if(transStatus=="approved" || transStatus=="rejected")
		{
			 int updatedBalance = currentBalance + transAmount;
			 String updateQuery="UPDATE Account SET AccBalance=? WHERE AccountId=?";
			 parameterNameValueMap.put("AccountId", parameterMap.get("AccountId"));
			 parameterNameValueMap.put("AccBalance", Integer.toString(updatedBalance));
			 if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap))
				 result=true;
		}
		if(transStatus=="pending")
		{
			 int updatedBalance = currentBalance - transAmount; 
			 String updateQuery="UPDATE Account SET AccBalance=? WHERE AccountId=?";
			 parameterNameValueMap.put("AccountId", parameterMap.get("AccountId"));
			 parameterNameValueMap.put("AccBalance", Integer.toString(updatedBalance));
			 if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap))
				 result=true;
		}
		return result;
	}
}