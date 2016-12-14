package com.kedacom.expenses.service.expenses;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.baseconfig.ConfPerCosterDao;
import com.kedacom.expenses.dao.expenses.ExpAdvanceDao;
import com.kedacom.expenses.dao.expenses.ExpAdvanceHisDao;
import com.kedacom.expenses.dao.expenses.ExpPaymentHDao;
import com.kedacom.expenses.dao.expenses.ExpUserAccountDao;
import com.kedacom.expenses.model.ExpHeadVO;
import com.kedacom.expenses.model.expenses.ExpAdvance;
import com.kedacom.expenses.model.expenses.ExpAdvanceHis;
import com.kedacom.expenses.model.expenses.ExpMatch;
import com.kedacom.expenses.model.expenses.ExpPaymentH;
import com.kedacom.expenses.model.expenses.ExpTransform;
import com.kedacom.expenses.model.expenses.ExpUserAccount;
import com.kedacom.expenses.model.expenses.ExpUserAccountHis;
import com.kedacom.expenses.service.base.BaseInfoServer;
import com.kedacom.expenses.service.findperson.FindApproverUtil;
import com.kedacom.expenses.service.system.IdentityService;
import com.kedacom.security.service.auth.SysUserService;
import com.kedacom.security.util.ContextUtil;

/**
 * (费用预支子表server).
 * @author zhujun
 * @version 2013-12-01
 */
@Service
public class ExpAdvanceService extends BaseService<ExpAdvance> {

	@Resource
	private ExpAdvanceDao expAdvanceDao;

	@Resource
	private ExpAdvanceHisDao expAdvanceHisDao;

	@Resource
	private BaseInfoServer baseServer;

	@Resource
	private ExpUserAccountDao accountDao;

	@Resource
	private ExpPaymentHDao expPaymentHDao;

	@Resource
	private SysUserService sysUserService;

	@Resource
	private ConfPerCosterDao confPerCosterDao;

	@Resource
	private ExpTransformService trunServer;

	@Resource
	private FindApproverUtil findApproverUtil;

	@Resource
	private IdentityService identityService;

	private final static String PER_CODE = "fyyzbh";
	private final static String SPE_CODE = "zxfyyzbh";

	private final static String flowKey = "fysplc";

	@Resource
	private Properties configproperties;

	public ExpAdvanceService() {
	}

	@Override
	protected IEntityDao<ExpAdvance, Long> getEntityDao() {
		return expAdvanceDao;
	}

	public void delExpAdv(long billId) {
		expAdvanceDao.delById(billId);
		expAdvanceHisDao.delBySqlKey("delByCosAdvId", billId);
	}

	/****
	 * (专项转个人预支).
	 * @param happenAmount
	 */
	public void trunSpecialAmount(Float happenAmount, String billId) {
		ExpTransform transForm = new ExpTransform();
		transForm.setBillType(ExpTransform.PER_TYPE);
		transForm.setHappenedAmount(happenAmount);
		transForm.setIsTurn(ExpTransform.TURN_YES);
		trunServer.add(transForm);
		ExpAdvance expAdv = expAdvanceDao.getById(new Long(billId));
		// 获取员工账号信息
		ExpUserAccount account = baseServer.getExpUserAccount(expAdv.getApplyerId());
		if (expAdv.getResidualAmount() > happenAmount) {
			expAdv.setResidualAmount(expAdv.getResidualAmount() - happenAmount);
			expAdvanceDao.update(expAdv);

		} else {
			expAdv.setResidualAmount(expAdv.getResidualAmount() - happenAmount);
			expAdv.setIsFinish((long) ExpAdvance.FINISH);
			expAdvanceDao.update(expAdv);
		}
		// 账户专项转个人金额
		account.setSpecialTurnAmount(account.getSpecialTurnAmount() + happenAmount);
		baseServer.addMatch(ExpMatch.BUS_TYPE3, transForm.getId(), expAdv.getId(), happenAmount, "专项转个人预支");
	}

