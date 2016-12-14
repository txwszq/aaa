/**
 * @(#)BaseServer.java 2013-12-3 Copyright 2013 it.kedacom.com, Inc. All rights
 *                     reserved.
 */
package com.kedacom.expenses.service.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.kedacom.expenses.dao.baseconfig.ConfAmountDao;
import com.kedacom.expenses.dao.baseconfig.ConfCosterDao;
import com.kedacom.expenses.dao.baseconfig.ConfPerCosterDao;
import com.kedacom.expenses.dao.expenses.ExpAdvanceDao;
import com.kedacom.expenses.dao.expenses.ExpMatchDao;
import com.kedacom.expenses.dao.expenses.ExpUserAccountDao;
import com.kedacom.expenses.dao.expenses.ExpUserAccountHisDao;
import com.kedacom.expenses.model.ExpHeadVO;
import com.kedacom.expenses.model.baseconfig.ConfAmount;
import com.kedacom.expenses.model.baseconfig.ConfCoster;
import com.kedacom.expenses.model.baseconfig.ConfPerCoster;
import com.kedacom.expenses.model.expenses.ExpAdvance;
import com.kedacom.expenses.model.expenses.ExpMatch;
import com.kedacom.expenses.model.expenses.ExpUserAccount;
import com.kedacom.expenses.model.expenses.ExpUserAccountHis;
import com.kedacom.expenses.service.findperson.FindApproverUtil;
import com.kedacom.security.dao.SysOrgDao;
import com.kedacom.security.model.Position;
import com.kedacom.security.model.SysOrg;
import com.kedacom.security.service.ISysOrg;
import com.kedacom.security.service.ISysUser;
import com.kedacom.security.service.auth.PositionService;
import com.kedacom.security.service.auth.SysUserService;

/**
 * (基础信息获取server).
 * @author zhujun
 * @version 2013-12-3
 */
@Service
public class BaseInfoServer {

	@Resource
	private PositionService positionServer;

	@Resource
	private ExpUserAccountDao accountDao;

	@Resource
	private ConfAmountDao amountDao;

	@Resource
	private ExpUserAccountHisDao accountHisDao;

	@Resource
	private ExpMatchDao expMatchDao;

	@Resource
	private ExpAdvanceDao advDao;

	@Resource
	private ConfCosterDao confCostDao;

	@Resource
	private ConfPerCosterDao confPerDao;

	@Resource
	private SysUserService sysUserService;

	@Resource
	private SysOrgDao sysOrgDao;

	/***
	 * (根据用户或者科目查询额度，优先用户,有多个结果的找最大的).
	 * @param userPk
	 * @param subId
	 * @return
	 */
	public ConfAmount getAmount(long userPk, long subId) {
		ConfAmount confAmount = new ConfAmount();
		List<ConfAmount> listAmount = new ArrayList<ConfAmount>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user_or_dept_id", userPk);
		param.put("cost_subject_code", subId);
		param.put("type", ConfAmount.TYPE_USERID);// 1是用户自己
		param.put("state", ConfAmount.STATE_SET);// 已设置
		listAmount = (List<ConfAmount>) amountDao.getBySqlKey("getAll", param);
		if (null != listAmount && listAmount.size() > 0) {
			confAmount = listAmount.get(0);
			for (ConfAmount amount : listAmount) {
				if (confAmount.getClaim_cost() < amount.getClaim_cost()) {
					confAmount = amount;
				}
			}
		} else if (null == confAmount || null == confAmount.getId()) {
			// 获取用户岗位
			List<Position> listPosition = positionServer.getByUserId(userPk);
			List<Long> listStr = new ArrayList<Long>();
			if (null != listPosition && listPosition.size() > 0) {
				for (Position postion : listPosition) {
					listStr.add(postion.getPosId());
				}
				listAmount = amountDao.getBySqlKey("getAmountForPos", listStr);
			}
			if (null != listAmount && listAmount.size() > 0) {
				confAmount = listAmount.get(0);
				for (ConfAmount amount : listAmount) {
					if (confAmount.getClaim_cost() < amount.getClaim_cost()) {
						confAmount = amount;
					}
				}
			}
		}
		return confAmount;
	}

