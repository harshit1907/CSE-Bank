package csebank_utility.asu.edu;


import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;

import csebank_database.asu.edu.ConnectionClass;



public class Encryption {


	public  String  keyFilePathLoader()
	{
		String keyFilePath=null;
	Properties properties=new Properties();
	InputStream inputStream=null;
	String filePath="//home//ubuntu//DB//prop.properties";
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
	
	public static void setPublicKeyPrivateKey() 
	{
		try{
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("DSA");
		keyGenerator.initialize(1024);
		KeyPair keyPair = keyGenerator.generateKeyPair();
		Utility.PRIVATE_KEY = keyPair.getPrivate();
		Utility.PUBLIC_KEY = keyPair.getPublic();
		}
		catch(Exception e)
		{
		System.out.println(e.getMessage());
		}
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
	System.out.println(Encryption.decrypt("/fYWzjKdhNtJRS0IJkFxxPm5bPi7Z66NFmAbLgJA6tA="));
	}
}
