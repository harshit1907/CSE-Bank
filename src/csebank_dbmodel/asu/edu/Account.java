package csebank_dbmodel.asu.edu;
import java.sql.*;

public class Account {
	private String AccountId;
	private String UserId;
	private String AccType;
	private Date AccOpenDate;
	private int AccBalance;
	private String AccStatus;
	
	//account table constructors
	public Account() {
		this.AccountId = null;
		this.UserId = null;
		this.AccType = null;
		this.AccOpenDate = null;
		this.AccBalance = 0;
		this.AccStatus = null;
	}
	
	public Account(String accountId, String userId, String accType, Date accOpenDate,
			int accBalance, String accStatus) {
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
	public Date getAccOpenDate() {
		return AccOpenDate;
	}

	public void setAccOpenDate(Date accOpenDate) {
		AccOpenDate = accOpenDate;
	}
	
	//accBalance getter setter functions
	public int getAccBalance() {
		return AccBalance;
	}

	public void setAccBalance(int accBalance) {
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
