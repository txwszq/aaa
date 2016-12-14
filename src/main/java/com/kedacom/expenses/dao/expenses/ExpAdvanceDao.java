package com.kedacom.expenses.dao.expenses;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.expenses.ExpAdvance;

/**
 * (费用预支底层dao).
 * @author zhujun
 * @version 2013-11-13
 */
@Repository
public class ExpAdvanceDao extends ExppensesBaseDao<ExpAdvance> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return ExpAdvance.class;
	}

}
