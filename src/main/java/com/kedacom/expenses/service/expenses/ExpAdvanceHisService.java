package com.kedacom.expenses.service.expenses;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.expenses.ExpAdvanceHisDao;
import com.kedacom.expenses.model.expenses.ExpAdvanceHis;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * (费用预支server).
 * @author zhujun
 * @version 2013-12-2
 */
@Service
public class ExpAdvanceHisService extends BaseService<ExpAdvanceHis> {

	@Resource
	private ExpAdvanceHisDao expAdvanceHisDao;

	public ExpAdvanceHisService() {
	}

	@Override
	protected IEntityDao<ExpAdvanceHis, Long> getEntityDao() {
		return expAdvanceHisDao;
	}

}
