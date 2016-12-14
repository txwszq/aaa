/**
 * @(#)ConfCoster.java 2013-11-13 Copyright 2013 it.kedacom.com, Inc. All rights
 *                     reserved.
 */
package com.kedacom.expenses.model.baseconfig;

import java.io.Serializable;

import com.kedacom.expenses.model.ExpBaseModel;

/**
 * (报销费用部门承担人).
 * @author zhujun
 * @version 2013-11-13
 */
public class ConfCoster extends ExpBaseModel implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 5220299216694593123L;

	private Long id;// PK
	private int isTakeDept;// 是否费用承担部门 0否1是
	private String costerPks;// 费用审核员PK
	private String costerNames;// 费用审核员姓名
	private String financePks;// 财务一级审批PKS
	private String financeNames;// 财务一级审批姓名
	private String financeCostPk;// 财务付款员PK
	private String financeCostName;// 财务付款员name
	private String deptId;// 部门PK
	private String def1;// 预留字段一
	private String def2;// 预留字段二
	private String def3;// 预留字段三
	private String def4;// 预留字段四
	private String def5;// 预留字段五

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getIsTakeDept() {
		return isTakeDept;
	}

	public void setIsTakeDept(int isTakeDept) {
		this.isTakeDept = isTakeDept;
	}

	public String getCosterPks() {
		return costerPks;
	}

	public void setCosterPks(String costerPks) {
		this.costerPks = costerPks;
	}

	public String getCosterNames() {
		return costerNames;
	}

	public void setCosterNames(String costerNames) {
		this.costerNames = costerNames;
	}

	public String getFinancePks() {
		return financePks;
	}

	public void setFinancePks(String financePks) {
		this.financePks = financePks;
	}

	public String getFinanceNames() {
		return financeNames;
	}

	public void setFinanceNames(String financeNames) {
		this.financeNames = financeNames;
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

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDef1() {
		return def1;
	}

	public void setDef1(String def1) {
		this.def1 = def1;
	}

	public String getDef2() {
		return def2;
	}

	public void setDef2(String def2) {
		this.def2 = def2;
	}

	public String getDef3() {
		return def3;
	}

	public void setDef3(String def3) {
		this.def3 = def3;
	}

	public String getDef4() {
		return def4;
	}

	public void setDef4(String def4) {
		this.def4 = def4;
	}

	public String getDef5() {
		return def5;
	}

	public void setDef5(String def5) {
		this.def5 = def5;
	}

}
