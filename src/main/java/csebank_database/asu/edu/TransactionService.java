package csebank_database.asu.edu;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import csebank_objectmodel.asu.edu.Transaction;
import csebank_utility.asu.edu.DBQueries;
import csebank_utility.asu.edu.DbParamNams;
import csebank_utility.asu.edu.EmailService;
import csebank_utility.asu.edu.Utility;
import exception.csebank_controllers.asu.edu.NoAccountExists;

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
		String insertQuery=DBQueries.addTransaction1;
		Utility utility=new Utility();
		String transId=utility.loadRandomNumber(8);
		String amount=parameterMap.get(DbParamNams.TRANS_AMOUNT);
		String transOwner=DbParamNams.USER_ROLE_EMPLOYEE;
		String transStatus=DbParamNams.PENDING_VALUE;
		String transType=DbParamNams.TRANS_TYPE_NON_CRITICAL;
		String desActNo=null;
		String transResult="Fail";
		String srcAcntNo=null;
		
		if(parameterMap.containsKey(DbParamNams.TRANS_SRC_EMAIL))
		{
			srcAcntNo=this.getAccountOnEMail(parameterMap.get(DbParamNams.TRANS_SRC_EMAIL));
		}
		else if(parameterMap.containsKey(DbParamNams.TRANS_SRC_PHONE))
		{
			srcAcntNo=this.getAccountOnPhone(parameterMap.get(DbParamNams.TRANS_SRC_PHONE));
		}
		else
			srcAcntNo=parameterMap.get(DbParamNams.TRANS_SRC_ACC_NO);
		
		if(Integer.parseInt(amount)>1000)
		{
			transOwner=DbParamNams.USER_ROLE_MANANEGR;
			transType=DbParamNams.TRANS_TYPE_CRITICAL;
		}
		if(transType.equalsIgnoreCase(DbParamNams.TRANS_TYPE_CRITICAL))
			{
			HashMap<String,String> mailBody=new HashMap<String,String>();
			mailBody.put(DbParamNams.ACCOUNT_ID, srcAcntNo);
			mailBody.put(DbParamNams.TRANS_AMOUNT, amount);
			AccountService accountService=new AccountService(mailBody);
			
			mailBody.put(DbParamNams.USER_EMAIL,accountService.getEmailonAccountId() );
			EmailService newMail = new EmailService();
			newMail.sendCriticalMail(mailBody);
		
			}
		if(parameterMap.containsKey(DbParamNams.TRANS_DEST_EMAIL))
		{
		desActNo=this.getAccountOnEMail(parameterMap.get(DbParamNams.TRANS_DEST_EMAIL));
		}
		else if(parameterMap.containsKey(DbParamNams.TRANS_DEST_PHONE))
		{
		desActNo=this.getAccountOnPhone(parameterMap.get(DbParamNams.TRANS_DEST_PHONE));
		}
		else
		desActNo=parameterMap.get(DbParamNams.TRANS_DEST_ACC_NO);
		
		HashMap<String,String> accountMap=new HashMap<String,String>();
		accountMap.put(DbParamNams.ACCOUNT_ID, srcAcntNo);
		AccountService accountService=new AccountService(accountMap);
		if(accountService.getAccountBalance()>=Integer.parseInt(amount))
			transResult="Success";
		parameterNameValueMap.put(DbParamNams.TRANS_ID,transId);
		parameterNameValueMap.put(DbParamNams.TRANS_TYPE,transType);
		parameterNameValueMap.put(DbParamNams.TRANS_DESC,parameterMap.get(DbParamNams.TRANS_DESC));
		parameterNameValueMap.put(DbParamNams.TRANS_STATUS,transStatus);
		parameterNameValueMap.put(DbParamNams.TRANS_SRC_ACC_NO,srcAcntNo);
		parameterNameValueMap.put(DbParamNams.TRANS_DEST_ACC_NO,desActNo);
		parameterNameValueMap.put(DbParamNams.TRANS_OWNER,transOwner);
		parameterNameValueMap.put(DbParamNams.TRANS_AMOUNT,amount);
		parameterNameValueMap.put(DbParamNams.TRANS_RESULT,transResult);
		parameterNameValueMap.put(DbParamNams.TRANS_COMMENTS,"None");
		parameterNameValueMap.put(DbParamNams.TRANS_APPROVEDBY,"None");
		
		
		connectionClass=new ConnectionClass();
		if(connectionClass.executeUpdateWithSQLQuery(insertQuery, parameterNameValueMap))
		{	result=transId;
		HashMap<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(DbParamNams.ACCOUNT_ID, srcAcntNo);
		paramMap.put(DbParamNams.TRANS_STATUS,transStatus);
		paramMap.put(DbParamNams.TRANS_AMOUNT, parameterMap.get(DbParamNams.TRANS_AMOUNT));
		
		accountService=new AccountService(paramMap);
		accountService.updateAccountBalance();	
		}	
		return result;
		
		
}
	
	public String getAccountOnEMail(String email){
		String selectQuery=DBQueries.addTransaction2;
		LinkedHashMap<String, String> parameterNaValMap=new LinkedHashMap<String, String>();
		parameterNaValMap.put(DbParamNams.USER_EMAIL, email);
		connectionClass=new ConnectionClass();
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNaValMap);
		String actNo=(String) resultlist.get(0).get(DbParamNams.ACCOUNT_ID);
		return actNo;
	}
	public String getAccountOnPhone(String phone){
		String selectQuery=DBQueries.addTransaction3;
		LinkedHashMap<String, String> parameterNaValMap=new LinkedHashMap<String, String>();
		parameterNaValMap.put(DbParamNams.USER_PHONE, phone);
		connectionClass=new ConnectionClass();
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNaValMap);
		String actNo=(String) resultlist.get(0).get(DbParamNams.ACCOUNT_ID);
		return actNo;
		
	}
	public String  addTransactionDepositWithdrw()
	{
		String result=null;
		String insertQuery=DBQueries.addTransaction1;
		Utility utility=new Utility();
		String transId=utility.loadRandomNumber(8);
		String amount=parameterMap.get(DbParamNams.TRANS_AMOUNT);
		String transOwner=DbParamNams.USER_ROLE_EMPLOYEE;
		String transStatus=DbParamNams.PENDING_VALUE;
		String transType=DbParamNams.TRANS_TYPE_NON_CRITICAL;
		String desActNo=null;
		String transResult="Fail";
		String srcAcntNo=parameterMap.get(DbParamNams.TRANS_SRC_ACC_NO);
		if(Integer.parseInt(amount)>1000)
		{
			transOwner=DbParamNams.USER_ROLE_MANANEGR;
			transType=DbParamNams.TRANS_TYPE_CRITICAL;
		}
		if(transType.equalsIgnoreCase(DbParamNams.TRANS_TYPE_CRITICAL)&&!srcAcntNo.equalsIgnoreCase(DbParamNams.TRANS_SRC_SELF))
			{
			HashMap<String,String> mailBody=new HashMap<String,String>();
			mailBody.put(DbParamNams.ACCOUNT_ID, srcAcntNo);
			mailBody.put(DbParamNams.TRANS_AMOUNT, amount);
			AccountService accountService=new AccountService(mailBody);
			
			mailBody.put(DbParamNams.USER_EMAIL,accountService.getEmailonAccountId() );
			EmailService newMail = new EmailService();
			newMail.sendCriticalMail(mailBody);
		
			}
		desActNo=parameterMap.get(DbParamNams.TRANS_DEST_ACC_NO);
		int trnsactionOnDay=this.getTransactionListOnTransTimeStampTransSrcAct(srcAcntNo,desActNo);
		if(srcAcntNo.equalsIgnoreCase(DbParamNams.TRANS_SRC_SELF)&&trnsactionOnDay<10)
			transResult="Success";
		
		if(desActNo.equalsIgnoreCase(DbParamNams.TRANS_SRC_SELF)&&trnsactionOnDay<10)
		{
			HashMap<String,String> accountMap=new HashMap<String,String>();
			accountMap.put(DbParamNams.ACCOUNT_ID, srcAcntNo);
			AccountService accountService=new AccountService(accountMap);
			if(accountService.getAccountBalance()>=Integer.parseInt(amount))
			transResult="Success";
			
		}
		parameterNameValueMap.clear();
		parameterNameValueMap.put(DbParamNams.TRANS_ID,transId);
		parameterNameValueMap.put(DbParamNams.TRANS_TYPE,transType);
		parameterNameValueMap.put(DbParamNams.TRANS_DESC,parameterMap.get(DbParamNams.TRANS_DESC));
		parameterNameValueMap.put(DbParamNams.TRANS_STATUS,transStatus);
		parameterNameValueMap.put(DbParamNams.TRANS_SRC_ACC_NO,srcAcntNo);
		parameterNameValueMap.put(DbParamNams.TRANS_DEST_ACC_NO,desActNo);
		parameterNameValueMap.put(DbParamNams.TRANS_OWNER,transOwner);
		parameterNameValueMap.put(DbParamNams.TRANS_AMOUNT,amount);
		parameterNameValueMap.put(DbParamNams.TRANS_RESULT,transResult);
		parameterNameValueMap.put(DbParamNams.TRANS_COMMENTS,"None");
		parameterNameValueMap.put(DbParamNams.TRANS_APPROVEDBY,"None");
		
		connectionClass=new ConnectionClass();
		if(connectionClass.executeUpdateWithSQLQuery(insertQuery, parameterNameValueMap))
		{	result=transId;
		}	
		return result;
		
		
}
	public Transaction getTransactionOnTransId()
	{
		String selectQuery=DBQueries.getTransactionOnTransId;
		Transaction transaction=null;
		parameterNameValueMap.put(DbParamNams.TRANS_ID, parameterMap.get(DbParamNams.TRANS_ID));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		transaction=this.createTransactionObject(resultlist).get(0);
			return transaction;
		}
	public List<Transaction> getTransactionListOnTransStatus()
	{
		String selectQuery=DBQueries.getTransactionListOnTransStatus;
		parameterNameValueMap.put(DbParamNams.TRANS_STATUS, parameterMap.get(DbParamNams.TRANS_STATUS));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		List<Transaction> transactionsList=this.createTransactionObject(resultlist);
			return transactionsList;
		}
	public List<Transaction> getTransactionListOnTransType()
	{
		String selectQuery=DBQueries.getTransactionListOnTransType;
		parameterNameValueMap.put(DbParamNams.TRANS_TYPE, parameterMap.get(DbParamNams.TRANS_TYPE));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		List<Transaction> transactionsList=this.createTransactionObject(resultlist);
			return transactionsList;
		}
	
	public List<Transaction> getTransactionListOnTransTypeTransfer()
	{
		String selectQuery=DBQueries.getTransactionListOnTransTypeforTransfer;
		parameterNameValueMap.put(DbParamNams.TRANS_TYPE, parameterMap.get(DbParamNams.TRANS_TYPE));
		parameterNameValueMap.put(DbParamNams.TRANS_SRC_ACC_NO, DbParamNams.TRANS_SRC_SELF);
		parameterNameValueMap.put(DbParamNams.TRANS_DEST_ACC_NO, DbParamNams.TRANS_SRC_SELF);
		List<Transaction> transactionsList=new ArrayList<Transaction>();
		
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		if(resultlist!=null)
			 transactionsList=this.createTransactionObject(resultlist);
			return transactionsList;
		}
	public List<Transaction> getTransactionListOnTransTypeforWithdraw()
	{
		String selectQuery=DBQueries.getTransactionListOnTransTypeforWithdraw;
		parameterNameValueMap.put(DbParamNams.TRANS_TYPE, parameterMap.get(DbParamNams.TRANS_TYPE));
		parameterNameValueMap.put(DbParamNams.TRANS_SRC_ACC_NO, DbParamNams.TRANS_SRC_SELF);
		parameterNameValueMap.put(DbParamNams.TRANS_DEST_ACC_NO,DbParamNams.TRANS_SRC_SELF);
		List<Transaction> transactionsList=new ArrayList<Transaction>();
		
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		if(resultlist!=null)
			 transactionsList=this.createTransactionObject(resultlist);
				return transactionsList;
		}
	public List<Transaction> getTransactionListOnAccountId()
    {
        String selectQuery=DBQueries.getTransactionListOnAccountId;
        parameterNameValueMap.put(DbParamNams.TRANS_SRC_ACC_NO, parameterMap.get(DbParamNams.ACCOUNT_ID));
        parameterNameValueMap.put(DbParamNams.TRANS_DEST_ACC_NO, parameterMap.get(DbParamNams.ACCOUNT_ID));
        List<Transaction> transactionsList=new ArrayList<Transaction>();
		
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		if(resultlist!=null)
			 transactionsList=this.createTransactionObject(resultlist);
		   return transactionsList;
    }

	public List<Transaction> getTransactionListOnTransOwner()
	{
		String selectQuery=DBQueries.getTransactionListOnTransOwner;
		parameterNameValueMap.put(DbParamNams.TRANS_OWNER, parameterMap.get(DbParamNams.TRANS_OWNER));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		List<Transaction> transactionsList=this.createTransactionObject(resultlist);
			return transactionsList;
	}
	public List<Transaction> getTransactionListOnTransAprrovedBy()
	{
		String selectQuery=DBQueries.getTransactionListOnTransAprrovedBy;
		parameterNameValueMap.put(DbParamNams.TRANS_APPROVEDBY, parameterMap.get(DbParamNams.TRANS_APPROVEDBY));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		List<Transaction> transactionsList=this.createTransactionObject(resultlist);
			return transactionsList;
	}
	public List<Transaction> getTransactionListOnTransTimeStamp()
	{
		String selectQuery=DBQueries.getTransactionListOnTransTimeStamp;
		List<Transaction> transactionsList=new ArrayList<Transaction>();
		parameterNameValueMap.put(DbParamNams.TRANS_TIMESTAMP, parameterMap.get(DbParamNams.TRANS_TIMESTAMP));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		if(resultlist!=null)
		 transactionsList=this.createTransactionObject(resultlist);
			return transactionsList;
	}
	private static Date localDateTimeToDate(LocalDateTime startOfDay) {
		  return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
		}

	private static LocalDateTime dateToLocalDateTime(Date date) {
		  return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
		}
	public static String getStartOfDay() {
		  Date date = new Date();
		   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			
		  LocalDateTime localDateTime = dateToLocalDateTime(date);
		  LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		  date= localDateTimeToDate(startOfDay);
		  return dateFormat.format(date);
		}
	public int getTransactionListOnTransTimeStampTransSrcAct(String transSrcNo,String desActNo)
	{
		String selectQuery=DBQueries.getTransactionListOnTransTimeStampTransSrcAct;
		int result=0;
		parameterNameValueMap.put(DbParamNams.TRANS_SRC_ACC_NO,transSrcNo);
		parameterNameValueMap.put(DbParamNams.TRANS_DEST_ACC_NO,desActNo);
		parameterNameValueMap.put(DbParamNams.TRANS_TIMESTAMP,getStartOfDay());
		
		connectionClass=new  ConnectionClass();
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		if(resultlist!=null)
			result=resultlist.size();
			return result;
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
				transaction.setTransAmount(Integer.parseInt((String)transMap.get(DbParamNams.TRANS_AMOUNT)));
				transaction.setTransId(Integer.parseInt((String)transMap.get(DbParamNams.TRANS_ID)));
				transaction.setTransDescription((String)transMap.get(DbParamNams.TRANS_DESC));
				transaction.setTransResult((String)transMap.get(DbParamNams.TRANS_RESULT));
				transaction.setTransComments((String)transMap.get(DbParamNams.TRANS_COMMENTS));

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			    java.util.Date parsedDate;
				try {
					parsedDate = dateFormat.parse((String) transMap.get(DbParamNams.TRANS_TIMESTAMP));
				
			    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
				transaction.setTransTimestamp(timestamp);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					transaction.setTransStatus((String)transMap.get(DbParamNams.TRANS_STATUS));
				transaction.setTransSrcAccNo((String)transMap.get(DbParamNams.TRANS_SRC_ACC_NO));
				transaction.setTransDestAccNo((String)transMap.get(DbParamNams.TRANS_DEST_ACC_NO));
				transaction.setTransOwner((String)transMap.get(DbParamNams.TRANS_OWNER));
				transaction.setTransType((String)transMap.get(DbParamNams.TRANS_TYPE));
				transaction.setTransApprovedBy((String)transMap.get(DbParamNams.TRANS_APPROVEDBY));
				transactionsList.add(transaction);
			}
		}
		
		return transactionsList;
	}
	public boolean approveTransaction()
	{
		boolean result=false;
		AccountLogService accountLogService=null;
		HashMap<String,String> paramMap=new HashMap<String,String>();
		String transStatus=parameterMap.get(DbParamNams.TRANS_STATUS);
		String updateQuery=DBQueries.approveTransaction;
		parameterNameValueMap.clear();
		parameterNameValueMap.put(DbParamNams.TRANS_STATUS, transStatus);
		parameterNameValueMap.put(DbParamNams.TRANS_APPROVEDBY, parameterMap.get(DbParamNams.TRANS_APPROVEDBY));
		parameterNameValueMap.put(DbParamNams.TRANS_COMMENTS, parameterMap.get(DbParamNams.TRANS_COMMENTS));
		parameterNameValueMap.put(DbParamNams.TRANS_ID, parameterMap.get(DbParamNams.TRANS_ID));
		if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap))
			result=true;
			if(transStatus.equalsIgnoreCase(DbParamNams.APPROVED_VALUE))
		{
			paramMap.put(DbParamNams.ACCOUNT_ID, parameterMap.get(DbParamNams.TRANS_DEST_ACC_NO));
			paramMap.put(DbParamNams.TRANS_STATUS, parameterMap.get(DbParamNams.TRANS_STATUS));
			paramMap.put(DbParamNams.TRANS_AMOUNT, parameterMap.get(DbParamNams.TRANS_AMOUNT));
			AccountService accountService=new AccountService(paramMap);
			if(accountService.updateAccountBalance())
			result=true;
				paramMap.clear();
			paramMap.put(DbParamNams.TRANS_ID, parameterMap.get(DbParamNams.TRANS_ID));
			paramMap.put(DbParamNams.ACCOUNT_ID, parameterMap.get(DbParamNams.TRANS_DEST_ACC_NO));
			accountLogService=new AccountLogService(paramMap);
			accountLogService.addAccountLogEntry();
		}
		else if(transStatus.equalsIgnoreCase(DbParamNams.REJECTED_VALUE))
		{
			
			
			paramMap.put(DbParamNams.ACCOUNT_ID, parameterMap.get(DbParamNams.TRANS_SRC_ACC_NO));
			paramMap.put(DbParamNams.TRANS_STATUS, parameterMap.get(DbParamNams.TRANS_STATUS));
			paramMap.put(DbParamNams.TRANS_AMOUNT, parameterMap.get(DbParamNams.TRANS_AMOUNT));
			AccountService accountService=new AccountService(paramMap);
			if(accountService.updateAccountBalance())
			result=true;
		}
		paramMap.clear();
		paramMap.put(DbParamNams.TRANS_ID, parameterMap.get(DbParamNams.TRANS_ID));
		paramMap.put(DbParamNams.ACCOUNT_ID, parameterMap.get(DbParamNams.TRANS_SRC_ACC_NO));
		accountLogService=new AccountLogService(paramMap);
		accountLogService.addAccountLogEntry();
	
		
		return result;
	}
	public boolean approveTransactionDepositWithdrw()
	{
		boolean result=false;
		AccountLogService accountLogService=null;
		HashMap<String,String> paramMap=new HashMap<String,String>();
		String transStatus=parameterMap.get(DbParamNams.TRANS_STATUS);
		String updateQuery=DBQueries.approveTransaction;
		parameterNameValueMap.clear();
		parameterNameValueMap.put(DbParamNams.TRANS_STATUS, transStatus);
		parameterNameValueMap.put(DbParamNams.TRANS_APPROVEDBY, parameterMap.get(DbParamNams.TRANS_APPROVEDBY));
		parameterNameValueMap.put(DbParamNams.TRANS_COMMENTS, parameterMap.get(DbParamNams.TRANS_COMMENTS));
		parameterNameValueMap.put(DbParamNams.TRANS_ID, parameterMap.get(DbParamNams.TRANS_ID));
		if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap))
			result=true;
		String srcActnNo=parameterMap.get(DbParamNams.TRANS_SRC_ACC_NO);
		String destActNo=parameterMap.get(DbParamNams.TRANS_DEST_ACC_NO);
				
		if(srcActnNo.equalsIgnoreCase(DbParamNams.TRANS_SRC_SELF)&&transStatus.equalsIgnoreCase(DbParamNams.APPROVED_VALUE))
		{
			paramMap.put(DbParamNams.ACCOUNT_ID,destActNo);
			paramMap.put(DbParamNams.TRANS_AMOUNT, parameterMap.get(DbParamNams.TRANS_AMOUNT));
			AccountService accountService=new AccountService(paramMap);
			if(accountService.updateAccountBalanceDepositWitdrw())
			result=true;
			paramMap.clear();
			paramMap.put(DbParamNams.TRANS_ID, parameterMap.get(DbParamNams.TRANS_ID));
			paramMap.put(DbParamNams.ACCOUNT_ID, parameterMap.get(DbParamNams.TRANS_DEST_ACC_NO));
			accountLogService=new AccountLogService(paramMap);
			accountLogService.addAccountLogEntry();
		}
		else if(destActNo.equalsIgnoreCase(DbParamNams.TRANS_SRC_SELF)&&transStatus.equalsIgnoreCase(DbParamNams.APPROVED_VALUE))
		{
			
			
			paramMap.put(DbParamNams.ACCOUNT_ID, parameterMap.get(DbParamNams.TRANS_SRC_ACC_NO));
			paramMap.put(DbParamNams.TRANS_AMOUNT, "-"+parameterMap.get(DbParamNams.TRANS_AMOUNT));
			AccountService accountService=new AccountService(paramMap);
			if(accountService.updateAccountBalanceDepositWitdrw())
			result=true;
			paramMap.clear();
			paramMap.put(DbParamNams.TRANS_ID, parameterMap.get(DbParamNams.TRANS_ID));
			paramMap.put(DbParamNams.ACCOUNT_ID, parameterMap.get(DbParamNams.TRANS_SRC_ACC_NO));
			accountLogService=new AccountLogService(paramMap);
			accountLogService.addAccountLogEntry();
		}
		
		return result;
	}

	public boolean getByEmail(String email){
			String selectQuery=DBQueries.addTransaction2;
			LinkedHashMap<String, String> parameterNaValMap=new LinkedHashMap<String, String>();
			parameterNaValMap.put(DbParamNams.USER_EMAIL, email);
			List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNaValMap);
			if(resultlist.size() == 0)
				return false;
			else
				return true;
		}
	
	public String getAccountIdByEmail(String email){
		String selectQuery=DBQueries.addTransaction2;
		LinkedHashMap<String, String> parameterNaValMap=new LinkedHashMap<String, String>();
		parameterNaValMap.put(DbParamNams.USER_EMAIL, email);
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNaValMap);
		if(resultlist.size() == 0)
			throw new NoAccountExists("No Account exists for the user.");
		else
			return resultlist.get(0).get(DbParamNams.ACCOUNT_ID).toString();
	}
	
	public boolean getByPhone(String phone){
		String selectQuery=DBQueries.addTransaction3;
		LinkedHashMap<String, String> parameterNaValMap=new LinkedHashMap<String, String>();
		parameterNaValMap.put(DbParamNams.USER_PHONE, phone);
		connectionClass=new ConnectionClass();
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNaValMap);
		if(resultlist.size() == 0)
			return false;
		else
			return true;
	}
	public String getAccountIdByPhone(String phone){
		String selectQuery=DBQueries.addTransaction3;
		LinkedHashMap<String, String> parameterNaValMap=new LinkedHashMap<String, String>();
		parameterNaValMap.put(DbParamNams.USER_PHONE, phone);
		connectionClass=new ConnectionClass();
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNaValMap);
		if(resultlist.size() == 0)
			throw new NoAccountExists("No Account exists for the user.");
		else
			return resultlist.get(0).get(DbParamNams.ACCOUNT_ID).toString();
	}
	
}