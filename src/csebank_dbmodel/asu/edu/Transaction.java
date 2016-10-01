package csebank_dbmodel.asu.edu;

public class Transaction {
	
	private String TransId;
	private String TransType;
	private String TransDescription;
	private String TransStatus;
	private String TransSrcAccno;
	private String TransDestAccno;
	private String TransOwner;
	private String TransTimestamp;
	private String TransApprovedBy;
	private String TransAmount;
	private String TransComments;
	private String TransResult;
	
	//Transaction table constructors
	public Transaction(){
		this.TransId = null;
		this.TransType = null;
		this.TransDescription = null;
		this.TransStatus = null;
		this.TransSrcAccno = null;
		this.TransDestAccno = null;
		this.TransOwner = null;
		this.TransTimestamp = null;
		this.TransApprovedBy = null;
		this.TransAmount = null;
		this.TransComments = null;
		this.TransResult = null;
	}
	public Transaction(String transId, String transType, String transDescription, String transStatus,
			String transSrcAccno, String transDestAccNo, String transOwner, String transTimestamp,
			String transApprovedBy, String transAmount, String transComments, String transResult){
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
	public String getTransId(){
		return TransId;
	}
	public void setTransId(String transId) {
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
	public String getTransTimestamp(){
		return TransTimestamp;
	}
	public void setTransTimestamp(String transTimestamp) {
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
	public String getTransAmount(){
		return TransAmount;
	}
	public void setTransAmount(String transAmount) {
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
