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
import com.kedacom.expenses.dao.expenses.ExpAdvanceDao;
import com.kedacom.expenses.dao.expenses.ExpMatchDao;
import com.kedacom.expenses.dao.expenses.ExpPaymentBDao;
import com.kedacom.expenses.dao.expenses.ExpPaymentHDao;
import com.kedacom.expenses.dao.expenses.ExpPaymentHisDao;
import com.kedacom.expenses.model.ExpHeadVO;
import com.kedacom.expenses.model.expenses.ExpAdvance;
import com.kedacom.expenses.model.expenses.ExpMatch;
import com.kedacom.expenses.model.expenses.ExpPaymentB;
import com.kedacom.expenses.model.expenses.ExpPaymentH;
import com.kedacom.expenses.model.expenses.ExpPaymentHis;
import com.kedacom.expenses.model.expenses.ExpUpdatePaymentHis;
import com.kedacom.expenses.model.expenses.ExpUserAccount;
import com.kedacom.expenses.model.expenses.ExpUserAccountHis;
import com.kedacom.expenses.service.base.BaseInfoServer;
import com.kedacom.expenses.service.findperson.FindApproverUtil;
import com.kedacom.expenses.utils.ExpensesUtils;
import com.kedacom.security.util.ContextUtil;

/**
 * @author Zhaozhiqiang
 * @version 3 Dec 2013
 */
@Service
public class ExpPaymentHService extends BaseService<ExpPaymentH> {

	private final String AGREE = "1";
	private final String REJECT = "2";
	@Resource
	private ExpPaymentHDao expPaymentHDao;

	@Resource
	private ExpPaymentBDao expPaymentBDao;

	@Resource
	private ExpPaymentHisDao expPaymentHisDao;

	@Resource
	private ExpAdvanceDao expAdvanceDao;

	@Resource
	private ExpUserAccountService expUserAccountService;

	@Resource
	private ExpUserAccountHisService expUserAccountHisService;

	@Resource
	private ExpMatchService expMatchService;

	@Resource
	private ExpUpdatePaymentHisService expUpdatePaymentHisService;

	@Resource
	private Properties configproperties;

	@Resource
	private FindApproverUtil findApproverUtil;

	@Resource
	private BaseInfoServer baseInfoServer;

	@Resource
	private ExpMatchDao expMatchDao;

	public ExpPaymentHService() {
	}

	@Override
	protected IEntityDao<ExpPaymentH, Long> getEntityDao() {
		return expPaymentHDao;
	}

	/**
	 * 保存报销单
	 * @param main
	 * @param subList
	 */
	public void addPayment(ExpPaymentH main, List<ExpPaymentB> subList, ExpPaymentHis his) {
		// 添加报销单主表
		expPaymentHDao.add(main);
		for (ExpPaymentB expPaymentB : subList) {
			// 添加报销单子表
			expPaymentB.setExpensesHID(main.getId());
			expPaymentB.setTakeDeptId(main.getTakeDeptId());
			expPaymentB.setTakeDeptName(main.getTakeDeptName());
			expPaymentBDao.add(expPaymentB);
		}
		his.setPaymentHID(main.getId());
		// 添加历史记录
		expPaymentHisDao.add(his);
	}

