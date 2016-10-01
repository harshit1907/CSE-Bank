package csebank_dbmodel.asu.edu;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;



public class Encryption {


	public  String  keyFilePathLoader()
	{
		String keyFilePath=null;
	Properties properties=new Properties();
	InputStream inputStream=null;
	String filePath="C:\\Users\\Harshit Kumar\\Desktop\\resumes\\prop.properties";
	try{
		inputStream=new FileInputStream(filePath);
		if(inputStream!=null)
		{
		properties.load(inputStream);
		keyFilePath=properties.getProperty("KEY_FILE_PATH");
		inputStream.close();
		}
	}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	return keyFilePath;
	}
	
	public static String encrypt(String plainText) {
		String encryptedText=null;
		
		try{
			SecretKey secretKey=KeyLoader.keyLoad(new Encryption().keyFilePathLoader());
			Cipher aesCipher=Cipher.getInstance("AES");
			byte byteText[]=plainText.getBytes("UTF-8");
			aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte encryptedByte[]=aesCipher.doFinal(byteText);
			encryptedText=Base64.encodeBase64String(encryptedByte);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedText;
	}
	
	public static String decrypt(String encryptedText)
	{
		String plainText=null;
		new Encryption().keyFilePathLoader();
		try{
			SecretKey secretKey=KeyLoader.keyLoad(new Encryption().keyFilePathLoader());
			Cipher aesCipher=Cipher.getInstance("AES");
			aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte cipherByte[]=Base64.decodeBase64(encryptedText);
			byte plainTextByte[]=aesCipher.doFinal(cipherByte);
			plainText=new String(plainTextByte);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return plainText;
	}


	public static void main(String args[]) throws Exception
	{
	}
}
