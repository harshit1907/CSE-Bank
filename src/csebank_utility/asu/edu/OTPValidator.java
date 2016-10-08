package csebank_utility.asu.edu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import csebank_dbmodel.asu.edu.PropertiesLoader;

public class OTPValidator {
	private String serialFilePath;
	
	public OTPValidator(){
		PropertiesLoader propertiesLoader=new PropertiesLoader();
		serialFilePath=propertiesLoader.getSERIALIZED_FILE();
	}
	public void createSerializedFile(OTPCollection optCollection)
	{
		try{
		File serialFile= new File(serialFilePath);
		if(!serialFile.exists())
			serialFile.createNewFile();
		FileOutputStream  fileOutputStream=new FileOutputStream(serialFile);
		ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(optCollection);
		objectOutputStream.close();
		fileOutputStream.close();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public OTPCollection loadSerializedFile()
	{
		OTPCollection otpCollection=null;
		
		try{
				FileInputStream  fileInputStream=new FileInputStream(new File(serialFilePath));
				ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
				otpCollection=(OTPCollection) objectInputStream.readObject();
				objectInputStream.close();
				fileInputStream.close();
			}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return otpCollection;
	}

	public boolean getOtpToken(String userId, String receivedOtp)
	{
		boolean flag= false;
		OTPCollection mapOfOtps = loadSerializedFile();
		HashMap<String,String> tempOtp = mapOfOtps.getOtpTokens();
		String otp = tempOtp.get(receivedOtp);
		
		if(otp.equals(receivedOtp)){
			flag=true;
			tempOtp.remove(userId);
		}
		mapOfOtps.setOtpTokens(tempOtp);
		createSerializedFile(mapOfOtps);
		return flag;
	
}

	public void setOtpToken(String userId, String otp)
	{
		OTPCollection mapOfOtps = loadSerializedFile();
		HashMap<String,String> tempOtp = mapOfOtps.getOtpTokens();
		tempOtp.put(userId,otp);
		mapOfOtps.setOtpTokens(tempOtp);
		createSerializedFile(mapOfOtps);
	
}
	
}
 @SuppressWarnings("serial")
class OTPCollection implements Serializable {

	HashMap<String, String> otpTokens = new HashMap<String, String>();

	public HashMap<String, String> getOtpTokens() {
		return otpTokens;
	}

	public void setOtpTokens(HashMap<String, String> otpTokens) {
		this.otpTokens = otpTokens;
	}
		
}
