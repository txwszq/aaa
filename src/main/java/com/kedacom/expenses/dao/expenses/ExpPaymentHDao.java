package com.kedacom.expenses.dao.expenses;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.expenses.ExpPaymentH;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
@Repository
public class ExpPaymentHDao extends ExppensesBaseDao<ExpPaymentH> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return ExpPaymentH.class;
	}

	public List<Map<String, Object>> getBySqlKey2(String sqlKey, Object params) {
		String statement = getIbatisMapperNamespace() + "." + sqlKey;
		List<Map<String, Object>> list = getSqlSessionTemplate().selectList(statement, params);
		return list;
	}
}
