package csebank_dbmodel.asu.edu;

import java.sql.Timestamp;

public class Transaction {
	
	private int TransId;
	private String TransType;
	private String TransDescription;
	private String TransStatus;
	private String TransSrcAccno;
	private String TransDestAccno;
	private String TransOwner;
	private Timestamp TransTimestamp;
	private String TransApprovedBy;
	private int TransAmount;
	private String TransComments;
	private String TransResult;
	
	//Transaction table constructors
	public Transaction(){
		this.TransId = 0;
		this.TransType = null;
		this.TransDescription = null;
		this.TransStatus = null;
		this.TransSrcAccno = null;
		this.TransDestAccno = null;
		this.TransOwner = null;
		this.TransTimestamp = null;
		this.TransApprovedBy = null;
		this.TransAmount = 0;
		this.TransComments = null;
		this.TransResult = null;
	}
	public Transaction(int transId, String transType, String transDescription, String transStatus,
			String transSrcAccno, String transDestAccNo, String transOwner, Timestamp transTimestamp,
			String transApprovedBy, int transAmount, String transComments, String transResult){
		this.TransId = transId;
		this.TransType = transType;
		this.TransDescription = transDescription;
		this.TransStatus = transStatus;
		this.TransSrcAccno = transSrcAccno;
		this.TransDestAccno = transDestAccNo;
		this.TransOwner = transOwner;
		this.TransTimestamp = transTimestamp;
		this.TransApprovedBy = transApprovedBy;
		this.TransAmount = transAmount;
		this.TransComments = transComments;
		this.TransResult = transResult;
	}
	
	//trans id getter and setter functions
	public int getTransId(){
		return TransId;
	}
	public void setTransId(int transId) {
		TransId = transId;
	}
	
	//trans type getter and setter functions
	public String getTransType(){
		return TransType;
	}
	public void setTransType(String transType) {
		TransType = transType;
	}
	
	//trans description getter and setter functions
	public String getTransDescription(){
		return TransDescription;
	}
	public void setTransDescription(String transDescription) {
		TransDescription = transDescription;
	}
	
	//trans status getter and setter functions
	public String getTransStatus(){
		return TransStatus;
	}
	public void setTransStatus(String transStatus) {
		TransStatus = transStatus;
	}
	
	//trans src accno getter and setter functions
	public String getTransSrcAccno(){
		return TransSrcAccno;
	}
	public void setTransSrcAccno(String transSrcAccno) {
		TransSrcAccno = transSrcAccno;
	}
	
	//trans dest accno getter and setter functions
	public String getTransDestAccno(){
		return TransDestAccno;
	}
	public void setTransDestAccno(String transDestAccno) {
		TransDestAccno = transDestAccno;
	}
	
	//trans owner getter and setter functions
	public String getTransOwner(){
		return TransOwner;
	}
	public void setTransOwner(String transOwner) {
		TransOwner = transOwner;
	}
	
	//trans timestamp getter and setter functions
	public Timestamp getTransTimestamp(){
		return TransTimestamp;
	}
	public void setTransTimestamp(Timestamp transTimestamp) {
		TransTimestamp = transTimestamp;
	}
	
	//trans approved by getter and setter functions
	public String getTransApprovedBy(){
		return TransApprovedBy;
	}
	public void setTransApprovedBy(String transApprovedBy) {
		TransApprovedBy = transApprovedBy;
	}
	
	//trans amount getter and setter functions
	public int getTransAmount(){
		return TransAmount;
	}
	public void setTransAmount(int transAmount) {
		TransAmount = transAmount;
	}
	
	//trans comments getter and setter functions
	public String getTransComments(){
		return TransComments;
	}
	public void setTransComments(String transComments) {
		TransComments = transComments;
	}
	
	//trans result getter and setter functions
	public String getTransResult(){
		return TransResult;
	}
	public void setTransResult(String transResult) {
		TransResult = transResult;
	}
}
