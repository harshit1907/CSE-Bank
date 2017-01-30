package csebank_utility.asu.edu;

public  interface DBQueries {

	String addTransaction1="INSERT INTO Transaction(transTimestamp,transId,transType,transDescription,transStatus,transSrcAccNo,transDestAccNo,transOwner,transAmount,transResult,transComments,transApprovedBy) VALUES (UNIX_TIMESTAMP(NOW()),?,?,?,?,?,?,?,?,?,?,?)";
	String addTransaction2="SELECT accountId FROM Account WHERE userId=(SELECT userId FROM User WHERE email=?) and accType='CHECKING';";
	String addTransaction3="SELECT accountId FROM Account WHERE userId=(SELECT userId FROM User WHERE phoneNumber=?) and accType='CHECKING';";

	String getTransactionOnTransId="SELECT * FROM Transaction where transId=?";
	String getTransactionListOnTransStatus="SELECT * FROM Transaction WHERE transStatus=?";
	String getTransactionListOnTransType="SELECT * FROM Transaction WHERE transType=? and transStatus='PENDING'";
	String getTransactionListOnAccountId="SELECT * FROM Transaction T WHERE T.transSrcAccNo=? OR T.transDestAccNo=?";
	String getTransactionListOnTransOwner="SELECT * FROM Transaction where transOwner=?";
	String getTransactionListOnTransAprrovedBy="SELECT * FROM Transaction where transOwner=?";
	String getTransactionListOnTransTimeStamp="SELECT * FROM Transaction where transTimestamp>=?";
	String approveTransaction="UPDATE Transaction SET transTimestamp=UNIX_TIMESTAMP(NOW()),transStatus=?,transApprovedBy=?,transComments=? where transId=?";
	
	String getTransactionListOnTransTimeStampTransSrcAct="SELECT * FROM Transaction where  transSrcAccNo=? and transDestAccNo=? and transTimestamp>=?";
	
	
	String addNewUser="INSERT INTO User(UserId,UserRole,FirstName,LastName,PhoneNumber,Email,Address,SSN,DOB,Password, UserStatus, LoginAttemptNumber, SecurityQn1, SecurityAns1, SecurityQn2, SecurityAns2, Organization) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	String addAccountLogEntry="INSERT INTO AccountLog SELECT T.TransId, A.AccBalance, A.AccountId FROM Account A INNER JOIN Transaction T ON A.AccountId=T.TransDestAccNo OR A.AccountId=T.TransSrcAccNo WHERE A.AccountId = ? AND T.TransId = ?";
	String getAccountLogEntries="SELECT * FROM AccountLog where accountId=?;";
	String getAcoountBalance="Select accountBalance from Account where accountId=?";
	String addAccount="INSERT INTO Account (accOpenDate, accountId, UserId, accType, accBalance, accStatus) VALUES (UNIX_TIMESTAMP(NOW()),?, ?, ?, ?, 'active');";
	String deleteAccount="UPDATE Account A SET A.accStatus='closed' WHERE A.accountId=?";
	String updateQuery="UPDATE Account A SET A.accStatus='blocked' WHERE A.accountId=?";
	String getAccountBalance="SELECT accBalance FROM Account WHERE accountId=?"; 
	String getAccountDetailsOnAccId="SELECT * FROM Account where accountId=?";
	String getAccountListOnUserId="SELECT * FROM Account where userId=? and accStatus='active'";
	String checkAccountStatus="SELECT accStatus FROM Account where accountId=?";
	String addCreditAccount_CreditAccInsertQuery="INSERT INTO Credit(UserId,CreditAccId,CreditLimit,CreditBalance) VALUES (?,?,?,?)";
	String setCreditLimit="UPDATE Credit SET creditLimit=? where userId=?";
	String addDevice="INSERT INTO Device(deviceId,userId) VALUES (?,?)";
	String getDeviceDetailsOnUserId="SELECT userId,deviceId FROM Device WHERE userId=?";
	String getDeviceDetailsOnDeviceId="SELECT UserId,DeviceId FROM Device WHERE deviceId=?";
	String checkDeviceDetails="SELECT userId,deviceId from Device where deviceId=? AND userId=?;";
	String getPendingKycReq="SELECT userFieldId, userId, fieldValue, status, kycid FROM KYCRequest WHERE status=?;";
	String getDob="SELECT DOB FROM User usr, Account acct WHERE acct.accountId=? and acct.userId=usr.userId;";
	String pdfGen="SELECT trans.transtimestamp,trans.transsrcaccno,trans.transdestaccno,acctlog.accountbalance FROM AccountLog acctlog,Transaction trans where trans.transid=acctlog.transid and AccountId=? order by trans.transtimestamp";
	String accBalSql="SELECT accBalance FROM Account WHERE accountId=?";
	String updateAccountBalance="UPDATE Account SET accBalance=? WHERE accountId=?";
	String updateCreditBalance="UPDATE Credit SET creditBalance=? WHERE creditAccId=?";
    String getCreditBalance="SELECT CreditBalance FROM Credit WHERE creditAccId=?";
    
    String getUserListOnUserRole="SELECT * FROM User where UserRole=?";
    String  validateLoginCredentials="SELECT UserId, Password FROM User where UserId=?";
    String checkSecurityAns1="SELECT SecurityAns1 FROM User where UserId=?";
    String checkSecurityAns2="SELECT SecurityAns2 FROM User where UserId=?";
    String setUserStatus="UPDATE User SET UserStatus=? where UserId=?";
    String updateLoginAttempt="UPDATE User SET LoginAttemptNumber=? where UserId=?";
    String modifyUserDetails_email="UPDATE User SET email=? where UserId=?";
    String modifyUserDetails_phoneNumber="UPDATE User SET phoneNumber=? where UserId=?";
    String getLoginAttempt="SELECT loginAttemptNumber FROM User WHERE UserId=?";
    String getUserDetails="SELECT * FROM User WHERE userId=?";
    String addOtp="insert into otptab(UserId,otp,otpTimeStamp) VALUES(?,?,UNIX_TIMESTAMP(NOW()))";
   String addKycReq="INSERT INTO KYCRequest(userId,userFieldId,fieldValue,status,kycid) VALUES (?,?,?,?,?);";
     String isOTPValid="SELECT OTP from otptab WHERE OTP=? and UserId=? and (UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(OTPTimeStamp))/60<10";
	String getSecurityqn = "SELECT securityqn1,securityqn2 from User where UserId=?";
	String getEmailandPhone = "SELECT userid from User where email=? or phoneNumber=?";

	String getEmailonUserId = "SELECT U.email FROM User U INNER JOIN Account A ON A.userId=U.userId WHERE A.accountId=?";;
	String updateStatus="UPDATE KYCRequest SET status=? WHERE userId=? and fieldValue=?;"; 
	String modifyUserDetails_password = "UPDATE User SET Password=? where UserId=?"; 
	
	String getTransactionListOnTransTypeforTransfer="SELECT * FROM Transaction WHERE transType=? and transStatus='PENDING' and transSrcAccNo<>? and transDestAccNo<>?";
	String getTransactionListOnTransTypeforWithdraw="SELECT * FROM Transaction WHERE transType=? and transStatus='PENDING' and (transSrcAccNo=? or transDestAccNo=?)";
	
}