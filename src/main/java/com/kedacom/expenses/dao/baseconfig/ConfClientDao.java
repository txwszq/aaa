package com.kedacom.expenses.dao.baseconfig;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.baseconfig.ConfClient;

/**
 * @Description 客户信息dao层操作类
 * @author zhangwenbin
 * @date 2013-11-13
 */
@Repository
public class ConfClientDao extends ExppensesBaseDao<ConfClient> {

	public Class getEntityClass() {
		return ConfClient.class;
	}

}