	/**
	 * 提交表单
	 * @param main
	 * @throws Exception_Exception
	 */
	public void submitPayment(String ids) throws Exception {
		String[] splitIds = ids.split(",");
		for (String id : splitIds) {
			ExpPaymentH eph = expPaymentHDao.getById(Long.valueOf(id));
			// 当前账户情况
			ExpHeadVO hvo = baseInfoServer.takeHeadVO(eph.getApplyerId());
			Map<String, Object> paymentMap = new HashMap<String, Object>();
			paymentMap.put("paymentHID", id);
			List<ExpPaymentHis> list = expPaymentHisDao.getBySqlKey("getAll", paymentMap);
			if (list != null && list.size() > 0) {
				ExpPaymentHis expPaymentHis = list.get(0);
				expPaymentHis.setPerAdvanced(hvo.getPerAdvanced());
				expPaymentHis.setPerPayedAmount(hvo.getPerPayedAmount());
				expPaymentHis.setPerPaymentedTotal(hvo.getPerPaymentedTotal());
				expPaymentHis.setPerResidual(hvo.getPerResidual());
				expPaymentHis.setSpecialAdvanced(hvo.getSpecialAdvanced());
				expPaymentHis.setSpecialPayedAmount(hvo.getSpecialPayedAmount());
				expPaymentHis.setSpecialPaymentedTotal(hvo.getSpecialPaymentedTotal());
				expPaymentHis.setSpecialResidual(hvo.getSpecialResidual());
				expPaymentHis.setTotalResidual(hvo.getTotalResidual());
				expPaymentHisDao.update(expPaymentHis);
			} else {
				ExpPaymentHis expPaymentHis = new ExpPaymentHis();
				expPaymentHis.setPaymentHID(Long.valueOf(id));
				expPaymentHis.setPerAdvanced(hvo.getPerAdvanced());
				expPaymentHis.setPerPayedAmount(hvo.getPerPayedAmount());
				expPaymentHis.setPerPaymentedTotal(hvo.getPerPaymentedTotal());
				expPaymentHis.setPerResidual(hvo.getPerResidual());
				expPaymentHis.setSpecialAdvanced(hvo.getSpecialAdvanced());
				expPaymentHis.setSpecialPayedAmount(hvo.getSpecialPayedAmount());
				expPaymentHis.setSpecialPaymentedTotal(hvo.getSpecialPaymentedTotal());
				expPaymentHis.setSpecialResidual(hvo.getSpecialResidual());
				expPaymentHis.setTotalResidual(hvo.getTotalResidual());
				expPaymentHisDao.add(expPaymentHis);
			}
			// 调用BPM流程
			String flowKey = configproperties.getProperty("paymentFlowKey");
			// 启动流程
			String processesId = findApproverUtil.startFlow(id, eph.getBillCode(), flowKey);
			// 修改报销单状态为 审批中
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("billState", 1);
			params.put("processesId", processesId);
			params.put("id", id);
			expPaymentHDao.update("updateBillState", params);
		}

	}

