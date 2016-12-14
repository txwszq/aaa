package com.kedacom.expenses.dao.qq;

import org.springframework.stereotype.Repository;

import com.kedacom.core.db.BaseDao;
import com.kedacom.expenses.model.qq.FriendCodeBak;

@Repository
public class FriendCodeBakDao extends BaseDao<FriendCodeBak> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return FriendCodeBak.class;
	}

}
