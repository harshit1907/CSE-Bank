package csebank_objectmodel.asu.edu;

public class AccountLog {
	private String accId;
	private String transId;
	private int accountBalance;
	
	public AccountLog(String accId, String transId, int accountBalance) {
		this.accId = accId;
		this.transId = transId;
		this.accountBalance = accountBalance;
	}
	public String getAccId() {
		return accId;
	}
	public void setAccId(String accId) {
		this.accId = accId;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public int getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(int accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	
}
