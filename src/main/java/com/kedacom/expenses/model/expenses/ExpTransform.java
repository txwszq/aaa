package com.kedacom.expenses.model.expenses;

import java.io.Serializable;

import com.kedacom.expenses.model.ExpBaseModel;

/**
 * (转移表).
 * @author zhujun
 * @version 2013-12-02
 */
public class ExpTransform extends ExpBaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private Long id;
	// 1，专项预支转个人预支 2，个人预支转专项
	private Long billType;
	// happened_amount
	private Float happenedAmount;
	// 0 没有转移1 已经转移
	private Long isTurn;

	public static final Long PER_TYPE = 1L;// 专转个
	public static final Long SPECIAL_TYPE = 2L;// 个转专

	public static final Long TURN_YES = 1L;// 已经转移
	public static final Long TURN_NO = 0L;// 没有转移

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
	 * 设置 1，专项预支转个人预支 2，个人预支转专项
	 */
	public Long getBillType() {
		return billType;
	}

	/**
	 * 获取 1，专项预支转个人预支 2，个人预支转专项
	 */
	public void setBillType(Long billType) {
		this.billType = billType;
	}

	/**
	 * 设置 happened_amount
	 */
	public Float getHappenedAmount() {
		return happenedAmount;
	}

	/**
	 * 获取 happened_amount
	 */
	public void setHappenedAmount(Float happenedAmount) {
		this.happenedAmount = happenedAmount;
	}

	/**
	 * 设置 0 没有转移 1 已经转移
	 */
	public Long getIsTurn() {
		return isTurn;
	}

	/**
	 * 获取 0 没有转移 1 已经转移
	 */
	public void setIsTurn(Long isTurn) {
		this.isTurn = isTurn;
	}

}
