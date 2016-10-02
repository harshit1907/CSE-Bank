package csebank_dbmodel.asu.edu;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class TransactionService {

	private ConnectionClass connectionClass=null;
	private HashMap<String,String> parameterMap;
	private LinkedHashMap<String, String> parameterNameValueMap;
	
	public TransactionService(HashMap<String,String> parameterMap)
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
	public String  addTransaction()
	{
		String result=null;
		String insertQuery="INSERT INTO Transaction(TransTimestamp,TransId,TransType,TransDescription,TransStatus,TransSrcAccNo,TransDestAccNo,TransOwner,TransAmount,TransResult) VALUES (Now(),?,?,?,?,?,?,?,?,?)";
		Utility utility=new Utility();
		String transId=utility.loadRandomNumber(10);
		parameterNameValueMap.put("TransId", transId);
		String amount=parameterMap.get("TransAmount");
		String transOwner="Internal";
		String transStatus="Pending";
		String transType="Non-Critical";
		String desActNo=null;
		String transResult="Fail";
		String srcAcntNo=parameterMap.get("TransSrcAccNo");
		if(Integer.parseInt(amount)>1000)
		{
			transOwner="Admin";
			transType="Critical";
		}
		if(parameterMap.containsKey("TransDestEmail"))
		{
			String selectQuery="SELECT AccountId FROM Account WHERE UserId=(SELECT UserId FROM User WHERE Email=?);";
			LinkedHashMap<String, String> parameterNaValMap=new LinkedHashMap<String, String>();
			parameterNaValMap.put("Email", parameterMap.get("TransDestEmail"));
			List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNaValMap);
			desActNo=(String) resultlist.get(0).get("AccountId");
		}
		if(parameterMap.containsKey("TransDestPhone"))
		{
			String selectQuery="SELECT AccountId FROM Account WHERE UserId=(SELECT UserId FROM User WHERE Phone=?);";
			LinkedHashMap<String, String> parameterNaValMap=new LinkedHashMap<String, String>();
			parameterNaValMap.put("Phone", parameterMap.get("TransDestPhone"));
			connectionClass=new ConnectionClass();
			List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNaValMap);
			desActNo=(String) resultlist.get(0).get("AccountId");
		}
		else
		desActNo=parameterMap.get("TransDestAccNo");
		
		HashMap<String,String> accountMap=new HashMap<String,String>();
		accountMap.put("AccountId", srcAcntNo);
		AccountService accountService=new AccountService(accountMap);
		if(accountService.getAcoountBalance()>=Integer.parseInt(amount))
			transResult="Success";
		
		parameterNameValueMap.put("TransId",transId);
		parameterNameValueMap.put("TransType",transType);
		parameterNameValueMap.put("TransDescription",parameterMap.get("TransDescription"));
		parameterNameValueMap.put("TransStatus",transStatus);
		parameterNameValueMap.put("TransSrcAccNo",srcAcntNo);
		parameterNameValueMap.put("TransDestAccNo",desActNo);
		parameterNameValueMap.put("TransOwner",transOwner);
		parameterNameValueMap.put("TransAmount",amount);
		parameterNameValueMap.put("TransResult",transResult);
		
		connectionClass=new ConnectionClass();
		if(connectionClass.executeUpdateWithSQLQuery(insertQuery, parameterNameValueMap))
		{	result=transId;
		//deduct in src;
		}	
		return result;
		
		
}
	public Transaction getTransactionOnTransId()
	{
		String selectQuery="SELECT * FROM Transaction where TransId=?";
		Transaction transaction=null;
		parameterNameValueMap.put("TransId", parameterMap.get("TransId"));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		transaction=this.createTransactionObject(resultlist).get(0);
			return transaction;
		}
	public List<Transaction> getTransactionListOnTransStatus()
	{
		String selectQuery="SELECT * FROM Transaction where TransStatus=?";
		parameterNameValueMap.put("TransStatus", parameterMap.get("TransStatus"));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		List<Transaction> transactionsList=this.createTransactionObject(resultlist);
			return transactionsList;
		}
	public List<Transaction> getTransactionListOnTransType()
	{
		String selectQuery="SELECT * FROM Transaction where TransType=?";
		parameterNameValueMap.put("TransType ", parameterMap.get("TransType"));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		List<Transaction> transactionsList=this.createTransactionObject(resultlist);
			return transactionsList;
		}
	
	public List<Transaction> getTransactionListOnUserId()
	{
		String selectQuery="SELECT * FROM Transaction where UserId=?";
		parameterNameValueMap.put("UserID", parameterMap.get("UserId"));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		List<Transaction> transactionsList=this.createTransactionObject(resultlist);
			return transactionsList;
	}
	public List<Transaction> getTransactionListOnTransOwner()
	{
		String selectQuery="SELECT * FROM Transaction where TransOwner=?";
		parameterNameValueMap.put("TransOwner", parameterMap.get("TransOwner"));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		List<Transaction> transactionsList=this.createTransactionObject(resultlist);
			return transactionsList;
	}
	public List<Transaction> getTransactionListOnTransAprrovedBy()
	{
		String selectQuery="SELECT * FROM Transaction where TransOwner=?";
		parameterNameValueMap.put("TransApprovedBy", parameterMap.get("TransApprovedBy"));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		List<Transaction> transactionsList=this.createTransactionObject(resultlist);
			return transactionsList;
	}
	public List<Transaction> getTransactionListOnTransTimeStamp()
	{
		String selectQuery="SELECT * FROM Transaction where TransTimestamp>=?";
		parameterNameValueMap.put("TransTimestamp", parameterMap.get("TransTimestamp"));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		List<Transaction> transactionsList=this.createTransactionObject(resultlist);
			return transactionsList;
	}
	public List<Transaction> createTransactionObject(List<HashMap<String,Object>> resultlist){
		List<Transaction> transactionsList=null;
		if(resultlist.size()>0)
		{
			transactionsList=new ArrayList<Transaction>();
			for(int i=0;i<resultlist.size();i++)
			{
				Transaction transaction=new Transaction();
				HashMap<String,Object> transMap=resultlist.get(i);
				transaction.setTransAmount((Integer)transMap.get("TransAmount"));
				transaction.setTransId((Integer)transMap.get("TransId"));
				transaction.setTransDescription((String)transMap.get("TransDescription"));
				transaction.setTransResult((String)transMap.get("TransResult"));
				transaction.setTransComments((String)transMap.get("TransComments"));
				transaction.setTransTimestamp((Timestamp) transMap.get("TransTimestamp"));
				transaction.setTransStatus((String)transMap.get("TransStatus"));
				transaction.setTransSrcAccno((String)transMap.get("TransSrcAccNo"));
				transaction.setTransDestAccno((String)transMap.get("TransDestAccNo"));
				transaction.setTransOwner((String)transMap.get("TransOwner"));
				transaction.setTransType((String)transMap.get("TransType"));
				transaction.setTransApprovedBy((String)transMap.get("TransApprovedBy"));
				transactionsList.add(transaction);
			}
		}
		
		return transactionsList;
	}
	public boolean approveTransaction()
	{
		boolean result=false;
		String transStatus=parameterMap.get("TransStatus");
		String updateQuery="UPDATE Transaction SET TransTimestamp=Now(),TransStatus=?,TransApprovedBy=?,TransComments=? where TransId=?";
		if(transStatus.equalsIgnoreCase("Approved"))
		{
		//add in dest	
		}
		else if(transStatus.equalsIgnoreCase("Rejected"))
		{
			//add in src
		}
		parameterNameValueMap.put("TransStatus", transStatus);
		parameterNameValueMap.put("TransApprovedBy", parameterMap.get("TransApprovedBy"));
		parameterNameValueMap.put("TransComments", parameterMap.get("TransComments"));
		parameterNameValueMap.put("TransId", parameterMap.get("TransId"));
		if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap))
			result=true;
		
		return result;
	}

}