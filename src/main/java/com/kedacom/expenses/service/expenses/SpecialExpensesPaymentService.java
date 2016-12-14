package com.kedacom.expenses.service.expenses;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.kedacom.core.page.QueryFilter;
import com.kedacom.expenses.dao.expenses.ExpMatchDao;
import com.kedacom.expenses.dao.expenses.ExpPaymentBDao;
import com.kedacom.expenses.dao.expenses.ExpPaymentHDao;
import com.kedacom.expenses.dao.expenses.ExpPaymentHisDao;
import com.kedacom.expenses.exception.BusinessRuntimeException;
import com.kedacom.expenses.model.ExpHeadVO;
import com.kedacom.expenses.model.expenses.ExpAdvance;
import com.kedacom.expenses.model.expenses.ExpMatch;
import com.kedacom.expenses.model.expenses.ExpPaymentB;
import com.kedacom.expenses.model.expenses.ExpPaymentH;
import com.kedacom.expenses.model.expenses.ExpPaymentHis;
import com.kedacom.expenses.model.expenses.ExpUserAccount;
import com.kedacom.expenses.model.expenses.ExpUserAccountHis;
import com.kedacom.expenses.service.base.BaseInfoServer;
import com.kedacom.expenses.service.findperson.FindApproverUtil;
import com.kedacom.security.model.SysOrg;
import com.kedacom.security.model.SysUser;
import com.kedacom.security.util.ContextUtil;

/**
 * 专项费用报销service
 * @author zhangwenbin
 * @version 2013-12-03
 */
@Service
public class SpecialExpensesPaymentService {

	private Log logger = LogFactory.getLog(SpecialExpensesPaymentService.class);

	@Resource
	private ExpPaymentHDao expPaymentHDao;

	@Resource
	private ExpPaymentBDao expPaymentBDao;

	@Resource
	private ExpPaymentHisDao expPaymentHisDao;

	@Resource
	private ExpMatchDao expMatchDao;

	@Resource
	private FindApproverUtil findApproverUtil;

	@Resource
	private Properties configproperties;

	@Resource
	private BaseInfoServer baseInfoServer;

	@Resource
	private ExpUserAccountService expUserAccountService;

	@Resource
	private ExpUserAccountHisService expUserAccountHisService;

	@Resource
	private ExpMatchService expMatchService;

	@Resource
	private ExpAdvanceService expAdvanceService;

	/**
	 * 根据主id查询主子表信息
	 * @param id
	 * @return
	 */
	public Map<String, Object> getEntityById(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询主表信息
		ExpPaymentH expPaymentH = (ExpPaymentH) expPaymentHDao.getById(id);
		map.put("expPaymentH", expPaymentH);
		// 查询子表信息
		List<ExpPaymentB> expPaymentBList = expPaymentBDao.getBySqlKey("getListByMainId", id);
		map.put("expPaymentBList", expPaymentBList);
		// 查询历史金额信息
		ExpPaymentHis expPaymentHis = (ExpPaymentHis) expPaymentHisDao.getOne("getHisListByMainId", id);
		map.put("expPaymentHis", expPaymentHis);
		// 查询已关联的预支单
		List<ExpMatch> expMatchList = expMatchDao.getBySqlKey("getListByFromId", id);
		map.put("expMatchList", expMatchList);
		return map;
	}

