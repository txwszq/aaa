package com.kedacom.expenses.dao.expenses;

import org.springframework.stereotype.Repository;

import com.kedacom.core.db.BaseDao;
import com.kedacom.expenses.model.expenses.ExpFinancePay;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
@Repository
public class ExpFinancePayDao extends BaseDao<ExpFinancePay> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return ExpFinancePay.class;
	}
}
