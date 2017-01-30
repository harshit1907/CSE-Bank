package csebank_objectmodel.asu.edu;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OtpTab {

	private  String userId;
	private  String otp;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd,HH:mm", timezone="CET")
	private  Timestamp otpTImeStamp;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public Timestamp getOtpTImeStamp() {
		return otpTImeStamp;
	}
	public void setOtpTImeStamp(Timestamp otpTImeStamp) {
		this.otpTImeStamp = otpTImeStamp;
	}
	public OtpTab(String userId, String otp, Timestamp otpTImeStamp) {
		super();
		this.userId = userId;
		this.otp = otp;
		this.otpTImeStamp = otpTImeStamp;
	}
	public OtpTab() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
