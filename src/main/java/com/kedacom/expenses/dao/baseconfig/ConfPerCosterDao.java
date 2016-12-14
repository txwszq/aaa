package com.kedacom.expenses.dao.baseconfig;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.baseconfig.ConfPerCoster;

@Repository
public class ConfPerCosterDao extends ExppensesBaseDao<ConfPerCoster> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return ConfPerCoster.class;
	}

}
