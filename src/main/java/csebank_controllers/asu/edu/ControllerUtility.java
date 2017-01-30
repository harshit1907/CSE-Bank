package csebank_controllers.asu.edu;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.web.bind.annotation.ResponseStatus;

import csebank_objectmodel.asu.edu.*;
import csebank_utility.asu.edu.DbParamNams;
import exception.csebank_controllers.asu.edu.CustomInvalidArgumetException;
import exception.csebank_controllers.asu.edu.OperationNotPermitted;
/**
 * Validator
 * @author Aravind
 *
 */
public class ControllerUtility implements DbParamNams{
	//constants
	//should be configurable
	static private final int USER_ID_MIN_LENGTH=6;
	static private final int SECURITY_ANSWER_MIN_LENGTH=6;
	static private final int SECURITY_QUESTION_MIN_LENGTH=12;
	static public final int LOGIN_ATTEMPT_MAX_NUM=5;
	static private final int OTP_LENGTH=6, MIN_ACCOUNT_ID_LEN=1;
	static private final int ORG_MAX_LENGTH=30, KYC_ID_LEN=8, 
			MAX_ALLOWED_TRANSACTION=10000;
	
	static private final EmailValidator EMAIL_VALIDATOR= EmailValidator.getInstance(false);
	
	static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat TIME_FORMAT=new SimpleDateFormat("yyyy-MM-dd,HH:mm");
	//Date when I wrote the this field :D
	@SuppressWarnings("deprecation")
	static Date START_DATE=new Date(2016-1900, 9, 9);
	@SuppressWarnings("deprecation")
	static Timestamp START_TIME=new Timestamp(2016-1900, 9, 9, 0, 0, 0, 0);
	static private String userNameExp, otpRegex, accIDRegex, kycIDRegex;
	static {
		userNameExp=String.format("^[a-zA-Z]([A-Za-z]|[0-9]){%d,}$", USER_ID_MIN_LENGTH-1);
		otpRegex=String.format("^\\d{%d}$", OTP_LENGTH);
		accIDRegex=String.format("^\\d{%d,}$", MIN_ACCOUNT_ID_LEN);
		kycIDRegex=String.format("^\\d{%d}$", KYC_ID_LEN);
		
	}
	
	public static  void validateUserID(String name){
		if(name== null|| !name.matches(userNameExp)){
			throw new CustomInvalidArgumetException ("Invalid User ID.");
		}
	}
	
	static  void validateUserRole(String role){		
		switch(role==null?"Null":role){
		case USER_ROLE_ADMIN:
		case USER_ROLE_CUSTOMER:
		case USER_ROLE_EMPLOYEE:
		case USER_ROLE_MANANEGR:
		case USER_ROLE_MERCHANT:
			break;
		default:
			throw new CustomInvalidArgumetException ("Invalid User Role.");
		}
	}
	
	 static void validateUserPassword(String password){
        //Minimum 6 characters at least 1 Alphabet, 1 Number and 1 Special Character:
        if(password == null || !password.matches("^(?=.*[a-zA-Z])(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{6,}$")){
            throw new CustomInvalidArgumetException ("Insecure password. Password should have a number, a special character($@$!%*#?&) and minumum length of 6");    
        }                   
    }
	 
	public static void validateUserPassword(User user, String password) {
		//validateUserPassword(user.getPassword());
		String SSN = Integer.toString(user.getSSN());
		if((!user.getEmail().isEmpty() && password.contains(user.getEmail())) 
				|| !user.getOrganization().isEmpty() && password.contains(user.getOrganization()) 
				|| !user.getDob().toString().isEmpty() && password.contains(user.getDob().toString()) 
				|| !SSN.isEmpty() && password.contains(SSN) 
				|| !user.getSecurityAns1().isEmpty() && password.contains(user.getSecurityAns1()) 
				|| !user.getSecurityAns2().isEmpty() && password.contains(user.getSecurityAns2()))
		{
			throw new CustomInvalidArgumetException ("Insecure password. Password should not include/match other user details");
		}
	}
	
	static  void validateUserEmailID(String emailID){
		//@localhost email id will be invalidated
		//user@[10.10.10.10] is still valid - defect with the utility not our problem
		if(emailID == null|| !EMAIL_VALIDATOR.isValid(emailID)){
			throw new CustomInvalidArgumetException ("Invalid User Email ID.");
		}
	}
	
