package csebank_controllers.asu.edu;

import static org.springframework.core.annotation.AnnotatedElementUtils.*;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import exception.csebank_controllers.asu.edu.*;

@EnableWebMvc
@ControllerAdvice
class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler{
	
	//Master Exception Handler - uncomment this when final testing. Todo
	
	@ExceptionHandler(Exception.class)
	ResponseEntity<String> handle(Exception exp){
		String body = "Internal Server Error. Contact Admin to Find more information";// + exception.getLocalizedMessage();
		HttpStatus responseStatus = this.resolveAnnotatedResponseStatus(exp);
	    return new ResponseEntity<String>(body, responseStatus);
	}
	
    @ExceptionHandler(UserIDAlreadyExists.class)
    ResponseEntity<String> handle(UserIDAlreadyExists exception) {
        String body = "Error:" + getErrorMsg(exception);
        HttpStatus responseStatus = this.resolveAnnotatedResponseStatus(exception);
        return new ResponseEntity<String>(body, responseStatus);
    } 
    
    @ExceptionHandler(NoAccountExists.class)
    ResponseEntity<String> handle(NoAccountExists exception) {
        String body = "Error:" + getErrorMsg(exception);
        HttpStatus responseStatus = this.resolveAnnotatedResponseStatus(exception);
        return new ResponseEntity<String>(body, responseStatus);
    } 
    @ExceptionHandler(LogFileNotFoundException.class)
    ResponseEntity<String> handle(LogFileNotFoundException exception) {
        String body = "Error:" + getErrorMsg(exception);
        HttpStatus responseStatus = this.resolveAnnotatedResponseStatus(exception);
        return new ResponseEntity<String>(body, responseStatus);
    }
    @ExceptionHandler(KYCNotFound.class)
    ResponseEntity<String> handle(KYCNotFound exception) {
        String body = "Error:" + getErrorMsg(exception);
        HttpStatus responseStatus = this.resolveAnnotatedResponseStatus(exception);
        return new ResponseEntity<String>(body, responseStatus);
    } 
    
    @ExceptionHandler(CustomInvalidArgumetException.class)
    ResponseEntity<String> handle(CustomInvalidArgumetException exception) {
        String body = "Error:" + getErrorMsg(exception);
        HttpStatus responseStatus = this.resolveAnnotatedResponseStatus(exception);
        return new ResponseEntity<String>(body, responseStatus);
    }    
    
    @ExceptionHandler(CustomerNotFoundException.class)
    ResponseEntity<String> handle(CustomerNotFoundException exception) {
        String body = "Error:" + getErrorMsg(exception);
        HttpStatus responseStatus = this.resolveAnnotatedResponseStatus(exception);
        return new ResponseEntity<String>(body, responseStatus);
    }
    
    @ExceptionHandler(OperationNotPermitted.class)
    ResponseEntity<String> handle(OperationNotPermitted exception) {
        String body = "Error:" + getErrorMsg(exception);
        HttpStatus responseStatus = this.resolveAnnotatedResponseStatus(exception);
        return new ResponseEntity<String>(body, responseStatus);
    }
    
    @ExceptionHandler(SQLException.class)
    ResponseEntity<String> handle(SQLException exception) {
    											//lets not disclose the what is the DB issue
    	String body = "Error: database_error"; // + exception.getLocalizedMessage();    	
    	//log this excpetion
    	//HttpStatus responseStatus = this.resolveAnnotatedResponseStatus(exception);
    	return new ResponseEntity<String>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    HttpStatus resolveAnnotatedResponseStatus(Throwable exception) {
        ResponseStatus annotation = findMergedAnnotation(exception.getClass(), ResponseStatus.class);
        if (annotation != null) {
            return annotation.value();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    
    String getErrorMsg(Exception e){
    	String msg="No error message.";
    	if(e.getMessage()!=null && !e.getMessage().trim().isEmpty()){
    		msg= e.getMessage();
    	} else if(e.getCause()!=null && ! (e.getCause().getMessage()!=null)){
    		msg=e.getCause().getMessage();
    	}
    	
    	return msg;
    }
    
    
    
}