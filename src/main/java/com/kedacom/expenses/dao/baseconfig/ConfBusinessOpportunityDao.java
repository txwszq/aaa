package com.kedacom.expenses.dao.baseconfig;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.baseconfig.ConfBusinessOpportunity;

/**
 * @Description 商机信息dao层操作类
 * @author zhangwenbin
 * @date 2013-11-13
 */
@Repository
public class ConfBusinessOpportunityDao extends ExppensesBaseDao<ConfBusinessOpportunity> {

	public Class getEntityClass() {
		return ConfBusinessOpportunity.class;
	}

	public List<ConfBusinessOpportunity> getAllByOtherTaker(Long userId) {
		List list = getSqlSessionTemplate().selectList(getIbatisMapperNamespace() + ".getAllByOtherTaker", userId);
		return list;
	}

}
