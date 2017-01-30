package csebank_utility.asu.edu;
/**
 * These constants are used for creating HashMap
 * which will be used by Database classes
 * All the middleware classes and DB classes should use this file.
 * 
 * Please feel free to add any missing constants
 * @author Aravind
 *
 */
public interface DbParamNams {
	//User
		String TRANS_SRC_SELF="self";

	String USER_ID="userId";
	String USER_ROLE="userRole";
	//type
	String USER_ROLE_MERCHANT="MERCHANT";
	String USER_ROLE_MANANEGR="MANAGER";
	String USER_ROLE_EMPLOYEE="EMPLOYEE";
	String USER_ROLE_ADMIN="ADMIN";
	String USER_ROLE_CUSTOMER="CUSTOMER";
	//---
	String USER_FIRST_NAME="firstName";
	String USER_LAST_NAME="lastName";
	String USER_PHONE="phoneNumber";
	String USER_EMAIL="email";
	String USER_ADDRESS="address";
	String USER_SSN="SSN";
	String USER_DOB="DOB";
	String USER_PASSWORD="password";
	String USER_STATUS="userStatus";
	//type
	String USER_STATUS_ACTIVE="ACTIVE";
	String USER_STATUS_SUSPENDED="BLOCKED";
	//---
	String USER_LOGIN_ATTEMPT="loginAttemptNumber";
	String USER_SEC_QN1="securityQn1";
	String USER_SEC_ANS1="securityAns1";
	String USER_SEC_QN2="securityQn2";
	String USER_SEC_ANS2="securityAns2";
	String USER_ORGANISATION="organization";

	//Account
	String ACCOUNT_ID="accountId";
	String ACCOUNT_TYPE="accType";
	//types
	String ACCOUNT_TYPE_SAVINGS="SAVINGS";
	String ACCOUNT_TYPE_CHECKING="CHECKING";
	String ACCOUNT_TYPE_CREDIT="CREDIT";
	//---
	String ACCOUNT_OPEN_DATE="accOpenDate";
	String ACCOUNT_BALANCE="accBalance";
	String ACCOUNT_STATUS="accStatus";
	//type
	String ACCOUNT_STATUS_ACTIVE="ACTIVE";
	String ACCOUNT_STATUS_BLOCKED="BLOCKED";
	//---


	//transaction
	String TRANS_ID="transId";
	String TRANS_TYPE = "transType";
	//types
	String TRANS_TYPE_CRITICAL = "CRITICAL";
	String TRANS_TYPE_NON_CRITICAL = "NONCRITICAL";
	//---
	String TRANS_DESC="transDescription";
	String TRANS_STATUS="transStatus";
	//types
	String PENDING_VALUE="PENDING";
	String PENDING_MERCHANT_VALUE="PENDING FOR MERCHANT APPROVAL";
	String APPROVED_VALUE="APPROVED";
	String REJECTED_VALUE="REJECTED";
	//---
	String TRANS_SRC_ACC_NO="transSrcAccNo";
	String TRANS_DEST_ACC_NO="transDestAccNo";
	String TRANS_OWNER="transOwner";
	String TRANS_AMOUNT="transAmount";
	String TRANS_RESULT="transResult";
	//type
	String TRANS_OWNER_INTERNAL = "Internal";
	String TRANS_OWNER_ADMIN = "Admin";
	
	//---
	String TRANS_TIMESTAMP="transTimestamp";
	String TRANS_APPROVEDBY="transApprovedBy";
	String TRANS_COMMENTS="transComments";
	
	String TRANS_DEST_EMAIL="trans_dest_email";
	String TRANS_SRC_EMAIL="trans_src_email";
	String TRANS_DEST_PHONE="trans_dest_phone";
	String TRANS_SRC_PHONE="trans_src_phone";

	//Kyc Request
	String REQUEST_FIELD_ID="userFieldId";
	String REQUEST_FIELD_VALUE="fieldValue"; 
	String REQUEST_STATUS="status";
	//type
	String REQUEST_PENDING_VALUE="PENDING";
	String REQUEST_APPROVED_VALUE="APPROVED";
	String REQUEST_REJECTED_VALUE="REJECTED";
	//--
	String REQUEST_OWNER="kycowner";

	//Session (TABLE)
	String SESSION_KEY="sessionKey";
	String SESSION_START="sessionStart";
	String SESSION_END="sessionEnd";
	String SESSION_REQUEST="sessionRequest";
	String SESSION_TIMEOUT="sessionTimeout";
	String SESSION_OTP="sessionOTP";

	//Credit
	String CREDIT_ACCOUNT_ID="creditAccId";
	String CREDIT_LIMIT="creditLimit";
	String CREDIT_BALANCE="creditBalance";

	// Constants
	Integer CREDIT_LIMIT_VALUE=5000;
	String REQUEST_ID = "kycid";

	String OTP="otp";
	String OTP_TIMESTAMP="OTPTimeStamp";
	//Device
	String DEVICE_ID="deviceId";
	

}
