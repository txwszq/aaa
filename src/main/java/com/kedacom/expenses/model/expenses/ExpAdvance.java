package com.kedacom.expenses.model.expenses;

import java.io.Serializable;
import java.util.Date;

import com.kedacom.expenses.model.ExpBaseModel;

/**
 * (费用预支).
 * @author zhujun
 * @version 2013-11-13
 */
public class ExpAdvance extends ExpBaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private Long id;

	private String advName;
	// processes_id
	private Long processesId;
	// 1，专项预支,2，个人费用预支
	private Long billType;
	// bill_code
	private String billCode = "";
	// applyer_id
	private Long applyerId;
	// applyer_name
	private String applyerName = "";
	// apply_dept_id
	private Long applyDeptId;
	// apply_dept_name
	private String applyDeptName = "";
	// 0，草稿 1，审批中 2，审批驳回 3，审批结束
	private Long billState;
	// curr_advance_amount
	private Float currAdvanceAmount = 0F;
	// 1,策划推广 2,接待
	private Long expertBus;
	// descipt
	private String descipt = "";
	// purpose
	private String purpose = "";
	// (个人) 额度-预支余额
	private Float balanceAmount = 0F;
	// approver_id
	private Long approverId;
	// approve_date
	private Date approveDate;
	// approve_note
	private String approveNote = "";
	// is_finish
	private Long isFinish;
	// 针对此预支单的而不是总共余额，此单据的余额
	private Float residualAmount = 0F;
	// conf_amount
	private Float confAmount = 0F;

	// 数据来源 0是员工输入 1是后台增加 默认员工输入
	private Long dataSource = 0L;

	private ExpAdvanceHis expAdvHis;

	public static final int NO_FINISH = 0; // 没完成
	public static final int FINISH = 1; // 完成

	public static final int ADV_BILL_TYPE = 1; // 专项预支
	public static final int PER_BILL_TYPE = 2;// 个人预支

	public static final Long DATA_SOURCE_BYPER = 0L;// 个人预支
	public static final Long DATA_SOURCE_MANA = 1L;// 个人预支

	public static final int FREE = 0;// 草稿状态
	public static final int APPROVE = 1;// 审批中
	public static final int NOPASS = 2;// 审批驳回
	public static final int PASS = 3;// 审批通过

	public static final String AGGREE = "1";
	public static final String REJECT = "2";

	public ExpAdvanceHis getExpAdvHis() {
		return expAdvHis;
	}

	public void setExpAdvHis(ExpAdvanceHis expAdvHis) {
		this.expAdvHis = expAdvHis;
	}

	/**
	 * 设置 id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 获取 id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getAdvName() {
		return advName;
	}

	public void setAdvName(String advName) {
		this.advName = advName;
	}

	/**
	 * 设置 processes_id
	 */
	public Long getProcessesId() {
		return processesId;
	}

	/**
	 * 获取 processes_id
	 */
	public void setProcessesId(Long processesId) {
		this.processesId = processesId;
	}

	/**
	 * 设置 专项预支 专项转出 专项转入 费用预支 费用转出 费用转入
	 */
	public Long getBillType() {
		return billType;
	}

	/**
	 * 获取 专项预支 专项转出 专项转入 费用预支 费用转出 费用转入
	 */
	public void setBillType(Long billType) {
		this.billType = billType;
	}

	/**
	 * 设置 bill_code
	 */
	public String getBillCode() {
		return billCode;
	}

	/**
	 * 获取 bill_code
	 */
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	/**
	 * 设置 applyer_id
	 */
	public Long getApplyerId() {
		return applyerId;
	}

	/**
	 * 获取 applyer_id
	 */
	public void setApplyerId(Long applyerId) {
		this.applyerId = applyerId;
	}

	/**
	 * 设置 applyer_name
	 */
	public String getApplyerName() {
		return applyerName;
	}

	/**
	 * 获取 applyer_name
	 */
	public void setApplyerName(String applyerName) {
		this.applyerName = applyerName;
	}

	/**
	 * 设置 apply_dept_id
	 */
	public Long getApplyDeptId() {
		return applyDeptId;
	}

	/**
	 * 获取 apply_dept_id
	 */
	public void setApplyDeptDd(Long applyDeptId) {
		this.applyDeptId = applyDeptId;
	}

	/**
	 * 设置 apply_dept_name
	 */
	public String getApplyDeptName() {
		return applyDeptName;
	}

	/**
	 * 获取 apply_dept_name
	 */
	public void setApplyDeptName(String applyDeptName) {
		this.applyDeptName = applyDeptName;
	}

	/**
	 * 设置 0，草稿 1，审批中 2，审批驳回 3，审批结束
	 */
	public Long getBillState() {
		return billState;
	}

	/**
	 * 获取 0，草稿 1，审批中 2，审批驳回 3，审批结束
	 */
	public void setBillState(Long billState) {
		this.billState = billState;
	}

	/**
	 * 设置 curr_advance_amount
	 */
	public Float getCurrAdvanceAmount() {
		return currAdvanceAmount;
	}

	/**
	 * 获取 curr_advance_amount
	 */
	public void setCurrAdvanceAmount(Float currAdvanceAmount) {
		this.currAdvanceAmount = currAdvanceAmount;
	}

	/**
	 * 设置 1,策划推广 2,接待
	 */
	public Long getExpertBus() {
		return expertBus;
	}

	/**
	 * 获取 1,策划推广 2,接待
	 */
	public void setExpertBus(Long expertBus) {
		this.expertBus = expertBus;
	}

	/**
	 * 设置 descipt
	 */
	public String getDescipt() {
		return descipt;
	}

	/**
	 * 获取 descipt
	 */
	public void setDescipt(String descipt) {
		this.descipt = descipt;
	}

	/**
	 * 设置 purpose
	 */
	public String getPurpose() {
		return purpose;
	}

	/**
	 * 获取 purpose
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	/**
	 * 设置 (个人) 额度+预支余额（可能正可能负）
	 */
	public Float getBalanceAmount() {
		return balanceAmount;
	}

	/**
	 * 获取 (个人) 额度+预支余额（可能正可能负）
	 */
	public void setBalanceAmount(Float balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	/**
	 * 设置 approver_id
	 */
	public Long getApproverId() {
		return approverId;
	}

	/**
	 * 获取 approver_id
	 */
	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}

	/**
	 * 设置 approve_date
	 */
	public Date getApproveDate() {
		return approveDate;
	}

	/**
	 * 获取 approve_date
	 */
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	/**
	 * 设置 approve_note
	 */
	public String getApproveNote() {
		return approveNote;
	}

	/**
	 * 获取 approve_note
	 */
	public void setApproveNote(String approveNote) {
		this.approveNote = approveNote;
	}

	/**
	 * 设置 is_finish
	 */
	public Long getIsFinish() {
		return isFinish;
	}

	/**
	 * 获取 is_finish
	 */
	public void setIsFinish(Long isFinish) {
		this.isFinish = isFinish;
	}

	/**
	 * 设置 针对此预支单的而不是总共余额，此单据的余额
	 */
	public Float getResidualAmount() {
		return residualAmount;
	}

	/**
	 * 获取 针对此预支单的而不是总共余额，此单据的余额
	 */
	public void setResidualAmount(Float residualAmount) {
		this.residualAmount = residualAmount;
	}

	/**
	 * 设置 conf_amount
	 */
	public Float getConfAmount() {
		return confAmount;
	}

	/**
	 * 获取 conf_amount
	 */
	public void setConfAmount(Float confAmount) {
		this.confAmount = confAmount;
	}

	public Long getDataSource() {
		return dataSource;
	}

	public void setDataSource(Long dataSource) {
		this.dataSource = dataSource;
	}

	public void setApplyDeptId(Long applyDeptId) {
		this.applyDeptId = applyDeptId;
	}

}
