package com.kedacom.expenses.dao.expenses;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.expenses.ExpUserAccount;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
@Repository
public class ExpUserAccountDao extends ExppensesBaseDao<ExpUserAccount> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return ExpUserAccount.class;
	}

}
