package com.kedacom.expenses.model.expenses;

import java.io.Serializable;

import com.kedacom.expenses.model.ExpBaseModel;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
public class ExpUpdatePaymentHis extends ExpBaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键
	private Long id;
	// 报销子表PK
	private Long paymentBId;
	// 发票费用类型
	private String costSubjectC;
	// 发票费用科目
	private String costSubjectP;
	// 实际费用类型
	private String realCostSubjectC;
	// 实际费用科目
	private String realCostSubjectP;
	// 记录类型 1.表示修改前 2.表示修改后
	private Integer recordType;

	/**
	 * 设置 主键
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 获取 主键
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 设置 报销子表PK
	 */
	public Long getPaymentBId() {
		return paymentBId;
	}

	/**
	 * 获取 报销子表PK
	 */
	public void setPaymentBId(Long paymentBId) {
		this.paymentBId = paymentBId;
	}

	/**
	 * 设置 发票费用类型
	 */
	public String getCostSubjectC() {
		return costSubjectC;
	}

	/**
	 * 获取 发票费用类型
	 */
	public void setCostSubjectC(String costSubjectC) {
		this.costSubjectC = costSubjectC;
	}

	/**
	 * 设置 发票费用科目
	 */
	public String getCostSubjectP() {
		return costSubjectP;
	}

	/**
	 * 获取 发票费用科目
	 */
	public void setCostSubjectP(String costSubjectP) {
		this.costSubjectP = costSubjectP;
	}

	/**
	 * 设置 实际费用类型
	 */
	public String getReal_costSubjectC() {
		return realCostSubjectC;
	}

	/**
	 * 获取 实际费用类型
	 */
	public void setRealCostSubjectC(String realCostSubjectC) {
		this.realCostSubjectC = realCostSubjectC;
	}

	/**
	 * 设置 实际费用科目
	 */
	public String getRealCostSubjectP() {
		return realCostSubjectP;
	}

	/**
	 * 获取 实际费用科目
	 */
	public void setRealCostSubjectP(String realCostSubjectP) {
		this.realCostSubjectP = realCostSubjectP;
	}

	/**
	 * 设置 记录类型 1.表示修改前 2.表示修改后
	 */
	public Integer getRecordType() {
		return recordType;
	}

	/**
	 * 获取 记录类型 1.表示修改前 2.表示修改后
	 */
	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}
}
