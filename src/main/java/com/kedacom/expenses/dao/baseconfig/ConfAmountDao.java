package com.kedacom.expenses.dao.baseconfig;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.baseconfig.ConfAmount;

@Repository
public class ConfAmountDao extends ExppensesBaseDao<ConfAmount> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return ConfAmount.class;
	}

	public Long getPosIdByUserId(Long userid) {
		return (Long) getSqlSessionTemplate().selectOne(getIbatisMapperNamespace() + ".getPosIdByUserId",
				Long.valueOf(userid));
	}
}
