package com.kedacom.expenses.service.baseconfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.baseconfig.ConfCostSubjectTypeDao;
import com.kedacom.expenses.model.baseconfig.ConfCostSubjectType;

@Service
public class ConfCostSubjectTypeService extends BaseService<ConfCostSubjectType> {

	@Resource
	private ConfCostSubjectTypeDao confCostSubjectTypeDao;

	public ConfCostSubjectTypeDao getConfCostSubjectTypeDao() {
		return confCostSubjectTypeDao;
	}

	public void setConfCostSubjectTypeDao(ConfCostSubjectTypeDao confCostSubjectTypeDao) {
		this.confCostSubjectTypeDao = confCostSubjectTypeDao;
	}

	public ConfCostSubjectTypeService() {
	}

	@Override
	protected IEntityDao<ConfCostSubjectType, Long> getEntityDao() {
		return confCostSubjectTypeDao;
	}

	public List<ConfCostSubjectType> getListByKey(String sqlKey, Object params) {
		return confCostSubjectTypeDao.getBySqlKey(sqlKey, params);
	}

	public ConfCostSubjectType getByCode(String code) {
		return confCostSubjectTypeDao.getUnique("getByCode", code);
	}

	/**
	 * 费用报销类型 code转成Text
	 * @param code
	 * @return
	 */
	public String code2Text(String code) {
		List<ConfCostSubjectType> list = getAll();
		Map<String, String> map = new HashMap<String, String>();
		for (ConfCostSubjectType confCostSubjectType : list) {
			map.put(confCostSubjectType.getCode(), confCostSubjectType.getCost_name());
		}
		String text = map.get(code);
		return text;
	}
}
