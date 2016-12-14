/**
 * @(#)ConfCosterDao.java 2013-11-13 Copyright 2013 it.kedacom.com, Inc. All
 *                        rights reserved.
 */
package com.kedacom.expenses.dao.baseconfig;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.baseconfig.ConfCoster;

/**
 * (部门费用审核人底层操作DAO).
 * @author zhujun
 * @version 2013-11-13
 */
@Repository
public class ConfCosterDao extends ExppensesBaseDao<ConfCoster> {

	/**
	 * @see com.kedacom.core.db.GenericDao#getEntityClass()
	 */
	@Override
	public Class getEntityClass() {
		return ConfCoster.class;
	}

}
