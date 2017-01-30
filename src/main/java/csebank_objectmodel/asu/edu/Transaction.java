package csebank_objectmodel.asu.edu;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Transaction {
	
	private int transId;
	private String transType;
	private String transDescription;
	private String transStatus;
	private String transSrcAccNo;
	private String transDestAccNo;
	private String transOwner;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd,HH:mm", timezone="CET")
	private Timestamp transTimestamp;
	private String transApprovedBy;
	private int transAmount;
	private String transComments;
	private String transResult;
	private String transDestEmail;
	private String transDestPhone;
	private String transSrcEmail;
	private String transSrcPhone;
	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Transaction(int transId, String transType, String transDescription, String transStatus, String transSrcAccNo,
			String transDestAccNo, String transOwner, Timestamp transTimestamp, String transApprovedBy, int transAmount,
			String transComments, String transResult, String transDestEmail, String transDestPhone,
			String transSrcEmail, String transSrcPhone) {
		super();
		this.transId = transId;
		this.transType = transType;
		this.transDescription = transDescription;
		this.transStatus = transStatus;
		this.transSrcAccNo = transSrcAccNo;
		this.transDestAccNo = transDestAccNo;
		this.transOwner = transOwner;
		this.transTimestamp = transTimestamp;
		this.transApprovedBy = transApprovedBy;
		this.transAmount = transAmount;
		this.transComments = transComments;
		this.transResult = transResult;
		this.transDestEmail = transDestEmail;
		this.transDestPhone = transDestPhone;
		this.transSrcEmail = transSrcEmail;
		this.transSrcPhone = transSrcPhone;
	}
	public int getTransId() {
		return transId;
	}
	public void setTransId(int transId) {
		this.transId = transId;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getTransDescription() {
		return transDescription;
	}
	public void setTransDescription(String transDescription) {
		this.transDescription = transDescription;
	}
	public String getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
	public String getTransSrcAccNo() {
		return transSrcAccNo;
	}
	public void setTransSrcAccNo(String transSrcAccNo) {
		this.transSrcAccNo = transSrcAccNo;
	}
	public String getTransDestAccNo() {
		return transDestAccNo;
	}
	public void setTransDestAccNo(String transDestAccNo) {
		this.transDestAccNo = transDestAccNo;
	}
	public String getTransOwner() {
		return transOwner;
	}
	public void setTransOwner(String transOwner) {
		this.transOwner = transOwner;
	}
	public Timestamp getTransTimestamp() {
		return transTimestamp;
	}
	public void setTransTimestamp(Timestamp transTimestamp) {
		this.transTimestamp = transTimestamp;
	}
	public String getTransApprovedBy() {
		return transApprovedBy;
	}
	public void setTransApprovedBy(String transApprovedBy) {
		this.transApprovedBy = transApprovedBy;
	}
	public int getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(int transAmount) {
		this.transAmount = transAmount;
	}
	public String getTransComments() {
		return transComments;
	}
	public void setTransComments(String transComments) {
		this.transComments = transComments;
	}
	public String getTransResult() {
		return transResult;
	}
	public void setTransResult(String transResult) {
		this.transResult = transResult;
	}
	public String getTransDestEmail() {
		return transDestEmail;
	}
	public void setTransDestEmail(String transDestEmail) {
		this.transDestEmail = transDestEmail;
	}
	public String getTransDestPhone() {
		return transDestPhone;
	}
	public void setTransDestPhone(String transDestPhone) {
		this.transDestPhone = transDestPhone;
	}
	public String getTransSrcEmail() {
		return transSrcEmail;
	}
	public void setTransSrcEmail(String transSrcEmail) {
		this.transSrcEmail = transSrcEmail;
	}
	public String getTransSrcPhone() {
		return transSrcPhone;
	}
	public void setTransSrcPhone(String transSrcPhone) {
		this.transSrcPhone = transSrcPhone;
	}

}