package csebank_objectmodel.asu.edu;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Session {
	private String sessionKey;
	private String userId;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd,HH:mm", timezone="CET")
	private Timestamp sessionStart;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd,HH:mm", timezone="CET")
	private Timestamp sessionEnd;
	private String sessionRequest;
	private String sessionTimeout;
	private String sessionOTP;
		
	
	public Session() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Session(String sessionKey, String userId, Timestamp sessionStart, Timestamp sessionEnd,
			String sessionRequest, String sessionTimeout, String sessionOTP) {
		this.sessionKey = sessionKey;
		this.userId = userId;
		this.sessionStart = sessionStart;
		this.sessionEnd = sessionEnd;
		this.sessionRequest = sessionRequest;
		this.sessionTimeout = sessionTimeout;
		this.sessionOTP = sessionOTP;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Timestamp getSessionStart() {
		return sessionStart;
	}
	public void setSessionStart(Timestamp sessionStart) {
		this.sessionStart = sessionStart;
	}
	public Timestamp getSessionEnd() {
		return sessionEnd;
	}
	public void setSessionEnd(Timestamp sessionEnd) {
		this.sessionEnd = sessionEnd;
	}
	public String getSessionRequest() {
		return sessionRequest;
	}
	public void setSessionRequest(String sessionRequest) {
		this.sessionRequest = sessionRequest;
	}
	public String getSessionTimeout() {
		return sessionTimeout;
	}
	public void setSessionTimeout(String sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}
	public String getSessionOTP() {
		return sessionOTP;
	}
	public void setSessionOTP(String sessionOTP) {
		this.sessionOTP = sessionOTP;
	}
	

}