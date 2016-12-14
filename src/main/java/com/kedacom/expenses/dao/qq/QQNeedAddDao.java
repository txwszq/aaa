package com.kedacom.expenses.dao.qq;

import com.kedacom.core.db.BaseDao;
import org.springframework.stereotype.Repository;
import com.kedacom.expenses.model.qq.QQNeedAdd;
@Repository
public class QQNeedAddDao extends BaseDao<QQNeedAdd> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return QQNeedAdd.class;
	}
	
}

















