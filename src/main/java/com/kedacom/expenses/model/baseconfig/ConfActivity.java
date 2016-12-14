package com.kedacom.expenses.model.baseconfig;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.kedacom.expenses.model.ExpBaseModel;

public class ConfActivity extends ExpBaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5086335683544114951L;

	private Long id;// 主键
	private int source;// 来源 0外部，1内部
	private String outActId;// 外部系统活动主键
	private String actDesc;// 活动说明
	private String actCreaterCode;// 活动创建工号
	private Date actBegin;// 活动开始时间
	private Date actEnd;// 活动结束时间
	private String busId;// 商机ID
	private String busName;// 商机名称
	private String busCode;// 商机编码
	private String busBudGet;// 商机预算
	private int isProject;// 是否立项 0是，1否
	private Date busBegin;// 商机立项时间
	private Date busEnd;// 商机结束时间
	private String busTakerCode;// 商机负责人code
	private String cusId;// 客户ID
	private String cusName;// 客户名称
	private String cusState;// 客户状态
	private String cusTakerCode;// 客户主要负责人Code
	private List<ConfOtherTaker> takers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public String getOutActId() {
		return outActId;
	}

	public void setOutActId(String outActId) {
		this.outActId = outActId;
	}

	public String getActDesc() {
		return actDesc;
	}

	public void setActDesc(String actDesc) {
		this.actDesc = actDesc;
	}

	public String getActCreaterCode() {
		return actCreaterCode;
	}

	public void setActCreaterCode(String actCreaterCode) {
		this.actCreaterCode = actCreaterCode;
	}

	public Date getActBegin() {
		return actBegin;
	}

	public void setActBegin(Date actBegin) {
		this.actBegin = actBegin;
	}

	public Date getActEnd() {
		return actEnd;
	}

	public void setActEnd(Date actEnd) {
		this.actEnd = actEnd;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public String getBusCode() {
		return busCode;
	}

	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}

	public String getBusBudGet() {
		return busBudGet;
	}

	public void setBusBudGet(String busBudGet) {
		this.busBudGet = busBudGet;
	}

	public int getIsProject() {
		return isProject;
	}

	public void setIsProject(int isProject) {
		this.isProject = isProject;
	}

	public Date getBusBegin() {
		return busBegin;
	}

	public void setBusBegin(Date busBegin) {
		this.busBegin = busBegin;
	}

	public Date getBusEnd() {
		return busEnd;
	}

	public void setBusEnd(Date busEnd) {
		this.busEnd = busEnd;
	}

	public String getBusTakerCode() {
		return busTakerCode;
	}

	public void setBusTakerCode(String busTakerCode) {
		this.busTakerCode = busTakerCode;
	}

	public String getCusId() {
		return cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusState() {
		return cusState;
	}

	public void setCusState(String cusState) {
		this.cusState = cusState;
	}

	public String getCusTakerCode() {
		return cusTakerCode;
	}

	public void setCusTakerCode(String cusTakerCode) {
		this.cusTakerCode = cusTakerCode;
	}

	public List<ConfOtherTaker> getTakers() {
		return takers;
	}

	public void setTakers(List<ConfOtherTaker> takers) {
		this.takers = takers;
	}

}