	/**
	 * 保存专项报销申请的信息，可以保存新数据也可以保存编辑的数据
	 * @param epb
	 */
	public void saveOrUpdateApply(ExpPaymentH eph, List<ExpPaymentB> subList, List<Long> advanceIdList,
			List<Long> delIdList) {

		// 判断选择的预支单能否再次被选
		if (advanceIdList != null && advanceIdList.size() > 0) {
			Map<String, Object> returnMap = checkAdvance(advanceIdList);
			boolean checkFlag = (Boolean) returnMap.get("flag");
			if (checkFlag) {
				throw new BusinessRuntimeException(returnMap.get("advanceMsg").toString());
			}
		}

		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		SysOrg sysOrg = (SysOrg) ContextUtil.getCurrentOrg();
		// 保存主表和子表数据
		if (eph.getId() == null) {// 保存操作
			// 完善主表信息
			eph.setBillType(ExpPaymentH.TYPE_SPECIAL_PAYMENT);
			eph.setBillState(ExpPaymentH.STATE_DRAFTING);
			eph.setIsFinish(ExpPaymentH.IS_NOFINISH);
			eph.setCreateby(sysUser.getUserId());
			eph.setCreatetime(new Date());
			eph.setUpdateby(sysUser.getUserId());
			eph.setUpdatetime(new Date());
			expPaymentHDao.add(eph);
		} else {// 更新操作
			eph.setUpdateby(ContextUtil.getCurrentUserId());
			eph.setUpdatetime(new Date());
			expPaymentHDao.update(eph);
		}

		// 更新子表信息
		if (subList != null) {
			for (ExpPaymentB epb : subList) {
				if (epb.getId() != null) {
					epb.setUpdateby(sysUser.getUserId());
					epb.setUpdatetime(new Date());
					expPaymentBDao.update(epb);
				} else {
					epb.setExpensesHID(eph.getId());
					epb.setCreateby(sysUser.getUserId());
					epb.setCreatetime(new Date());
					epb.setUpdateby(sysUser.getUserId());
					epb.setUpdatetime(new Date());
					expPaymentBDao.add(epb);
				}
			}
		}

		// 建立与抵消的预支单的关联
		if (advanceIdList != null && advanceIdList.size() > 0) {
			// 先删除已有的数据
			expMatchDao.delByExpensesHId(eph.getId());
			// 保存系统关联表
			ExpMatch expM = new ExpMatch();
			expM.setBusiType(ExpMatch.BUS_TYPE2);
			expM.setCreatetime(new Date());
			expM.setFromId(eph.getId());
			expM.setCreateby(sysUser.getUserId());
			expM.setCreatetime(new Date());
			expM.setUpdateby(sysUser.getUserId());
			expM.setUpdatetime(new Date());
			for (Long advanceId : advanceIdList) {
				expM.setToId(advanceId);
				expMatchDao.add(expM);
			}
		} else {
			// 先删除已有的数据
			expMatchDao.delByExpensesHId(eph.getId());
		}

		// 删除子表信息
		if (delIdList != null && delIdList.size() > 0) {
			for (Long delId : delIdList) {
				expPaymentBDao.delById(delId);
			}
		}
	}

	/**
	 * 保存专项报销申请的信息，并启动流程
	 * @param epb
	 */
	public void commitApply(ExpPaymentH eph, List<ExpPaymentB> subList, ExpPaymentHis expPaymentHis,
			List<Long> advanceIdList, List<Long> delIdList) throws Exception {

		// 保存主表和子表还有历史表的数据
		saveOrUpdateApply(eph, subList, advanceIdList, delIdList);

		// 保存金额历史信息
		if (expPaymentHis != null) {
			if (expPaymentHis.getId() != null) {
				expPaymentHisDao.update(expPaymentHis);
			} else {
				expPaymentHis.setPaymentHID(eph.getId());
				expPaymentHisDao.add(expPaymentHis);
			}
		}

		// 启动流程
		String flowKey = configproperties.getProperty("paymentFlowKey");
		if (eph.getBillState() == ExpPaymentH.STATE_REJUSTING) {// 驳回状态的单子
		} else {// 草稿状态
			String processId = findApproverUtil.startFlow(eph.getId().toString(), eph.getBillCode(), flowKey);
			eph.setProcessesId(new Long(processId));
		}
		// 更改状态
		eph.setBillState(ExpPaymentH.STATE_APPROVING);
		expPaymentHDao.update(eph);

	}

	/**
	 * 查询所有个人申请的活动信息
	 * @return
	 */
	public List<ExpPaymentH> queryList(QueryFilter queryFilter) {
		return expPaymentHDao.getAll(queryFilter);
	}

	/**
	 * 删除专项报销申请
	 * @param ids
	 */
	public void deleteByApplyId(Long id) throws Exception {
		expPaymentHDao.delById(id);
		// 删除专项报销申请子表信息
		expPaymentBDao.delByExpensesHId(id);
		// 删除历史表信息
		expPaymentHisDao.delByExpensesHId(id);
		// 删除抵扣的预支单
		expMatchDao.delByExpensesHId(id);
	}

	/**
	 * 批量删除专项报销申请
	 * @param ids
	 */
	public void deleteActivityApplys(String ids) throws Exception {
		String array[] = ids.split(",");
		for (int i = 0; i < array.length; i++) {
			Long id = Long.parseLong(array[i]);
			deleteByApplyId(id);
		}
	}

