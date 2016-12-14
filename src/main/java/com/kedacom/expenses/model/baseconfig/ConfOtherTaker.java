package com.kedacom.expenses.model.baseconfig;

import java.io.Serializable;

import com.kedacom.expenses.model.ExpBaseModel;

public class ConfOtherTaker extends ExpBaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private Long id;
	// 1表示客户，2表示商机，3表示活动
	private Long type;
	// 表示3张表的主键
	private Long business_id;
	// user_id
	private Long user_id;
	// user_name
	private String user_name;
	// user_account
	private String user_account;
	// user_code
	private String user_code;

	public static final Long CLIENT_TYPE = 1L;// 客户
	public static final Long BUS_TYPE = 2L;// 商机
	public static final Long ACT_TYPE = 3L;// 活动

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
	 * 设置 1表示客户，2表示商机，3表示活动
	 */
	public Long getType() {
		return type;
	}

	/**
	 * 获取 1表示客户，2表示商机，3表示活动
	 */
	public void setType(Long type) {
		this.type = type;
	}

	/**
	 * 设置 表示3张表的主键
	 */
	public Long getBusiness_id() {
		return business_id;
	}

	/**
	 * 获取 表示3张表的主键
	 */
	public void setBusiness_id(Long business_id) {
		this.business_id = business_id;
	}

	/**
	 * 设置 user_id
	 */
	public Long getUser_id() {
		return user_id;
	}

	/**
	 * 获取 user_id
	 */
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	/**
	 * 设置 user_name
	 */
	public String getUser_name() {
		return user_name;
	}

	/**
	 * 获取 user_name
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	/**
	 * 设置 user_account
	 */
	public String getUser_account() {
		return user_account;
	}

	/**
	 * 获取 user_account
	 */
	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	/**
	 * 设置 user_code
	 */
	public String getUser_code() {
		return user_code;
	}

	/**
	 * 获取 user_code
	 */
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
}
