package com.kedacom.expenses.model.expenses;

import java.io.Serializable;

import com.kedacom.expenses.model.ExpBaseModel;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
public class ExpMatch extends ExpBaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private Long id;
	// 1. 支付报销 2. 预支冲抵报销 3. 专项转个人预支 （通过转移表）
	private Long busiType;
	// 用户发生金额
	private Long fromId;
	// 公司方发生金额
	private Long toId;
	// happen_amount
	private Float happenAmount;
	// remark
	private String remark;

	// 关联的预支单编码
	private String advanceBillCode;

	/** 支付报销 **/
	public static final long BUS_TYPE1 = 1L;
	/** 预支冲抵报销 **/
	public static final long BUS_TYPE2 = 2L;
	/** 专项转个人预支 **/
	public static final long BUS_TYPE3 = 3L;
	/** 个人报销抵个人预支 **/
	public static final long BUS_TYPE4 = 4L;

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
	 * 设置 1. 支付报销 2. 预支冲抵报销 3. 专项转个人预支 （通过转移表）
	 */
	public Long getBusiType() {
		return busiType;
	}

	/**
	 * 获取 1. 支付报销 2. 预支冲抵报销 3. 专项转个人预支 （通过转移表）
	 */
	public void setBusiType(Long busiType) {
		this.busiType = busiType;
	}

	/**
	 * 设置 用户发生金额
	 */
	public Long getFromId() {
		return fromId;
	}

	/**
	 * 获取 用户发生金额
	 */
	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	/**
	 * 设置 公司方发生金额
	 */
	public Long getToId() {
		return toId;
	}

	/**
	 * 获取 公司方发生金额
	 */
	public void setToId(Long toId) {
		this.toId = toId;
	}

	/**
	 * 设置 happen_amount
	 */
	public Float getHappenAmount() {
		return happenAmount;
	}

	/**
	 * 获取 happen_amount
	 */
	public void setHappenAmount(Float happenAmount) {
		this.happenAmount = happenAmount;
	}

	/**
	 * 设置 remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 获取 remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAdvanceBillCode() {
		return advanceBillCode;
	}

	public void setAdvanceBillCode(String advanceBillCode) {
		this.advanceBillCode = advanceBillCode;
	}

}
