package csebank_dbmodel.asu.edu;

import java.sql.Date;

public class User {
	
	private String UserId;
	private String UserRole;
	private String FirstName;
	private String LastName;
	private String Phone;
	private String Email;
	private String Address;
	private int SSN;
	private Date DOB;
	private String Password;
	private String UserStatus;
	private String LoginAttempt;
	private String SecurityQn;
	private String SecurityAns;
	private String Organization;
	
	//user table constructors
	public User(){
		this.UserId = null;
		this.UserRole = null;
		this.FirstName = null;
		this.LastName = null;
		this.Phone = null;
		this.Email = null;
		this.Address = null;
		this.SSN = 0;
		this.DOB = null;
		this.Password = null;
		this.UserStatus = null;
		this.LoginAttempt = null;
		this.SecurityQn = null;
		this.SecurityAns = null;
		this.Organization = null;
	}
	public User(String userId, String userRole, String firstName, String lastName, String phone,
			String email, String address, int ssn, Date dob, String password, String userStatus,
			String loginAttempt, String securityQn, String securityAns, String organization){
		this.UserId = userId;
		this.UserRole = userRole;
		this.FirstName = firstName;
		this.LastName = lastName;
		this.Phone = phone;
		this.Email = email;
		this.Address = address;
		this.SSN = ssn;
		this.DOB = dob;
		this.Password = password;
		this.UserStatus = userStatus;
		this.LoginAttempt = loginAttempt;
		this.SecurityQn = securityQn;
		this.SecurityAns = securityAns;
		this.Organization = organization;
	}
	
	//user getter setter functions
	public String getUserId(){
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	
	//userrole getter setter functions
	public String getUserRole(){
		return UserRole;
	}
	public void setUserRole(String userRole) {
		UserId = userRole;
	}
	
	//firstname getter setter functions
	public String getFirstName(){
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	
	//lastname getter setter functions
	public String getLastName(){
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	
	//phone getter setter functions
	public String getPhone(){
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	
	//email getter setter functions
	public String getEmail(){
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
	//address getter setter functions
	public String getAddress(){
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	
	//ssn getter setter functions
	public int getSSN(){
		return SSN;
	}
	public void setSSN(int ssn) {
		SSN = ssn;
	}
	
	//dob getter setter functions
	public Date getDOB(){
		return DOB;
	}
	public void setDOB(Date dob) {
		DOB = dob;
	}
	
	//password getter setter functions
	public String getPassword(){
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
	//userstatus getter setter functions
	public String getUserStatus(){
		return UserStatus;
	}
	public void setUserStatus(String userStatus) {
		UserStatus = userStatus;
	}	
	
	//loginattempt getter setter functions
	public String getLoginAttempt(){
		return LoginAttempt;
	}
	public void setLoginAttempt(String loginAttempt) {
		LoginAttempt = loginAttempt;
	}
	
	//securityqn getter setter functions
	public String getSecurityQn(){
		return SecurityQn;
	}
	public void setSecurityQn(String securityQn) {
		SecurityQn = securityQn;
	}
	
	//securityans getter setter functions
	public String getSecurityAns(){
		return SecurityAns;
	}
	public void setSecurityAns(String securityAns) {
		SecurityAns = securityAns;
	}

	//organization getter setter functions
	public String getOrganization(){
		return Organization;
	}
	public void setOrganization(String organization) {
		Organization = organization;
	}	
	
}


