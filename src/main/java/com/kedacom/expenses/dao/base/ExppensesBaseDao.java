package com.kedacom.expenses.dao.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kedacom.core.db.GenericDao;
import com.kedacom.core.page.QueryFilter;
import com.kedacom.core.utils.BeanUtils;
import com.kedacom.expenses.model.ExpBaseModel;
import com.kedacom.security.util.ContextUtil;

public abstract class ExppensesBaseDao<E extends ExpBaseModel> extends GenericDao<E, Long> {

	public void saveOrUpdate(E entity) throws Exception {
		Long id = entity.getId();
		if (BeanUtils.isEmpty(id)) {
			this.add(entity);
		} else {
			this.update(entity);
		}
	}

	@Override
	public void add(E entity) {
		String addStatement = getIbatisMapperNamespace() + ".add";
		if ((entity instanceof ExpBaseModel)) {
			ExpBaseModel baseModel = (ExpBaseModel) entity;
			baseModel.setCreatetime(new Date());
			baseModel.setUpdatetime(new Date());

			Long curUserId = ContextUtil.getCurrentUserId();
			if (curUserId != null) {
				baseModel.setCreateby(curUserId);
			}
		}
		getSqlSessionTemplate().insert(addStatement, entity);
	}

	@Override
	public int update(E entity) {
		String updStatement = getIbatisMapperNamespace() + ".update";

		if ((entity instanceof ExpBaseModel)) {
			ExpBaseModel baseModel = (ExpBaseModel) entity;
			baseModel.setUpdatetime(new Date());

			Long curUserId = ContextUtil.getCurrentUserId();
			if (curUserId != null) {
				baseModel.setUpdateby(curUserId);
			}
		}

		int affectCount = getSqlSessionTemplate().update(updStatement, entity);
		return affectCount;
	}

	/**
	 * 查询结果,返回一个Map
	 * @param sqlKey
	 * @param params
	 * @param pageBean
	 * @return
	 */
	public List<Map<String, Object>> getResultByMap(String sqlKey, QueryFilter queryFilter) {
		String statementName = getIbatisMapperNamespace() + "." + sqlKey;
		List list = getList(statementName, queryFilter);

		queryFilter.setForWeb();
		return list;
	}

	/**
	 * 查询结果,返回一个Map
	 * @param sqlKey
	 * @param params
	 * @param pageBean
	 * @return
	 */
	public List<Map<String, Object>> getResultByMap(String sqlKey, Object param) {
		String statementName = getIbatisMapperNamespace() + "." + sqlKey;
		List<Map<String, Object>> list = getSqlSessionTemplate().selectList(statementName, param);
		return list;
	}
}
