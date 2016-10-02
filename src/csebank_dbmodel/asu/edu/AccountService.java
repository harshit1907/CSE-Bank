package csebank_dbmodel.asu.edu;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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
	public Integer getAcoountBalance()
	{
		String selectQuery="Select AccBalance from Account where AccountId=?";
		parameterNameValueMap.put("AccountId", parameterMap.get("AccountId"));
		List<HashMap<String,Object>> resultlist=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		Integer accBalance=(Integer) resultlist.get(0).get("AccountId");
	return accBalance;
	}
}