	/**
	 * (获取用户账户表).
	 * @param userPk
	 * @return
	 */
	public ExpUserAccount getExpUserAccount(long userPk) {
		Map<String, Object> par = new HashMap<String, Object>();
		par.put("userId", userPk);
		List<ExpUserAccount> listExpAdv = accountDao.getBySqlKey("getAll", par);
		if (listExpAdv.size() > 0 && null != listExpAdv) {
			return listExpAdv.get(0);
		}
		return null;
	}

	/**
	 * (增加账户历史信息).
	 * @param bill_id 单据ID 报销，预支主键
	 * @param bus_type 业务类型 1, 费用报销 2, 专项报销 3, 个人预支4, 专项预支 5 , 付款 6 ,专项转出
	 *            （后台操作需要记录）7 ,个人转出 （后台操作需要记录）
	 * @param userAccountId 账户主表主键
	 * @param happenAmount 发生金额 可正可负
	 * @param userid 用户主键
	 * @param remark 备注，用于描述
	 */
	public void addAccountHis(long bill_id, long bus_type, long userAccountId, Float happenAmount, long userid, String remark) {
		ExpUserAccountHis accountHis = new ExpUserAccountHis();
		accountHis.setBillId(bill_id);
		accountHis.setBusType(bus_type);
		accountHis.setUserAccountId(userAccountId);
		accountHis.setHappenDate(new Date());
		accountHis.setHappenAmount(happenAmount);
		accountHis.setUserId(userid);
		accountHis.setRemark(remark + happenAmount);
		accountHisDao.add(accountHis);
	}

	/**
	 * (插入关联表数据).
	 * @param bus_type 业务类型
	 * @param from_id 用户方发生金额
	 * @param to_id 公司方发生金额
	 * @param happen_amount
	 * @param remark
	 */
	public void addMatch(long bus_type, long from_id, long to_id, Float happen_amount, String remark) {
		ExpMatch match = new ExpMatch();
		match.setBusiType(bus_type);
		match.setFromId(from_id);
		match.setToId(to_id);
		match.setHappenAmount(happen_amount);
		match.setRemark(remark + happen_amount);
		expMatchDao.add(match);
	}

	/**
	 * (获取头信息).
	 * @param userId
	 * @return
	 */
	public ExpHeadVO takeHeadVO(long userId) {
		ExpHeadVO headVO = new ExpHeadVO();
		// 预支额度
		ConfAmount confAmount = getAmount(userId, 1000000L);
		// 用户账户表
		ExpUserAccount account = getExpUserAccount(userId);
		// 如果没用户表就创建用户表
		if (null == account) {
			ExpUserAccount accountNew = new ExpUserAccount();
			accountNew.setUserId(userId);
			accountNew.setCreatetime(new Date());
			accountNew.setCreateby(userId);
			accountDao.add(accountNew);
			account = accountNew;
		}

		headVO.setAdvConfAmount(confAmount.getClaim_cost() == null ? 0F : confAmount.getClaim_cost());// 额度
		headVO.setPerAdvanced(account.getPerAdvanced());// 个人已预支
		headVO.setPerPaymentedTotal(account.getPerPaymentedTotal());// 个人已报销
		headVO.setPerPayedAmount(account.getPerPayedAmount());// 个人已付款
		headVO.setPerResidual(account.getPerResidual());// 个人预支余额
		headVO.setSpecialAdvanced(account.getSpecialAdvanced());// 专项已预支
		headVO.setSpecialPaymentedTotal(account.getSpecialPaymentedTotal());// 专项已报销
		headVO.setSpecialPayedAmount(account.getSpecialPayedAmount());// 专项付款
		headVO.setSpecialResidual(account.getSpecialResidual());// 专项预支余额
		// 可预支总额=额度-个人预支余额-专项转移余额
		headVO.setTotalResidual((confAmount.getClaim_cost() == null ? 0F : confAmount.getClaim_cost()) - (headVO.getPerResidual() + account.getSpecialTurnAmount()));
		headVO.setAdvancedTotal(account.getPerAdvanced() + account.getSpecialAdvanced());
		headVO.setPaymentedTotal(account.getPerPaymentedTotal() + account.getSpecialPaymentedTotal());
		headVO.setPayedAmount(account.getPerPayedAmount() + account.getSpecialPayedAmount());
		headVO.setAdvancedResidual(headVO.getPerResidual() + headVO.getSpecialResidual());// 已预支余额
		return headVO;
	}