	/****
	 * (获取预支单据 新建或者打开).
	 * @param 1 专项预支 2 个人费用预支
	 * @param type
	 * @return
	 */
	public ExpAdvance initForm(long userPk, int type, String billID) {
		ExpAdvance adv = new ExpAdvance();
		if (StringUtils.isNotEmpty(billID)) {
			adv = expAdvanceDao.getById(new Long(billID));
		} else {
			adv = (ExpAdvance) expAdvanceDao.getOne("getUserInfo", userPk);
			adv.setBillState((long) ExpAdvance.FREE);
			// 初始用户信息 根据用户PK获取用户基本信息
			if (ExpAdvance.PER_BILL_TYPE == type) {
				adv.setBillCode(identityService.nextId(PER_CODE));
				adv.setBillType((long) ExpAdvance.PER_BILL_TYPE);
			} else if (ExpAdvance.ADV_BILL_TYPE == type) {
				adv.setBillCode(identityService.nextId(SPE_CODE));
				adv.setBillType((long) ExpAdvance.ADV_BILL_TYPE);
			}
		}
		adv = getExpAdvHead(userPk, adv);
		// 个人预支审批中的金额 个人申请的时候看，审批跟申请数据就可能不一致
		Float advPerAmount = baseServer.getNoFinishExp(userPk, ExpAdvance.PER_BILL_TYPE);
		adv.getExpAdvHis().setPerResidual(adv.getExpAdvHis().getPerResidual() + advPerAmount);// 个人预支余额=账户个人预支余额+没审核通过的个人预支单据剩余金额总和
		// 专项预支审批中的数据个人申请的时候 看审批跟申请数据就可能不一致
		Float advSpecialAmount = baseServer.getNoFinishExp(userPk, ExpAdvance.ADV_BILL_TYPE);
		adv.getExpAdvHis().setSpecialResidual(adv.getExpAdvHis().getSpecialResidual() + advSpecialAmount);// 专项预支余额=账户专项预支余额+没审核通过的专项预支单据剩余金额总和
		return adv;
	}

	/**
	 * (保存数据).
	 * @param expAdvPer
	 * @return
	 */
	public ExpAdvance saveAdvPer(ExpAdvance expAdvPer) {
		// 重新获取一次金额
		expAdvPer = getExpAdvHead(expAdvPer.getApplyerId(), expAdvPer);
		expAdvPer.setIsFinish((long) ExpAdvance.NO_FINISH);
		expAdvPer.setBillState((long) ExpAdvance.FREE);
		expAdvPer.setResidualAmount(expAdvPer.getCurrAdvanceAmount());
		if (null == expAdvPer.getId()) {
			expAdvanceDao.add(expAdvPer);
		} else {
			expAdvanceDao.update(expAdvPer);
		}
		expAdvPer.getExpAdvHis().setCostAdvanceId(expAdvPer.getId());
		if (null == expAdvPer.getExpAdvHis().getId()) {
			expAdvanceHisDao.add(expAdvPer.getExpAdvHis());
		} else {
			expAdvanceHisDao.update(expAdvPer.getExpAdvHis());
		}
		return expAdvPer;
	}

	/**
	 * (后台直接增加的预支单据).
	 * @param expAdvPer
	 * @return
	 */
	public ExpAdvance submitAdvPer(ExpAdvance expAdvPer) {
		// 重新获取一次金额
		expAdvPer = getExpAdvHead(expAdvPer.getApplyerId(), expAdvPer);
		expAdvPer.setIsFinish((long) ExpAdvance.NO_FINISH);
		expAdvPer.setBillState((long) ExpAdvance.PASS);
		expAdvPer.setDataSource(ExpAdvance.DATA_SOURCE_MANA);
		// 对数据进行一次保存/更新 保证数据的完整以及正确性
		expAdvPer.setResidualAmount(expAdvPer.getCurrAdvanceAmount());
		if (null == expAdvPer.getId()) {
			expAdvanceDao.add(expAdvPer);
		} else {
			expAdvanceDao.update(expAdvPer);
		}
		expAdvPer.getExpAdvHis().setCostAdvanceId(expAdvPer.getId());
		if (null == expAdvPer.getExpAdvHis().getId()) {
			expAdvanceHisDao.add(expAdvPer.getExpAdvHis());
		} else {
			expAdvanceHisDao.update(expAdvPer.getExpAdvHis());
		}
		// 数据的运算操作
		expAdvPer = countExpAdv(expAdvPer);
		return expAdvPer;
	}