	/**
	 * 提交专项报销申请
	 * @param ids
	 */
	public void onlySubmitApply(String ids) throws Exception {
		String flowKey = configproperties.getProperty("paymentFlowKey");
		String array[] = ids.split(",");
		for (int i = 0; i < array.length; i++) {
			// 查询历史金额信息
			ExpPaymentHis expPaymentHis = (ExpPaymentHis) expPaymentHisDao.getOne("getHisListByMainId", new Long(
					array[i]));
			// 当前预支余额
			ExpHeadVO takeHeadVO = baseInfoServer.takeHeadVO(ContextUtil.getCurrentUserId());

			// 保存或更新金额历史信息
			if (expPaymentHis != null && expPaymentHis.getId() != null) {
				expPaymentHis.setSpecialResidual(takeHeadVO.getSpecialResidual());
				expPaymentHisDao.update(expPaymentHis);
			} else {
				expPaymentHis = new ExpPaymentHis();
				expPaymentHis.setSpecialResidual(takeHeadVO.getSpecialResidual());
				expPaymentHis.setPaymentHID(new Long(array[i]));
				expPaymentHisDao.add(expPaymentHis);
			}

			// 把流程变量保存至主表中
			ExpPaymentH eph = expPaymentHDao.getById(new Long(array[i]));
			if (eph.getBillState() == ExpPaymentH.STATE_REJUSTING) {// 驳回状态的单子
			} else {// 草稿状态
				String processId = findApproverUtil.startFlow(array[i], eph.getBillCode(), flowKey);
				eph.setProcessesId(new Long(processId));
			}

			// 更新状态和流程id
			eph.setBillState(ExpPaymentH.STATE_APPROVING);
			expPaymentHDao.update(eph);
		}
	}

