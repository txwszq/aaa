package com.kedacom.expenses.dao.qq;

import com.kedacom.core.db.BaseDao;
import org.springframework.stereotype.Repository;
import com.kedacom.expenses.model.qq.QQNumManage;
@Repository
public class QQNumManageDao extends BaseDao<QQNumManage> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return QQNumManage.class;
	}
	
}

