	static  void validateUserPhoneNum(String phone){		
		if(phone == null || !phone.matches("\\d{10}")){
			try{Integer.parseInt(phone);} catch(Exception e){
				throw new CustomInvalidArgumetException ("Invalid User phone number.");
			}
			throw new CustomInvalidArgumetException ("Invalid User phone number.");
		}
	}
	
	static  void validateUserSSN(int ssn){		
		if(!(ssn>=100000000 && ssn <= 999999999)){
			throw new CustomInvalidArgumetException ("Invalid SSN. SSN should have valid 9 digits.");
		}
	}
	
	static  void validateUserName(String first, String last){
		if(first==null || !first.matches("[a-zA-Z]+")){
			throw new CustomInvalidArgumetException ("Invalid User First Name.");
		}
		if(last==null || !last.matches("[a-zA-Z]+")){
			throw new CustomInvalidArgumetException ("Invalid User Last Name.");
		}
	}
	static void validateUserOrg(String org){
		//optional argument
		if(org!=null && org.length()>ORG_MAX_LENGTH){
			throw new CustomInvalidArgumetException ("Invalid User Organisation.");
		}
	}
	static void validateUserAddress(String org){
		if(org==null || org.isEmpty()){
			throw new CustomInvalidArgumetException ("Invalid User Address.");
		}
	}
	static void validateUserStatus(String status){
		switch(status==null?"Null":status){
		case USER_STATUS_ACTIVE:			
		case USER_STATUS_SUSPENDED:
			break;
		default:
			throw new CustomInvalidArgumetException ("Invalid User Status.");
		}
	}
	public static void validateUser(User user) {
		//try{
		validateUserID(user.getUserId());
		validateUserRole(user.getUserRole());
		validateUserEmailID(user.getEmail());
		validateUserName(user.getFirstName(), user.getLastName());
		validateUserPassword(user.getPassword());
		validateUserPassword(user, user.getPassword());
		validateUserOrg(user.getOrganization());
		validateUserDOB(user.getDob());
		validateUserAddress(user.getAddress());
		validateUserLoginAttempt(user.getLoginAttemptNumber());
		validateUserSSN(user.getSSN());
		validateUserSecQn(user.getSecurityQn1());
		validateUserSecQn(user.getSecurityQn2());
		validateUserSecAns(user.getSecurityAns1());
		validateUserSecAns(user.getSecurityAns2());
		validateUserStatus(user.getUserStatus());
//		} catch(Exception e){
//			throw new CustomInvalidArgumetException(e.getMessage());
//		}
	}

