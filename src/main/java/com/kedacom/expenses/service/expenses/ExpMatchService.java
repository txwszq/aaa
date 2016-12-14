package com.kedacom.expenses.service.expenses;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.expenses.ExpMatchDao;
import com.kedacom.expenses.model.expenses.ExpMatch;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
@Service
public class ExpMatchService extends BaseService<ExpMatch> {

	@Resource
	private ExpMatchDao expMatchDao;

	public ExpMatchService() {
	}

	@Override
	protected IEntityDao<ExpMatch, Long> getEntityDao() {
		return expMatchDao;
	}

	// 按照form_ID查询关联的单子
	public List<ExpMatch> getListByFromId(Long fromId) {
		return expMatchDao.getBySqlKey("getListByFromId", fromId);
	}

	// 按照to_ID查询关联的单子
	public List<ExpMatch> getListByToId(Long toId) {
		return expMatchDao.getBySqlKey("getListByToId", toId);
	}

}
