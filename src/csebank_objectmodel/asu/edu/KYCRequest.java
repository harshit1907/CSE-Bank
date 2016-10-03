package csebank_objectmodel.asu.edu;

public class KYCRequest {
	private String UserfieldId;
	private String UserId;
	private String FieldValue;
	private String Status;
	
	//KYCRequest table constructors
	public KYCRequest() {
		this.UserfieldId = null;
		this.UserId = null;
		this.FieldValue = null;
		this.Status = null;
	}
	
	public KYCRequest(String userfieldId, String userId, String fieldValue, String status) {
		this.UserfieldId = userfieldId;
		this.UserId = userId;
		this.FieldValue = fieldValue;
		this.Status = status;
	}

	//userFieldId getter setter functions
	public String getUserfieldId() {
		return UserfieldId;
	}

	public void setUserfieldId(String userfieldId) {
		UserfieldId = userfieldId;
	}

	//userId getter setter functions
	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}
	
	//fieldvalue getter setter functions
	public String getFieldValue() {
		return FieldValue;
	}

	public void setFieldValue(String fieldValue) {
		FieldValue = fieldValue;
	}
	
	//status getter setter functions
	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	
}