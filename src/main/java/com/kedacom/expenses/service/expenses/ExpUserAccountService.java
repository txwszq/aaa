package com.kedacom.expenses.service.expenses;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.expenses.ExpUserAccountDao;
import com.kedacom.expenses.filter.PageFilter;
import com.kedacom.expenses.model.expenses.ExpUserAccount;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
@Service
public class ExpUserAccountService extends BaseService<ExpUserAccount> {

	@Resource
	private ExpUserAccountDao expUserAccountDao;

	public ExpUserAccountService() {
	}

	@Override
	protected IEntityDao<ExpUserAccount, Long> getEntityDao() {
		return expUserAccountDao;
	}

	public ExpUserAccount getUserAccountByUserId(Long userId) {
		return (ExpUserAccount) expUserAccountDao.getOne("getByUserId", userId);
	}

	public List<ExpUserAccount> getUserAccount(PageFilter filter) {
		return expUserAccountDao.getBySqlKey("getUserAccount", filter);
	}
}
