package com.kedacom.expenses.dao.expenses;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.expenses.ExpTransform;

/**
 * (转移表记录).
 * @author zhujun
 * @version 2013-11-13
 */
@Repository
public class ExpTransformDao extends ExppensesBaseDao<ExpTransform> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return ExpTransform.class;
	}

}
