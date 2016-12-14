package com.kedacom.expenses.model.expenses;

import java.io.Serializable;

import com.kedacom.expenses.model.ExpBaseModel;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
public class ExpUserAccount extends ExpBaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private Long id;
	private String userName;
	// user_id
	private Long userId;
	/** 已预支总额（个人） **/
	private Float perAdvanced = 0F;
	/** 已报销总额（个人） **/
	private Float perPaymentedTotal = 0F;
	/** 已付款总额（个人） **/
	private Float perPayedAmount = 0F;
	/** 最新余额（个人） **/
	private Float perResidual = 0F;
	/** 个人预支转专项预支（个-专） **/
	private Float perTurnAmount = 0F;
	/** 专项预支转个人预支（专-个） **/
	private Float specialTurnAmount = 0F;
	/** 已预支总额（专项） **/
	private Float specialAdvanced = 0F;
	/** 已报销总额（专项） **/
	private Float specialPaymentedTotal = 0F;
	/** 已付款总额（专项） **/
	private Float specialPayedAmount = 0F;
	/** 最新余额（专项） **/
	private Float specialResidual = 0F;

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
	 * 设置 per_advanced
	 */
	public Float getPerAdvanced() {
		return perAdvanced;
	}

	/**
	 * 获取 per_advanced
	 */
	public void setPerAdvanced(Float perAdvanced) {
		this.perAdvanced = perAdvanced;
	}

	/**
	 * 设置 per_paymented_total
	 */
	public Float getPerPaymentedTotal() {
		return perPaymentedTotal;
	}

	/**
	 * 获取 per_paymented_total
	 */
	public void setPerPaymentedTotal(Float perPaymentedTotal) {
		this.perPaymentedTotal = perPaymentedTotal;
	}

	/**
	 * 设置 per_payed_amount
	 */
	public Float getPerPayedAmount() {
		return perPayedAmount;
	}

	/**
	 * 获取 per_payed_amount
	 */
	public void setPerPayedAmount(Float perPayedAmount) {
		this.perPayedAmount = perPayedAmount;
	}

	/**
	 * 设置 per_residual
	 */
	public Float getPerResidual() {
		return perResidual;
	}

	/**
	 * 获取 per_residual
	 */
	public void setPerResidual(Float perResidual) {
		this.perResidual = perResidual;
	}

	/**
	 * 设置 个人预支转专项预支
	 */
	public Float getPerTurnAmount() {
		return perTurnAmount;
	}

	/**
	 * 获取 个人预支转专项预支
	 */
	public void setPerTurnAmount(Float perTurnAmount) {
		this.perTurnAmount = perTurnAmount;
	}

	/**
	 * 设置 专项预支转个人预支
	 */
	public Float getSpecialTurnAmount() {
		return specialTurnAmount;
	}

	/**
	 * 获取 专项预支转个人预支
	 */
	public void setSpecialTurnAmount(Float specialTurnAmount) {
		this.specialTurnAmount = specialTurnAmount;
	}

	/**
	 * 设置 special_advanced
	 */
	public Float getSpecialAdvanced() {
		return specialAdvanced;
	}

	/**
	 * 获取 special_advanced
	 */
	public void setSpecialAdvanced(Float specialAdvanced) {
		this.specialAdvanced = specialAdvanced;
	}

	/**
	 * 设置 special_paymented_total
	 */
	public Float getSpecialPaymentedTotal() {
		return specialPaymentedTotal;
	}

	/**
	 * 获取 special_paymented_total
	 */
	public void setSpecialPaymentedTotal(Float specialPaymentedTotal) {
		this.specialPaymentedTotal = specialPaymentedTotal;
	}

	/**
	 * 设置 special_payed_amount
	 */
	public Float getSpecialPayedAmount() {
		return specialPayedAmount;
	}

	/**
	 * 获取 special_payed_amount
	 */
	public void setSpecialPayedAmount(Float specialPayedAmount) {
		this.specialPayedAmount = specialPayedAmount;
	}

	/**
	 * 设置 special_residual
	 */
	public Float getSpecialResidual() {
		return specialResidual;
	}

	/**
	 * 获取 special_residual
	 */
	public void setSpecialResidual(Float specialResidual) {
		this.specialResidual = specialResidual;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
