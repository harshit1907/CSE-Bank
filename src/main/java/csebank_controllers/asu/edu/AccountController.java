package csebank_controllers.asu.edu;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.DocumentException;

import csebank_database.asu.edu.*;
import csebank_objectmodel.asu.edu.Account;

import csebank_utility.asu.edu.DbParamNams;
import exception.csebank_controllers.asu.edu.OperationNotPermitted;
 
/*
 * author: Mihir
 * 
 */

@Controller
public class AccountController {
	
	@Autowired
	UserController userController;
	
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER', 'ROLE_CUSTOMER', 'ROLE_MERCHANT')")
	@RequestMapping(value="/account/add",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String openAccount(@RequestBody Account account) {
		ControllerUtility.valdiateAccount(account);
		String accountId=null;
		if(account.getUserId()==null){
			account.setUserId(userController.getCurrentUserId());
		}else{
			int userPermLevel=ControllerUtility.getPermissionLevel(userController.getUserRole());
			if(userPermLevel<3 && !account.getUserId().equals(userController.getCurrentUserId())){
				throw new OperationNotPermitted("Not authorized to open an account");
			}
			userPermLevel=ControllerUtility.getPermissionLevel(userController.getUserRole(account.getUserId()));
			if(userPermLevel>2){
				throw new OperationNotPermitted("Internal users, cannot own an account.");
			}
		}
	    // call addAccount from AccountService
	    HashMap<String, String> param=new HashMap<>();
	    param.put(DbParamNams.USER_ID,account.getUserId());
	    param.put(DbParamNams.ACCOUNT_TYPE,account.getAccType());
	    param.put(DbParamNams.ACCOUNT_BALANCE,account.getAccBalance());
	    param.put(DbParamNams.ACCOUNT_STATUS,DbParamNams.ACCOUNT_STATUS_ACTIVE);
	    param.put(DbParamNams.ACCOUNT_OPEN_DATE, ControllerUtility.getCurrentDate());
		//HashMap<String, String> param = ControllerUtility.getAccountMap(account);
	    accountId = new AccountService(param).addAccount();
	    if(accountId==null) 
	    	return "You are only allowed to create one savings, credit and checkings account.";
	    else 
	    	return accountId;
	}

	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER', 'ROLE_ADMIN')")
	@RequestMapping(value="/account/allaccounts/{userId}",method = RequestMethod.GET)
	public @ResponseBody List<Account> getAccountListOnUserId(@PathVariable String userId) {
		ControllerUtility.validateUserID(userId);
	    // call getAccountListOnUserId from AccountService
	    HashMap<String, String> param=new HashMap<>();
	    param.put(DbParamNams.USER_ID,userId);
			
	    List<Account> accList = new AccountService(param).getAccountListOnUserId();
	    return accList;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MERCHANT')")
	@RequestMapping(value="/account/allaccounts",method = RequestMethod.GET)
	public @ResponseBody List<Account> getAccountListOnUserId() {		
	    // call getAccountListOnUserId from AccountService
	    HashMap<String, String> param=new HashMap<>();
	    param.put(DbParamNams.USER_ID,userController.getCurrentUserId());
			
	    List<Account> accList = new AccountService(param).getAccountListOnUserId();
	    return accList;
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/accountDetails/{accountId}",method = RequestMethod.GET)
	public @ResponseBody Account getAccountDetails(@PathVariable String accountId) {
		ControllerUtility.validateAccID(accountId);
	    // call getAccountDetailsOnAccId from AccountService
	    HashMap<String, String> param=new HashMap<>();
	    param.put(DbParamNams.ACCOUNT_ID,accountId);
		Account account = new AccountService(param).getAccountDetailsOnAccId();
	    return account;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER', 'ROLE_ADMIN')")
	@RequestMapping(value="/account/close",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Boolean closeAccount(@RequestBody Account account) {
		ControllerUtility.valdiateAccount(account);
		Boolean result = false;
	    // call deleteAccount from AccountService
	    HashMap<String, String> param=new HashMap<>();
	    param.put(DbParamNams.ACCOUNT_ID,account.getAccountId());
	    validateAccount(account.getAccountId());
		result = new AccountService(param).deleteAccount();

	    return result;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER', 'ROLE_ADMIN')")
	@RequestMapping(value="/account/block",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void blockAccount(@RequestBody Account account) throws Exception {
		ControllerUtility.valdiateAccount(account);
		Boolean result = false;
	    // call blockAccount from AccountService
	    HashMap<String, String> param=new HashMap<>();
	    param.put(DbParamNams.ACCOUNT_ID,account.getAccountId());
		validateAccount(account.getAccountId());
		new AccountService(param).blockAccount();    
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/account/balance/{accountId}",method = RequestMethod.GET)
	public @ResponseBody int getAccountBalance(@PathVariable String accountId) {
		ControllerUtility.validateAccID(accountId);
	    // call getAccountBalance from AccountService
	    HashMap<String, String> param=new HashMap<>();
	    param.put(DbParamNams.ACCOUNT_ID,accountId);
	    validateAccount(accountId);
		int balance = new AccountService(param).getAccountBalance();
	    return balance;
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/account/pdf/{accountId}",method = RequestMethod.GET)
	public @ResponseBody String generatePDF(@PathVariable String accountId) {
		ControllerUtility.validateAccID(accountId);
	    // call genPdf from PDFGenerator class
		validateAccount(accountId);
		String result = null;
		try {
			result = new PdfGenerator().genPdf(accountId);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return result;
	}
	
	boolean validateAccount(String accountID){
		Account account_=getAccountDetails(accountID);
		if(account_.getAccountStatus().equals(DbParamNams.ACCOUNT_STATUS_BLOCKED)){
			throw new OperationNotPermitted("Account deactivated permanently.");
		}
		int permLevel=ControllerUtility.getPermissionLevel(userController.getUserRole());
		if(permLevel<3 && account_.getUserId().equals(userController.getCurrentUserId())){
			throw new OperationNotPermitted("User can access only their accounts.");
		}
		return true;
	}
	/*@RequestMapping(value="/account/log",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<Account>> getAccountLogEntries(@RequestBody Account account) {
	    
	    // call addAccount from AccountService
	    HashMap<String, String> param=new HashMap<>();
	    param.put("accountId",account.getAccountId());
		
	    List<Account> accList = new AccountService(param).getAccountListOnUserId();
	    return new ResponseEntity<List<Account>>(accList, HttpStatus.OK);
	}*/

}