	/**
	 * 财务提交
	 * @param id
	 */
	public void financeSubmit(Long id) {
		ExpPaymentH eph = expPaymentHDao.getById(Long.valueOf(id));
		// 当前账户情况
		ExpHeadVO hvo = baseInfoServer.takeHeadVO(eph.getApplyerId());
		Map<String, Object> paymentMap = new HashMap<String, Object>();
		paymentMap.put("paymentHID", id);
		List<ExpPaymentHis> list = expPaymentHisDao.getBySqlKey("getAll", paymentMap);
		if (list != null && list.size() > 0) {
			ExpPaymentHis expPaymentHis = list.get(0);
			expPaymentHis.setPerAdvanced(hvo.getPerAdvanced());
			expPaymentHis.setPerPayedAmount(hvo.getPerPayedAmount());
			expPaymentHis.setPerPaymentedTotal(hvo.getPerPaymentedTotal());
			expPaymentHis.setPerResidual(hvo.getPerResidual());
			expPaymentHis.setSpecialAdvanced(hvo.getSpecialAdvanced());
			expPaymentHis.setSpecialPayedAmount(hvo.getSpecialPayedAmount());
			expPaymentHis.setSpecialPaymentedTotal(hvo.getSpecialPaymentedTotal());
			expPaymentHis.setSpecialResidual(hvo.getSpecialResidual());
			expPaymentHis.setTotalResidual(hvo.getTotalResidual());
			expPaymentHisDao.update(expPaymentHis);
		} else {
			ExpPaymentHis expPaymentHis = new ExpPaymentHis();
			expPaymentHis.setPaymentHID(Long.valueOf(id));
			expPaymentHis.setPerAdvanced(hvo.getPerAdvanced());
			expPaymentHis.setPerPayedAmount(hvo.getPerPayedAmount());
			expPaymentHis.setPerPaymentedTotal(hvo.getPerPaymentedTotal());
			expPaymentHis.setPerResidual(hvo.getPerResidual());
			expPaymentHis.setSpecialAdvanced(hvo.getSpecialAdvanced());
			expPaymentHis.setSpecialPayedAmount(hvo.getSpecialPayedAmount());
			expPaymentHis.setSpecialPaymentedTotal(hvo.getSpecialPaymentedTotal());
			expPaymentHis.setSpecialResidual(hvo.getSpecialResidual());
			expPaymentHis.setTotalResidual(hvo.getTotalResidual());
			expPaymentHisDao.add(expPaymentHis);
		}

		// 把主单据状态更新为审批结束
		expPaymentHDao.update("upFinish", id);
		eph.setBillState(3L);
		// 获取报销申请人ID
		Long currentUserId = eph.getApplyerId();

		// 销账
		// 1.计算本次报销总金额
		Float sum = eph.getPaymentAmount();

		// 用户账户金额历史表 记录一条记录
		ExpUserAccountHis expUserAccountHis = new ExpUserAccountHis();
		expUserAccountHis.setBillId(id);
		expUserAccountHis.setUserId(currentUserId);
		expUserAccountHis.setHappenAmount(sum);
		expUserAccountHis.setHappenDate(new Date());
		expUserAccountHisService.add(expUserAccountHis);

		// 2.查找未销账的预支单 按时间升序排序,先预支的先抹平
		List<ExpAdvance> ealist = expAdvanceDao.getBySqlKey("getAllAsc");// TODO
		if (ealist.size() > 0) {
			// 3.循环预支单
			for (ExpAdvance expAdvance : ealist) {
				// 预支单金额
				Float residualAmount = expAdvance.getResidualAmount();

				ExpMatch expMatch = new ExpMatch();
				expMatch.setBusiType(ExpMatch.BUS_TYPE4);
				expMatch.setCreateby(currentUserId);
				expMatch.setCreatetime(new Date());
				// 报销，转移单号
				expMatch.setFromId(id);
				expMatch.setToId(expAdvance.getId());
				// 够抵消
				if ((sum - residualAmount) >= 0) {
					// 报销总金额 = 报销总金额 - 预支单金额
					sum -= residualAmount;
					expAdvance.setIsFinish(1l);
					expAdvance.setResidualAmount(0f);
					expAdvanceDao.update(expAdvance);

					// 数据记录到中间表
					expMatch.setHappenAmount(residualAmount);
					expMatchService.add(expMatch);
				} else {
					// 不够抵消
					expAdvance.setResidualAmount(residualAmount - sum);
					expAdvanceDao.update(expAdvance);
					// 数据记录到中间表
					expMatch.setHappenAmount(sum);
					expMatchService.add(expMatch);
					sum -= residualAmount;
					break;
				}
			}

			if (sum < 0) {
				eph.setResidualAmount(0f);
				eph.setIsFinish(1l);
				expPaymentHDao.update(eph);
			} else {
				eph.setResidualAmount(sum);
				eph.setIsFinish(0l);
				expPaymentHDao.update(eph);
			}
		} else {
			eph.setResidualAmount(sum);
			eph.setIsFinish(0l);
			expPaymentHDao.update(eph);
		}
		ExpUserAccount entity = expUserAccountService.getUserAccountByUserId(currentUserId);
		baseInfoServer.addAccountHis(eph.getId(), 1, entity.getId(), eph.getPaymentAmount(), eph.getApplyerId(),
				"财务录入报销单据");

		// 当前账户中报销金额
		Float perPaymentedTotal = entity.getPerPaymentedTotal();
		// 目前报销情况 原账户中报销金额+此次报销金额
		Float immPt = eph.getPaymentAmount() + perPaymentedTotal;
		entity.setPerPaymentedTotal(immPt);
		expUserAccountService.update(entity);
	}

	/**
	 * 审批方法
	 * @param type 表示通过或驳回
	 * @param paymentHId 主表ID
	 * @param appoveNote 建议
	 * @throws Exception
	 */

	public void approve(String type, Long paymentHId, String approveNote, Short isNeedLead, Short isNeedPay)
			throws Exception {
		// 获取主表信息
		ExpPaymentH eph = expPaymentHDao.getById(paymentHId);
		if (ExpPaymentH.TYPE_EXPENSE_PAYMENT.equals(eph.getBillType())) {// 费用报销
			approvePayment(type, eph, approveNote, isNeedLead, isNeedPay);
		} else if (ExpPaymentH.TYPE_SPECIAL_PAYMENT.equals(eph.getBillType())) {// 专项费用报销
			approveSpecial(type, eph, approveNote, isNeedLead, isNeedPay);
		} else if (ExpPaymentH.TYPE_ACTIVITY_APPLY.equals(eph.getBillType())) {// 活动申请
			approveActivity(type, eph, approveNote, isNeedLead, isNeedPay);
		} else {
			throw new RuntimeException("非法操作");
		}

	}

