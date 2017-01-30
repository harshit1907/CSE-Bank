package csebank_objectmodel.asu.edu;


public class CreditAccount {
	private String creditAccountId;
	private String userId;
	private int creditLimit;
	private int creditBalance;
	
	public CreditAccount() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CreditAccount(String creditAccountId, String userId, int creditLimit, int creditBalance) {
		super();
		this.creditAccountId = creditAccountId;
		this.userId = userId;
		this.creditLimit = creditLimit;
		this.creditBalance = creditBalance;
	}
	public String getCreditAccountId() {
		return creditAccountId;
	}
	public void setCreditAccountId(String creditAccountId) {
		this.creditAccountId = creditAccountId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(int creditLimit) {
		this.creditLimit = creditLimit;
	}
	public int getCreditBalance() {
		return creditBalance;
	}
	public void setCreditBalance(int creditBalance) {
		this.creditBalance = creditBalance;
	}
	
	
}