package com.kedacom.expenses.model.expenses;

import java.io.Serializable;
import java.util.Date;

import com.kedacom.expenses.model.ExpBaseModel;

/**
 * (费用报销子表).
 * @author zhujun
 * @version 2013-12-02
 */
public class ExpPaymentB extends ExpBaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private Long id;
	// 0 不通过 1 通过
	private Long billState;
	// approve_note
	private String approveNote;
	// expenses_h_id
	private Long expensesHID;
	// num
	private Long num;
	// begin_date
	private Date beginDate;
	// end_date
	private Date endDate;
	// day_num
	private Long dayNum;
	// cost_subject_c
	private String costSubjectC;
	// cost_subject_p
	private String costSubjectP;
	// real_cost_subject_c
	private String realCostSubjectC;
	// real_cost_subject_p
	private String realCostSubjectP;
	// amount
	private Float amount;
	// act_id
	private Long actId;
	// bus_id
	private Long busId;
	// cus_id
	private Long cusId;
	// PLM项目 PPM项目
	private Long projectSrcType;
	// project_code
	private String projectCode;
	// project_name
	private String projectName;
	// server_code
	private String serverCode;
	// take_dept_id
	private Long takeDeptId;
	// take_dept_name
	private String takedeptname;
	// cost_taker_id
	private Long costTakerId;
	// cost_taker_name
	private String costTakerName;
	// descipt
	private String descipt;
	// is_pay
	private Long isPay;
	// exp_pay_id
	private Long expPayId;
	// is_leader2
	private Long isLeader2;

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

	/**
	 * 设置 0 不通过 1 通过
	 */
	public Long getBillState() {
		return billState;
	}

	/**
	 * 获取 0 不通过 1 通过
	 */
	public void setBillState(Long billState) {
		this.billState = billState;
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
	 * 设置 expenses_h_id
	 */
	public Long getExpensesHID() {
		return expensesHID;
	}

	/**
	 * 获取 expenses_h_id
	 */
	public void setExpensesHID(Long expensesHID) {
		this.expensesHID = expensesHID;
	}

	/**
	 * 设置 num
	 */
	public Long getNum() {
		return num;
	}

	/**
	 * 获取 num
	 */
	public void setNum(Long num) {
		this.num = num;
	}

	/**
	 * 设置 begin_date
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * 获取 begin_date
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 设置 end_date
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 获取 end_date
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 设置 day_num
	 */
	public Long getDayNum() {
		return dayNum;
	}

	/**
	 * 获取 day_num
	 */
	public void setDayNum(Long dayNum) {
		this.dayNum = dayNum;
	}

	/**
	 * 设置 cost_subject_c
	 */
	public String getCostSubjectC() {
		return costSubjectC;
	}

	/**
	 * 获取 cost_subject_c
	 */
	public void setCostSubjectC(String costSubjectC) {
		this.costSubjectC = costSubjectC;
	}

	/**
	 * 设置 cost_subject_p
	 */
	public String getCostSubjectP() {
		return costSubjectP;
	}

	/**
	 * 获取 cost_subject_p
	 */
	public void setCostSubjectP(String costSubjectP) {
		this.costSubjectP = costSubjectP;
	}

	/**
	 * 设置 real_cost_subject_c
	 */
	public String getRealCostSubjectC() {
		return realCostSubjectC;
	}

	/**
	 * 获取 real_cost_subject_c
	 */
	public void setRealCostSubjectC(String realCostSubjectC) {
		this.realCostSubjectC = realCostSubjectC;
	}

	/**
	 * 设置 real_cost_subject_p
	 */
	public String getRealCostSubjectP() {
		return realCostSubjectP;
	}

	/**
	 * 获取 real_cost_subject_p
	 */
	public void setRealCostSubjectP(String realCostSubjectP) {
		this.realCostSubjectP = realCostSubjectP;
	}

	/**
	 * 设置 amount
	 */
	public Float getAmount() {
		return amount;
	}

	/**
	 * 获取 amount
	 */
	public void setAmount(Float amount) {
		this.amount = amount;
	}

	/**
	 * 设置 act_id
	 */
	public Long getActId() {
		return actId;
	}

	/**
	 * 获取 act_id
	 */
	public void setActId(Long actId) {
		this.actId = actId;
	}

	/**
	 * 设置 bus_id
	 */
	public Long getBusId() {
		return busId;
	}

	/**
	 * 获取 bus_id
	 */
	public void setBusId(Long busId) {
		this.busId = busId;
	}

	/**
	 * 设置 cus_id
	 */
	public Long getCusId() {
		return cusId;
	}

	/**
	 * 获取 cus_id
	 */
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	/**
	 * 设置 PLM项目 PPM项目
	 */
	public Long getProjectSrcType() {
		return projectSrcType;
	}

	/**
	 * 获取 PLM项目 PPM项目
	 */
	public void setProjectSrcType(Long projectSrcType) {
		this.projectSrcType = projectSrcType;
	}

	/**
	 * 设置 project_code
	 */
	public String getProjectCode() {
		return projectCode;
	}

	/**
	 * 获取 project_code
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * 设置 project_name
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * 获取 project_name
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * 设置 server_code
	 */
	public String getServerCode() {
		return serverCode;
	}

	/**
	 * 获取 server_code
	 */
	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	/**
	 * 设置 take_dept_id
	 */
	public Long getTakeDeptId() {
		return takeDeptId;
	}

	/**
	 * 获取 take_dept_id
	 */
	public void setTakeDeptId(Long takeDeptId) {
		this.takeDeptId = takeDeptId;
	}

	/**
	 * 设置 take_dept_name
	 */
	public String getTakeDeptName() {
		return takedeptname;
	}

	/**
	 * 获取 take_dept_name
	 */
	public void setTakeDeptName(String takeDeptName) {
		this.takedeptname = takeDeptName;
	}

	/**
	 * 设置 cost_taker_id
	 */
	public Long getCostTakerId() {
		return costTakerId;
	}

	/**
	 * 获取 cost_taker_id
	 */
	public void setCostTakerId(Long costTakerId) {
		this.costTakerId = costTakerId;
	}

	/**
	 * 设置 cost_taker_name
	 */
	public String getCostTakerName() {
		return costTakerName;
	}

	/**
	 * 获取 cost_taker_name
	 */
	public void setCostTakerName(String costTakerName) {
		this.costTakerName = costTakerName;
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
	 * 设置 is_pay
	 */
	public Long getIsPay() {
		return isPay;
	}

	/**
	 * 获取 is_pay
	 */
	public void setIsPay(Long isPay) {
		this.isPay = isPay;
	}

	/**
	 * 设置 exp_pay_id
	 */
	public Long getExpPayId() {
		return expPayId;
	}

	/**
	 * 获取 exp_pay_id
	 */
	public void setExpPayId(Long expPayId) {
		this.expPayId = expPayId;
	}

	/**
	 * 设置 is_leader2
	 */
	public Long getIsLeader2() {
		return isLeader2;
	}

	/**
	 * 获取 is_leader2
	 */
	public void setIs_leader2(Long isLeader2) {
		this.isLeader2 = isLeader2;
	}

}
