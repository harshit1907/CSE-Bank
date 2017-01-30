package csebank_utility.asu.edu;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Set;
import java.util.StringTokenizer;


public class Utility {
	private String serialFilePath;
	public static 	PublicKey PUBLIC_KEY=null;
	public static  PrivateKey PRIVATE_KEY=null;

	public Utility(){
		PropertiesLoader propertiesLoader=new PropertiesLoader();
		serialFilePath=propertiesLoader.getSERIALIZED_FILE();
	}

	public void createSerializedFile(SetOfAccountTransID setOfAccountTransID)
	{
		try{
			File serialFile=new File(serialFilePath);
			if(!serialFile.exists())
				serialFile.createNewFile();
			FileOutputStream  fileOutputStream=new FileOutputStream(serialFile);
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
	public static boolean verifyRequest(String userName, String request, PublicKey publicKey){
		boolean result = true;
		try{
			Signature sig = Signature.getInstance("SHA1withDSA");
			sig.initVerify(publicKey);
			sig.update(userName.getBytes());
			result = sig.verify(getBytes(request));			
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		return result;
	}
	 private static byte[] getBytes( String str )
	  {
	   ByteArrayOutputStream bos = new ByteArrayOutputStream();
	   StringTokenizer st = new StringTokenizer( str, "-", false );
	   while( st.hasMoreTokens() )
	   {
	     int i = Integer.parseInt( st.nextToken() );
	     bos.write( ( byte )i );
	   }
	   return bos.toByteArray();
	  }

	public static String getSignedRequest(PrivateKey privateKey, String userName) {
		byte[] realSig = null;
		try{
			Signature dsa = Signature.getInstance("SHA1withDSA");				
			dsa.initSign(privateKey);
				dsa.update(userName.getBytes());
			realSig = dsa.sign();
			
		}catch(Exception ex){
		System.out.println(ex.getMessage());
		}
		return getString(realSig);
	}
	private static String getString( byte[] bytes )
	{
		StringBuffer sb = new StringBuffer();
		for( int i=0; i<bytes.length; i++ )
		{
			byte b = bytes[ i ];
			sb.append( ( int )( 0x00FF & b ) );
			if( i+1 <bytes.length )
			{
				sb.append( "-" );
			}
		}
		return sb.toString();
	}
}

