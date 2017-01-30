package csebank_utility.asu.edu;


import java.util.HashMap;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import csebank_controllers.asu.edu.UserController;

public class EmailService {
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
   public String sendCriticalMail(HashMap<String, String> mailBody) {
	   
	   //to, from, subject, message
      String to = mailBody.get(DbParamNams.USER_EMAIL);
      String from = "csebankgroup1@gmail.com";
      String accno = mailBody.get(DbParamNams.ACCOUNT_ID);
      String transAmount = mailBody.get(DbParamNams.TRANS_AMOUNT);
     
      String subject = "Critical transaction alert for your CSE-Bank account";
      String text = "Dear Customer,\n Thank you for banking with CSE-Bank. \n You have initiated a critical transaction from account " + maskAccNumber(accno) +
    		  		" for fund transfer of $" +  transAmount + ". If you have not made this transaction, please email us at csebankgroup1@gmail.com."+ 
    		  				"NEVER SHARE your Card number, CVV, PIN, OTP, Internet Banking User ID, Password with anyone, even if the caller claims to be a bank employee."+
    		  				"Sharing these details can lead to unauthorised access to your account.\n\nSincerely,\nCSE-Bank Ltd.";
    		  
      
      final String username = "csebankgroup1@gmail.com";
      final String password = "cse545fall2016";

      String host = "smtp.gmail.com";

      Properties prop = new Properties();
      prop.put("mail.smtp.auth", "true");
      prop.put("mail.smtp.starttls.enable", "true");
      prop.put("mail.smtp.host", host);
      prop.put("mail.smtp.port", "587");

      // Get the Session object.
      Session session = Session.getInstance(prop,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
	   }
         });

      try {
	   Message message = new MimeMessage(session);
	
	   // Set From: header field
	   message.setFrom(new InternetAddress(from));
	
	   // Set To: header field 
	   message.setRecipients(Message.RecipientType.TO,
               InternetAddress.parse(to));
	
	   // Set Subject: header field
	   message.setSubject(subject);
	
	   // Set the message
	   message.setText(text);

	   // Send message
	   Transport.send(message);


      } catch (MessagingException e) {
         
    	  logger.error("Mail sending failed.", e);
    	  
    	  return "failure";
      }
      return "success";
   }

 public String sendMail(String email,String subjectLine, String body) {
	   
	   //to, from, subject, message
      String to = email;
      String from = "csebankgroup1@gmail.com";
      
      String subject = subjectLine;
      String text = body;
      
      final String username = "csebankgroup1@gmail.com";
      final String password = "cse545fall2016";

      String host = "smtp.gmail.com";

      Properties prop = new Properties();
      prop.put("mail.smtp.auth", "true");
      prop.put("mail.smtp.starttls.enable", "true");
      prop.put("mail.smtp.host", host);
      prop.put("mail.smtp.port", "587");

      // Get the Session object.
      Session session = Session.getInstance(prop,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
	   }
         });

      try {
	   Message message = new MimeMessage(session);
	
	   // Set From: header field
	   message.setFrom(new InternetAddress(from));
	
	   // Set To: header field 
	   message.setRecipients(Message.RecipientType.TO,
               InternetAddress.parse(to));
	
	   // Set Subject: header field
	   message.setSubject(subject);
	
	   // Set the message
	   message.setText(text);

	   // Send message
	   Transport.send(message);

	   System.out.println("Mail sent....");

      } catch (MessagingException e) {
         return "failure";
      }
      return "success";
   }



public static String maskAccNumber(String accNo) {

	StringBuilder maskedNo = new StringBuilder();
	for (int i = 0; i < accNo.length()-4; i++) {
		maskedNo.append('X');
	} 
	for (int i = accNo.length()-4; i < accNo.length(); i++) {
		maskedNo.append(accNo.charAt(i));
	} 
	// return the masked number
	return maskedNo.toString();
}
}