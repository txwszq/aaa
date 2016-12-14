package com.kedacom.expenses.service.expenses;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.core.utils.TimeUtil;
import com.kedacom.expenses.dao.baseconfig.ConfAmountDao;
import com.kedacom.expenses.dao.expenses.ExpPaymentBDao;
import com.kedacom.expenses.exception.BusinessRuntimeException;
import com.kedacom.expenses.model.baseconfig.ConfAmount;
import com.kedacom.expenses.model.expenses.ExpPaymentB;
import com.kedacom.security.util.ContextUtil;

/**
 * (费用报销子表server).
 * @author zhujun
 * @version 2013-12-02
 */
@Service
public class ExpPaymentBService extends BaseService<ExpPaymentB> {

	@Resource
	private ExpPaymentBDao expPaymentBDao;

	@Resource
	private ConfAmountDao confAmountDao;

	public ExpPaymentBService() {
	}

	@Override
	protected IEntityDao<ExpPaymentB, Long> getEntityDao() {
		return expPaymentBDao;
	}

	public List<Map<String, String>> getPaymentRel(String sqlKey, Object params) {
		return expPaymentBDao.getPaymentRel(sqlKey, params);
	}

	/**
	 * 非法时间,该之间段不能报销
	 * @param expPaymentB
	 * @param realCostSubjectC
	 * @return
	 */
	public Boolean invalid(ExpPaymentB expPaymentB, String realCostSubjectC) {
		Long userId = ContextUtil.getCurrentUserId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginDate", TimeUtil.getDateString(expPaymentB.getBeginDate()));
		params.put("endDate", TimeUtil.getDateString(expPaymentB.getEndDate()));
		params.put("realCostSubjectC", realCostSubjectC);
		params.put("userId", userId);
		List<ExpPaymentB> result = expPaymentBDao.getBySqlKey("getByDate", params);
		if (result.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 超额,无法报销
	 * @param amount 此次报销金额
	 * @param unit 额度控制单位,1表示按月,2表示按天
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @param realCostSubjectC
	 * @return
	 */
	public Boolean isAboveQuota(Long userid, byte unit, Float amount, String costSubjectCode, Date beginDate,
			Date endDate) {
		// 获取该用户的岗位ID
		Long posid = confAmountDao.getPosIdByUserId(userid);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("costSubjectCode", costSubjectCode);
		params.put("userid", userid);
		params.put("posid", posid);
		List<ConfAmount> list = confAmountDao.getBySqlKey("getAmount", params);
		if (list == null || list.size() == 0) {
			throw new BusinessRuntimeException("您的账户存在异常,可能原因没有设置额度限制,请联系管理员.");
		}
		ConfAmount unique = list.get(0);
		int diff = 0;
		// 按月
		if (unit == 1) {
			// 按月 最少一个月,同一个月算1
			diff = TimeUtil.getMonthDiff(endDate, beginDate) + 1;
		} else if (unit == 2) {
			// 按天 最少一天,同一天,算1天
			diff = TimeUtil.getDayDiff(endDate, beginDate) + 1;
		}

		float amountLimit = unique.getClaim_cost() * diff;
		if (amount > amountLimit) {
			return true;
		} else {
			return false;
		}
	}

}
