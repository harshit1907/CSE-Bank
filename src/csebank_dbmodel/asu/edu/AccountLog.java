package csebank_dbmodel.asu.edu;

public class AccountLog {
	private String AccId;
	private String TransId;
	private int AccountBalance;
	
	//account log table constructors
	public AccountLog() {
		this.AccId = null;
		this.TransId = null;
		this.AccountBalance = 0;
	}
	
	public AccountLog(String accId, String transId, int accountBalance) {
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
	public int getAccountBalance() {
		return AccountBalance;
	}

	public void setAccountBalance(int accountBalance) {
		AccountBalance = accountBalance;
	}
}
