package csebank_controllers.asu.edu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import csebank_database.asu.edu.PdfGenerator;
import csebank_utility.asu.edu.Utility;
import exception.csebank_controllers.asu.edu.CustomInvalidArgumetException;
import exception.csebank_controllers.asu.edu.LogFileNotFoundException;
import exception.csebank_controllers.asu.edu.OperationNotPermitted;

@Controller
public class LogController {
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	UserController userController;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/log/number_of_files",method = RequestMethod.GET)
	@ResponseBody Integer numberOfLines() {
		String logFile = "";
	    Enumeration e = Logger.getRootLogger().getAllAppenders();
	    while ( e.hasMoreElements() ){
	      Appender app = (Appender)e.nextElement();
	      if ( app instanceof FileAppender ){
	        //System.out.println("File: " + ((FileAppender)app).getFile());
	        logFile = ((FileAppender)app).getFile();
	      }
	    }
		String logFolder = "";
		if (null != logFile && logFile.length() > 0 )
		{
		    int endIndex = logFile.lastIndexOf(File.separator);
		    if (endIndex != -1)  
		    {
		        logFolder = logFile.substring(0, endIndex);
		    }
		}
		
		String loggingPDFFolder = logFolder + File.separator + "pdfs";
		File theDir = new File(loggingPDFFolder);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			return new File(logFolder).listFiles().length;
		}
		
		return new File(logFolder).listFiles().length - 1;
	}
	
	Integer maxNumberOfFiles(){
		String logFile = getLogFile();
		String logFolder = "";
		if (null != logFile && logFile.length() > 0 )
		{
		        logFolder = (new File(logFile)).getParent();
		}
		
		String loggingPDFFolder = logFolder + File.separator + "pdfs";
		File theDir = new File(loggingPDFFolder);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			return new File(logFolder).listFiles().length;
		}
		
		return new File(logFolder).listFiles().length - 1;
	}
	
	String createFolder(){
		String logFile = getLogFile();
		String logFolder = "";
		if (null != logFile && logFile.length() > 0 )
		{
		   
		        logFolder = new File(logFile).getParent();// .substring(0, endIndex);
		    
		}
		String loggingPDFFolder = logFolder + File.separator + "pdfs";
		File theDir = new File(loggingPDFFolder);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			theDir.mkdir();
		}
		
		return loggingPDFFolder;
	}
	
	String getLogFile(){
		String logFile = null;
	    Enumeration e = Logger.getRootLogger().getAllAppenders();
	    while ( e.hasMoreElements() ){
	      Appender app = (Appender)e.nextElement();
	      if ( app instanceof FileAppender ){
	        //System.out.println("File: " + ((FileAppender)app).getFile());
	        logFile = ((FileAppender)app).getFile();
	      }
	    }
		return logFile;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/log/{file_number}",method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> LogFile(@PathVariable Integer file_number) throws IOException {
		String userName =userController.getCurrentUserId();
		String buffer = Utility.getSignedRequest(Utility.PRIVATE_KEY, userName);			
		boolean verify = Utility.verifyRequest(userName, buffer,Utility.PUBLIC_KEY);
			if(verify){
			
		if(file_number <= 0)
		{
			throw new CustomInvalidArgumetException("Incorrect log file number");	
		}
		if(file_number > maxNumberOfFiles())
		{
			throw new LogFileNotFoundException();
		}
		String logFile = getLogFile();
		String logFileName = null;
	    BufferedReader bufReader = null;
		try {
			if(file_number == 1)
			{
				logFileName = logFile;
			}
			else if (file_number < maxNumberOfFiles() + 1)
			{
				logFileName = logFile + "." + String.valueOf(file_number - 1);
			}
//			System.out.println("Searching for File:  " + logFileName); // replace by logger
			bufReader = new BufferedReader(new FileReader(logFileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    ArrayList<String> listOfLines = new ArrayList<>();

	    String line = null;
		try {
			line = bufReader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    while (line != null) {
	      listOfLines.add(line);
	      try {
			line = bufReader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }

	    try {
			bufReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	    
	    //Make a new folder to store pdf's
	    String logPDFFolder = createFolder();
		String resultFilename = null;
	    if(logPDFFolder != null)
	    {
			String[] stringArr = new String[listOfLines.size()];
		    stringArr = listOfLines.toArray(stringArr);
		    
		    String password = userController.getUser(userController.getCurrentUserId()).getPassword();
		    
			try {
				resultFilename = new PdfGenerator().genLog(logPDFFolder, stringArr, password);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	    PathResource pdfFile = new PathResource(logPDFFolder + File.separator + resultFilename + ".pdf");
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    headers.add("Access-Control-Allow-Origin", "*");
	    headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
	    headers.add("Access-Control-Allow-Headers", "Content-Type");
	    headers.add("Content-Disposition", "filename=" + resultFilename + ".pdf");
	    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	    headers.add("Pragma", "no-cache");
	    headers.add("Expires", "0");

	    headers.setContentLength(pdfFile.contentLength());
	    ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
	      new InputStreamResource(pdfFile.getInputStream()), headers, HttpStatus.OK);
	    return response;
		}
		else{
			throw new OperationNotPermitted("The User is not Authentic!!");
		}
	
        
	}
}