package com.kedacom.expenses.model.baseconfig;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.kedacom.expenses.model.ExpBaseModel;

public class ConfBusinessOpportunity extends ExpBaseModel implements Serializable {

	private Long id;

	private String boCrmId;

	private String boName;

	private String boCode;

	private String boBudget;

	private Boolean isProjectApproval;

	private Date projectApprovalTime;

	private Date projectApprovalEndtime;

	private String boPrincipalName;

	private String boPrincipalCode;

	private String depEhrPk;

	private String departmentName;

	private String departmentCode;

	private Short dataSource = 1;// 默认为CRM来源

	private List<ConfOtherTaker> takers;

	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBoCrmId() {
		return boCrmId;
	}

	public void setBoCrmId(String boCrmId) {
		this.boCrmId = boCrmId == null ? null : boCrmId.trim();
	}

	public String getBoName() {
		return boName;
	}

	public void setBoName(String boName) {
		this.boName = boName == null ? null : boName.trim();
	}

	public String getBoCode() {
		return boCode;
	}

	public void setBoCode(String boCode) {
		this.boCode = boCode == null ? null : boCode.trim();
	}

	public String getBoBudget() {
		return boBudget;
	}

	public void setBoBudget(String boBudget) {
		this.boBudget = boBudget;
	}

	public Boolean getIsProjectApproval() {
		return isProjectApproval;
	}

	public void setIsProjectApproval(Boolean isProjectApproval) {
		this.isProjectApproval = isProjectApproval;
	}

	public Date getProjectApprovalTime() {
		return projectApprovalTime;
	}

	public void setProjectApprovalTime(Date projectApprovalTime) {
		this.projectApprovalTime = projectApprovalTime;
	}

	public Date getProjectApprovalEndtime() {
		return projectApprovalEndtime;
	}

	public void setProjectApprovalEndtime(Date projectApprovalEndtime) {
		this.projectApprovalEndtime = projectApprovalEndtime;
	}

	public String getDepEhrPk() {
		return depEhrPk;
	}

	public void setDepEhrPk(String depEhrPk) {
		this.depEhrPk = depEhrPk;
	}

	public String getBoPrincipalName() {
		return boPrincipalName;
	}

	public void setBoPrincipalName(String boPrincipalName) {
		this.boPrincipalName = boPrincipalName;
	}

	public String getBoPrincipalCode() {
		return boPrincipalCode;
	}

	public void setBoPrincipalCode(String boPrincipalCode) {
		this.boPrincipalCode = boPrincipalCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public Short getDataSource() {
		return dataSource;
	}

	public void setDataSource(Short dataSource) {
		this.dataSource = dataSource;
	}

	public List<ConfOtherTaker> getTakers() {
		return takers;
	}

	public void setTakers(List<ConfOtherTaker> takers) {
		this.takers = takers;
	}

}
