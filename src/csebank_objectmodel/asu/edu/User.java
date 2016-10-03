package csebank_objectmodel.asu.edu;

import java.sql.Date;

public class User {
	
	private String userId;
	private String userRole;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String address;
	private int SSN;
	private Date DOB;
	private String password;
	private String userStatus;
	private String loginAttemptNumber;
	private String securityQn1;
	private String securityAns1;
	private String securityQn2;
	private String securityAns2;
	private String organization;
	
	public User(String userId, String userRole, String firstName, String lastName, String phoneNumber, String email,
			String address, int sSN, Date dOB, String password, String userStatus, String loginAttemptNumber,
			String securityQn1, String securityAns1, String securityQn2, String securityAns2, String organization) {
		this.userId = userId;
		this.userRole = userRole;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
		SSN = sSN;
		DOB = dOB;
		this.password = password;
		this.userStatus = userStatus;
		this.loginAttemptNumber = loginAttemptNumber;
		this.securityQn1 = securityQn1;
		this.securityAns1 = securityAns1;
		this.securityQn2 = securityQn2;
		this.securityAns2 = securityAns2;
		this.organization = organization;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getSSN() {
		return SSN;
	}
	public void setSSN(int sSN) {
		SSN = sSN;
	}
	public Date getDOB() {
		return DOB;
	}
	public void setDOB(Date dOB) {
		DOB = dOB;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public String getLoginAttemptNumber() {
		return loginAttemptNumber;
	}
	public void setLoginAttemptNumber(String loginAttemptNumber) {
		this.loginAttemptNumber = loginAttemptNumber;
	}
	public String getSecurityQn1() {
		return securityQn1;
	}
	public void setSecurityQn1(String securityQn1) {
		this.securityQn1 = securityQn1;
	}
	public String getSecurityAns1() {
		return securityAns1;
	}
	public void setSecurityAns1(String securityAns1) {
		this.securityAns1 = securityAns1;
	}
	public String getSecurityQn2() {
		return securityQn2;
	}
	public void setSecurityQn2(String securityQn2) {
		this.securityQn2 = securityQn2;
	}
	public String getSecurityAns2() {
		return securityAns2;
	}
	public void setSecurityAns2(String securityAns2) {
		this.securityAns2 = securityAns2;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	
	
	
}
