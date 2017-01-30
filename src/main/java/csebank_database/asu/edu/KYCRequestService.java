package csebank_database.asu.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import csebank_objectmodel.asu.edu.KYCRequest;
import csebank_utility.asu.edu.DBQueries;
import csebank_utility.asu.edu.DbParamNams;
import csebank_utility.asu.edu.Utility;

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
	
	public String addKycReq()
    {
     String result=null;
        Utility utility=new Utility();
        String kycycId=utility.loadRandomNumber(8);
        String insertQuery=DBQueries.addKycReq;
             
             parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
             parameterNameValueMap.put(DbParamNams.REQUEST_FIELD_ID, parameterMap.get(DbParamNams.REQUEST_FIELD_ID));
             parameterNameValueMap.put(DbParamNams.REQUEST_FIELD_VALUE, parameterMap.get(DbParamNams.REQUEST_FIELD_VALUE)); 
             parameterNameValueMap.put(DbParamNams.REQUEST_STATUS, parameterMap.get(DbParamNams.REQUEST_STATUS)); 
             //we don't need this right now
             //parameterNameValueMap.put(DbParamNams.REQUEST_OWNER, parameterMap.get(DbParamNams.REQUEST_OWNER)); 
             parameterNameValueMap.put(DbParamNams.REQUEST_ID,kycycId); 
             if(connectionClass.executeUpdateWithSQLQuery(insertQuery, parameterNameValueMap))
                result=kycycId; 
             parameterNameValueMap.clear();
        
        return result;
    }
	
		 public List<KYCRequest> getPendingKycReq()
		 {
			 parameterNameValueMap.clear();
			 String getQuery=DBQueries.getPendingKycReq;
			 parameterNameValueMap.put(DbParamNams.REQUEST_STATUS,DbParamNams.PENDING_VALUE);
			 List<HashMap<String,Object>> resultList=connectionClass.executeSelectQuery(getQuery,parameterNameValueMap);
			 List<KYCRequest> kyclist=this.createKycRequestObject(resultList);			 
			return kyclist;
			 
		 }
		 public boolean updateStatus()
		 {
			 boolean result=false;
			 String updateQuery=DBQueries.updateStatus;
			 String getStatus=parameterMap.get(DbParamNams.REQUEST_STATUS);
			 parameterNameValueMap.clear();
			 parameterNameValueMap.put(DbParamNams.REQUEST_STATUS, getStatus); 
			 parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
			 parameterNameValueMap.put(DbParamNams.REQUEST_FIELD_VALUE, parameterMap.get(DbParamNams.REQUEST_FIELD_VALUE));
			 if(getStatus.equals(DbParamNams.APPROVED_VALUE))
			 {
				 HashMap<String,String> modifyparameterMap=new HashMap<String, String>();
				 if(parameterMap.get(DbParamNams.REQUEST_FIELD_ID).equalsIgnoreCase("email")){
					 modifyparameterMap.put(DbParamNams.USER_EMAIL, parameterMap.get(DbParamNams.REQUEST_FIELD_VALUE));
				 }
				 else if(parameterMap.get(DbParamNams.REQUEST_FIELD_ID).equalsIgnoreCase("phoneNumber")){
					 modifyparameterMap.put(DbParamNams.USER_PHONE, parameterMap.get(DbParamNams.REQUEST_FIELD_VALUE));
				 }
				 modifyparameterMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
				 
				 UserService userservice=new UserService(modifyparameterMap);
				 userservice.modifyUserDetails();
			 }
			 
			 boolean check = connectionClass.executeUpdateWithSQLQuery(updateQuery, parameterNameValueMap);
			 if(check)
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
				     kycrequest.setFieldValue((String) kycmap.get(DbParamNams.REQUEST_FIELD_VALUE));
				     kycrequest.setStatus((String)kycmap.get(DbParamNams.REQUEST_STATUS));
				     kycrequest.setUserFieldId((String) kycmap.get(DbParamNams.REQUEST_FIELD_ID));             			 
				     kycrequest.setUserId((String) kycmap.get(DbParamNams.USER_ID));
				     kycrequest.setKycid((String) kycmap.get(DbParamNams.REQUEST_ID));
				     kyclist.add(kycrequest);			 }
		 }
			 return kyclist;
	}

}