	/**
	 * (获取expAdvance).
	 * @param userPk
	 * @return
	 */
	private ExpAdvance getExpAdvHead(long userPk, ExpAdvance expAdv) {
		ExpHeadVO account = baseServer.takeHeadVO(userPk);
		ExpAdvanceHis expHis = new ExpAdvanceHis();
		if (null != expAdv.getExpAdvHis()) {
			if (expAdv.getBillState() == ExpAdvance.APPROVE) {
				expHis = expAdv.getExpAdvHis();
			} else {
				expHis = expAdv.getExpAdvHis();
				expHis.setPerPayedAmount(account.getPerPayedAmount());
				expHis.setPerPaymentedTotal(account.getPerPaymentedTotal());
				expHis.setPerResidual(account.getPerResidual());
				expHis.setPerAdvanced(account.getPerAdvanced());
				expHis.setSpecialAdvanced(account.getSpecialAdvanced());
				expHis.setSpecialPayedAmount(account.getSpecialPayedAmount());
				expHis.setSpecialPaymentedTotal(account.getSpecialPaymentedTotal());
				expHis.setSpecialResidual(account.getSpecialResidual());
				expAdv.setExpAdvHis(expHis);
			}
		} else {
			expHis.setPerPayedAmount(account.getPerPayedAmount());
			expHis.setPerPaymentedTotal(account.getPerPaymentedTotal());
			expHis.setPerResidual(account.getPerResidual());
			expHis.setPerAdvanced(account.getPerAdvanced());
			expHis.setSpecialAdvanced(account.getSpecialAdvanced());
			expHis.setSpecialPayedAmount(account.getSpecialPayedAmount());
			expHis.setSpecialPaymentedTotal(account.getSpecialPaymentedTotal());
			expHis.setSpecialResidual(account.getSpecialResidual());
			expAdv.setExpAdvHis(expHis);
		}
		// 1，专项预支；2，个人预支
		if (ExpAdvance.PER_BILL_TYPE == expAdv.getBillType()) {
			// 获取用户可用余额 专项最新余额+个人最新余额-审批没通过的单据
			expAdv.setBalanceAmount(account.getTotalResidual());
			expAdv.setConfAmount(account.getAdvConfAmount());
			expAdv.setBillType((long) ExpAdvance.PER_BILL_TYPE);
		} else if (ExpAdvance.ADV_BILL_TYPE == expAdv.getBillType()) {
			expAdv.setBillType((long) ExpAdvance.ADV_BILL_TYPE);
		}
		return expAdv;
	}

	/**
	 * (提交预支单审批).
	 * @param exp
	 */
	public ExpAdvance commitAdv(ExpAdvance exp) throws Exception {
		exp = getExpAdvHead(exp.getApplyerId(), exp);
		exp.setResidualAmount(exp.getCurrAdvanceAmount());
		if (null != exp.getProcessesId()) {// 驳回状态的单子
			exp.setIsFinish((long) ExpAdvance.NO_FINISH);
			exp.setBillState((long) ExpAdvance.APPROVE);
		} else {// 草稿状态
			exp.getExpAdvHis().setCostAdvanceId(exp.getId());
			if (null == exp.getExpAdvHis().getId()) {
				expAdvanceHisDao.add(exp.getExpAdvHis());
			} else {
				expAdvanceHisDao.update(exp.getExpAdvHis());
			}
			// 启动流程
			String flowKey = configproperties.getProperty("advanceFlowKey");
			String processId = findApproverUtil.startFlow(exp.getId().toString(), exp.getBillCode(), flowKey);
			exp.setIsFinish((long) ExpAdvance.NO_FINISH);
			exp.setBillState((long) ExpAdvance.APPROVE);
			// 保存流程实例id
			if (processId != null) {
				exp.setProcessesId(new Long(processId));
			}
		}
		if (null != exp.getId()) {
			expAdvanceDao.update(exp);
		} else {
			expAdvanceDao.add(exp);
		}
		return exp;
	}

	/**
	 * (审批操作 对于预支单审批通过需要更新账户表，对于报销审批通过不能立即写入账户表).
	 * @param processId
	 * @param isPass 是否通过
	 * @param isFinish
	 * @throws Exception
	 */
	public void approveAdv(Long id, String type, String argeeContent, Short isNeedLead, Short isNeedPay)
			throws Exception {
		// 根据流程获取预支单据号
		ExpAdvance exp = (ExpAdvance) expAdvanceDao.getById(id);
		if (type.equals(ExpAdvance.AGGREE)) {// 同意审批
			List<Object> processVar = approveVar(isNeedLead, isNeedPay);
			// 调用流程(通过)
			// 如果流程结束
			// 计算金额变化
			if (false) {
				countExpAdv(exp);
				exp.setBillState(new Long(ExpAdvance.PASS));
			}
		} else if (type.equals(ExpAdvance.REJECT)) {
			exp.setBillState(new Long(ExpAdvance.NOPASS));
		}
		exp.setApproveNote(argeeContent);
		exp.setApproverId(ContextUtil.getCurrentUser().getUserId());
		exp.setApproveDate(new Date());
		exp.setUpdateby(ContextUtil.getCurrentUser().getUserId());
		exp.setUpdatetime(new Date());
		expAdvanceDao.update(exp);
	}

