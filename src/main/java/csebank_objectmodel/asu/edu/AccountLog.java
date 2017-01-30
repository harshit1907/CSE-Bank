package csebank_objectmodel.asu.edu;

public class AccountLog {
	private String accountId;
	private String transId;
	private int accountBalance;
	public AccountLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AccountLog(String accountId, String transId, int accountBalance) {
		super();
		this.accountId = accountId;
		this.transId = transId;
		this.accountBalance = accountBalance;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
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
