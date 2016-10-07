package csebank_dbmodel.asu.edu;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/*
 * validateCreditTrans - block account if amount > threshold will be done HARSHIT
 */
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
	
	public List<Credit> createCreditObject(List<HashMap<String,Object>> resultlist){
		List<Credit> creditList=null;
		if(resultlist.size()>0)
		{
			creditList=new ArrayList<Credit>();
			for(int i=0;i<resultlist.size();i++)
			{
				Credit credit=new Credit();
				HashMap<String,Object> creditMap=resultlist.get(i);
				credit.setUserId((String)creditMap.get("UserId"));
				credit.setCreditAccId((String)creditMap.get("CreditAccId"));
				credit.setCreditLimit((Integer)creditMap.get("CreditLimit"));
	
				creditList.add(credit);
			}
		}
		
		return creditList;
	}
	
	public String addCreditAccount()
	{
		String result=null;
		String accInsertQuery="INSERT INTO Account(AccountId,UserId,AccType,AccOpenDate,AccBalance,AccStatus) VALUES (?,?,?,?,?,?)";
		String CreditAccInsertQuery="INSERT INTO Credit(UserId,CreditAccId,CreditLimit) VALUES (?,?,?)";		
		String userId = parameterMap.get("UserId");
		String accountId = parameterMap.get("AccountId");
		String accType = "Credit";
		String accOpenDate = parameterMap.get("AccOpenDate");
		String accBalance = parameterMap.get("AccBalance");
		String accStatus = parameterMap.get("AccStatus");
		String creditLimit = "5000";
	
		parameterNameValueMap.put("UserId", userId);
		parameterNameValueMap.put("AccountId", accountId);
		parameterNameValueMap.put("AccType", accType);
		parameterNameValueMap.put("AccOpenDate", accOpenDate);
		parameterNameValueMap.put("AccBalance", accBalance);
		parameterNameValueMap.put("AccStatus", accStatus);
		parameterNameValueMap.put("CreditAccId", accountId);
		parameterNameValueMap.put("CreditLimit", creditLimit);
		
		//inserting into both account and credit tables
		
		connectionClass=new ConnectionClass();
		if(connectionClass.executeUpdateWithSQLQuery(accInsertQuery, parameterNameValueMap) && 
				connectionClass.executeUpdateWithSQLQuery(CreditAccInsertQuery, parameterNameValueMap))
		{	result=userId;
		//deduct in src;
		}	
		
		
		return result;	
	}
	public boolean setCreditLimit(){
		
		String updateQuery="UPDATE Credit SET CreditLimit=? where UserId=?";
		boolean update=false;
		String cl = parameterMap.get("CreditLimit");
		parameterNameValueMap.put("CreditLimit", cl);
		parameterNameValueMap.put("UserId", parameterMap.get("UserId"));
		if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap)){
			update = true;
		}
		return update;
		
	}
	
	public boolean validateCreditTrans(){
		/*
		 *block account if amount > threshold will be done HARSHIT 
		 */
		return true;
	}
	
}
