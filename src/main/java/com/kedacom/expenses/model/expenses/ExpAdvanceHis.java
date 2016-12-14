package com.kedacom.expenses.model.expenses;

import java.io.Serializable;

import com.kedacom.expenses.model.ExpBaseModel;

/**
 * (费用预支历史).
 * @author zhujun
 * @version 2013-11-13
 */
public class ExpAdvanceHis extends ExpBaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private Long id;
	// cost_advance_id
	private Long costAdvanceId;
	// 在此单据之前 个人已经预支 的金额 历史信息 在实时表中取
	private Float perAdvanced = 0F;
	// 在此单据之前个人已经报销的总额 历史总额在实时表中取
	private Float perPaymentedTotal = 0F;
	// per_payed_amount
	private Float perPayedAmount = 0F;
	// （个人） 当前已经预支总额+已付款总金额-已报销总额-转移=当前预支余额
	private Float perResidual = 0F;
	// 此单据之前专项预支总额 历史总和
	private Float specialAdvanced = 0F;
	// 此单据之前专项已报销的总额
	private Float specialPaymentedTotal = 0F;
	// special_payed_amount
	private Float specialPayedAmount = 0F;
	// （专项）已预支总额+付款总额-已报销总额-转移=当前预支余额
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
	 * 设置 cost_advance_id
	 */
	public Long getCostAdvanceId() {
		return costAdvanceId;
	}

	/**
	 * 获取 cost_advance_id
	 */
	public void setCostAdvanceId(Long costAdvanceId) {
		this.costAdvanceId = costAdvanceId;
	}

	/**
	 * 设置 在此单据之前 个人已经预支 的金额 历史信息 在实时表中取
	 */
	public Float getPerAdvanced() {
		return perAdvanced;
	}

	/**
	 * 获取 在此单据之前 个人已经预支 的金额 历史信息 在实时表中取
	 */
	public void setPerAdvanced(Float perAdvanced) {
		this.perAdvanced = perAdvanced;
	}

	/**
	 * 设置 在此单据之前个人已经报销的总额 历史总额在实时表中取
	 */
	public Float getPerPaymentedTotal() {
		return perPaymentedTotal;
	}

	/**
	 * 获取 在此单据之前个人已经报销的总额 历史总额在实时表中取
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
	 * 设置 （个人） 当前已经预支总额+已付款总金额-已报销总额-转移=当前预支余额
	 */
	public Float getPerResidual() {
		return perResidual;
	}

	/**
	 * 获取 （个人） 当前已经预支总额+已付款总金额-已报销总额-转移=当前预支余额
	 */
	public void setPerResidual(Float perResidual) {
		this.perResidual = perResidual;
	}

	/**
	 * 设置 此单据之前专项预支总额 历史总和
	 */
	public Float getSpecialAdvanced() {
		return specialAdvanced;
	}

	/**
	 * 获取 此单据之前专项预支总额 历史总和
	 */
	public void setSpecialAdvanced(Float specialAdvanced) {
		this.specialAdvanced = specialAdvanced;
	}

	/**
	 * 设置 此单据之前专项已报销的总额
	 */
	public Float getSpecialPaymentedTotal() {
		return specialPaymentedTotal;
	}

	/**
	 * 获取 此单据之前专项已报销的总额
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
	 * 设置 （专项）已预支总额+付款总额-已报销总额-转移=当前预支余额
	 */
	public Float getSpecialResidual() {
		return specialResidual;
	}

	/**
	 * 获取 （专项）已预支总额+付款总额-已报销总额-转移=当前预支余额
	 */
	public void setSpecialResidual(Float specialResidual) {
		this.specialResidual = specialResidual;
	}

}
