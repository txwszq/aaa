package com.kedacom.expenses.model.expenses;

import java.io.Serializable;
import java.util.Date;

import com.kedacom.expenses.model.ExpBaseModel;

/**
 * (付款表).
 * @author zhujun
 * @version 2013-12-02
 */
public class ExpPay extends ExpBaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private Long id;
	// user_id
	private Long userId;
	// pay_amount
	private Float payAmount;
	// remark
	private String remark;
	// pay_date
	private Date payDate;

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
	 * 设置 user_id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 获取 user_id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 设置 pay_amount
	 */
	public Float getPayAmount() {
		return payAmount;
	}

	/**
	 * 获取 pay_amount
	 */
	public void setPayAmount(Float payAmount) {
		this.payAmount = payAmount;
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

	/**
	 * 设置 pay_date
	 */
	public Date getPayDate() {
		return payDate;
	}

	/**
	 * 获取 pay_date
	 */
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

}
