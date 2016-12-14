package com.kedacom.expenses.model;

/**
 * (费用报销结果VO).
 * @author zhujun
 * @version 2014-1-20
 */
public class ExpenseFee {

	// 中间表ID主键 不传
	private String seqId;
	// FEE报销记录ID
	private String feeId;
	// CRM客户/合作方Id
	private String crmAccntId;
	// CRM活动Id
	private String crmActId;
	// 报销金额
	private Float expAmt;
	// 报销说明
	private String feeDesc;
	// 报销结束日期
	private String endT;
	// 费用类型
	private String feeType;
	// 费用类别编码
	private String feeTypeCode;
	// 费用子类型
	private String feeSubType;
	// 费用子类型编码
	private String feeSubTypeCode;
	// CRM商机Id
	private String crmOppId;
	// 报销开始日期
	private String startT;
	// 员工Id（邮箱
	private String empLogin;
	// 传输标识（0标识未传输、1标识传输成功、-1标识传输失败） ,已下不用传
	private Integer transCd;
	private String dataDt;
	private String errorCode;
	private String errorMsg;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getFeeId() {
		return feeId;
	}

	public void setFeeId(String feeId) {
		this.feeId = feeId;
	}

	public String getCrmAccntId() {
		return crmAccntId;
	}

	public void setCrmAccntId(String crmAccntId) {
		this.crmAccntId = crmAccntId;
	}

	public String getCrmActId() {
		return crmActId;
	}

	public void setCrmActId(String crmActId) {
		this.crmActId = crmActId;
	}

	public Float getExpAmt() {
		return expAmt;
	}

	public void setExpAmt(Float expAmt) {
		this.expAmt = expAmt;
	}

	public String getFeeDesc() {
		return feeDesc;
	}

	public void setFeeDesc(String feeDesc) {
		this.feeDesc = feeDesc;
	}

	public String getEndT() {
		return endT;
	}

	public void setEndT(String endT) {
		this.endT = endT;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFeeTypeCode() {
		return feeTypeCode;
	}

	public void setFeeTypeCode(String feeTypeCode) {
		this.feeTypeCode = feeTypeCode;
	}

	public String getFeeSubType() {
		return feeSubType;
	}

	public void setFeeSubType(String feeSubType) {
		this.feeSubType = feeSubType;
	}

	public String getFeeSubTypeCode() {
		return feeSubTypeCode;
	}

	public void setFeeSubTypeCode(String feeSubTypeCode) {
		this.feeSubTypeCode = feeSubTypeCode;
	}

	public String getCrmOppId() {
		return crmOppId;
	}

	public void setCrmOppId(String crmOppId) {
		this.crmOppId = crmOppId;
	}

	public String getStartT() {
		return startT;
	}

	public void setStartT(String startT) {
		this.startT = startT;
	}

	public String getEmpLogin() {
		return empLogin;
	}

	public void setEmpLogin(String empLogin) {
		this.empLogin = empLogin;
	}

	public Integer getTransCd() {
		return transCd;
	}

	public void setTransCd(Integer transCd) {
		this.transCd = transCd;
	}

	public String getDataDt() {
		return dataDt;
	}

	public void setDataDt(String dataDt) {
		this.dataDt = dataDt;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
