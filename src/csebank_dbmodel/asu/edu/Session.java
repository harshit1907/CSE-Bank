package csebank_dbmodel.asu.edu;

public class Session {
	private String SessionKey;
	private String UserId;
	private String SessionStart;
	private String SessionEnd;
	private String SessionRequest;
	private String SessionTimeout;
	private String SessionOTP;
	
	//Session table constructors
	public Session() {
		this.SessionKey = null;
		this.UserId = null;
		this.SessionStart = null;
		this.SessionEnd = null;
		this.SessionRequest = null;
		this.SessionTimeout = null;
		this.SessionOTP = null;
	}
	
	public Session(String sessionKey, String userId, String sessionStart, String sessionEnd,
			String sessionRequest, String sessionTimeout, String sessionOTP) {
		this.SessionKey = sessionKey;
		this.UserId = userId;
		this.SessionStart = sessionStart;
		this.SessionEnd = sessionEnd;
		this.SessionRequest = sessionRequest;
		this.SessionTimeout = sessionTimeout;
		this.SessionOTP = sessionOTP;
	}

	//session key getter setter functions
	public String getSessionKey() {
		return SessionKey;
	}

	public void setSessionKey(String sessionKey) {
		SessionKey = sessionKey;
	}

	//user id getter setter functions
	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}
	
	//session start getter setter functions
	public String getSessionStart() {
		return SessionStart;
	}

	public void setSessionStart(String sessionStart) {
		SessionStart = sessionStart;
	}
	
	//session end getter setter functions
	public String getSessionEnd() {
		return SessionEnd;
	}

	public void setSessionEnd(String sessionEnd) {
		SessionEnd = sessionEnd;
	}
	
	//session request getter setter functions
	public String getSessionRequest() {
		return SessionRequest;
	}

	public void setSessionRequest(String sessionRequest) {
		SessionRequest = sessionRequest;
	}
	
	//session timeout getter setter functions
	public String getSessionTimeout() {
		return SessionTimeout;
	}

	public void setSessionTimeout(String sessionTimeout) {
		SessionTimeout = sessionTimeout;
	}
	
	//session otp getter setter functions
	public String getSessionOTP() {
		return SessionOTP;
	}

	public void setSessionOTP(String sessionOTP) {
		SessionOTP = sessionOTP;
	}
}
