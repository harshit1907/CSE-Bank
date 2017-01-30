package csebank_database.asu.edu;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


import csebank_objectmodel.asu.edu.Account;
import csebank_utility.asu.edu.DBQueries;
import csebank_utility.asu.edu.DbParamNams;
import csebank_utility.asu.edu.Utility;
import exception.csebank_controllers.asu.edu.NoAccountExists;

public class AccountService {
	private ConnectionClass connectionClass=null;
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
        boolean queryStatus=false;
        String result=null;
        Utility utility=new Utility();
        String accountId=utility.loadRandomNumber(16);
        String insertQuery=DBQueries.addAccount;
        parameterNameValueMap.put(DbParamNams.ACCOUNT_ID, accountId);
        parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
        parameterNameValueMap.put(DbParamNams.ACCOUNT_TYPE, parameterMap.get(DbParamNams.ACCOUNT_TYPE));
        if(parameterMap.get(DbParamNams.ACCOUNT_TYPE).equalsIgnoreCase("Credit"))
        	parameterNameValueMap.put(DbParamNams.ACCOUNT_BALANCE, DbParamNams.CREDIT_LIMIT_VALUE.toString());
        else
        parameterNameValueMap.put(DbParamNams.ACCOUNT_BALANCE, parameterMap.get(DbParamNams.ACCOUNT_BALANCE));
        if(connectionClass.executeUpdateWithSQLQuery(insertQuery, parameterNameValueMap))
        	queryStatus=true;
        if(parameterMap.get(DbParamNams.ACCOUNT_TYPE).equalsIgnoreCase("Credit")){
            HashMap<String, String> paramMap=new HashMap<String, String>();
            paramMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
            paramMap.put(DbParamNams.CREDIT_ACCOUNT_ID, accountId);
         	new CreditService(paramMap).addCreditAccount();
        }
        if(queryStatus)
        	result= accountId;

