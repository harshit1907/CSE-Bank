package csebank_objectmodel.asu.edu;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Account {
	private String accountId;
	private String accStatus;
	private String userId;
	private String accType;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date accOpenDate;
	private String accBalance;
	
	
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Account(String accountId, String accountStatus, String userId, String accType, Date accOpenDate,
			String accBalance) {
		super();
		this.accountId = accountId;
		this.accStatus = accountStatus;
		this.userId = userId;
		this.accType = accType;
		this.accOpenDate = accOpenDate;
		this.accBalance = accBalance;
	}

	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountStatus() {
		return accStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accStatus = accountStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public Date getAccOpenDate() {
		return accOpenDate;
	}
	public void setAccOpenDate(Date accOpenDate) {
		this.accOpenDate = accOpenDate;
	}
	public String getAccBalance() {
		return accBalance;
	}
	public void setAccBalance(String accBalance) {
		this.accBalance = accBalance;
	}
	
		
}