	/**
	 * 财务提交专项费用报销
	 * @param epb
	 * @throws Exception_Exception
	 */
	public void financeCommitApply(ExpPaymentH eph, List<ExpPaymentB> subList, ExpPaymentHis expPaymentHis,
			List<Long> advanceIdList, List<Long> delIdList) throws Exception {

		// 保存主表和子表还有历史表的数据
		saveOrUpdateApply(eph, subList, advanceIdList, delIdList);

		// 保存金额历史信息
		if (expPaymentHis != null) {
			if (expPaymentHis.getId() != null) {
				expPaymentHisDao.update(expPaymentHis);
			} else {
				expPaymentHis.setPaymentHID(eph.getId());
				expPaymentHisDao.add(expPaymentHis);
			}
		}

		// 计算金额，计算流程
		// 主表主id
		Long paymentHId = eph.getId();
		// 当前用户
		Long currentUserId = ContextUtil.getCurrentUserId();
		Long applyUserId = eph.getApplyerId();// 申请人
		Float paymentAmount = eph.getPaymentAmount();// 专项报销的金额
		Float happenAmount = paymentAmount;// 实际抵扣的金额
		// 查询申请人个人账户表
		ExpUserAccount expUserAccount = expUserAccountService.getUserAccountByUserId(applyUserId);
		float specialResidual = expUserAccount.getSpecialResidual();// 现在专项余额，此金额可以正可负

		// 销账
		// 查找已经关联的预支单，按时间升序排序，先预支的先抹平
		List<ExpMatch> expMatchList = expMatchService.getListByFromId(paymentHId);
		for (ExpMatch expMatch : expMatchList) {
			if (happenAmount <= 0) {// 没有可抵消的金额就结束
				break;
			}
			// 预支单的信息
			ExpAdvance expAdvance = expAdvanceService.getById(expMatch.getToId());
			if (expAdvance.getIsFinish() == ExpAdvance.FINISH) {
				continue;
			}
			// 预支金额
			Float curResidualAmount = expAdvance.getResidualAmount();// 目前剩余的金额
			if (curResidualAmount == null || curResidualAmount == 0.0f) {// 没有数据说明没有被抵消，取预支的金额
				curResidualAmount = expAdvance.getCurrAdvanceAmount();
			}

			float needRecordHappenAmount = 0;// 需要记录的发生金额
			float needRecordResidualAmount = 0;// 需要记录的预支单剩余金额
			if (happenAmount >= curResidualAmount) {// 报销金额大于剩余预支金额
				specialResidual -= curResidualAmount;// 说明个人账户余额-抵消的金额
				needRecordHappenAmount = curResidualAmount;// 关联表中记录的发生金额为预支剩余的金额
			} else {// 报销金额小于剩余预支金额
				specialResidual -= happenAmount;
				needRecordHappenAmount = happenAmount;// 关联表中记录的发生金额为报销的金额
				needRecordResidualAmount = curResidualAmount - happenAmount;
			}
			// 此时的发生金额就是抵消预支单的金额，然后继续抵消下个预支单
			happenAmount -= curResidualAmount;

			// 更新报销单与预支单的关联信息
			expMatch.setHappenAmount(needRecordHappenAmount);
			expMatch.setRemark("系统自动计算，专项报销抵消预支单金额的记账。");
			expMatchService.update(expMatch);

			// 修改专项预支单状态
			expAdvance.setResidualAmount(needRecordResidualAmount);
			if (needRecordResidualAmount == 0) {// 记录的剩余金额为0说明已经抵消完
				expAdvance.setIsFinish(new Long(ExpAdvance.FINISH));
			} else {
				expAdvance.setIsFinish(new Long(ExpAdvance.NO_FINISH));
			}
			expAdvanceService.update(expAdvance);
		}

		// 更新个人账户表
		// 专项已报销总额，已报销的专项总额+报销金额=现在的专项已报销总额
		expUserAccount.setSpecialPaymentedTotal(expUserAccount.getSpecialPaymentedTotal() + paymentAmount);
		expUserAccount.setSpecialResidual(specialResidual);// 专项最新余额
		expUserAccountService.update(expUserAccount);

		// 记录用户账户金额历史表
		ExpUserAccountHis expUserAccountHis = new ExpUserAccountHis();
		expUserAccountHis.setUserAccountId(expUserAccount.getId());
		expUserAccountHis.setBillId(paymentHId);
		expUserAccountHis.setUserId(applyUserId);
		expUserAccountHis.setBusType(ExpUserAccountHis.BUSTYPE2);
		expUserAccountHis.setHappenAmount(paymentAmount);
		expUserAccountHis.setHappenDate(new Date());
		expUserAccountHis.setRemark("系统自动记账，专项报销抵消预支时记账。");
		expUserAccountHisService.add(expUserAccountHis);

		// 更改状态
		eph.setBillState(ExpPaymentH.STATE_APPROVE_END);
		if (happenAmount <= 0) {// 这次报销的金额全部抵消说明已经抵消完
			eph.setIsFinish(ExpPaymentH.IS_FINISH);
			eph.setResidualAmount(0f);
		} else {
			eph.setIsFinish(ExpPaymentH.IS_NOFINISH);
			eph.setResidualAmount(happenAmount);
		}
		eph.setUpdateby(currentUserId);
		eph.setUpdatetime(new Date());
		expPaymentHDao.update(eph);
	}

	private Map<String, Object> checkAdvance(List<Long> advanceIdList) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("flag", false);// 默认false为通过，反之true不能通过
		for (Long advanceId : advanceIdList) {
			ExpAdvance expAdvance = expAdvanceService.getById(advanceId);
			if (expAdvance.getIsFinish() == ExpAdvance.FINISH) {
				returnMap.put("advanceMsg", "该预支单【" + expAdvance.getBillCode() + "】已经抵消完成，请重新选择！");
				returnMap.put("flag", true);
				break;
			}
			List<ExpMatch> expMList = expMatchService.getListByToId(advanceId);
			if (expMList != null && expMList.size() > 0) {// 如果有数据说明不能再次选择
				float sumAmount = 0.0f;
				for (ExpMatch epm : expMList) {
					ExpPaymentH eph = expPaymentHDao.getById(epm.getFromId());// 报销单
					Long ephSillState = eph.getBillState();// 申请单状态
					if (ephSillState == ExpPaymentH.STATE_APPROVING || ephSillState == ExpPaymentH.STATE_DRAFTING
							|| ephSillState == ExpPaymentH.STATE_REJUSTING) {
						// 如果状态为审批中、驳回、草稿需要计算总的报销金额
						sumAmount += eph.getPaymentAmount();
					}
				}
				if (sumAmount >= expAdvance.getResidualAmount()) {
					returnMap.put("advanceMsg", "您选的预支单【" + expAdvance.getBillCode() + "】已经不能再选择！");
					returnMap.put("flag", true);
					break;
				}
			}
		}
		return returnMap;
	}
}
