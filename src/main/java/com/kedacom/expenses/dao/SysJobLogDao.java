package com.kedacom.expenses.dao;

import org.springframework.stereotype.Repository;

import com.kedacom.core.db.BaseDao;
import com.kedacom.expenses.model.SysJobLog;

@Repository
public class SysJobLogDao extends BaseDao<SysJobLog> {

	public Class getEntityClass() {
		return SysJobLog.class;
	}
}