	/**
	 * 封装流程变量
	 * @return
	 */
	private List<Object> approveVar(Short isNeedLead, Short isNeedPay) {
		// 是否需要分管领导和是否需要付款人
		List<Object> processVar = null;
		if (isNeedLead != null) {
			processVar = new ArrayList<Object>();
			Object isNeedLeadVar = new Object();
			processVar.add(isNeedLeadVar);
		}
		if (isNeedPay != null) {
			if (processVar == null) {
				processVar = new ArrayList<Object>();
			}
			Object isNeedPayVar = new Object();
			processVar.add(isNeedPayVar);
		}
		return processVar;
	}

	/**
	 * (计算金额的变化).
	 * @param exp
	 * @return
	 */
	private ExpAdvance countExpAdv(ExpAdvance exp) {
		// 获取账户余额表
		ExpUserAccount account = baseServer.getExpUserAccount(exp.getApplyerId());
		// 如果是专项预支的
		if (ExpAdvance.ADV_BILL_TYPE == exp.getBillType()) {
			// 算出专项预支总金额
			account.setSpecialAdvanced(account.getSpecialAdvanced() + exp.getCurrAdvanceAmount());
			// 专项预支-专项报销+专项付款-转移（个转专）
			account.setSpecialResidual(account.getSpecialAdvanced() - account.getSpecialPaymentedTotal()
					+ account.getSpecialPayedAmount() - account.getPerTurnAmount());
			accountDao.update(account);// 更新用户表 专项预支没转个人预支之前系统不会默认转
			// 增加一条账户历史信息
			baseServer.addAccountHis(exp.getId(), ExpUserAccountHis.BUSTYPE4, account.getId(),
					-exp.getCurrAdvanceAmount(), exp.getApplyerId(), "专项预支");
		}
		// 个人预支
		else if (ExpAdvance.PER_BILL_TYPE == exp.getBillType()) {
			// 按创建时间获取没抹平的个人费用报销单
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("billState", ExpPaymentH.STATE_APPROVE_END);
			params.put("billType", ExpPaymentH.EXPENSE_PAYMENT);
			params.put("applyerId", exp.getApplyerId());
			params.put("isFinish", 0);
			List<ExpPaymentH> listPay = expPaymentHDao.getBySqlKey("getAllAsc", params);
			float advBalance = exp.getResidualAmount();// 预支单据剩余金额
			// Float per_paymented_total = 0F;// 个人报销总额
			if (null != listPay && listPay.size() > 0) {
				for (ExpPaymentH payment : listPay) {
					// 如果个人报销单金额大于个人预支单金额
					if (advBalance > 0F && payment.getResidualAmount() > advBalance) {
						// per_paymented_total = per_paymented_total +
						// advBalance;
						// 报销单减少没完成
						payment.setResidualAmount(payment.getResidualAmount() - advBalance);
						payment.setIsFinish(ExpPaymentH.IS_NOFINISH);
						expPaymentHDao.update(payment);
						// 中间表增加一条记录
						baseServer.addMatch(ExpMatch.BUS_TYPE2, payment.getId(), exp.getId(), advBalance, "预支冲抵报销");
						advBalance = 0L;
					}
					// 如果预支单金额大于报销单金额
					else if (advBalance > 0L && payment.getResidualAmount() <= advBalance) {
						advBalance = advBalance - payment.getResidualAmount();// 余额
						// per_paymented_total = per_paymented_total +
						// payment.getResidualAmount();// 报销总额
						// 报销单完成
						payment.setResidualAmount(0F);
						payment.setIsFinish(ExpPaymentH.IS_FINISH);
						expPaymentHDao.update(payment);
						baseServer.addMatch(ExpMatch.BUS_TYPE2, payment.getId(), exp.getId(), advBalance, "预支冲抵报销");
					}
				}
			}
			// 更新用户账户表信息
			account.setPerAdvanced(exp.getCurrAdvanceAmount() + account.getPerAdvanced());// 个人已预支总额
			// 个人最新余额=个人预支-个人报销-专项转个人的+付款
			account.setPerResidual(exp.getCurrAdvanceAmount() + account.getPerResidual()
					- account.getPerPaymentedTotal() + account.getPerPayedAmount() - account.getSpecialTurnAmount());
			// 更新预支单状态和剩余金额
			exp.setResidualAmount(advBalance);
			if (advBalance > 0) {
				// 预支单没完成
				exp.setIsFinish((long) ExpAdvance.NO_FINISH);
			} else {
				exp.setIsFinish((long) ExpAdvance.FINISH);
			}
			// 审批完成之后增加一条个人账户预支信息
			accountDao.update(account);
			baseServer.addAccountHis(exp.getId(), ExpUserAccountHis.BUSTYPE3, account.getId(),
					-exp.getCurrAdvanceAmount(), account.getUserId(), "个人预支");
		}
		return exp;
	}
}
