package com.kedacom.expenses.service.baseconfig;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.page.QueryFilter;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.baseconfig.ConfAmountDao;
import com.kedacom.expenses.model.baseconfig.ConfAmount;

@Service
public class ConfAmountService extends BaseService<ConfAmount> {

	@Resource
	private ConfAmountDao confAmountDao;

	public ConfAmountService() {
	}

	@Override
	protected IEntityDao<ConfAmount, Long> getEntityDao() {
		return confAmountDao;
	}

	public ConfAmountDao getConfAmountDao() {
		return confAmountDao;
	}

	public void setConfAmountDao(ConfAmountDao confAmountDao) {
		this.confAmountDao = confAmountDao;
	}

	public List<Map<String, Object>> getConfAmountMap(String sqlKey, QueryFilter queryFilter) {
		return confAmountDao.getResultByMap(sqlKey, queryFilter);
	}

	public ConfAmount getOnlyByCondition(Map<String, Object> params) {
		return (ConfAmount) confAmountDao.getOne("getAll", params);
	}
}
