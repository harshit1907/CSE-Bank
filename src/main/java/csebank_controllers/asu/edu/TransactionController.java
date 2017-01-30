
package csebank_controllers.asu.edu;


import java.util.ArrayList;

/**
 * Ashutosh
 */

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;

import csebank_database.asu.edu.PdfGenerator;
import csebank_database.asu.edu.TransactionService;
import csebank_objectmodel.asu.edu.Account;
import csebank_objectmodel.asu.edu.Transaction;
import csebank_objectmodel.asu.edu.User;
import csebank_utility.asu.edu.DbParamNams;
import csebank_utility.asu.edu.EmailService;
import exception.csebank_controllers.asu.edu.CustomInvalidArgumetException;
import exception.csebank_controllers.asu.edu.NoAccountExists;
import exception.csebank_controllers.asu.edu.OperationNotPermitted;


@RestController
@RequestMapping("/transactions")
public class TransactionController {
	
	@Autowired
	AccountController accountController;
	@Autowired
	UserController userController;
	
	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MERCHANT')")
	@RequestMapping( value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)		
	public String addTransaction(@RequestBody Transaction transaction) throws CustomInvalidArgumetException{
			// validators	
			ControllerUtility.validateNewTransaction(transaction);
			HashMap<String,String> transactMap = ControllerUtility.getTransactionMap(transaction);
			TransactionService transact = new TransactionService(transactMap);			
			
			Account account = accountController.getAccountDetails(transactMap.get(DbParamNams.TRANS_SRC_ACC_NO));
			if(!matchUserWithTrans(userController.getCurrentUserId(), transaction) || !account.getUserId().equals(userController.getCurrentUserId()) ){
				throw new OperationNotPermitted("Sorry! User/Merchant can add only transactions relating to their accounts");
			}
			account=null; boolean valid = false;
			if(transactMap.get(DbParamNams.TRANS_DEST_ACC_NO)!=null){
				account = accountController.getAccountDetails(transactMap.get(DbParamNams.TRANS_DEST_ACC_NO));
			} else if(transactMap.get(DbParamNams.TRANS_DEST_EMAIL)!=null){
				 valid = transact.getByEmail(transactMap.get(DbParamNams.TRANS_DEST_EMAIL));
				 if(!valid)
						throw new NoAccountExists("No Account Exists corresponding to the Email id.");
			}else if(transactMap.get(DbParamNams.TRANS_DEST_PHONE)!=null){
				valid = transact.getByPhone(transactMap.get(DbParamNams.TRANS_DEST_PHONE));
				 if(!valid)
						throw new NoAccountExists("No Account Exists corresponding to the Phone number.");
			}							
			String result = transact.addTransaction();
			//return transId	
			return result;
	}


	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MERCHANT')")
	@RequestMapping( value = "/addMoney", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)		
	public String addMoney(@RequestBody Transaction transaction) throws CustomInvalidArgumetException{
			// validators	
			ControllerUtility.validateTransAmount(Integer.toString(transaction.getTransAmount()));
			if(!transaction.getTransSrcAccNo().equalsIgnoreCase(DbParamNams.TRANS_SRC_SELF)){
				throw new CustomInvalidArgumetException("While adding money source account should be \'SELF\'");
			}
			
			HashMap<String,String> transactMap = ControllerUtility.getTransactionMap(transaction);
			TransactionService transact = new TransactionService(transactMap);			
			
			Account account = accountController.getAccountDetails(transactMap.get(DbParamNams.TRANS_DEST_ACC_NO));
			if(!account.getUserId().equals(userController.getCurrentUserId())){
				throw new OperationNotPermitted("Sorry! cannot add money to other's account.");
			}
			String result = transact.addTransactionDepositWithdrw();
			//return transId	
			return result;
	}

	
@PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MERCHANT')")
	@RequestMapping( value = "/withdrawMoney", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)		
	public String withdrawMoney(@RequestBody Transaction transaction) throws CustomInvalidArgumetException{
			// validators	
			ControllerUtility.validateTransAmount(Integer.toString(transaction.getTransAmount()));
			if(!transaction.getTransDestAccNo().equalsIgnoreCase(DbParamNams.TRANS_SRC_SELF)){
				throw new CustomInvalidArgumetException("While adding money source account should be \'SELF\'");
			}
			HashMap<String,String> transactMap = ControllerUtility.getTransactionMap(transaction);
			TransactionService transact = new TransactionService(transactMap);						
			Account account = accountController.getAccountDetails(transactMap.get(DbParamNams.TRANS_SRC_ACC_NO));
			if(!account.getUserId().equals(userController.getCurrentUserId())){
				throw new OperationNotPermitted("Sorry! cannot withdraw money from other's account.");
			}							
			String result = transact.addTransactionDepositWithdrw();
			//return transId	
			return result;
	}
	//cgeck for email and phone in src rather than dest---------------please rewrite --done
	//only email can be used as a source
	//@PreAuthorize("hasAnyRole('ROLE_MERCHANT')")
	@RequestMapping( value = "/merchant/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)		
	public String addMerchantTransaction(@RequestBody HashMap<String,String> transactMap) throws CustomInvalidArgumetException{
			// validators	
			ControllerUtility.validateAccID(transactMap.get(DbParamNams.TRANS_DEST_ACC_NO));
			ControllerUtility.validateTransAmount(transactMap.get(DbParamNams.TRANS_AMOUNT));						
			//ControllerUtility.validateAccID(transactMap.get(DbParamNams.TRANS_DEST_ACC_NO));
			ControllerUtility.validateUserEmailID(transactMap.get(DbParamNams.TRANS_SRC_EMAIL));
			//with logged in user
			matchUserWithTrans(userController.getCurrentUserId(), transactMap.get(DbParamNams.TRANS_DEST_ACC_NO));			
			
			//HashMap<String,String> transactMap = ControllerUtility.getTransactionMap(transaction);
			TransactionService transact = new TransactionService(transactMap);			
			
			String custAccountId=transact.getAccountIdByEmail(transactMap.get(DbParamNams.TRANS_SRC_EMAIL));
			User customer=userController.getUser(accountController.getAccountDetails(custAccountId).getUserId());
			if(!(customer.getFirstName().equalsIgnoreCase(transactMap.get(DbParamNams.USER_FIRST_NAME)) 
					&& customer.getLastName().equalsIgnoreCase(transactMap.get(DbParamNams.USER_LAST_NAME)))){
				throw new CustomInvalidArgumetException("First and last name of the customer provided does not match. Please retry.");		
					}
						
			transact = new TransactionService(getNewMap(transactMap));
			String result = transact.addTransaction();
			//notify the customer
			String subject = "Regarding your Transaction with the Merchant.", org= userController.getUser().getOrganization();
			String text = "Dear customer,\n We received a debit request from the merchant:"+org+" to your account for an amount of "+transactMap.get(DbParamNams.TRANS_AMOUNT)
			+".\n Please contact bank employees, if this is not authorized by you.\n\nSincerely,\nCSE-Bank Ltd.";

			EmailService newMail = new EmailService();
			newMail.sendMail(customer.getEmail(),subject,text);
			
			//return transId	
			return result;
	}

	private HashMap<String, String> getNewMap(HashMap<String, String> transactMap) {
		HashMap<String, String> ret=new HashMap<String, String>();
		ret.put(DbParamNams.TRANS_SRC_EMAIL, transactMap.get(DbParamNams.TRANS_SRC_EMAIL));
		ret.put(DbParamNams.TRANS_DEST_ACC_NO, transactMap.get(DbParamNams.TRANS_DEST_ACC_NO));
		ret.put(DbParamNams.TRANS_AMOUNT, transactMap.get(DbParamNams.TRANS_AMOUNT));
		ret.put(DbParamNams.TRANS_DESC, transactMap.get(DbParamNams.TRANS_DESC));
		return ret;
	}


	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER')")
	@RequestMapping(value="/status/{transStatus}", method = RequestMethod.GET)
        @ResponseBody List<Transaction> getTransListByStatus(@PathVariable String transStatus){			
			ControllerUtility.validateTransStatus(transStatus);
            HashMap<String, String> param=new HashMap<String, String>();
            param.put(DbParamNams.TRANS_STATUS,transStatus);
            TransactionService transactionService=new TransactionService(param);
            return transactionService.getTransactionListOnTransStatus();
        }
		
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER')")
	@RequestMapping(value="/type", method = RequestMethod.GET)
	@ResponseBody List<Transaction> getTransListByType(){		
		List<Transaction> trans=new ArrayList<>();
		if(userController.getUserRole().equals(DbParamNams.USER_ROLE_MANANEGR)){
			if(getTransListByType_(DbParamNams.TRANS_TYPE_CRITICAL)!=null)
			trans.addAll(getTransListByType_(DbParamNams.TRANS_TYPE_CRITICAL));
		}
		if(getTransListByType_(DbParamNams.TRANS_TYPE_NON_CRITICAL)!=null)
		trans.addAll(getTransListByType_(DbParamNams.TRANS_TYPE_NON_CRITICAL));
		return trans;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER')")
	@RequestMapping(value="/fundtransfer", method = RequestMethod.GET)
	@ResponseBody List<Transaction> getTransListByTypeforFund(){		
		List<Transaction> trans=new ArrayList<>();
		if(userController.getUserRole().equals(DbParamNams.USER_ROLE_MANANEGR)){
			if(getTransListByTypeTransfer_(DbParamNams.TRANS_TYPE_CRITICAL)!=null)
			trans.addAll(getTransListByTypeTransfer_(DbParamNams.TRANS_TYPE_CRITICAL));
		}
		if(getTransListByTypeTransfer_(DbParamNams.TRANS_TYPE_NON_CRITICAL)!=null)
		trans.addAll(getTransListByTypeTransfer_(DbParamNams.TRANS_TYPE_NON_CRITICAL));
		return trans;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER')")
	@RequestMapping(value="/withdraw", method = RequestMethod.GET)
	@ResponseBody List<Transaction> getTransListByTypeforWithdrawDeposit(){		
		List<Transaction> trans=new ArrayList<>();
		if(userController.getUserRole().equals(DbParamNams.USER_ROLE_MANANEGR)){
			if(getTransListByTypeWithdraw_(DbParamNams.TRANS_TYPE_CRITICAL)!=null)
			trans.addAll(getTransListByTypeWithdraw_(DbParamNams.TRANS_TYPE_CRITICAL));
		}
		if(getTransListByTypeWithdraw_(DbParamNams.TRANS_TYPE_NON_CRITICAL)!=null)
		trans.addAll(getTransListByTypeWithdraw_(DbParamNams.TRANS_TYPE_NON_CRITICAL));
		return trans;
	}
        List<Transaction> getTransListByType_(String transType){		
			ControllerUtility.validateTransType(transType);
			HashMap<String, String> param=new HashMap<String, String>();
			if(transType.equals(DbParamNams.TRANS_TYPE_CRITICAL) &&
					ControllerUtility.getPermissionLevel(userController.getUserRole()) != 4){
				throw new OperationNotPermitted("Only Manger is allowed to do critical transaction");
			}
            param.put(DbParamNams.TRANS_TYPE,transType);
            TransactionService transactionService=new TransactionService(param);
            return transactionService.getTransactionListOnTransType();
        }
        
        List<Transaction> getTransListByTypeTransfer_(String transType){		
			ControllerUtility.validateTransType(transType);
			HashMap<String, String> param=new HashMap<String, String>();
			if(transType.equals(DbParamNams.TRANS_TYPE_CRITICAL) &&
					ControllerUtility.getPermissionLevel(userController.getUserRole()) != 4){
				throw new OperationNotPermitted("Only Manger is allowed to do critical transaction");
			}
            param.put(DbParamNams.TRANS_TYPE,transType);
            TransactionService transactionService=new TransactionService(param);
            return transactionService.getTransactionListOnTransTypeTransfer();
        }
        
        List<Transaction> getTransListByTypeWithdraw_(String transType){		
			ControllerUtility.validateTransType(transType);
			HashMap<String, String> param=new HashMap<String, String>();
			if(transType.equals(DbParamNams.TRANS_TYPE_CRITICAL) &&
					ControllerUtility.getPermissionLevel(userController.getUserRole()) != 4){
				throw new OperationNotPermitted("Only Manger is allowed to do critical transaction");
			}
            param.put(DbParamNams.TRANS_TYPE,transType);
            TransactionService transactionService=new TransactionService(param);
            return transactionService.getTransactionListOnTransTypeforWithdraw();
        }
			
	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MERCHANT')")
	@RequestMapping(value="/accountId/{accountId}", method = RequestMethod.GET)
      @ResponseBody List<Transaction> getTransListByAccountId(@PathVariable String accountId){        
		
		ControllerUtility.validateAccID(accountId);
		String userName= SecurityContextHolder.getContext().getAuthentication().getName();
		
		HashMap<String, String> param=new HashMap<String, String>();
        param.put(DbParamNams.ACCOUNT_ID,accountId);
        Account account = accountController.getAccountDetails(param.get(DbParamNams.ACCOUNT_ID));
		if(!account.getUserId().equals(userName)){
			throw new OperationNotPermitted("User can only see his own account");
		}
        TransactionService transactionservice=new TransactionService(param);
        return transactionservice.getTransactionListOnAccountId();
    }

	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER')")
	@RequestMapping(value="/owner/{tranOwner}", method = RequestMethod.GET)
    @ResponseBody List<Transaction> getTransListByTransOwner(@PathVariable String tranOwner){        
		ControllerUtility.validateTransOwner(tranOwner);
		HashMap<String, String> param=new HashMap<String, String>();
        param.put(DbParamNams.TRANS_OWNER,tranOwner);
        TransactionService transactionservice=new TransactionService(param);
        return transactionservice.getTransactionListOnTransOwner();
    }
	
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER')")
	@RequestMapping(value="/approvedby/{approvedBy}", method = RequestMethod.GET)
    @ResponseBody List<Transaction> getTransListonApprovedBy(@PathVariable String approvedBy){		
		ControllerUtility.validateUserID(approvedBy);		
        HashMap<String, String> param=new HashMap<String, String>();
        param.put(DbParamNams.TRANS_APPROVEDBY,approvedBy);
        TransactionService transactionservice=new TransactionService(param);
        return transactionservice.getTransactionListOnTransAprrovedBy();
    }
	
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER')")
	@RequestMapping(value="/timestamp/{timeStamp}", method = RequestMethod.GET)
    @ResponseBody List<Transaction> getTransListonOnTimeStamp(@PathVariable String timeStamp){		
		ControllerUtility.validateTimeStamp(Timestamp.valueOf(timeStamp));		
        HashMap<String, String> param=new HashMap<String, String>();
        param.put(DbParamNams.TRANS_TIMESTAMP,timeStamp);
        TransactionService transactionservice=new TransactionService(param);
        return transactionservice.getTransactionListOnTransTimeStamp();
    }
	
	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER','ROLE_EMPLOYEE','ROLE_MANAGER', 'ROLE_MERCHANT')")
	@RequestMapping(value="/transId/{transId}", method = RequestMethod.GET)
    @ResponseBody Transaction getTransListonOnTransId(@PathVariable String transId){		
		ControllerUtility.validateTransId(transId);		
        HashMap<String, String> param=new HashMap<String, String>();
        param.put(DbParamNams.TRANS_ID,transId);
        TransactionService transactionservice=new TransactionService(param);
        Transaction transaction= transactionservice.getTransactionOnTransId();
        //validating permission to see a transaction
        int userPermLevel=ControllerUtility.getPermissionLevel(userController.getUserRole());
        if( !(userPermLevel<3 &&  matchUserWithTrans(userController.getCurrentUserId(), transaction)
        		|| userPermLevel==4 
        		|| userPermLevel==3 && transaction.getTransType().equals(DbParamNams.TRANS_TYPE_NON_CRITICAL))){
        	throw new OperationNotPermitted();
        }
        return transaction;
    }
	
	private boolean matchUserWithTrans(String userId, Transaction transaction){
		
		String u1=null,u2=null;
		if(transaction.getTransSrcAccNo()!=null && !transaction.getTransSrcAccNo().isEmpty() && !transaction.getTransSrcAccNo().equalsIgnoreCase(DbParamNams.TRANS_SRC_SELF)){
			u1=accountController.getAccountDetails(transaction.getTransSrcAccNo()).getUserId();
		}
		if(transaction.getTransDestAccNo()!=null && !transaction.getTransDestAccNo().isEmpty() && !transaction.getTransDestAccNo().equalsIgnoreCase(DbParamNams.TRANS_SRC_SELF)){
			u2=accountController.getAccountDetails(transaction.getTransDestAccNo()).getUserId();
		}
		//should match either of the accounts
		if(u1!=null &&  u1.equals(userId) || u2!=null && u2.equals(userId))
			return true;		
		return false;
		
	}
	
	private void matchUserWithTrans(String userId, String accountId) {
		String u1=null;
		if(!accountId.equals(DbParamNams.TRANS_SRC_SELF)){
			u1=accountController.getAccountDetails(accountId).getUserId();
			if(!u1.equals(userId)){
				throw new CustomInvalidArgumetException("Transaction for the account(s) should belong to the same person adding the transaction.");
			}
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER')")
	@RequestMapping(value= "/updateStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String approveTransaction(@RequestBody Transaction transaction_){
		Transaction transaction=getTransListonOnTransId(transaction_.getTransId()+"");
		if(transaction.getTransStatus().equalsIgnoreCase(DbParamNams.PENDING_VALUE))
				{
			  
			transaction.setTransStatus(transaction_.getTransStatus());
			ControllerUtility.validateExistingTransaction(transaction);
			HashMap<String,String> transactMap = ControllerUtility.getTransactionMap(transaction);
			if(transactMap.get(DbParamNams.TRANS_TYPE).equals(DbParamNams.TRANS_TYPE_CRITICAL)){
				String userName= SecurityContextHolder.getContext().getAuthentication().getName();
				String userRole =  userController.getUser(userName).getUserRole();
				if(userRole.equals(DbParamNams.USER_ROLE_EMPLOYEE)){
					throw new OperationNotPermitted("Manager is allowed to approve critical transactions.");
				}
			}
			TransactionService transact = new TransactionService(transactMap);

			boolean result = transact.approveTransaction();
			return Boolean.toString(result);
		}
		throw new OperationNotPermitted("You can only update pending transactions.!!");                
	}
	
	
	  @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER')")
	    @RequestMapping(value= "/updateAddWithdrawStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	        public String approveAddWithdrawTransaction(@RequestBody Transaction transaction_){
		             Transaction transaction=getTransListonOnTransId(transaction_.getTransId()+"");
	                if(transaction.getTransStatus().equalsIgnoreCase(DbParamNams.PENDING_VALUE)) { 
	                transaction.setTransStatus(transaction_.getTransStatus());
	                ControllerUtility.validateExistingForAddwithdrawTransaction(transaction);
	                HashMap<String,String> transactMap = ControllerUtility.getTransactionMap(transaction);
	                if(transactMap.get(DbParamNams.TRANS_TYPE).equals(DbParamNams.TRANS_TYPE_CRITICAL)){
	                    String userName= SecurityContextHolder.getContext().getAuthentication().getName();
	                    String userRole =  userController.getUser(userName).getUserRole();
	                    
	                    if(userRole.equals(DbParamNams.USER_ROLE_EMPLOYEE)){
	                        throw new OperationNotPermitted("Manager is allowed to approve critical transactions.");
	                        }
	                }
	                
	                TransactionService transact = new TransactionService(transactMap);
	                
	                boolean result = transact.approveTransactionDepositWithdrw();
	                return Boolean.toString(result);
	                }
	                throw new OperationNotPermitted("You can only update pending transactions.!!");
	        }
	

	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MERCHANT')")
	@RequestMapping(value= "/downloadstatement/{accountId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
		public ResponseEntity<InputStreamResource>  getBankStatementPDF(@PathVariable String accountId) throws IOException, DocumentException, SQLException{

		ControllerUtility.validateAccID(accountId);
		Account account=accountController.getAccountDetails(accountId);
		if(!account.getUserId().equals(userController.getCurrentUserId())){
			throw new OperationNotPermitted("User can only see transaction for his own account.");
		}
		PdfGenerator pdfService = new PdfGenerator();
		String pdfPath = pdfService.genPdf(accountId);
		
		PathResource pdfFile = new PathResource(pdfPath);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		
		return ResponseEntity.ok().contentLength(pdfFile.contentLength())
				.contentType(MediaType.parseMediaType("application/pdf"))
				.body(new InputStreamResource(pdfFile.getInputStream()));
		}
	}