	/**
	 * This method returns HashMap of key,value pair if given an object with Proper getters and setters 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	static  HashMap<String, String> getUserMap(User user){
		HashMap<String, String> toReturn=new HashMap<>();
		if(user==null) return toReturn;
		if(user.getAddress()!=null){
			toReturn.put(USER_ADDRESS, user.getAddress());
		}
		if(user.getDob()!=null){			
			toReturn.put(USER_DOB, DATE_FORMAT.format(user.getDob()));
		}
		if(user.getEmail()!=null){
			toReturn.put(USER_EMAIL, user.getEmail());
		}
		if(user.getFirstName()!=null){
			toReturn.put(USER_FIRST_NAME, user.getFirstName());
		}
		if(user.getLastName()!=null){
			toReturn.put(USER_LAST_NAME, user.getLastName());
		}
		if(user.getLoginAttemptNumber()!=null){
			toReturn.put(USER_LOGIN_ATTEMPT, user.getLoginAttemptNumber());
		}
		if(user.getOrganization()!=null){
			toReturn.put(USER_ORGANISATION, user.getOrganization());
		}
		if(user.getPassword()!=null){
			toReturn.put(USER_PASSWORD, user.getPassword());
		}
		if(user.getPhoneNumber()!=null){
			toReturn.put(USER_PHONE, user.getPhoneNumber());
		}
		if(user.getSecurityAns1()!=null){
			toReturn.put(USER_SEC_ANS1, user.getSecurityAns1());
		}
		if(user.getSecurityAns2()!=null){
			toReturn.put(USER_SEC_ANS2, user.getSecurityAns2());
		}
		if(user.getSecurityQn1()!=null){
			toReturn.put(USER_SEC_QN1, user.getSecurityQn1());
		}
		if(user.getSecurityQn2()!=null){
			toReturn.put(USER_SEC_QN2, user.getSecurityQn2());
		}
		if(user.getSSN()!=0){
			toReturn.put(USER_SSN, Integer.toString(user.getSSN()));
		}
		if(user.getUserId() != null){
			toReturn.put(USER_ID, user.getUserId());
		}
		if(user.getPassword() != null){
			toReturn.put(USER_PASSWORD, user.getPassword());
		}
		if(user.getUserRole()!=null){
			toReturn.put(USER_ROLE, user.getUserRole());
		}
		if(user.getUserStatus()!=null){
			toReturn.put(USER_STATUS, user.getUserStatus());
		}
		return toReturn;
	}
	
	@SuppressWarnings("deprecation")
	 static void validateUserDOB(Date dob) {
		int birtYear=dob.getYear();
		Date curentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());		
		int currentYear=curentDate.getYear();		
		if(currentYear -birtYear < 16 || currentYear - birtYear > 120){
			throw new CustomInvalidArgumetException("Invalid data of birth, user must be above 15 years old and less than 120 years old.");
		}
		
	}

	 static void validateUserLoginAttempt(String loginAttemptNumber) {
//		int attempts=Integer.parseInt(loginAttemptNumber);
//		if(!(attempts>=0 && attempts<=LOGIN_ATTEMPT_MAX_NUM)){
//			throw new CustomInvalidArgumetException ("Invalid login attempt number.");
//		}
		 //if(loginAttemptNumber!=null)
		//	 throw new CustomInvalidArgumetException ("Client should not send login Attempt Number.");
	}

	 static void validateUserSecQn(String securityQn1) {		
		if(securityQn1==null || securityQn1.length()<SECURITY_QUESTION_MIN_LENGTH){
			throw new CustomInvalidArgumetException ("Invalid Security question.");
		}		
	}

	 static void validateUserSecAns(String securityAns1) {
		if(securityAns1 == null || securityAns1.length() < SECURITY_ANSWER_MIN_LENGTH){
			throw new CustomInvalidArgumetException ("Invalid Security answer.");
		}
	}
	 
	 static void sensorUserObject(User user){
		 user.setPassword(null);
		 user.setLoginAttemptNumber(null);
		 user.setSecurityAns1(null);
		 user.setSecurityAns2(null);
		 user.setSecurityQn1(null);
		 user.setSecurityQn2(null);
		 //user.setUserStatus(null);
	 }
	 
	 /**********************KYC Request***************************/
	 static void validateUserField(String userfieldId, String fieldValue) {
		if(userfieldId!=null && userfieldId.equals(USER_PHONE))
			validateUserPhoneNum(fieldValue);
		else if(userfieldId!=null && userfieldId.equals(USER_EMAIL))		
			validateUserEmailID(fieldValue);
		else
			throw new CustomInvalidArgumetException("Invalid field value in the request.");				
	}
	
	 static void validateRequest(KYCRequest request, boolean newRequest) {
		validateUserID(request.getUserId());
		validateUserField(request.getUserFieldId(), request.getFieldValue());					
		if(!newRequest){
			validateRequestStatus(request.getStatus());
			validateKycID(request.getKycid());
		} else{
			if(request==null || !request.getStatus().equals(REQUEST_PENDING_VALUE))
				throw new CustomInvalidArgumetException("Invalid request status.");
		}
	}
	
	 private static void validateRequestStatus(String status) {
		switch(status==null?"Null":status){
		case REQUEST_APPROVED_VALUE:
		case REQUEST_REJECTED_VALUE:
			break;
		default:
			throw new CustomInvalidArgumetException("Invalid request status.");
		}
		
	}
	static void validateKycID(String accountId) {
			System.out.println("kyc id is "+accountId);
			if(!accountId.matches(kycIDRegex)){
				throw new CustomInvalidArgumetException("Invalid KYC ID.");
			}			
	}
	 
	static HashMap<String, String> getKYCMap(KYCRequest request){
		HashMap <String, String> ret=new HashMap<>();
		if(request.getFieldValue()!=null){
			ret.put(REQUEST_FIELD_VALUE, request.getFieldValue());
		}
		if(request.getUserId()!=null){
			ret.put(USER_ID, request.getUserId());
		}
		if(request.getStatus()!=null){
			ret.put(REQUEST_STATUS, request.getStatus());
		}
		if(request.getUserFieldId()!=null){
			ret.put(REQUEST_FIELD_ID, request.getUserFieldId());//todo put proper constants
		}if(request.getKycid()!=null){
			ret.put(REQUEST_ID, request.getKycid());
		}
		return ret;
	}
	
