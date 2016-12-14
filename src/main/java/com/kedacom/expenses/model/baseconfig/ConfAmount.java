package com.kedacom.expenses.model.baseconfig;

import java.io.Serializable;

import com.kedacom.expenses.model.ExpBaseModel;

public class ConfAmount extends ExpBaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static byte TYPE_USERID = 1;

	public final static byte TYPE_DEPID = 2;

	public final static byte STATE_NOT_SET = 0;

	public final static byte STATE_SET = 1;

	// id
	private Long id;
	// 费用科目类型ID
	private String cost_subject_code;
	// 报销额度
	private Float claim_cost;
	// 状态 0:未设置,1:已设置
	private Byte state;
	// 用户ID
	private Long user_or_dept_id;
	// 1表示用户ID,2表示部门ID
	private Byte type;

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
	 * 设置 费用科目类型ID
	 */
	public String getCost_subject_code() {
		return cost_subject_code;
	}

	/**
	 * 获取 费用科目类型ID
	 */
	public void setCost_subject_code(String cost_subject_code) {
		this.cost_subject_code = cost_subject_code;
	}

	/**
	 * 设置 报销额度
	 */

	/**
	 * 设置 状态 0:未设置,1:已设置
	 */
	public Byte getState() {
		return state;
	}

	public Float getClaim_cost() {
		return claim_cost;
	}

	public void setClaim_cost(Float claimCost) {
		claim_cost = claimCost;
	}

	/**
	 * 获取 状态 0:未设置,1:已设置
	 */
	public void setState(Byte state) {
		this.state = state;
	}

	/**
	 * 设置 用户ID
	 */
	public Long getUser_or_dept_id() {
		return user_or_dept_id;
	}

	/**
	 * 获取 用户ID
	 */
	public void setUser_or_dept_id(Long user_or_dept_id) {
		this.user_or_dept_id = user_or_dept_id;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}
}