        return result;
    }
		public boolean updateAccountBalanceDepositWitdrw()
	{
		boolean result=false;
		int currentBalance = this.getAccountBalance();
		int transAmount = Integer.parseInt(parameterMap.get(DbParamNams.TRANS_AMOUNT));
		 int updatedBalance= currentBalance + transAmount;;
		 String updateQuery=DBQueries.updateAccountBalance;
		 parameterNameValueMap.clear();
		 parameterNameValueMap.put(DbParamNams.ACCOUNT_BALANCE, updatedBalance+"");
		 parameterNameValueMap.put(DbParamNams.ACCOUNT_ID, parameterMap.get(DbParamNams.ACCOUNT_ID));
			
		 connectionClass=new ConnectionClass();
		connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap);
		AccountService accountService=new AccountService(parameterMap);
		 if(accountService.getAccountDetailsOnAccId().getAccType().equalsIgnoreCase(DbParamNams.ACCOUNT_TYPE_CREDIT))
		 {
			 CreditService creditService=new CreditService(parameterMap);
			 creditService.updateCreditBalance();
			 result=true;
		 }
		return result;
	}
	public String getEmailonUserId()
    {
        String getQuery=DBQueries.getEmailonUserId;
        parameterNameValueMap.put(DbParamNams.USER_EMAIL, parameterMap.get(DbParamNams.USER_EMAIL));
       parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
        List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(getQuery, parameterNameValueMap);
        String email=null;    
        if(resultlist.size()>0)
            {
              HashMap<String, Object> userMap=resultlist.get(0);
              email=(String)userMap.get(DbParamNams.USER_SEC_QN1);
            }
        return email;
        
    }
	
	public String getEmailonAccountId()
    {
        String getQuery=DBQueries.getEmailonUserId;
        parameterNameValueMap.put(DbParamNams.ACCOUNT_ID, parameterMap.get(DbParamNams.ACCOUNT_ID));
        List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(getQuery, parameterNameValueMap);
        String email=null;    
        if(resultlist.size()>0)
            {
              HashMap<String, Object> userMap=resultlist.get(0);
              email=(String)userMap.get(DbParamNams.USER_EMAIL);
            }
        return email;
        
    }
	public boolean deleteAccount()
	{
		boolean result=false;
		String updateQuery=DBQueries.deleteAccount;
		parameterNameValueMap.put(DbParamNams.ACCOUNT_ID, parameterMap.get(DbParamNams.ACCOUNT_ID));
		if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap))
			result=true;
		return result;
	}
	
	public void blockAccount() throws Exception
	{
		Boolean result=false;
		String updateQuery=DBQueries.updateQuery;
		parameterNameValueMap.put(DbParamNams.ACCOUNT_ID,parameterMap.get(DbParamNams.ACCOUNT_ID));
		if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap))
			result=true;
		else 
			throw new Exception("Some issue occured while updating the DB.");
		
	}
	
	public int getAccountBalance() 
	{
		
		String getBalanceQuery=DBQueries.getAccountBalance;
		parameterNameValueMap.put(DbParamNams.ACCOUNT_ID, parameterMap.get(DbParamNams.ACCOUNT_ID));
		List<HashMap<String,Object>> resultList = connectionClass.executeSelectQuery(getBalanceQuery, parameterNameValueMap);
		if(resultList.isEmpty()) throw new NoAccountExists();
		int balance=Integer.parseInt((String) resultList.get(0).get(DbParamNams.ACCOUNT_BALANCE));
		return balance;
	}
	
	public Account getAccountDetailsOnAccId()
	{
		String selectQuery=DBQueries.getAccountDetailsOnAccId;
		Account account=null;
		parameterNameValueMap.put(DbParamNams.ACCOUNT_ID, parameterMap.get(DbParamNams.ACCOUNT_ID));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		if(resultlist.isEmpty()){
			throw new NoAccountExists();
		}
		account=this.createAccountObject(resultlist).get(0);
		return account;
	}	

	public List<Account> getAccountListOnUserId()
	{
		String selectQuery=DBQueries.getAccountListOnUserId;
		parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		List<Account> accountList=this.createAccountObject(resultlist);
		return accountList;
	}
	
	public String checkAccountStatus()
	{
		String selectQuery=DBQueries.checkAccountStatus;
		parameterNameValueMap.put(DbParamNams.ACCOUNT_ID, parameterMap.get(DbParamNams.ACCOUNT_ID));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		if(resultlist.isEmpty()) throw new NoAccountExists();
		String status=(String) resultlist.get(0).get(DbParamNams.ACCOUNT_STATUS);
			return status;
	}	
			
	public boolean updateAccountBalance()
	{
		boolean result=false;
		String transStatus = parameterMap.get(DbParamNams.TRANS_STATUS);
		int currentBalance = this.getAccountBalance();
		int transAmount = Integer.parseInt(parameterMap.get(DbParamNams.TRANS_AMOUNT));
		 int updatedBalance=0;
		if(transStatus.equalsIgnoreCase(DbParamNams.APPROVED_VALUE) || transStatus.equalsIgnoreCase(DbParamNams.REJECTED_VALUE)){
			updatedBalance= currentBalance + transAmount;
		}
			if(transStatus.equalsIgnoreCase(DbParamNams.PENDING_VALUE))
			{
			  updatedBalance = currentBalance - transAmount;
			}
		 String updateQuery=DBQueries.updateAccountBalance;
		 parameterNameValueMap.clear();
		 parameterNameValueMap.put(DbParamNams.ACCOUNT_BALANCE, updatedBalance+"");
		 parameterNameValueMap.put(DbParamNams.ACCOUNT_ID, parameterMap.get(DbParamNams.ACCOUNT_ID));
			
		 connectionClass=new ConnectionClass();
		connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap);
		AccountService accountService=new AccountService(parameterMap);
		 if(accountService.getAccountDetailsOnAccId().getAccType().equalsIgnoreCase(DbParamNams.ACCOUNT_TYPE_CREDIT))
		 {
			 CreditService creditService=new CreditService(parameterMap);
			 creditService.updateCreditBalance();
			 result=true;
		 }
		return result;
	}
	public List<Account> createAccountObject(List<HashMap<String,Object>> resultlist){
		List<Account> accountList=null;
		if(resultlist.size()>0)
		{
			accountList=new ArrayList<Account>();
			for(int i=0;i<resultlist.size();i++)
			{
				Account account=new Account();
				HashMap<String,Object> accountMap=resultlist.get(i);
				account.setAccountId(accountMap.get(DbParamNams.ACCOUNT_ID).toString());
				account.setUserId(accountMap.get(DbParamNams.USER_ID).toString());
				account.setAccountStatus(accountMap.get(DbParamNams.ACCOUNT_STATUS).toString());
				account.setAccBalance((String) accountMap.get(DbParamNams.ACCOUNT_BALANCE));
				account.setAccType(accountMap.get(DbParamNams.ACCOUNT_TYPE).toString());
				DateFormat IN_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
				try {
					account.setAccOpenDate(new java.sql.Date( IN_DATE_FORMAT.parse( (String)accountMap.get(DbParamNams.ACCOUNT_OPEN_DATE )).getTime()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				accountList.add(account);
			}
		}
		return accountList;
}
}
