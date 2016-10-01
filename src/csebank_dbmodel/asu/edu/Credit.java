package csebank_dbmodel.asu.edu;

public class Credit {
	private String CreditAccId;
	private String UserId;
	private String CreditLimit;
	
	//Credit table constructors
	public Credit() {
		this.CreditAccId = null;
		this.UserId = null;
		this.CreditLimit = null;
	}
	
	public Credit(String creditaccId, String userId, String creditLimit) {
		this.CreditAccId = creditaccId;
		this.UserId = userId;
		this.CreditLimit = creditLimit;
	}

	//credit account id getter setter functions
	public String getCreditAccId() {
		return CreditAccId;
	}

	public void setCreditAccId(String creditaccId) {
		CreditAccId = creditaccId;
	}

	//user id getter setter functions
	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}
	
	//credit limit getter setter functions
	public String getCreditLimit() {
		return CreditLimit;
	}

	public void setCreditLimit(String creditLimit) {
		CreditLimit = creditLimit;
	}
}
