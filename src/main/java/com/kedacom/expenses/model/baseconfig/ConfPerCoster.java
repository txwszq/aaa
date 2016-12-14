package com.kedacom.expenses.model.baseconfig;

import java.io.Serializable;

import com.kedacom.expenses.model.ExpBaseModel;

public class ConfPerCoster extends ExpBaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	// 特殊人员费用审核员ID
	private Long id;
	// 特殊人员PK
	private String person_id;
	// 特殊人员工号
	private String person_code;
	// 特殊人员姓名
	private String person_name;
	// 费用审核员主键
	private String coster_ids;
	// 费用审核员姓名
	private String coster_names;
	// 财务一级审核员主键
	private String finance_ids;
	// 财务一级审核员名字
	private String finance_names;
	// 财务付款员PK
	private String financeCostPk;
	// 财务付款员name
	private String financeCostName;
	// 预留字段一
	private String def1;
	// 预留字段二
	private String def2;
	// 预留字段三
	private String def3;
	// 预留字段四
	private String def4;
	// 预留字段五
	private String def5;

	/**
	 * 设置 特殊人员费用审核员ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 获取 特殊人员费用审核员ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 设置 特殊人员PK
	 */
	public String getPerson_id() {
		return person_id;
	}

	/**
	 * 获取 特殊人员PK
	 */
	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}

	/**
	 * 设置 特殊人员工号
	 */
	public String getPerson_code() {
		return person_code;
	}

	/**
	 * 获取 特殊人员工号
	 */
	public void setPerson_code(String person_code) {
		this.person_code = person_code;
	}

	/**
	 * 设置 特殊人员姓名
	 */
	public String getPerson_name() {
		return person_name;
	}

	/**
	 * 获取 特殊人员姓名
	 */
	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}

	/**
	 * 设置 费用审核员主键
	 */
	public String getCoster_ids() {
		return coster_ids;
	}

	/**
	 * 获取 费用审核员主键
	 */
	public void setCoster_ids(String coster_ids) {
		this.coster_ids = coster_ids;
	}

	/**
	 * 设置 费用审核员姓名
	 */
	public String getCoster_names() {
		return coster_names;
	}

	/**
	 * 获取 费用审核员姓名
	 */
	public void setCoster_names(String coster_names) {
		this.coster_names = coster_names;
	}

	/**
	 * 设置 财务一级审核员主键
	 */
	public String getFinance_ids() {
		return finance_ids;
	}

	/**
	 * 获取 财务一级审核员主键
	 */
	public void setFinance_ids(String finance_ids) {
		this.finance_ids = finance_ids;
	}

	/**
	 * 设置 财务一级审核员名字
	 */
	public String getFinance_names() {
		return finance_names;
	}

	/**
	 * 获取 财务一级审核员名字
	 */
	public void setFinance_names(String finance_names) {
		this.finance_names = finance_names;
	}

	public String getFinanceCostPk() {
		return financeCostPk;
	}

	public void setFinanceCostPk(String financeCostPk) {
		this.financeCostPk = financeCostPk;
	}

	public String getFinanceCostName() {
		return financeCostName;
	}

	public void setFinanceCostName(String financeCostName) {
		this.financeCostName = financeCostName;
	}

	/**
	 * 设置 预留字段一
	 */
	public String getDef1() {
		return def1;
	}

	/**
	 * 获取 预留字段一
	 */
	public void setDef1(String def1) {
		this.def1 = def1;
	}

	/**
	 * 设置 预留字段二
	 */
	public String getDef2() {
		return def2;
	}

	/**
	 * 获取 预留字段二
	 */
	public void setDef2(String def2) {
		this.def2 = def2;
	}

	/**
	 * 设置 预留字段三
	 */
	public String getDef3() {
		return def3;
	}

	/**
	 * 获取 预留字段三
	 */
	public void setDef3(String def3) {
		this.def3 = def3;
	}

	/**
	 * 设置 预留字段四
	 */
	public String getDef4() {
		return def4;
	}

	/**
	 * 获取 预留字段四
	 */
	public void setDef4(String def4) {
		this.def4 = def4;
	}

	/**
	 * 设置 预留字段五
	 */
	public String getDef5() {
		return def5;
	}

	/**
	 * 获取 预留字段五
	 */
	public void setDef5(String def5) {
		this.def5 = def5;
	}
}
