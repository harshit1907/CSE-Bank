package csebank_dbmodel.asu.edu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class CreditIntrestAndSavingIntrest {

	private ConnectionClass connectionClass=null;
	private LinkedHashMap<String, String> parameterNameValueMap;
	
	public CreditIntrestAndSavingIntrest()
	{
		parameterNameValueMap=new LinkedHashMap<String,String>();
	}
	public HashMap<String,Integer> getSavingAndActiveAccountId(String AccType,String AccStatus)
	{
		 connectionClass=new ConnectionClass();
		 String selectQuery="SELECT AccountId,AccBalance from Account where AccStatus =? AND AccType =?";
			parameterNameValueMap.put("AccStatus",AccStatus );
			parameterNameValueMap.put("AccType",AccType );
		 List<HashMap<String,Object>> resultList=connectionClass.executeSelectQuery(selectQuery, null);
		 HashMap<String,Integer> accountMap=null;
		 if(!resultList.isEmpty())
		 {
			 accountMap=new HashMap<String,Integer>();
		 for(int i=0;i<resultList.size();i++)
			 accountMap.put((String) resultList.get(i).get("AccountId"),(Integer) resultList.get(i).get("AccBalance"));
		}
		 parameterNameValueMap.clear();
		 return accountMap;
	}
	public void generateSavingInterestTransaction()
	{
		HashMap<String,Integer> accountMap=getSavingAndActiveAccountId("Active","Savings");
		String insertQueryTransaction="INSERT INTO TRANSACTION SELECT ? AS TransId, 'Non-Critical' AS TransType,'1% monthly interest credit' AS TransDescription,'Approved' AS TransStatus,	'123KINGKONG' AS TransSrcAccNo,	? AS TransDestAccNo,'Admin' AS TransOwner,‘2016-01-01 00:00:01' AS TransTimestamp,'Aadmin AS TransApprovedBy,	(A.AccBalance * 1) / 100 AS TransAmount,'Monthly Interest credit' AS TransComments,'Success' AS TransResult FROM Account A WHERE A.AccountId =?";
		String insertQueryAccountLog="INSERT INTO ACCOUNTLOG(AccountId,TransId,AccountBalance) VALUES(?,?,>)";		
		Set<String> accountSet=accountMap.keySet();
		Iterator<String> iterator=accountSet.iterator();
		while(iterator.hasNext())	{
			connectionClass=new ConnectionClass();
			String transId=new Utility().loadRandomNumber(10);
			String accountId=iterator.next();
			Integer amount=accountMap.get(accountId);
			parameterNameValueMap.put("TransId",transId );
			parameterNameValueMap.put("AccountId",accountId );
			parameterNameValueMap.put("AccountId",accountId );
			connectionClass.executeUpdateWithSQLQuery(insertQueryTransaction, parameterNameValueMap);
			parameterNameValueMap.clear();
			connectionClass=new ConnectionClass();
			parameterNameValueMap.put("AccountId", accountId);
			parameterNameValueMap.put("TransId", transId);
			parameterNameValueMap.put("AccountBalance",(amount*.01)+"");
			connectionClass.executeUpdateWithSQLQuery(insertQueryAccountLog, parameterNameValueMap);
			parameterNameValueMap.clear();
			
		
	}
		String updateQuery="UPDATE Account A SET A.AccBalance = A.AccBalance+((A.AccBalance*1)/100) WHERE A.AccStatus = 'Active' AND A.AccType = 'Savings'";
		connectionClass=new ConnectionClass();
		connectionClass.executeUpdateWithSQLQuery(updateQuery, null);
		
	}
	public void generateCreditInterestTransaction()
	{
		HashMap<String,Integer> accountMap=getSavingAndActiveAccountId("Active","Credit");
		String insertQueryTransaction="INSERT INTO TRANSACTION SELECT ? AS TransId, 'Non-Critical' AS TransType,'2% monthly interest credit' AS TransDescription,'Approved' AS TransStatus,	'123KINGKONG' AS TransSrcAccNo,	? AS TransDestAccNo,'Admin' AS TransOwner,‘2016-01-01 00:00:01' AS TransTimestamp,'Aadmin AS TransApprovedBy,	(A.AccBalance * 1) / 100 AS TransAmount,'Monthly Interest credit' AS TransComments,'Success' AS TransResult FROM Account A WHERE A.AccountId =?";
		String insertQueryAccountLog="INSERT INTO ACCOUNTLOG(AccountId,TransId,AccountBalance) VALUES(?,?,>)";		
		Set<String> accountSet=accountMap.keySet();
		Iterator<String> iterator=accountSet.iterator();
		while(iterator.hasNext())	{
			connectionClass=new ConnectionClass();
			String transId=new Utility().loadRandomNumber(10);
			String accountId=iterator.next();
			Integer amount=accountMap.get(accountId);
			parameterNameValueMap.put("TransId",transId );
			parameterNameValueMap.put("AccountId",accountId );
			parameterNameValueMap.put("AccountId",accountId );
			connectionClass.executeUpdateWithSQLQuery(insertQueryTransaction, parameterNameValueMap);
			parameterNameValueMap.clear();
			connectionClass=new ConnectionClass();
			parameterNameValueMap.put("AccountId", accountId);
			parameterNameValueMap.put("TransId", transId);
			parameterNameValueMap.put("AccountBalance",(amount*.01)+"");
			connectionClass.executeUpdateWithSQLQuery(insertQueryAccountLog, parameterNameValueMap);
			parameterNameValueMap.clear();
			
		
	}
		String updateQueryAccount="UPDATE Account A SET A.AccBalance = A.AccBalance+((A.AccBalance*2)/100) WHERE A.AccStatus = 'Active' AND A.AccType = 'Credit'";
		connectionClass=new ConnectionClass();
		connectionClass.executeUpdateWithSQLQuery(updateQueryAccount, null);
		
		String updateQueryCredit="UPDATE Credit C SET C.CreditBalance = C.CreditBalance - (((C.CreditLimit-C.CreditBalance)*2)/100)";
		connectionClass=new ConnectionClass();
		connectionClass.executeUpdateWithSQLQuery(updateQueryCredit, null);
		
	}
	
}