	/**
	 * (获取用户提交状态中的数据).
	 * @param userPk
	 * @return
	 */
	public Float getNoFinishExp(long userPk, int billtype) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applyerId", userPk);
		param.put("billState", ExpAdvance.APPROVE);
		param.put("billType", billtype);
		param.put("isFinish", 0);
		// 获取没完成的非草稿个人预支单
		List<ExpAdvance> listAdv = advDao.getBySqlKey("getAll", param);
		Float advPerAmount = 0F;// 审批没通过的个人预支总额
		if (null != listAdv && listAdv.size() > 0) {
			for (ExpAdvance exp : listAdv) {
				advPerAmount = advPerAmount + exp.getResidualAmount();
			}
		}
		return advPerAmount;
	}

	/**
	 * (返回费用审核一级员，财务付款员，费用审核员).
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getAllApprovers(long userId) {
		Map<String, Object> approvers = new HashMap<String, Object>();
		ConfPerCoster perCoster = findAccount(userId);
		// 优先查询单个人的配置情况
		if (null != perCoster && null != perCoster.getId() && null != perCoster.getFinance_ids()) {
			// 财务一级审批
			approvers.put(FindApproverUtil.FINANCE_FIRST_AUDIT, getUserAccount(perCoster.getFinance_ids()));
			// 费用审核员
			approvers.put(FindApproverUtil.EXPENSE_AUDIT, getUserAccount(perCoster.getCoster_ids()));
			// 财务付款员
			approvers.put(FindApproverUtil.PAYER_AUDIT, getUserAccount(perCoster.getFinanceCostPk()));
			return approvers;
		} else {
			// 根据用户获取所在部门
			ISysOrg sysOrg = sysOrgDao.getPrimaryOrgByUserId(userId);
			ConfCoster coster = getTakingDeptConfCoster(sysOrg);
			if (null != coster) {
				// 财务一级审批
				approvers.put(FindApproverUtil.FINANCE_FIRST_AUDIT, getUserAccount(coster.getFinancePks()));
				// 费用审核员
				approvers.put(FindApproverUtil.EXPENSE_AUDIT, getUserAccount(coster.getCosterPks()));
				// 财务付款员
				approvers.put(FindApproverUtil.PAYER_AUDIT, getUserAccount(coster.getFinanceCostPk()));
			}
			return approvers;
		}
	}

	/**
	 * ( 根据用户PK查询).
	 * @param userId
	 * @return
	 */
	private ConfPerCoster findAccount(long userId) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("person_id", userId);
		ConfPerCoster perCoster = (ConfPerCoster) confPerDao.getOne("getAll", map);
		return perCoster;
	}

	/**
	 * (根据员工所在部门递归寻找承担部门).
	 * @param sysOrgId
	 * @return
	 */
	public ConfCoster getTakingDeptConfCoster(ISysOrg sysOrg) {
		// 查询本部门是否为承担部门
		ConfCoster coster = (ConfCoster) confCostDao.getOne("getByTakeDept", sysOrg.getOrgId());
		if (null == coster) {
			SysOrg sysUpOrg = (SysOrg) sysOrgDao.getById(sysOrg.getOrgSupId());
			if (null == sysUpOrg) {
				return null;
			}
			return getTakingDeptConfCoster(sysUpOrg);
		}
		return coster;
	}

	/**
	 * (根据id找人 ).
	 * @param financeIds
	 * @return
	 */
	public String getUserAccount(String financeIds) {
		if (StringUtils.isNotEmpty(financeIds)) {
			String[] setStr = financeIds.split(",");
			Set setIds = new HashSet(Arrays.asList(setStr));
			String userEamil = "";
			List<ISysUser> listUser = sysUserService.getByIdSet(setIds);
			if (null != listUser && listUser.size() > 0) {
				for (ISysUser susUser : listUser) {
					userEamil = userEamil + susUser.getAccount() + ",";
				}
			}
			if (StringUtils.isNotEmpty(userEamil)) {
				return userEamil.substring(0, userEamil.length() - 1);
			}
			return userEamil;
		}
		return null;

	}

	/**
	 * 根据userID查他的最近一级费用承担部门
	 * @param userId
	 * @return
	 */
	public SysOrg getTakingDeptByUserId(long userPk) {
		SysOrg sysUpOrg = (SysOrg) sysOrgDao.getPrimaryOrgByUserId(userPk);
		ConfCoster coster = getTakingDeptConfCoster(sysUpOrg);
		if (null != coster) {
			return (SysOrg) sysOrgDao.getById(new Long(coster.getDeptId()));
		}
		return null;
	}
}
