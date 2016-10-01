package csebank_dbmodel.asu.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class DeviceService {

	ConnectionClass connectionClass=null;
	private HashMap<String,String> parameterMap;
	private LinkedHashMap<String, String> parameterNameValueMap;
	
	public DeviceService(HashMap<String,String> parameterMap)
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
	
	
	public boolean addDevice()
	{
		boolean result=false;
		String insertQuery="INSERT INTO DEVICES(DeviceId,UserId) VALUES (?,?);";
			 parameterNameValueMap.put("DeviceId", parameterMap.get("DeviceId"));
			 parameterNameValueMap.put("UserId", parameterMap.get("UserId"));
			 if(connectionClass.executeUpdateWithSQLQuery(insertQuery, parameterNameValueMap))
				result=true; 
			 parameterNameValueMap.clear();
		
		return result;
	}
	
	public List<Device> getDeviceDetailsOnUserId()
	{
		 List<Device> deviceList=new ArrayList<Device>();
		 String selectQuery="SELECT UserId,DeviceId from DEVICES where UserId=?;";
		 parameterNameValueMap.put("UserId", parameterMap.get("UserId"));
		 List<HashMap<String,Object>> resultList=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		 for(int i=0;i<resultList.size();i++)
		 {
		 Device device=new Device(Encryption.decrypt(resultList.get(0).get("UserId").toString()), resultList.get(0).get("UserId").toString());
		 deviceList.add(device);
		 }
	     parameterNameValueMap.clear();
		 
		return deviceList;
	}
	public Device getDeviceDetailsOnDeviceId() 
	{
		 Device device=null;
		 String selectQuery="SELECT UserId,DeviceId from DEVICES where DeviceId=?;";
		 parameterNameValueMap.put("DeviceId", parameterMap.get("DeviceId"));
		 List<HashMap<String,Object>> resultList=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
			 device=new Device(Encryption.decrypt(resultList.get(0).get("UserId").toString()), resultList.get(0).get("UserId").toString());
		
		 parameterNameValueMap.clear();
		
		return device;
	}
	public boolean checkDeviceDetails()
	{
		 boolean result=false;
		 String selectQuery="SELECT UserId,DeviceId from DEVICES where DeviceId=? and UserId=?;";
		 parameterNameValueMap.put("DeviceId", parameterMap.get("DeviceId"));
		 parameterNameValueMap.put("UserId", parameterMap.get("UserId"));
		 List<HashMap<String,Object>> resultList=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		 if(resultList.size()==1)
		 result=true;
	    		 parameterNameValueMap.clear();
		return result;
	}
	
	
	
}
@SuppressWarnings("serial")
class ParameterMapNullException extends Exception{
	public ParameterMapNullException(){
		
	}
	public ParameterMapNullException(String message)
	{
		super(message);
	}
}
