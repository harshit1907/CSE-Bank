package csebank_dbmodel.asu.edu;

public class Account {
	private String AccountId;
	private String UserId;
	private String AccType;
	private String AccOpenDate;
	private String AccBalance;
	private String AccStatus;
	
	//account table constructors
	public Account() {
		this.AccountId = null;
		this.UserId = null;
		this.AccType = null;
		this.AccOpenDate = null;
		this.AccBalance = null;
		this.AccStatus = null;
	}
	
	public Account(String accountId, String userId, String accType, String accOpenDate,
			String accBalance, String accStatus) {
		this.AccountId = accountId;
		this.UserId = userId;
		this.AccType = accType;
		this.AccOpenDate = accOpenDate;
		this.AccBalance = accBalance;
		this.AccStatus = accStatus;
	}

	//accountId getter setter functions
	public String getAccountId() {
		return AccountId;
	}

	public void setAccountId(String accountId) {
		AccountId = accountId;
	}

	//userId getter setter functions
	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}
	
	//accType getter setter functions
	public String getAccType() {
		return AccType;
	}

	public void setAccType(String accType) {
		AccType = accType;
	}
	
	//accOpenDate getter setter functions
	public String getAccOpenDate() {
		return AccOpenDate;
	}

	public void setAccOpenDate(String accOpenDate) {
		AccOpenDate = accOpenDate;
	}
	
	//accBalance getter setter functions
	public String getAccBalance() {
		return AccBalance;
	}

	public void setAccBalance(String accBalance) {
		AccBalance = accBalance;
	}
	
	//accStatus getter setter functions
	public String getAccStatus() {
		return AccStatus;
	}

	public void setAccStatus(String accStatus) {
		AccStatus = accStatus;
	}
	
}
