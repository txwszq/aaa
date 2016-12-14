/**
 * @(#)ExpenseBaseModel.java 2013-12-2 Copyright 2013 it.kedacom.com, Inc. All
 *                           rights reserved.
 */
package com.kedacom.expenses.model;

import java.io.Serializable;
import java.util.Date;

/**
 * (用一句话描述类的主要功能).
 * @author zhujun
 * @version 2013-12-2
 */
public class ExpBaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

    protected Long id;
	protected Long createby;
	protected Date createtime;
	protected Date updatetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    protected Long updateby;

	public Long getCreateby() {
		return createby;
	}

	public void setCreateby(Long createby) {
		this.createby = createby;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Long getUpdateby() {
		return updateby;
	}

	public void setUpdateby(Long updateby) {
		this.updateby = updateby;
	}

}
