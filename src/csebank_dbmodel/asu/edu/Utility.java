package csebank_dbmodel.asu.edu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Set;

public class Utility {
	private String serialFilePath;
	public Utility(){
		PropertiesLoader propertiesLoader=new PropertiesLoader();
		serialFilePath=propertiesLoader.getSERIALIZED_FILE();
	}

	public void createSerializedFile(SetOfAccountTransID setOfAccountTransID)
	{
		try{
		FileOutputStream  fileOutputStream=new FileOutputStream(new File(serialFilePath));
		ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(setOfAccountTransID);
		objectOutputStream.close();
		fileOutputStream.close();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public SetOfAccountTransID loadSerializedFile()
	{
		SetOfAccountTransID setOfAccountTransID=null;
		try{
		FileInputStream  fileInputStream=new FileInputStream(new File(serialFilePath));
		ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
		setOfAccountTransID=(SetOfAccountTransID) objectInputStream.readObject();
		objectInputStream.close();
		fileInputStream.close();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return setOfAccountTransID;
	}
	
	public StringBuffer createRandomNumber(int length)
	{
		SecureRandom secureRandom=new SecureRandom();
		StringBuffer stringBuffer=new StringBuffer(length);
		for(int i=0;i<length;i++)
		{
			int number=secureRandom.nextInt(10);
			stringBuffer.append((char)('0'+number));
		}
		return stringBuffer;
	}
	public String loadRandomNumber(int length)
	{
		SetOfAccountTransID setOfAccountTransID=loadSerializedFile();
		Set<String> idSet=setOfAccountTransID.getIdSet();
		String number=createRandomNumber(length).toString();
		while(idSet.contains(number))
			number=createRandomNumber(length).toString();	
		idSet.add(number);
		setOfAccountTransID.setIdSet(idSet);
		
		createSerializedFile(setOfAccountTransID);
		return number;
	
}
}
	@SuppressWarnings("serial")
	class SetOfAccountTransID implements Serializable{
		Set<String> idSet;

		public Set<String> getIdSet() {
			return idSet;
		}

		public void setIdSet(Set<String> idSet) {
			this.idSet = idSet;
		}
	
	}
