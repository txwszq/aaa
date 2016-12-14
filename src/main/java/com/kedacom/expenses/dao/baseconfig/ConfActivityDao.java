package com.kedacom.expenses.dao.baseconfig;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.baseconfig.ConfActivity;

/**
 * (活动操作底层DAO).
 * @author zhujun
 * @version 2013-11-13
 */
@Repository
public class ConfActivityDao extends ExppensesBaseDao<ConfActivity> {

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return ConfActivity.class;
	}

	public List<ConfActivity> getAllByOtherTaker(Long userId) {
		List list = getSqlSessionTemplate().selectList(getIbatisMapperNamespace() + ".getAllByOtherTaker", userId);
		return list;
	}

}
