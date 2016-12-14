package com.kedacom.expenses.service.expenses;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.expenses.ExpUserAccountHisDao;
import com.kedacom.expenses.model.expenses.ExpUserAccountHis;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
@Service
public class ExpUserAccountHisService extends BaseService<ExpUserAccountHis> {

	@Resource
	private ExpUserAccountHisDao expUserAccountHisDao;

	public ExpUserAccountHisService() {
	}

	@Override
	protected IEntityDao<ExpUserAccountHis, Long> getEntityDao() {
		return expUserAccountHisDao;
	}

}