	 /************************** Account Validation********************/
	static HashMap<String, String> getAccountMap(Account account){
		HashMap <String, String>toRet=new HashMap<>();
		if(account == null) return toRet;
		if(account.getAccBalance()!=null)
			toRet.put(ACCOUNT_BALANCE, account.getAccBalance());
		//if(account.getAccOpenDate() !=null) //TODO uncomments this
			//toRet.put(ACCOUNT_OPEN_DATE	, DATE_FORMAT.format(account.getAccOpenDate()));
		if(account.getAccountId()!=null)
			toRet.put(ACCOUNT_ID, account.getAccountId());
		if(account.getAccountStatus()!=null)
			toRet.put(ACCOUNT_STATUS, account.getAccountStatus());
		if(account.getAccType()!=null)
			toRet.put(ACCOUNT_TYPE, account.getAccType());
		return toRet;
	}
	
	static void valdiateAccount(Account account){
		validateAccBalance(account.getAccBalance());
		//validateAccOpenDate(account.getAccOpenDate());		
		//validateAccStatus(account.getAccountStatus());
		validateAccType(account.getAccType());			
		validateAccBalance(account.getAccBalance());
	}
	
	static void validateAccType(String accType) {
		switch(accType==null?"Null":accType){
		case ACCOUNT_TYPE_CHECKING:
		case ACCOUNT_TYPE_CREDIT:
		case ACCOUNT_TYPE_SAVINGS:
			break;
		default:
			throw new CustomInvalidArgumetException("Invalid account type.");
		}		
	}
	
	static void validateAccStatus(String accountStatus) {
		switch(accountStatus==null?"Null":accountStatus){
		case ACCOUNT_STATUS_ACTIVE:
		case ACCOUNT_STATUS_BLOCKED:
			break;
		default:
			throw new CustomInvalidArgumetException("Invalid Account status.");
		}		
	}
	
	static void validateAccID(String accountId) {
		if(!accountId.matches(accIDRegex) && !accountId.equalsIgnoreCase(TRANS_SRC_SELF)){
			throw new CustomInvalidArgumetException("Invalid Account ID.");
		}			
	}
	
	static void validateAccOpenDate(Date accOpenDate) {
		if(START_DATE.before(accOpenDate)){
			throw new CustomInvalidArgumetException("Invalid Account open date.");
		}		
	}
	
	static void validateAccBalance(String accBalance) {
		try{
			double d=Double.parseDouble(accBalance);//if its a double let it pass
			if(d<0 || d>1000){
				throw new CustomInvalidArgumetException("Invalid Account balance");
			}
		} catch(Exception e){
			throw new CustomInvalidArgumetException("Invalid Account balance.");
		}				
	}
	
	
	/************************Transaction validator ***********/
	static HashMap<String, String> getTransactionMap(Transaction trans){
		HashMap<String, String> ret=new HashMap<>();
		if(trans.getTransApprovedBy()!=null)
			ret.put(TRANS_APPROVEDBY , trans.getTransApprovedBy());
		ret.put(TRANS_AMOUNT, Integer.toString(trans.getTransAmount()));
		if(trans.getTransComments()!=null)
			ret.put(TRANS_COMMENTS, trans.getTransComments());
		if(trans.getTransDescription()!=null)
			ret.put(TRANS_DESC, trans.getTransDescription());
		if(trans.getTransDestAccNo()!=null)
			ret.put(TRANS_DEST_ACC_NO, trans.getTransDestAccNo());
		if(trans.getTransDestEmail()!=null)
			ret.put(DbParamNams.TRANS_DEST_EMAIL,trans.getTransDestEmail());
			 if(trans.getTransDestPhone()!=null)
				 ret.put(DbParamNams.TRANS_DEST_PHONE,trans.getTransDestPhone());
			 if(trans.getTransSrcEmail()!=null)
					ret.put(DbParamNams.TRANS_SRC_EMAIL,trans.getTransSrcEmail());
					 if(trans.getTransSrcPhone()!=null)
						 ret.put(DbParamNams.TRANS_SRC_PHONE,trans.getTransSrcPhone());
		ret.put(TRANS_ID, Integer.toString(trans.getTransId()));
		if(trans.getTransOwner()!=null)
			ret.put(TRANS_OWNER, trans.getTransOwner());
		if(trans.getTransResult()!=null)
			ret.put(TRANS_RESULT, trans.getTransResult());
		if(trans.getTransSrcAccNo()!=null)
			ret.put(TRANS_SRC_ACC_NO, trans.getTransSrcAccNo());
		if(trans.getTransStatus()!=null)
			ret.put(TRANS_STATUS, trans.getTransStatus());
		if(trans.getTransTimestamp()!=null)
			ret.put(TRANS_TIMESTAMP, TIME_FORMAT.format(trans.getTransTimestamp()));
		if(trans.getTransType()!=null)
			ret.put(TRANS_TYPE, trans.getTransType());		
		return ret;
	}
	
	
	static void validateNewTransaction(Transaction trans){
		
		validateAccID(trans.getTransSrcAccNo());
		validateTransAmount(Integer.toString(trans.getTransAmount()));
		
		if(trans.getTransDestAccNo()!=null)
			validateAccID(trans.getTransDestAccNo());
		}
	

