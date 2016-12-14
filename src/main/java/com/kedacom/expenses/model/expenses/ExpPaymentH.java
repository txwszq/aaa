package com.kedacom.expenses.model.expenses;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.kedacom.expenses.model.ExpBaseModel;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
public class ExpPaymentH extends ExpBaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Long TYPE_EXPENSE_PAYMENT = 1L;
	public static final Long TYPE_SPECIAL_PAYMENT = 2L;
	public static final Long TYPE_ACTIVITY_APPLY = 3L;

	public static final Long STATE_DRAFTING = 0L;
	public static final Long STATE_APPROVING = 1L;
	public static final Long STATE_REJUSTING = 2L;
	public static final Long STATE_APPROVE_END = 3L;

	public static final Long IS_NOFINISH = 0L; // 未完成
	public static final Long IS_FINISH = 1L;// 已完成

	/** 个人费用报销 **/
	public static final Long EXPENSE_PAYMENT = 1L;
	/** 专项报销 **/
	public static final Long SPECIAL_PAYMENT = 2L;
	/** 专项报销 **/
	public static final Long ACTIVITITY_TYPE = 3L;

	public static final Long DATA_SOURCE_PERSONAL = 0L;// 个人
	public static final Long DATA_SOURCE_MANAGER = 1L;// 财务后台

	// id
	private Long id;
	// bpm 流程ID
	private Long processesId;
	// 1，费用报销 2，专项报销 3，活动申请
	private Long billType;
	// 根据具体类型来定义单据头字母
	private String billCode;
	// applyer_id
	private Long applyerId;
	// applyer_name
	private String applyerName;
	// apply_dept_id
	private Long applyDeptId;
	// apply_dept_name
	private String applyDeptName;
	// 0，草稿 1，审批中2，审批驳回 3，审批结束
	private Long billState;
	// bill_num
	private Long billNum;
	// real_bill_num
	private Long realBillNum;
	// 申请总金额
	private Float paymentAmount;
	// 此单据还剩多少金额没有完成 公式：剩余金额=当前报销金额-已付款金额+预支冲抵金额 此种状态就是已完成支付
	private Float residualAmount;
	// 0,未完成1,完成
	private Long isFinish;
	// 实际报销总金额
	private Float realPaymentAmount;
	// take_dept_id
	private Long takeDeptId;
	// take_dept_name
	private String takeDeptName;
	// descript
	private String descript;
	// approve_id
	private Long approveId;
	// approve_name
	private String approveName;
	// approve_date
	private Date approveDate;
	// approve_note
	private String approveNote;
	// 1，接待 2，推广
	private Long specialtyBus;
	// 0，否（默认） 1，是
	private Long isTakeDepts;
	// 数据来源 0是员工输入 1是后台增加 默认员工输入
	private Long dataSource = 0L;
	/** 子表信息 （同步K3时传值用 转JSON） **/
	private List<ExpPaymentB> listPayBs;

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
	 * 设置 bpm 流程ID
	 */
	public Long getProcessesId() {
		return processesId;
	}

	/**
	 * 获取 bpm 流程ID
	 */
	public void setProcessesId(Long processesId) {
		this.processesId = processesId;
	}

	/**
	 * 设置 1，费用报销 2，专项报销 3，活动申请
	 */
	public Long getBillType() {
		return billType;
	}

	/**
	 * 获取 1，费用报销 2，专项报销 3，活动申请
	 */
	public void setBillType(Long billType) {
		this.billType = billType;
	}

	/**
	 * 设置 根据具体类型来定义单据头字母
	 */
	public String getBillCode() {
		return billCode;
	}

	/**
	 * 获取 根据具体类型来定义单据头字母
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
	public void setApplyDeptId(Long applyDeptId) {
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
	 * 设置 bill_num
	 */
	public Long getBillNum() {
		return billNum;
	}

	/**
	 * 获取 bill_num
	 */
	public void setBillNum(Long billNum) {
		this.billNum = billNum;
	}

	/**
	 * 设置 real_bill_num
	 */
	public Long getRealBillNum() {
		return realBillNum;
	}

	/**
	 * 获取 real_bill_num
	 */
	public void setRealBillNum(Long realBillNum) {
		this.realBillNum = realBillNum;
	}

	/**
	 * 设置 申请总金额
	 */
	public Float getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * 获取 申请总金额
	 */
	public void setPaymentAmount(Float paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	/**
	 * 设置 此单据还剩多少金额没有完成 公式：剩余金额=当前报销金额-已付款金额+预支冲抵金额 此种状态就是已完成支付
	 */
	public Float getResidualAmount() {
		return residualAmount;
	}

	/**
	 * 获取 此单据还剩多少金额没有完成 公式：剩余金额=当前报销金额-已付款金额+预支冲抵金额 此种状态就是已完成支付
	 */
	public void setResidualAmount(Float residualAmount) {
		this.residualAmount = residualAmount;
	}

	/**
	 * 设置 0,未完成 1,完成
	 */
	public Long getIsFinish() {
		return isFinish;
	}

	/**
	 * 获取 0,未完成 1,完成
	 */
	public void setIsFinish(Long isFinish) {
		this.isFinish = isFinish;
	}

	/**
	 * 设置 实际报销总金额
	 */
	public Float getRealPaymentAmount() {
		return realPaymentAmount;
	}

	/**
	 * 获取 实际报销总金额
	 */
	public void setRealPaymentAmount(Float realPaymentAmount) {
		this.realPaymentAmount = realPaymentAmount;
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
		return takeDeptName;
	}

	/**
	 * 获取 take_dept_name
	 */
	public void setTakeDeptName(String takeDeptName) {
		this.takeDeptName = takeDeptName;
	}

	/**
	 * 设置 descript
	 */
	public String getDescript() {
		return descript;
	}

	/**
	 * 获取 descript
	 */
	public void setDescript(String descript) {
		this.descript = descript;
	}

	/**
	 * 设置 approve_id
	 */
	public Long getApproveId() {
		return approveId;
	}

	/**
	 * 获取 approve_id
	 */
	public void setApproveId(Long approveId) {
		this.approveId = approveId;
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
	 * 设置 1，接待 2，推广
	 */
	public Long getSpecialtyBus() {
		return specialtyBus;
	}

	/**
	 * 获取 1，接待 2，推广
	 */
	public void setSpecialtyBus(Long specialtyBus) {
		this.specialtyBus = specialtyBus;
	}

	/**
	 * 设置 0，否（默认） 1，是
	 */
	public Long getIsTakeDepts() {
		return isTakeDepts;
	}

	/**
	 * 获取 0，否（默认） 1，是
	 */
	public void setIsTakeDepts(Long isTakeDepts) {
		this.isTakeDepts = isTakeDepts;
	}

	public Long getDataSource() {
		return dataSource;
	}

	public void setDataSource(Long dataSource) {
		this.dataSource = dataSource;
	}

	public String getApproveName() {
		return approveName;
	}

	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}

	public List<ExpPaymentB> getListPayBs() {
		return listPayBs;
	}

	public void setListPayBs(List<ExpPaymentB> listPayBs) {
		this.listPayBs = listPayBs;
	}

}
