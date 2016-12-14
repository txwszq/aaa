/**
 * @(#)Snippet.java 2013-12-9 Copyright 2013 it.kedacom.com, Inc. All rights
 *                  reserved.
 */
package com.kedacom.expenses.model;

/**
 * (用一句话描述类的主要功能).
 * @author zhujun
 * @version 2013-12-9
 */
public class ExpHeadVO {

	// 个人预支总额
	private Float perAdvanced = 0.0F;
	// 个人报销总额
	private Float perPaymentedTotal = 0.0F;
	// 个人已付款金额
	private Float perPayedAmount = 0.0F;
	// （个人） 当前已经预支总额+已付款总金额-已报销总额-转移(个转专)=当前预支余额
	// 个人预支余额
	private Float perResidual = 0.0F;
	// 专项预支总额
	private Float specialAdvanced = 0.0F;
	// 专项报销总额
	private Float specialPaymentedTotal = 0.0F;
	// 专项付款总额
	private Float specialPayedAmount = 0.0F;
	// （专项）已预支总额+付款总额-已报销总额-转移=当前预支余额
	// 专项预支余额
	private Float specialResidual = 0.0F;
	// 当前可预支总额（个人）+当前可预支余额（专项）
	private Float totalResidual = 0.0F;
	// 当前已预支余额
	private Float advancedResidual = 0.0F;
	// 已预支总额 专项+个人
	private Float advancedTotal = 0.0F;
	// 已报销总额 专项+个人
	private Float paymentedTotal = 0.0F;
	// 已付款总额
	private Float payedAmount = 0.0F;
	// 预支额度
	private Float advConfAmount = 0.0F;

	/** 个人费用报销 **/
	public static final long PER_PAY_TYPE = 0;
	/** 专项费用报销 **/
	public static final long SPECIAL_PAY_TYPE = 1;
	/** 活动申请 **/
	public static final long ACT_TYPE = 2;
	/** 专项预支 **/
	public static final long SPECIAL_ADV_TYPE = 3;
	/** 个人预支 **/
	public static final long PER_ADV_TYPE = 4;

	public Float getPerAdvanced() {
		return perAdvanced;
	}

	public void setPerAdvanced(Float perAdvanced) {
		this.perAdvanced = perAdvanced;
	}

	public Float getPerPaymentedTotal() {
		return perPaymentedTotal;
	}

	public void setPerPaymentedTotal(Float perPaymentedTotal) {
		this.perPaymentedTotal = perPaymentedTotal;
	}

	public Float getPerPayedAmount() {
		return perPayedAmount;
	}

	public void setPerPayedAmount(Float perPayedAmount) {
		this.perPayedAmount = perPayedAmount;
	}

	public Float getPerResidual() {
		return perResidual;
	}

	public void setPerResidual(Float perResidual) {
		this.perResidual = perResidual;
	}

	public Float getSpecialAdvanced() {
		return specialAdvanced;
	}

	public void setSpecialAdvanced(Float specialAdvanced) {
		this.specialAdvanced = specialAdvanced;
	}

	public Float getSpecialPaymentedTotal() {
		return specialPaymentedTotal;
	}

	public void setSpecialPaymentedTotal(Float specialPaymentedTotal) {
		this.specialPaymentedTotal = specialPaymentedTotal;
	}

	public Float getSpecialPayedAmount() {
		return specialPayedAmount;
	}

	public void setSpecialPayedAmount(Float specialPayedAmount) {
		this.specialPayedAmount = specialPayedAmount;
	}

	public Float getSpecialResidual() {
		return specialResidual;
	}

	public void setSpecialResidual(Float specialResidual) {
		this.specialResidual = specialResidual;
	}

	public Float getTotalResidual() {
		return totalResidual;
	}

	public void setTotalResidual(Float totalResidual) {
		this.totalResidual = totalResidual;
	}

	public Float getAdvancedTotal() {
		return advancedTotal;
	}

	public void setAdvancedTotal(Float advancedTotal) {
		this.advancedTotal = advancedTotal;
	}

	public Float getPaymentedTotal() {
		return paymentedTotal;
	}

	public void setPaymentedTotal(Float paymentedTotal) {
		this.paymentedTotal = paymentedTotal;
	}

	public Float getPayedAmount() {
		return payedAmount;
	}

	public void setPayedAmount(Float payedAmount) {
		this.payedAmount = payedAmount;
	}

	public Float getAdvConfAmount() {
		return advConfAmount;
	}

	public void setAdvConfAmount(Float advConfAmount) {
		this.advConfAmount = advConfAmount;
	}

	public Float getAdvancedResidual() {
		return advancedResidual;
	}

	public void setAdvancedResidual(Float advancedResidual) {
		this.advancedResidual = advancedResidual;
	}

}
