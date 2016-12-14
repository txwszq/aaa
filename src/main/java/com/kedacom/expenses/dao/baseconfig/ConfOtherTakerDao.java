package com.kedacom.expenses.dao.baseconfig;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.baseconfig.ConfOtherTaker;

@Repository
public class ConfOtherTakerDao extends ExppensesBaseDao<ConfOtherTaker> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return ConfOtherTaker.class;
	}

	public ConfOtherTaker getConfOtherTasker(Long type, Long businessId, Long userId) {
		Map<String, Long> param = new HashMap<String, Long>();
		param.put("type", type);
		param.put("businessId", businessId);
		param.put("userId", userId);
		ConfOtherTaker confOtherTaker = (ConfOtherTaker) getUnique("getConfOtherTasker", param);
		return confOtherTaker;
	}

}
