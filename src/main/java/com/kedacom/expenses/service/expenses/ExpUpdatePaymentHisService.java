package com.kedacom.expenses.service.expenses;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.expenses.ExpUpdatePaymentHisDao;
import com.kedacom.expenses.model.expenses.ExpUpdatePaymentHis;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
@Service
public class ExpUpdatePaymentHisService extends BaseService<ExpUpdatePaymentHis> {

	@Resource
	private ExpUpdatePaymentHisDao expUpdatePaymentHisDao;

	public ExpUpdatePaymentHisService() {
	}

	@Override
	protected IEntityDao<ExpUpdatePaymentHis, Long> getEntityDao() {
		return expUpdatePaymentHisDao;
	}

}
