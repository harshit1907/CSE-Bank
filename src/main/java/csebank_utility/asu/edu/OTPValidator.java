package csebank_utility.asu.edu;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import csebank_database.asu.edu.OtpTabService;
import exception.csebank_controllers.asu.edu.CustomInvalidArgumetException;


public class OTPValidator {

	public String sendOTP(String userId, String phoneNumber) throws Exception {

		String otp= generateOTP();
		HashMap <String,String> otpTabMap = new HashMap<String, String>();
		otpTabMap.put(DbParamNams.USER_ID, userId);
		otpTabMap.put(DbParamNams.OTP, otp);

		OtpTabService otpdb = new OtpTabService(otpTabMap); 
		otpdb.addOtp();

		String message= "Dear Customer, One Time Password(OTP) to confirm your request is "+ otp;

		CloseableHttpClient httpClient = null;
		try {
			httpClient = HttpClientBuilder.create().build();	
			// specify the post request
			HttpPost postRequest = new HttpPost("http://textbelt.com/text");

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("number", phoneNumber));
			urlParameters.add(new BasicNameValuePair("message", message));

			postRequest.setEntity(new UrlEncodedFormEntity(urlParameters));
			HttpResponse httpResponse = httpClient.execute(postRequest);
			
			System.out.println(httpResponse.getStatusLine());

			StatusLine status = httpResponse.getStatusLine();
			int statusCode= status.getStatusCode();
			if(statusCode==200)
				return "success";

		} catch (Exception e) {
			throw new CustomInvalidArgumetException("Unable to send OTP to mobile, please check the mobile number.");
		} 
		finally {
			try {
				if(httpClient!=null)
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
		return "failure";
	}	



	public void validateOTP(String userId, String otp) throws Exception{

		HashMap <String,String> otpTabMap = new HashMap<String, String>();
		otpTabMap.put(DbParamNams.USER_ID, userId);
		otpTabMap.put(DbParamNams.OTP, otp);

		OtpTabService otpdb = new OtpTabService(otpTabMap); 
		if(!otpdb.validateOtp()){
			throw new CustomInvalidArgumetException("OTP does not match.");
		}
	}



	static String generateOTP()
	{
		SecureRandom secureRandom=new SecureRandom();
		StringBuffer stringBuffer=new StringBuffer(6);
		for(int i=0;i<6;i++)
		{
			int number=secureRandom.nextInt(10);
			stringBuffer.append((char)('0'+number));
		}
		return stringBuffer.toString();
	}

}
