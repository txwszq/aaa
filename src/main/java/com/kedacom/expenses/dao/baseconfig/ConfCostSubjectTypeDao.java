package com.kedacom.expenses.dao.baseconfig;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.baseconfig.ConfCostSubjectType;

@Repository
public class ConfCostSubjectTypeDao extends ExppensesBaseDao<ConfCostSubjectType> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return ConfCostSubjectType.class;
	}

}
