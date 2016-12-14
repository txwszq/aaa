package com.kedacom.expenses.service.expenses;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.expenses.ExpPaymentHisDao;
import com.kedacom.expenses.model.expenses.ExpPaymentHis;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
@Service
public class ExpPaymentHisService extends BaseService<ExpPaymentHis> {

	@Resource
	private ExpPaymentHisDao expPaymentHisDao;

	public ExpPaymentHisService() {
	}

	@Override
	protected IEntityDao<ExpPaymentHis, Long> getEntityDao() {
		return expPaymentHisDao;
	}

}
