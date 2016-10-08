package csebank_managers.asu.edu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import csebank_utility.asu.edu.OTPValidator;

public class OTPService {

	 public String sendOTP(String userId, String phoneNumber) {
		 
		 String otp= getOTP();
		 OTPValidator otpValidation = new OTPValidator();
		 otpValidation.setOtpToken(userId,otp); 
		 
		 String message= "Dear Customer, One Time Password(OTP) to confirm your request is "+ otp;
		 
		 CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		    try {
		      
		      // specify the post request
		      HttpPost postRequest = new HttpPost("http://textbelt.com/text");

		      List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		      urlParameters.add(new BasicNameValuePair("number", phoneNumber));
		      urlParameters.add(new BasicNameValuePair("message", message));

		      postRequest.setEntity(new UrlEncodedFormEntity(urlParameters));
		      HttpResponse httpResponse = httpClient.execute(postRequest);
		      HttpEntity entity = httpResponse.getEntity();

		      
		      System.out.println(httpResponse.getStatusLine());
		      
		      StatusLine status = httpResponse.getStatusLine();
		      int statusCode= status.getStatusCode();
		      if(statusCode==200)
		    		return "success";
		    	
		    } catch (Exception e) {
		      return "failure";
		    } 
		    finally {
		      try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
		return "failure";
	}	
	
	

	String validateOTP(String userId, String otp){
		 OTPValidator otpValidation = new OTPValidator();
		 boolean valid = otpValidation.getOtpToken(userId, otp); 
		if(valid==true){
			return "success";
		}
		else 
			return "failure";
	}
		
		
		String getOTP()
		{
			 String OTP= "";
			Random rand=new Random();
		     	
		    for (int i=0; i<=5; i++){
		      		int randomNum = rand.nextInt(9 - 0) + 1;
		      		OTP= OTP + randomNum;
		    	}
		     return(OTP);
		  }
		 
	}
