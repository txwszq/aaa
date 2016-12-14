package com.kedacom.expenses.dao.expenses;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.expenses.ExpUserAccountHis;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
@Repository
public class ExpUserAccountHisDao extends ExppensesBaseDao<ExpUserAccountHis> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return ExpUserAccountHis.class;
	}

}
