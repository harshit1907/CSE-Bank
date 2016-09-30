package csebank_dbmodel.asu.edu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;


public class KeyLoader {
	
	public static void keyGenerate(String path){
		try{
		 File keyFile=new File(path);
		 KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
		 SecretKey secretKey=keyGenerator.generateKey();
		 byte keyByte[]=secretKey.getEncoded();
		 char hexKey[]=Hex.encodeHex(keyByte);
		 String keyString=String.valueOf(hexKey);
		 if(!keyFile.exists())
			 keyFile.createNewFile();
		
		 FileWriter fileWriter=new FileWriter(keyFile.getAbsoluteFile());
		 BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
		 bufferedWriter.write(keyString);	
		 bufferedWriter.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static SecretKey keyLoad(String path)
	{
		SecretKey secretKey=null;
		try{
		 File keyFile=new File(path);
		 FileReader fileReader=new FileReader(keyFile.getAbsoluteFile());
		 BufferedReader bufferedReader=new BufferedReader(fileReader);
		 String key=bufferedReader.readLine();
		 char decodeHex[]=key.toCharArray();
		 byte byteKey[]=Hex.decodeHex(decodeHex);
		 bufferedReader.close();
		 secretKey=new SecretKeySpec(byteKey,"AES");
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		return secretKey;
	}
}
