package csebank_controllers.asu.edu;

import csebank_dbmodel.asu.edu.Transaction;
import csebank_dbmodel.asu.edu.TransactionService;
import java.util.*;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;


@RestController
@RequestMapping("/transactions")
public class TransactionController {
	//Add transaction to db
	@RequestMapping(
		method = RequestMethod.POST,  
	    produces = MediaType.APPLICATION_JSON_VALUE)		
	public String addTransaction(@RequestParam(value= "transaction") HashMap<String, String> transaction){
		
		TransactionService transact = new TransactionService(transaction);
		String result = transact.addTransaction();
		return result;
	}
	
	//Retrieve transaction form db
	@RequestMapping(
		method = RequestMethod.GET,		
		produces = MediaType.APPLICATION_JSON_VALUE)
	public String getTransactions(@RequestParam("filterType") HashMap<String, String> filterType){
		
		HashMap<String, String> transaction = new HashMap<String,String>();
		TransactionService transact = new TransactionService(transaction);
		String searchKey = (String) filterType.keySet().toArray()[0];
		String searchValue = filterType.get(searchKey);
		List<Transaction> transactionsList=new ArrayList<Transaction>();
		Transaction transOnId = new Transaction();
		Gson gson = new Gson();
		
		switch(searchKey){
		
		case "transStatus":
			transactionsList = transact.getTransactionListOnTransStatus();
			break;
		case "transType":
			transactionsList = transact.getTransactionListOnTransType();
			break;
		case "userId":
			transactionsList = transact.getTransactionListOnUserId();
			break;
		case "tranOwner":
			transactionsList = transact.getTransactionListOnTransOwner();
			break;
		case "approvedBy":
			transactionsList = transact.getTransactionListOnTransAprrovedBy();
			break;
		case "timeStamp":
			transactionsList = transact.getTransactionListOnTransTimeStamp();
			break;
		case "transId":
			transOnId = transact.getTransactionOnTransId();
			return gson.toJson(transOnId);
			
		}
		String transList = new Gson().toJson(transactionsList);

		return transList;
}
	
	@RequestMapping(
		method = RequestMethod.PUT,		
		produces = MediaType.APPLICATION_JSON_VALUE)
	public String approveTransaction(@RequestParam("transaction") HashMap<String, String> transaction){
			
			TransactionService transact = new TransactionService(transaction);
			boolean result = transact.approveTransaction();
			return Boolean.toString(result);
		}
}