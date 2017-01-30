package csebank_database.asu.edu;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import csebank_utility.asu.edu.DBQueries;
import csebank_utility.asu.edu.DbParamNams;


public class CreditService {
	private ConnectionClass connectionClass=null;
	private HashMap<String,String> parameterMap;
	private LinkedHashMap<String, String> parameterNameValueMap;
	
	public CreditService(HashMap<String,String> parameterMap)
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
	
	
	
	public boolean addCreditAccount()
	   {
	       boolean result=false;
	       String CreditAccInsertQuery=DBQueries.addCreditAccount_CreditAccInsertQuery;
	       
	       String userId = parameterMap.get(DbParamNams.USER_ID);
	   
	       parameterNameValueMap.put(DbParamNams.USER_ID, userId);
	       parameterNameValueMap.put(DbParamNams.CREDIT_ACCOUNT_ID, parameterMap.get(DbParamNams.CREDIT_ACCOUNT_ID));
	       parameterNameValueMap.put(DbParamNams.CREDIT_LIMIT, DbParamNams.CREDIT_LIMIT_VALUE.toString());
	       parameterNameValueMap.put(DbParamNams.CREDIT_BALANCE, DbParamNams.CREDIT_LIMIT_VALUE.toString());
	      //inserting into credit tables
	       connectionClass=new ConnectionClass();
	       if(connectionClass.executeUpdateWithSQLQuery(CreditAccInsertQuery, parameterNameValueMap))
	       {    result=false;
	       }    
	       
	       return result;    
	   }
	   public boolean setCreditLimit(){
	       
	       String updateQuery=DBQueries.setCreditLimit;
	       boolean update=false;
	       parameterNameValueMap.put(DbParamNams.CREDIT_LIMIT,parameterMap.get(DbParamNams.CREDIT_LIMIT));
	       parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
	       if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap)){
	           update = true;
	       }
	       return update;   
	   }
	   
	   public Integer getCreditBalance()
	    {
	       String selectQuery = DBQueries.getCreditBalance;
	       parameterNameValueMap.put(DbParamNams.CREDIT_ACCOUNT_ID, parameterMap.get(DbParamNams.ACCOUNT_ID));
	        List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
	        Integer accBalance=Integer.parseInt(resultlist.get(0).get(DbParamNams.CREDIT_BALANCE).toString());
	        return accBalance;
	    }
	   
	   public boolean updateCreditBalance() {
	      
	       boolean update=false;
	       int transAmount = Integer.parseInt(parameterMap.get(DbParamNams.TRANS_AMOUNT));
	       int currentBalance = this.getCreditBalance();
	       int updatedBalance = currentBalance - transAmount;
	       parameterNameValueMap.clear();
	        String updateQuery = DBQueries.updateCreditBalance;
	        parameterNameValueMap.put(DbParamNams.CREDIT_BALANCE, Integer.toString(updatedBalance));
	        parameterNameValueMap.put(DbParamNams.CREDIT_ACCOUNT_ID, parameterMap.get(DbParamNams.ACCOUNT_ID));
	        connectionClass=new ConnectionClass();
	        if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap))
	            update=true;
	       return update;   
	   }

	   public boolean validateCreditTrans() {
	       boolean result=false;
	       int transAmount = Integer.parseInt(parameterMap.get(DbParamNams.TRANS_AMOUNT));
	       int currentBalance = this.getCreditBalance();
	       if(transAmount<=currentBalance) 
	           result = true;
	       return result;
	   }
}
