package com.kedacom.expenses.service.expenses;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.expenses.ExpPayDao;
import com.kedacom.expenses.model.expenses.ExpPay;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
@Service
public class ExpPayService extends BaseService<ExpPay> {

	@Resource
	private ExpPayDao expPayDao;

	public ExpPayService() {
	}

	@Override
	protected IEntityDao<ExpPay, Long> getEntityDao() {
		return expPayDao;
	}

}
