package csebank_dbmodel.asu.edu;

public class AccountLog {
	private String AccId;
	private String TransId;
	private String AccountBalance;
	
	//account log table constructors
	public AccountLog() {
		this.AccId = null;
		this.TransId = null;
		this.AccountBalance = null;
	}
	
	public AccountLog(String accId, String transId, String accountBalance) {
		this.AccId = transId;
		this.TransId = transId;
		this.AccountBalance = accountBalance;
	}

	//accId getter setter functions
	public String getAccId() {
		return AccId;
	}

	public void setAccId(String accId) {
		AccId = accId;
	}

	//transId getter setter functions
	public String getTransId() {
		return TransId;
	}

	public void setUserId(String transId) {
		TransId = transId;
	}
	
	//Account balance getter setter functions
	public String getAccountBalance() {
		return AccountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		AccountBalance = accountBalance;
	}
}
