package com.kedacom.expenses.dao.qq;

import org.springframework.stereotype.Repository;

import com.kedacom.core.db.BaseDao;
import com.kedacom.expenses.model.qq.FriendCode;

@Repository
public class FriendCodeDao extends BaseDao<FriendCode> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return FriendCode.class;
	}

}
