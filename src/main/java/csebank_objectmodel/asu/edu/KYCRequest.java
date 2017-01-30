package csebank_objectmodel.asu.edu;

public class KYCRequest {
	private String userFieldId;
	private String userId;
	private String fieldValue;
	private String status;
	private String kycid;
	public String getUserFieldId() {
		return userFieldId;
	}
	public void setUserFieldId(String userFieldId) {
		this.userFieldId = userFieldId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getKycid() {
		return kycid;
	}
	public void setKycid(String kycid) {
		this.kycid = kycid;
	}
	public KYCRequest(String userFieldId, String userId, String fieldValue, String status, 
			String kycid) {
		super();
		this.userFieldId = userFieldId;
		this.userId = userId;
		this.fieldValue = fieldValue;
		this.status = status;
		this.kycid = kycid;
	}
	public KYCRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}