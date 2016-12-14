package com.kedacom.expenses.model.expenses;

import java.io.Serializable;
import java.util.Date;

import com.kedacom.expenses.model.ExpBaseModel;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
public class ExpUserAccountHis extends ExpBaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private Long id;
	// user_account_id
	private Long userAccountId;
	// 预支单据，付款单据，报销ID
	private Long billId;
	// user_id
	private Long userId;
	// 1, 费用报销 2, 专项报销 3, 个人预支4, 专项预支 5 , 付款 6 ,专项转出 （后台操作需要记录）
	// 7 ,个人转出 （后台操作需要记录）
	private Long busType;
	// happen_amount
	private Float happenAmount;
	// happen_date
	private Date happenDate;
	// remark
	private String remark;

	/** 费用报销 **/
	public static final long BUSTYPE1 = 1L;
	/** 专项报销 **/
	public static final long BUSTYPE2 = 2L;
	/** 个人预支 **/
	public static final long BUSTYPE3 = 3L;
	/** 专项预支 **/
	public static final long BUSTYPE4 = 4L;
	/** 付款 **/
	public static final long BUSTYPE5 = 5L;
	/** 专项转出 **/
	public static final long BUSTYPE6 = 6L;
	/** 个人转出 **/
	public static final long BUSTYPE7 = 7L;

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
	 * 设置 user_account_id
	 */
	public Long getUserAccountId() {
		return userAccountId;
	}

	/**
	 * 获取 user_account_id
	 */
	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}

	/**
	 * 设置 预支单据，付款单据，报销ID
	 */
	public Long getBillId() {
		return billId;
	}

	/**
	 * 获取 预支单据，付款单据，报销ID
	 */
	public void setBillId(Long billId) {
		this.billId = billId;
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
	 * 设置 1, 费用报销 2, 专项报销 3, 个人预支 4, 专项预支 5 , 付款 6 ,专项转出 （后台操作需要记录） 7 ,个人转出
	 * （后台操作需要记录）
	 */
	public Long getBusType() {
		return busType;
	}

	/**
	 * 获取 1, 费用报销 2, 专项报销 3, 个人预支 4, 专项预支 5 , 付款 6 ,专项转出 （后台操作需要记录） 7 ,个人转出
	 * （后台操作需要记录）
	 */
	public void setBusType(Long busType) {
		this.busType = busType;
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
	 * 设置 happen_date
	 */
	public Date getHappenDate() {
		return happenDate;
	}

	/**
	 * 获取 happen_date
	 */
	public void setHappenDate(Date happenDate) {
		this.happenDate = happenDate;
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

}
