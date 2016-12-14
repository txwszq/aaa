package com.kedacom.expenses.model.baseconfig;

import java.io.Serializable;

import com.kedacom.expenses.model.ExpBaseModel;

public class ConfCostSubjectType extends ExpBaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private Long id;
	// 费用CODE
	private String code;
	// 费用名称
	private String cost_name;
	// 父ID
	private Long parent_id;
	// 是否是叶子
	private Boolean is_leaf;
	// 状态 是否精确到人 精确到人 值为1, 不精确到人为0
	private Byte is_exact_person = 0;
	// 是否严格控制时间
	private Boolean ctrl_time;
	// 是否控制额度
	private Boolean ctrl_amount;
	// 额度控制单位(1:月,2:天)
	private Byte amount_unit;
	// 科远代码(K3)
	private String keyuan_k3_code;
	// 领世软件代码(K3)
	private String lingshi_k3_code;
	// 科达代码(K3)
	private String keda_k3_code;
	// 备注
	private String remarks;

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
	 * 设置 费用名称
	 */
	public String getCost_name() {
		return cost_name;
	}

	/**
	 * 获取 费用名称
	 */
	public void setCost_name(String cost_name) {
		this.cost_name = cost_name;
	}

	/**
	 * 设置 父ID
	 */
	public Long getParent_id() {
		return parent_id;
	}

	/**
	 * 获取 父ID
	 */
	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	/**
	 * 设置 是否是叶子
	 */
	public Boolean getIs_leaf() {
		return is_leaf;
	}

	/**
	 * 获取 是否是叶子
	 */
	public void setIs_leaf(Boolean is_leaf) {
		this.is_leaf = is_leaf;
	}

	/**
	 * 设置 状态
	 */
	public Byte getIs_exact_person() {
		return is_exact_person;
	}

	/**
	 * 获取 状态
	 */
	public void setIs_exact_person(Byte is_exact_person) {
		this.is_exact_person = is_exact_person;
	}

	/**
	 * 设置 是否严格控制时间
	 */
	public Boolean getCtrl_time() {
		return ctrl_time;
	}

	/**
	 * 获取 是否严格控制时间
	 */
	public void setCtrl_time(Boolean ctrl_time) {
		this.ctrl_time = ctrl_time;
	}

	/**
	 * 设置 是否控制额度
	 */
	public Boolean getCtrl_amount() {
		return ctrl_amount;
	}

	public Byte getAmount_unit() {
		return amount_unit;
	}

	public void setAmount_unit(Byte amount_unit) {
		this.amount_unit = amount_unit;
	}

	/**
	 * 获取 是否控制额度
	 */
	public void setCtrl_amount(Boolean ctrl_amount) {
		this.ctrl_amount = ctrl_amount;
	}

	/**
	 * 设置 科远代码(K3)
	 */
	public String getKeyuan_k3_code() {
		return keyuan_k3_code;
	}

	/**
	 * 获取 科远代码(K3)
	 */
	public void setKeyuan_k3_code(String keyuan_k3_code) {
		this.keyuan_k3_code = keyuan_k3_code;
	}

	/**
	 * 设置 领世软件代码(K3)
	 */
	public String getLingshi_k3_code() {
		return lingshi_k3_code;
	}

	/**
	 * 获取 领世软件代码(K3)
	 */
	public void setLingshi_k3_code(String lingshi_k3_code) {
		this.lingshi_k3_code = lingshi_k3_code;
	}

	/**
	 * 设置 科达代码(K3)
	 */
	public String getKeda_k3_code() {
		return keda_k3_code;
	}

	/**
	 * 获取 科达代码(K3)
	 */
	public void setKeda_k3_code(String keda_k3_code) {
		this.keda_k3_code = keda_k3_code;
	}

	/**
	 * 设置 备注
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * 获取 备注
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