	/**
	 * 管理员修改子表记录
	 * @param expPaymentB
	 */
	public void updateExpPaymentB(ExpPaymentB expPaymentB) {
		Date date = new Date();
		ExpPaymentB beforeUpdateExpB = expPaymentBDao.getById(expPaymentB.getId());
		ExpUpdatePaymentHis before = ExpensesUtils.convortBean(beforeUpdateExpB, 1, date);
		expUpdatePaymentHisService.add(before);
		// 更新记录
		expPaymentBDao.update(expPaymentB);
		// 在历史表中记录,更改前数据,更改后数据

		ExpUpdatePaymentHis after = ExpensesUtils.convortBean(expPaymentB, 2, date);
		expUpdatePaymentHisService.add(after);
	}

	/**
	 * 统计
	 * @param type 统计的维度
	 * @param qfilter 条件
	 * @return
	 */
	public List<Map<String, Object>> summary(String type, String id, Long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("applerId", userId);
		params.put("actId", id);
		params.put("busId", id);
		params.put("cusId", id);
		params.put("proCode", id);
		// TODO
		// 按照类型来执行sql语句
		List<Map<String, Object>> list = expPaymentHDao.getBySqlKey2(type, params);
		return list;
	}

	/**
	 * 根据流程ID找到单据信息
	 * @param processId
	 * @return
	 */
	public Map<String, Object> getApplyListByBusinessId(Long businessId) {
		ExpPaymentH expPaymentH = (ExpPaymentH) expPaymentHDao.getById(businessId);
		Long paymentHID = expPaymentH.getId();
		Map<String, Object> map = findById(paymentHID);
		map.put("main", expPaymentH);
		// 查询已关联的预支单
		List<ExpMatch> expMatchList = expMatchDao.getBySqlKey("getListByFromId", paymentHID);
		map.put("expMatchList", expMatchList);
		return map;
	}

	/**
	 * 根据主表ID查询记录
	 * @param paymentHID
	 * @return
	 */
	public Map<String, Object> findById(Long paymentHID) {
		Map<String, Object> map = new HashMap<String, Object>();
		ExpPaymentHis expPaymentHis = (ExpPaymentHis) expPaymentHisDao.getOne("getHisListByMainId", paymentHID);
		// List<ExpPaymentB> expPaymentBList =
		// expPaymentBDao.getBySqlKey("getListByMainId", paymentHID);
		List<Map<String, Object>> expPaymentBList = expPaymentBDao.getResultByMap("getListByMainId", paymentHID);
		ExpPaymentH expPaymentH = (ExpPaymentH) expPaymentHDao.getById(paymentHID);
		map.put("main", expPaymentH);
		map.put("his", expPaymentHis);
		map.put("rows", expPaymentBList);
		return map;
	}

	/**
	 * 删除记录
	 * @param ids
	 */
	public void deleteExpPayment(String ids) {
		String[] splitIds = ids.split(",");
		for (String idStr : splitIds) {
			Long id = Long.parseLong(idStr);
			if (canNotUpdateOrdelete(id)) {
				throw new RuntimeException("只有草稿状态下的记录才能删除!");
			}
			expPaymentHDao.delById(id);
			expPaymentBDao.delBySqlKey("delByMainId", id);
		}
	}

