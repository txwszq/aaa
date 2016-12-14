package com.kedacom.expenses.model.baseconfig;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kedacom.expenses.model.ExpBaseModel;

public class ConfClient extends ExpBaseModel implements Serializable {

	private Long id;

	private String clientCrmId;

	private String clientCode;

	private String clientName;

	// Prospect、Examine、Active、Reject、Inactive"
	// 潜在、审核中、有效、拒绝、失效
	private String clientStatus;

	private Long clientPrincipalId;

	private String clientPrincipalName;

	private String clientPrincipalCode;

	private Long departmentId;

	private String depEhrPk;

	private String departmentName;

	private String departmentCode;

	private Short dataSource = 1;// 默认为CRM来源

	private static final long serialVersionUID = 1L;

	private List<ConfOtherTaker> takers;

	// Prospect、Examine、Active、Reject、Inactive"

	// Prospect、Examine、Active、Reject、Inactive"

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientCrmId() {
		return clientCrmId;
	}

	public void setClientCrmId(String clientCrmId) {
		this.clientCrmId = clientCrmId == null ? null : clientCrmId.trim();
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName == null ? null : clientName.trim();
	}

	public String getClientStatus() {
		return clientStatus;
	}

	public void setClientStatus(String clientStatus) {
		this.clientStatus = clientStatus == null ? null : clientStatus.trim();
	}

	public String getDepEhrPk() {
		return depEhrPk;
	}

	public void setDepEhrPk(String depEhrPk) {
		this.depEhrPk = depEhrPk;
	}

	public Long getClientPrincipalId() {
		return clientPrincipalId;
	}

	public void setClientPrincipalId(Long clientPrincipalId) {
		this.clientPrincipalId = clientPrincipalId;
	}

	public String getClientPrincipalName() {
		return clientPrincipalName;
	}

	public void setClientPrincipalName(String clientPrincipalName) {
		this.clientPrincipalName = clientPrincipalName;
	}

	public String getClientPrincipalCode() {
		return clientPrincipalCode;
	}

	public void setClientPrincipalCode(String clientPrincipalCode) {
		this.clientPrincipalCode = clientPrincipalCode;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
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

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public List<ConfOtherTaker> getTakers() {
		return takers;
	}

	public void setTakers(List<ConfOtherTaker> takers) {
		this.takers = takers;
	}

}
