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
	
	public boolean deleteAccount()
	{
		boolean result=false;
		String updateQuery="UPDATE Account A SET A.AccStatus='closed' WHERE A.accountId=?";
		parameterNameValueMap.put("AccountId", accountId);
		if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap))
			result=true;
		return result;
	}
	
	public boolean blockAccount()
	{
		boolean result=false;
		String updateQuery="UPDATE Account A SET A.AccStatus='blocked' WHERE A.accountId=?";
		parameterNameValueMap.put("AccountId", accountId);
		if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap))
			result=true;
		return result;
	}
	
	public int getAccountBalance() 
	{
		String getBalanceQuery="SELECT AccBalance FROM Account WHERE accountId=?";
		parameterNameValueMap.put("AccountId", parameterMap.get("AccountId"));
		List<HashMap<String,Object>> resultList = connectionClass.executeSelectQuery(getBalanceQuery, parameterNameValueMap);
		int balance=((Integer)resultList.get(0).get("AccBalance"));
		return balance;
	}
	
	public Account getAccountDetailsOnAccId()
	{
		String selectQuery="SELECT * FROM Account where accountId=?";
		Account account=null;
		parameterNameValueMap.put("accountId", parameterMap.get("accountId"));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		account=this.createAccountObject(resultlist).get(0);
			return account;
	}	

	public List<Account> getAccountListOnUserId()
	{
		String selectQuery="SELECT * FROM Account where userId=?";
		parameterNameValueMap.put("userId", parameterMap.get("userId"));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		List<Account> accountList=this.createAccountObject(resultlist);
			return accountList;
	}
	
	public String checkAccountStatus()
	{
		String selectQuery="SELECT accountStatus FROM Account where accountId=?";
		Account account=null;
		parameterNameValueMap.put("accountId", parameterMap.get("accountId"));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		account=this.createAccountObject(resultlist).get(0);
		String status=resultList.get(0).get("accountStatus");
			return status;
	}	
			
	public boolean updateAccountBalance()
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