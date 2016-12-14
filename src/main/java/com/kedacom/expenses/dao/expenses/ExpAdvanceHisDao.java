package com.kedacom.expenses.dao.expenses;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.expenses.ExpAdvanceHis;

/**
 * (费用预支历史表底层dao).
 * @author zhujun
 * @version 2013-12-02
 */
@Repository
public class ExpAdvanceHisDao extends ExppensesBaseDao<ExpAdvanceHis> {

	@Override
	public Class getEntityClass() {
		return ExpAdvanceHis.class;
	}

}
