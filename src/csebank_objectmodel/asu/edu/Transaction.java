package csebank_objectmodel.asu.edu;

import java.sql.Timestamp;

public class Transaction {
	
	private int transId;
	private String transType;
	private String transDescription;
	private String transStatus;
	private String transSrcAccno;
	private String transDestAccno;
	private String transOwner;
	private Timestamp transTimestamp;
	private String transApprovedBy;
	private int transAmount;
	private String transComments;
	private String transResult;
	
	public Transaction(int transId, String transType, String transDescription, String transStatus, String transSrcAccno,
			String transDestAccno, String transOwner, Timestamp transTimestamp, String transApprovedBy, int transAmount,
			String transComments, String transResult) {
		this.transId = transId;
		this.transType = transType;
		this.transDescription = transDescription;
		this.transStatus = transStatus;
		this.transSrcAccno = transSrcAccno;
		this.transDestAccno = transDestAccno;
		this.transOwner = transOwner;
		this.transTimestamp = transTimestamp;
		this.transApprovedBy = transApprovedBy;
		this.transAmount = transAmount;
		this.transComments = transComments;
		this.transResult = transResult;
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
	public String getTransSrcAccno() {
		return transSrcAccno;
	}
	public void setTransSrcAccno(String transSrcAccno) {
		this.transSrcAccno = transSrcAccno;
	}
	public String getTransDestAccno() {
		return transDestAccno;
	}
	public void setTransDestAccno(String transDestAccno) {
		this.transDestAccno = transDestAccno;
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
}