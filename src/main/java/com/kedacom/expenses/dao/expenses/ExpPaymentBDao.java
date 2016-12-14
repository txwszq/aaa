package com.kedacom.expenses.dao.expenses;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.expenses.ExpPaymentB;

/**
 * (费用报销子表表底层dao).
 * @author zhujun
 * @version 2013-12-02
 */
@Repository
public class ExpPaymentBDao extends ExppensesBaseDao<ExpPaymentB> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return ExpPaymentB.class;
	}

	public List<Long> getIdsBymainId(String sqlKey, Object params) {
		String statement = getIbatisMapperNamespace() + "." + sqlKey;

		List<Long> list = getSqlSessionTemplate().selectList(statement, params);
		return list;
	}

	public List<Map<String, String>> getPaymentRel(String sqlKey, Object params) {
		String statement = getIbatisMapperNamespace() + "." + sqlKey;

		List<Map<String, String>> list = getSqlSessionTemplate().selectList(statement, params);
		return list;
	}

	public void delByExpensesHId(Long expensesHId) {
		delBySqlKey("delByExpensesHId", expensesHId);
	}
}
