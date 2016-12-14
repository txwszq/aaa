package com.kedacom.expenses.model.expenses;

import com.kedacom.expenses.model.ExpBaseModel;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
public class ExpFinancePay extends ExpBaseModel {

	private static final long serialVersionUID = 1L;

	public static final long STATE_NOT_PAYMENT = 0l;// 未付款
	public static final long STATE_FINISH_PAYMENT = 1l;// 已付款

	// id
	private Long id;
	// hid 报销主表ID
	private String code;
	// 0:未付款 1:已付款
	private Long state;
	// 付款金额
	private Float amount;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 设置 0:未付款 1:已付款
	 */
	public Long getState() {
		return state;
	}

	/**
	 * 获取 0:未付款 1:已付款
	 */
	public void setState(Long state) {
		this.state = state;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

}
