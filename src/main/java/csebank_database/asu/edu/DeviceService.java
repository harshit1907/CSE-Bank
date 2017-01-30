package csebank_database.asu.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import csebank_objectmodel.asu.edu.Device;
import csebank_utility.asu.edu.DBQueries;
import csebank_utility.asu.edu.DbParamNams;


public class DeviceService {

	private ConnectionClass connectionClass=null;
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
		String insertQuery=DBQueries.addDevice;
			 parameterNameValueMap.put(DbParamNams.DEVICE_ID, parameterMap.get(DbParamNams.DEVICE_ID));
			 parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
			 if(connectionClass.executeUpdateWithSQLQuery(insertQuery, parameterNameValueMap))
				result=true; 
			 parameterNameValueMap.clear();
		
		return result;
	}
	
	public List<Device> getDeviceDetailsOnUserId()
	{
		 List<Device> deviceList=new ArrayList<Device>();
		 String selectQuery=DBQueries.getDeviceDetailsOnUserId;
		 parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
		 List<HashMap<String,Object>> resultList=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		 for(int i=0;i<resultList.size();i++)
		 {
		 Device device=new Device(resultList.get(0).get(DbParamNams.DEVICE_ID).toString(), resultList.get(0).get(DbParamNams.USER_ID).toString());
		 deviceList.add(device);
		 }
	     parameterNameValueMap.clear();
		 
		return deviceList;
	}
	public Device getDeviceDetailsOnDeviceId() 
	{
		 Device device=null;
		 String selectQuery=DBQueries.getDeviceDetailsOnDeviceId;
		 parameterNameValueMap.put(DbParamNams.DEVICE_ID, parameterMap.get(DbParamNams.DEVICE_ID));
			 List<HashMap<String,Object>> resultList=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
			 device=new Device(resultList.get(0).get(DbParamNams.USER_ID).toString(), resultList.get(0).get(DbParamNams.USER_ID).toString());
		
		 parameterNameValueMap.clear();
		
		return device;
	}
	public boolean checkDeviceDetails()
	{
		 boolean result=false;
		 String selectQuery=DBQueries.checkDeviceDetails;
		 parameterNameValueMap.put(DbParamNams.DEVICE_ID, parameterMap.get(DbParamNams.DEVICE_ID));
		 parameterNameValueMap.put(DbParamNams.USER_ID, parameterMap.get(DbParamNams.USER_ID));
		 List<HashMap<String,Object>> resultList=connectionClass.executeSelectQuery(selectQuery, parameterNameValueMap);
		 for(int i=0;i<resultList.size();i++){
			 if(((resultList.get(0).get(DbParamNams.USER_ID).toString()).equalsIgnoreCase(parameterMap.get(DbParamNams.USER_ID)))&&((resultList.get(0).get(DbParamNams.DEVICE_ID).toString()).equalsIgnoreCase(parameterMap.get(DbParamNams.DEVICE_ID))))
				 result=true;
		 }
		 
		 parameterNameValueMap.clear();
		return result;
	}
	
}