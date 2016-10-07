package csebank_dbmodel.asu.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class KYCRequestService {
 ConnectionClass connectionClass=null;
	private HashMap<String,String> parameterMap;
	private LinkedHashMap<String, String> parameterNameValueMap;
	public  KYCRequestService(HashMap<String,String> parameterMap)
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
		 public boolean addKycReq()
			{
				boolean result=false;
				String insertQuery="INSERT INTO kycrequest(UserfieldId,UserId,FieldValue,Status) VALUES (?,?,?,?);";
					 parameterNameValueMap.put("UserfieldId", parameterMap.get("UserfieldId"));
					 parameterNameValueMap.put("UserId", parameterMap.get("UserId"));
					 parameterNameValueMap.put("FieldValue", parameterMap.get("FieldValue")); 
					 parameterNameValueMap.put("Status", parameterMap.get("Status")); 
					 if(connectionClass.executeUpdateWithSQLQuery(insertQuery, parameterNameValueMap))
						result=true; 
					 parameterNameValueMap.clear();
				
				return result;
			}
		 public List<KYCRequest> getPendingKycReq()
		 {
			 String getQuery="Select UserfieldId,UserId,FieldValue,Status from KYCRequest where status=?;";
			 parameterNameValueMap.put("Status","pending");
			 List<HashMap<String,Object>> resultList=connectionClass.executeSelectQuery(getQuery,parameterNameValueMap);
			 List<KYCRequest> kyclist=this.createKycRequestObject(resultList);
			return kyclist;
			 
		 }
		 public boolean updateStatus()
		 {
			 boolean result=false;
			 String updateQuery="update KYCRequest set Status=? where UserId=?;";
			 parameterNameValueMap.put("UserId", parameterMap.get("UserId"));
			 parameterNameValueMap.put("Status", parameterMap.get("Status")); 
			 if(connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap))
					result=true;
				
				return result;
			 
		 }
		 public List<KYCRequest> createKycRequestObject(List<HashMap<String,Object>> resultlist)
		 {
			 List<KYCRequest> kyclist=null;
			 if(resultlist.size()>0)
			 {
				 kyclist=new ArrayList<KYCRequest>();
				 for(int i=0;i<resultlist.size();i++)
					{
				     HashMap<String,Object> kycmap=resultlist.get(i);
				     KYCRequest kycrequest=new KYCRequest();
				     kycrequest.setFieldValue((String) kycmap.get("FieldValue"));
				     kycrequest.setStatus((String)kycmap.get("status"));
				     kycrequest.setUserfieldId((String) kycmap.get("UserfieldId"));             			 
				     kycrequest.setUserId((String) kycmap.get("UserId"));
				     kyclist.add(kycrequest);			 }
		 }
			 return kyclist;
	}

}