	static void validateExistingTransaction(Transaction trans){
		
		validateAccID(trans.getTransSrcAccNo());
		validateTransAmount(Integer.toString(trans.getTransAmount()));
		validateAccID(trans.getTransDestAccNo());
		validateTransId(Integer.toString(trans.getTransId()));
		
		validateTransStatus(trans.getTransStatus());
		//validateTransType(trans.getTransType());
	}
	
static void validateExistingForAddwithdrawTransaction(Transaction trans){
		
		if(trans.getTransSrcAccNo()!=DbParamNams.TRANS_SRC_SELF)
			validateAccID(trans.getTransSrcAccNo());
		validateTransAmount(Integer.toString(trans.getTransAmount()));
		if(trans.getTransSrcAccNo()!=DbParamNams.TRANS_SRC_SELF)
			validateAccID(trans.getTransDestAccNo());
		
		validateTransId(Integer.toString(trans.getTransId()));
		
		validateTransStatus(trans.getTransStatus());
		}
	
	
	
	static  void validateTransId(String transId){
		try{	
			Double temp = Double.parseDouble(transId);
		}
		catch(Exception e){
			throw new CustomInvalidArgumetException ("Invalid transaction ID.");
		}
			if(transId == null || transId.length()!=8 ){
			throw new CustomInvalidArgumetException ("Invalid transaction ID.");
		}
	}
	
	static void validateTransAmount(String transAmountS) {
		try{
			Double transAmount=Double.parseDouble(transAmountS);//if its a double let it pass
			if(transAmount <=0 || transAmount>MAX_ALLOWED_TRANSACTION)
				throw new CustomInvalidArgumetException("Invalid transaction amount.");
		} catch(Exception e){
			throw new CustomInvalidArgumetException("Invalid transaction amount.");
		}				
	}
	
	 static void validateTransType(String transType) {
		switch(transType==null?"Null":transType){
		case TRANS_TYPE_CRITICAL:
		case TRANS_TYPE_NON_CRITICAL:
			break;
		default:
			throw new CustomInvalidArgumetException("Invalid Transaction type.");
		}		
	}
	
	 //Ashutosh
	 static void validateTransOwner(String transOwner) {
			switch(transOwner==null?"Null":transOwner){
			case USER_ROLE_EMPLOYEE:
			case USER_ROLE_MANANEGR:
				break;
			default:
				throw new CustomInvalidArgumetException("Invalid Transaction owner.");
			}		
		}
	 
	 static void validateTimeStamp(Timestamp transTimestamp) {
		if(START_TIME.before(transTimestamp))
			throw new CustomInvalidArgumetException("Invalid Transaction time stamp.");
		
	}
	 static void validateTransStatus(String transStatus) {
		switch(transStatus==null?"Null":transStatus){
			case PENDING_VALUE:
			case PENDING_MERCHANT_VALUE:
			case APPROVED_VALUE:
			case REJECTED_VALUE:
				break;
			default:
				throw new CustomInvalidArgumetException("Invalid status type.");
		}		
	}
	
	private static void validateTransResult(String transResult) {
		// TODO Auto-generated method stub		
	}
	
	/************************Session********************************/
	
	static HashMap<String, String> getSessionMap(Session ses){
		HashMap<String , String> ret=new HashMap<>();
		if(ses.getSessionKey()!=null)
			ret.put(SESSION_KEY, ses.getSessionKey());
		if(ses.getSessionEnd()!=null)
			ret.put(SESSION_END, TIME_FORMAT.format(ses.getSessionEnd()));
		if(ses.getSessionOTP()!=null)
			ret.put(SESSION_OTP, ses.getSessionOTP());
		if(ses.getSessionRequest()!=null)
			ret.put(SESSION_REQUEST, ses.getSessionRequest());
		if(ses.getSessionStart()!=null)
			ret.put(SESSION_START, TIME_FORMAT.format(ses.getSessionStart()));
		if(ses.getSessionTimeout()!=null)
			ret.put(SESSION_TIMEOUT, ses.getSessionTimeout());
		if(ses.getUserId()!=null)
			ret.put(USER_ID, ses.getUserId());		
		return ret;
	}
	
