package com.kedacom.expenses.dao.expenses;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.expenses.ExpPaymentHis;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
@Repository
public class ExpPaymentHisDao extends ExppensesBaseDao<ExpPaymentHis> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return ExpPaymentHis.class;
	}

	public void delByExpensesHId(Long expensesHId) {
		delBySqlKey("delByExpensesHId", expensesHId);
	}
}
