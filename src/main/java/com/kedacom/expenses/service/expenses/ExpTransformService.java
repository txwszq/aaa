package com.kedacom.expenses.service.expenses;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.expenses.ExpAdvanceDao;
import com.kedacom.expenses.dao.expenses.ExpTransformDao;
import com.kedacom.expenses.dao.expenses.ExpUserAccountDao;
import com.kedacom.expenses.model.expenses.ExpAdvance;
import com.kedacom.expenses.model.expenses.ExpMatch;
import com.kedacom.expenses.model.expenses.ExpTransform;
import com.kedacom.expenses.model.expenses.ExpUserAccount;
import com.kedacom.expenses.model.expenses.ExpUserAccountHis;
import com.kedacom.expenses.service.base.BaseInfoServer;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * (转移表server).
 * @author zhujun
 * @version 2013-11-13
 */
@Service
public class ExpTransformService extends BaseService<ExpTransform> {

	@Resource
	private ExpTransformDao expTransformDao;

	@Resource
	private BaseInfoServer baseServer;

	@Resource
	private ExpAdvanceDao expAdvanceDao;

	@Resource
	private ExpUserAccountDao accountDao;

	public ExpTransformService() {
	}

	@Override
	protected IEntityDao<ExpTransform, Long> getEntityDao() {
		return expTransformDao;
	}

	/**
	 * (转账操作 暂时只针对专项转个人专项).
	 * @param tranForm
	 * @param adv
	 */
	public void transformCost(ExpTransform tranForm, ExpAdvance adv) {
		expTransformDao.add(tranForm);
		// 获取账户余额表
		ExpUserAccount account = baseServer.getExpUserAccount(adv.getApplyerId());
		// 专项预支
		if (ExpAdvance.ADV_BILL_TYPE == adv.getBillType() && ExpAdvance.PASS == adv.getBillState()) {
			// 更新专项预支剩余金额
			if (tranForm.getHappenedAmount() < adv.getResidualAmount()) {
				adv.setResidualAmount(adv.getResidualAmount() - tranForm.getHappenedAmount());// 剩余金额
				adv.setIsFinish((long) ExpAdvance.NO_FINISH);
				expAdvanceDao.update(adv);
			} else if (tranForm.getHappenedAmount() == adv.getResidualAmount()) {
				adv.setResidualAmount(0F);
				adv.setIsFinish((long) ExpAdvance.FINISH);
				expAdvanceDao.update(adv);
			}
			// 更新账户余额
			account.setSpecialTurnAmount(account.getSpecialTurnAmount() + tranForm.getHappenedAmount());
			account
					.setSpecialResidual(account.getSpecialAdvanced() - account.getSpecialPaymentedTotal() + account.getSpecialPayedAmount()
							- account.getSpecialTurnAmount());// 算出最新的专项余额
			accountDao.update(account);
			// 账户历史表中增加一条数据
			baseServer.addAccountHis(adv.getId(), ExpUserAccountHis.BUSTYPE6, account.getId(), tranForm.getHappenedAmount(), adv.getApplyerId(), "专项预支转移到个人预支");
			// 关系表中增加一条记录
			baseServer.addMatch(ExpMatch.BUS_TYPE3, tranForm.getId(), adv.getId(), tranForm.getHappenedAmount(), "专项预支转个人预支");
		}

	}

}
