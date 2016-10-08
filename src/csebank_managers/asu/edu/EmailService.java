package csebank_managers.asu.edu;


import java.util.HashMap;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {
	
   public String sendMail(HashMap<String, String> mailBody) {
	   
	   //to, from, subject, message
      String to = mailBody.get("toMail");
      String from = "csebankgroup1@gmail.com";
      String subject = mailBody.get("subject");
      String text = mailBody.get("message");
      
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
}