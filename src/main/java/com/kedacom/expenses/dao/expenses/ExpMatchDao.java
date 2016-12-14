package com.kedacom.expenses.dao.expenses;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.expenses.ExpMatch;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
@Repository
public class ExpMatchDao extends ExppensesBaseDao<ExpMatch> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return ExpMatch.class;
	}

	public void delByExpensesHId(Long expensesHId) {
		delBySqlKey("delByExpensesHId", expensesHId);
	}
}
