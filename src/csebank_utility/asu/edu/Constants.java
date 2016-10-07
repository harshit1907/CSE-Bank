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
public interface Constants {
//User
	String USER_ID="UserId";
	String USER_ROLE="UserRole"; 
	String USER_FIRST_NAME="FirstName";
	String USER_LAST_NAME="LastName";
	String USER_PHONE="Phone";
	String USER_EMAIL="Email";
	String USER_ADDRESS="Address";
	String USER_SSN="SSN";
	String USER_DOB="DOB";
	String USER_PASSWORD="Password";
	String USER_STATUS="UserStatus";
	String USER_LOGIN_ATTEMPT="LoginAttempt";
	String USER_SEC_QN1="SecurityQn1";
	String USER_SEC_ANS1="SecurityAns1";
	String USER_SEC_QN2="SecurityQn2";
	String USER_SEC_ANS2="SecurityAns2";
	String USER_ORGANISATION="Organization";
	
//Account
	String ACCOUNT_ID="AccountID";
	String ACCOUNT_TYPE="AccType";
	String ACCOUNT_OPEN_DATE="AccOpenDate";
	String ACCOUNT_BALANCE="AccBalance";
	String ACCOUNT_STATUS="AccStatus";

	
//transaction
	String TRANS_ID="TransId";
	String TRANS_TYPE = "TransType";
	String TRANS_DESC="TransDescription";
	String TRANS_STATUS="TransStatus";
	String TRANS_SRC_ACC_NO="TransSrcAccNo";
	String TRANS_DEST_ACC_NO="TransDestAccNo";
	String TRANS_OWNER="TransOwner";
	String TRANS_AMOUNT="TransAmount";
	String TRANS_RESULT="TransResult";
	
//Request
	String REQUEST_FIELD_ID="UserfieldId";
	String REQUEST_FIELD_VALUE="FieldValue"; 
	String REQUEST_STATUS="Status";
	
//Session (TABLE)
	String SESSION_KEY="SessionKey";	
	String SESSION_START="SessionStart";
	String SESSION_END="SessionEnd";
	String SESSION_REQUEST="SessionRequest";
	String SESSION_TIMEOUT="SessionTimeout";
	String SESSION_OTP="SessionOTP";

//Device
	String DEVICE_ID="DeviceId";
	
//Credit
	String CREDIT_ACCOUNT_ID="CreditAccId";
	String CREDIT_LIMIMT="CreditLimit";
}