	/**
	 * 如果不是草稿状态就返回true
	 * @param id
	 * @return
	 */
	private boolean canNotUpdateOrdelete(Long id) {
		ExpPaymentH expPaymentH = expPaymentHDao.getById(id);
		if (Long.valueOf(expPaymentH.getBillState()) != 0l && Long.valueOf(expPaymentH.getBillState()) != 2l) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 报销单更新方法
	 * @param mainDescript 主表只能更新描述信息
	 * @param subList 子表信息
	 * @param delIds 被删除的记录ID
	 * @throws Exception
	 */
	public void updateExpPayment(Long id, ExpPaymentH expPaymentH, List<ExpPaymentB> subList, String delIds)
			throws Exception {
		if (canNotUpdateOrdelete(id)) {
			throw new RuntimeException("只有草稿状态下的记录才能修改!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("descript", expPaymentH.getDescript());
		params.put("takeDeptName", expPaymentH.getTakeDeptName());
		params.put("takeDeptId", expPaymentH.getTakeDeptId());
		params.put("billNum", expPaymentH.getBillNum());
		params.put("paymentAmount", expPaymentH.getPaymentAmount());
		params.put("id", id);
		expPaymentHDao.update("editMain", params);
		if (StringUtils.isNotEmpty(delIds)) {
			String[] splitIds = delIds.split(",");
			// 删除记录
			for (String delIdStr : splitIds) {
				Long delId = Long.valueOf(delIdStr);
				expPaymentBDao.delById(delId);
			}
		}

		for (ExpPaymentB epb : subList) {
			epb.setExpensesHID(id);
			epb.setTakeDeptId(expPaymentH.getTakeDeptId());
			epb.setTakeDeptName(expPaymentH.getTakeDeptName());
			expPaymentBDao.saveOrUpdate(epb);
		}

	}

	/**
	 * 费用报销的审批
	 * @param type1同意2驳回至发起人
	 * @param eph主表信息
	 * @param approveNote审批意见
	 * @param isNeedLead是否需要领导审批，0表示需要，非0表示不需要
	 * @param isNeedPay是否需要付款，0表示需要，非0表示不需要
	 */
	private void approvePayment(String type, ExpPaymentH eph, String approveNote, Short isNeedLead, Short isNeedPay)
			throws Exception {
		boolean end = false;
		Long paymentHId = eph.getId();
		// 获取该主单据的所有子单据ID
		List<Long> ids = expPaymentBDao.getIdsBymainId("getIdsByMainId", paymentHId);

		String processId = eph.getProcessesId().toString();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("approveNote", approveNote);
		paramMap.put("id", paymentHId);
		// 更新审批意见
		expPaymentHDao.update("upApNote", paramMap);

		if (AGREE.equals(type)) {

			// 是否需要分管领导和是否需要付款人
			// 调用流程(通过)


			// 更新为通过
			expPaymentBDao.update("upAgree", ids);

			// 判断是否结束
			if (false) {
				// 把主单据状态更新为审批结束
				expPaymentHDao.update("upFinish", paymentHId);
				eph.setBillState(3L);
				// 获取报销申请人ID
				Long currentUserId = eph.getApplyerId();

				// 销账
				// 1.计算本次报销总金额
				Float sum = eph.getPaymentAmount();

				// 用户账户金额历史表 记录一条记录
				ExpUserAccountHis expUserAccountHis = new ExpUserAccountHis();
				expUserAccountHis.setBillId(paymentHId);
				expUserAccountHis.setUserId(currentUserId);
				expUserAccountHis.setHappenAmount(sum);
				expUserAccountHis.setHappenDate(new Date());
				expUserAccountHisService.add(expUserAccountHis);

				// 2.查找未销账的预支单 按时间升序排序,先预支的先抹平
				List<ExpAdvance> ealist = expAdvanceDao.getBySqlKey("getAllAsc");// TODO
				if (ealist.size() > 0) {
					// 3.循环预支单
					for (ExpAdvance expAdvance : ealist) {
						// 预支单金额
						Float residualAmount = expAdvance.getResidualAmount();

						ExpMatch expMatch = new ExpMatch();
						expMatch.setBusiType(ExpMatch.BUS_TYPE4);
						expMatch.setCreateby(currentUserId);
						expMatch.setCreatetime(new Date());
						// 报销，转移单号
						expMatch.setFromId(paymentHId);
						expMatch.setToId(expAdvance.getId());
						// 够抵消
						if ((sum - residualAmount) >= 0) {
							// 报销总金额 = 报销总金额 - 预支单金额
							sum -= residualAmount;
							expAdvance.setIsFinish(1l);
							expAdvance.setResidualAmount(0f);
							expAdvanceDao.update(expAdvance);

							// 数据记录到中间表
							expMatch.setHappenAmount(residualAmount);
							expMatchService.add(expMatch);
						} else {
							// 不够抵消
							expAdvance.setResidualAmount(residualAmount - sum);
							expAdvanceDao.update(expAdvance);
							// 数据记录到中间表
							expMatch.setHappenAmount(sum);
							expMatchService.add(expMatch);
							sum -= residualAmount;
							break;
						}
					}

					if (sum < 0) {
						eph.setResidualAmount(0f);
						eph.setIsFinish(1l);
						expPaymentHDao.update(eph);
					} else {
						eph.setResidualAmount(sum);
						eph.setIsFinish(0l);
						expPaymentHDao.update(eph);
					}
				} else {
					eph.setResidualAmount(sum);
					eph.setIsFinish(0l);
					expPaymentHDao.update(eph);
				}
				//
				ExpUserAccount entity = expUserAccountService.getUserAccountByUserId(currentUserId);
				baseInfoServer.addAccountHis(eph.getId(), 1, entity.getId(), eph.getPaymentAmount(),
						eph.getApplyerId(), "个人报销");

				// 当前账户中报销金额
				Float perPaymentedTotal = entity.getPerPaymentedTotal();
				// 目前报销情况 原账户中报销金额+此次报销金额
				Float immPt = eph.getPaymentAmount() + perPaymentedTotal;
				entity.setPerPaymentedTotal(immPt);
				expUserAccountService.update(entity);
			}

		} else if (REJECT.equals(type)) {
			// 调用流程(驳回)
			// 更新为不通过
			expPaymentBDao.update("upReject", ids);
			// 把主单据状态更新为驳回
			expPaymentHDao.update("upReject", paymentHId);
		}
		eph.setApproveId(ContextUtil.getCurrentUserId());
		eph.setApproveName(ContextUtil.getCurrentUser().getFullname());
		expPaymentHDao.update(eph);
	}

	/**
	 * 专项费用报销的审批
	 * @param type1同意2驳回至发起人
	 * @param eph主表信息
	 * @param approveNote审批意见
	 * @param isNeedLead是否需要领导审批，0表示需要，非0表示不需要
	 * @param isNeedPay是否需要付款，0表示需要，非0表示不需要
	 */
	private void approveSpecial(String type, ExpPaymentH eph, String approveNote, Short isNeedLead, Short isNeedPay)
			throws Exception {
		// 主表主id
		Long paymentHId = eph.getId();
		// 流程id
		String processId = eph.getProcessesId().toString();
		// 当前用户
		Long currentUserId = ContextUtil.getCurrentUserId();

		if (AGREE.equals(type)) {// 同意审批
			if (false) {
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
					ExpAdvance expAdvance = expAdvanceDao.getById(expMatch.getToId());
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
					expAdvanceDao.update(expAdvance);
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

				// 修改报销单状态,把主单据状态更新为完成
				eph.setBillState(ExpPaymentH.STATE_APPROVE_END);
				if (happenAmount <= 0) {// 这次报销的金额全部抵消说明已经抵消完
					eph.setIsFinish(ExpPaymentH.IS_FINISH);
					eph.setResidualAmount(0f);
				} else {
					eph.setIsFinish(ExpPaymentH.IS_NOFINISH);
					eph.setResidualAmount(happenAmount);
				}

			}
		} else if (REJECT.equals(type)) {// 驳回至发起人
			// 调用流程(驳回至发起人)
			eph.setBillState(ExpPaymentH.STATE_REJUSTING);
		}
		// 更新审批意见
		eph.setApproveId(currentUserId);
		eph.setApproveName(ContextUtil.getCurrentUser().getFullname());
		eph.setApproveNote(approveNote);
		eph.setApproveDate(new Date());
		eph.setUpdateby(currentUserId);
		eph.setUpdatetime(new Date());
		expPaymentHDao.update(eph);
	}

	/**
	 * 活动申请的审批
	 * @param type1同意2驳回至发起人
	 * @param eph主表信息
	 * @param approveNote审批意见
	 * @param isNeedLead是否需要领导审批，0表示需要，非0表示不需要
	 * @param isNeedPay是否需要付款，0表示需要，非0表示不需要
	 */
	private void approveActivity(String type, ExpPaymentH eph, String approveNote, Short isNeedLead, Short isNeedPay)
			throws Exception {
		// 流程id
		String processId = eph.getProcessesId().toString();
		// 当前用户
		Long currentUserId = ContextUtil.getCurrentUserId();

		if (AGREE.equals(type)) {// 同意审批
				// 修改报销单状态,把主单据状态更新为完成
				eph.setResidualAmount(0f);
				eph.setBillState(ExpPaymentH.STATE_APPROVE_END);
				eph.setIsFinish(ExpPaymentH.IS_FINISH);
		} else if (REJECT.equals(type)) {// 驳回至发起人
			// 调用流程(驳回至发起人)
			eph.setBillState(ExpPaymentH.STATE_REJUSTING);
		}
		// 更新审批意见
		eph.setApproveId(currentUserId);
		eph.setApproveName(ContextUtil.getCurrentUser().getFullname());
		eph.setApproveNote(approveNote);
		eph.setApproveDate(new Date());
		eph.setUpdateby(currentUserId);
		eph.setUpdatetime(new Date());
		expPaymentHDao.update(eph);
	}

	/**
	 * 封装流程变量
	 * @return
	 */
	private List<Object> approveVar(Short isNeedLead, Short isNeedPay) {
		// 是否需要分管领导和是否需要付款人
		if (isNeedLead != null) {
		}
		if (isNeedPay != null) {
		}
		return null;
	}

}
