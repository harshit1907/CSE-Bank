package csebank_objectmodel.asu.edu;
 
import java.util.*;

public class Account {
	private String accountId;
	private int accountNumber;
	private int routingNumber;
	private String accountStatus;
	private String userId;
	private String accType;
	private Date accOpenDate;
	private int accBalance;
	private String accStatus;
	
	
	public Account(String accountId, int accountNumber, int routingNumber, String accountStatus, String userId,
			String accType, Date accOpenDate, int accBalance, String accStatus) {
		this.accountId = accountId;
		this.accountNumber = accountNumber;
		this.routingNumber = routingNumber;
		this.accountStatus = accountStatus;
		this.userId = userId;
		this.accType = accType;
		this.accOpenDate = accOpenDate;
		this.accBalance = accBalance;
		this.accStatus = accStatus;
	}
	public String getaccountId() {
		return accountId;
	}
	public void setaccountId(String accountId) {
		this.accountId = accountId;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public int getRoutingNumber() {
		return routingNumber;
	}
	public void setRoutingNumber(int routingNumber) {
		this.routingNumber = routingNumber;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
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
	public int getAccBalance() {
		return accBalance;
	}
	public void setAccBalance(int accBalance) {
		this.accBalance = accBalance;
	}
	public String getAccStatus() {
		return accStatus;
	}
	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}	
}