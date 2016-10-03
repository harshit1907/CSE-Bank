package csebank_objectmodel.asu.edu;


public class CreditAccount {
	private String creditAccId;
	private String userId;
	private int creditLimit;
	private int creditBalance;
	
	public CreditAccount(String creditAccId, String userId, int creditLimit, int creditBalance) {
		this.creditAccId = creditAccId;
		this.userId = userId;
		this.creditLimit = creditLimit;
		this.creditBalance = creditBalance;
	}
	public String getCreditAccId() {
		return creditAccId;
	}
	public void setCreditAccId(String creditAccId) {
		this.creditAccId = creditAccId;
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