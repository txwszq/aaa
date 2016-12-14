package com.kedacom.expenses.dao.system;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kedacom.expenses.dao.base.ExppensesBaseDao;
import com.kedacom.expenses.model.system.Identity;

@Repository
public class IdentityDao extends ExppensesBaseDao<Identity> {

	public Class getEntityClass() {
		return Identity.class;
	}

	public Identity getByAlias(String alias) {
		String statment = getIbatisMapperNamespace() + ".getByAlias_" + getDbType();
		Identity obj = (Identity) getSqlSessionTemplate().selectOne(statment, alias);
		return obj;
	}

	public boolean isAliasExisted(String alias) {
		return ((Integer) getOne("isAliasExisted", alias)).intValue() > 0;
	}

	public boolean isAliasExistedByUpdate(Identity indetity) {
		return ((Integer) getOne("isAliasExistedByUpdate", indetity)).intValue() > 0;
	}

	public List<Identity> getList() {
		return getBySqlKey("getList");
	}
}