	static void timeSession(Session ses){
		
		validateSessionKey(ses.getSessionKey());
		validateTimeStamp(ses.getSessionEnd());
		validateTimeStamp(ses.getSessionStart());
		validateOTP(ses.getSessionOTP());
		validateSessionRequest(ses.getSessionRequest());
		validateUserID(ses.getUserId());
	}
	
	private static void validateSessionRequest(String sessionRequest) {
		// TODO Auto-generated method stub
		
	}
	private static void validateSessionKey(String sessionKey) {
		// TODO Auto-generated method stub
		
	}
	static void validateOTP(String sessionOTP) {
		if(sessionOTP== null || !sessionOTP.matches(otpRegex))
			throw new CustomInvalidArgumetException("Invalid Transaction time stamp.");
	}
	
	/************************Device*******************/
	static HashMap<String , String> getDeviceMap(Device dev){
		HashMap<String , String> ret=new HashMap<String , String>();
		if(dev.getDeviceId()!=null)
			ret.put(DEVICE_ID, dev.getDeviceId());
		if(dev.getUserId()!=null)
			ret.put(USER_ID, dev.getUserId());
		return ret;
	}
	
	static void valdiateDevice(Device dev){
		validateUserID(dev.getUserId());
		validateDeviceID(dev.getDeviceId());
	}
	
	static void validateDeviceID(String deviceId) {
		// TODO Auto-generated method stub	
	}
	
	/*********************Account Log************************/
	
	static HashMap<String, String> getAccountLogMap(AccountLog log){
		HashMap<String , String> ret=new HashMap<String , String>();
		if(log.getAccountId()!=null)
			ret.put(ACCOUNT_ID, log.getAccountId());
		if(log.getTransId()!=null)
			ret.put(TRANS_ID, log.getTransId());
		ret.put(ACCOUNT_BALANCE, Integer.toString(log.getAccountBalance()));
		return ret;
	}
	
	void validateAccLog(AccountLog log){
		validateAccID(log.getAccountId());
		//validateTransID(log.getTransId());
		//validateAccBalance(log.getAccountBalance()); todo
	}
	/********************CreditAccount**************************/
	static HashMap<String, String> getCreditAccount(CreditAccount acc){
		HashMap<String , String> ret=new HashMap<String , String>();
		if(acc.getCreditAccountId()!=null)
			ret.put(CREDIT_ACCOUNT_ID, acc.getCreditAccountId());
		ret.put(CREDIT_BALANCE, acc.getCreditBalance()+"");		
		ret.put(CREDIT_LIMIT, acc.getCreditLimit()+"");
		if(acc.getUserId()!=null)
			ret.put(USER_ID, acc.getUserId());
		return ret;
	}
	
	static void validateCreditAccount(CreditAccount acc){
		validateUserID(acc.getUserId());
		//validateCreditBalance();//todo
		//validateCreditLimit();//todo
		validateAccID(acc.getCreditAccountId());
	}
	
	public static String getCurrentDate(){
		Date date=new Date(System.currentTimeMillis());
		return DATE_FORMAT.format(date);
	}
	
	static void validateUserPermission(String changeeRole, String changerRole) {
		int changee=getPermissionLevel(changeeRole), changer=getPermissionLevel(changerRole);
		if(changer>changee){
			//valid
		} else{
			throw new OperationNotPermitted("Current user is not authorized to process the request");
		}
	}

	static int getPermissionLevel(String role){
		int ret=-1;
		switch(role){
		case DbParamNams.USER_ROLE_ADMIN:
			ret= 5;
			break;
		case DbParamNams.USER_ROLE_MANANEGR:
			ret =4;
			break;
		case DbParamNams.USER_ROLE_EMPLOYEE:
			ret=3;
			break;
		case DbParamNams.USER_ROLE_MERCHANT:
			ret=2;
			break;
		case DbParamNams.USER_ROLE_CUSTOMER:
			ret=1;
			break;			
		}
		return ret;
	}
}