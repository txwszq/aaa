package com.kedacom.expenses.service.expenses;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.expenses.ExpFinancePayDao;
import com.kedacom.expenses.model.expenses.ExpFinancePay;

/**
 * (描述).
 * @author zhujun
 * @version 2013-12-02
 */
@Service
public class ExpFinancePayService extends BaseService<ExpFinancePay> {

	@Resource
	private ExpFinancePayDao expFinancePayDao;

	public ExpFinancePayService() {
	}

	@Override
	protected IEntityDao<ExpFinancePay, Long> getEntityDao() {
		return expFinancePayDao;
	}

	public int deleteByCode(String code) {
		return expFinancePayDao.delBySqlKey("delByCode", code);
	}

	public int updateByCode(String code, ExpFinancePay expFinancePay) {
		return expFinancePayDao.update("updateByCode", expFinancePay);
	}

	public ExpFinancePay getByCode(String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		return (ExpFinancePay) expFinancePayDao.getOne("getAll", map);
	}

}
