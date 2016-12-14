package com.kedacom.expenses.model.qq;

import com.kedacom.core.model.BaseModel;

public class QQNumManage extends BaseModel {

	private static final long serialVersionUID = 1L;

	// id
	private Long id;
	// qq_num
	private String qq_num;
	// qq_pwd
	private String qq_pwd;
	// 1表示正常,0表示不正常
	private Long friend_is_normal;
	// 1表示正常,0表示不正常
	private Long qzone_is_normal;
	// 1表示需要添加朋友网好友,0表示不需要
	private Long need_add_friend;
	// 1表示需要添加QQ好友,0表示不需要
	private Long need_add_qzone;
	// user_id
	private Long user_id;
	// 序号
	private Integer sn;

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

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
	 * 设置 qq_num
	 */
	public String getQq_num() {
		return qq_num;
	}

	/**
	 * 获取 qq_num
	 */
	public void setQq_num(String qq_num) {
		this.qq_num = qq_num;
	}

	/**
	 * 设置 qq_pwd
	 */
	public String getQq_pwd() {
		return qq_pwd;
	}

	/**
	 * 获取 qq_pwd
	 */
	public void setQq_pwd(String qq_pwd) {
		this.qq_pwd = qq_pwd;
	}

	/**
	 * 设置 1表示正常,0表示不正常
	 */
	public Long getFriend_is_normal() {
		return friend_is_normal;
	}

	/**
	 * 获取 1表示正常,0表示不正常
	 */
	public void setFriend_is_normal(Long friend_is_normal) {
		this.friend_is_normal = friend_is_normal;
	}

	/**
	 * 设置 1表示正常,0表示不正常
	 */
	public Long getQzone_is_normal() {
		return qzone_is_normal;
	}

	/**
	 * 获取 1表示正常,0表示不正常
	 */
	public void setQzone_is_normal(Long qzone_is_normal) {
		this.qzone_is_normal = qzone_is_normal;
	}

	/**
	 * 设置 1表示需要添加朋友网好友,0表示不需要
	 */
	public Long getNeed_add_friend() {
		return need_add_friend;
	}

	/**
	 * 获取 1表示需要添加朋友网好友,0表示不需要
	 */
	public void setNeed_add_friend(Long need_add_friend) {
		this.need_add_friend = need_add_friend;
	}

	/**
	 * 设置 1表示需要添加QQ好友,0表示不需要
	 */
	public Long getNeed_add_qzone() {
		return need_add_qzone;
	}

	/**
	 * 获取 1表示需要添加QQ好友,0表示不需要
	 */
	public void setNeed_add_qzone(Long need_add_qzone) {
		this.need_add_qzone = need_add_qzone;
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
}
