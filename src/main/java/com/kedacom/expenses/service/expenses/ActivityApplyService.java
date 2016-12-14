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
import com.kedacom.expenses.dao.expenses.ExpPaymentBDao;
import com.kedacom.expenses.dao.expenses.ExpPaymentHDao;
import com.kedacom.expenses.model.expenses.ExpPaymentB;
import com.kedacom.expenses.model.expenses.ExpPaymentH;
import com.kedacom.expenses.service.findperson.FindApproverUtil;
import com.kedacom.security.model.SysOrg;
import com.kedacom.security.model.SysUser;
import com.kedacom.security.util.ContextUtil;

/**
 * 活动申请service
 * @author zhangwenbin
 * @version 2013-12-03
 */
@Service
public class ActivityApplyService {

	private Log logger = LogFactory.getLog(ActivityApplyService.class);

	@Resource
	private ExpPaymentHDao expPaymentHDao;

	@Resource
	private ExpPaymentBDao expPaymentBDao;

	@Resource
	private FindApproverUtil findApproverUtil;

	@Resource
	private Properties configproperties;

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
		return map;
	}

	/**
	 * 保存活动申请的信息，可以保存新数据也可以保存编辑的数据
	 * @param epb
	 */
	public void saveOrUpdateApply(ExpPaymentH eph, List<ExpPaymentB> subList, List<Long> delIdList) throws Exception {
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		SysOrg sysOrg = (SysOrg) ContextUtil.getCurrentOrg();
		// 保存主表和子表数据
		if (eph.getId() == null) {// 保存操作
			// 完善主表信息
			eph.setBillType(ExpPaymentH.TYPE_ACTIVITY_APPLY);
			eph.setBillState(ExpPaymentH.STATE_DRAFTING);
			eph.setIsFinish(ExpPaymentH.IS_NOFINISH);
			eph.setCreateby(sysUser.getUserId());
			eph.setCreatetime(new Date());
			eph.setUpdateby(sysUser.getUserId());
			eph.setUpdatetime(new Date());
			expPaymentHDao.add(eph);
		} else {// 更新操作
			eph.setUpdateby(sysUser.getUserId());
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

		// 删除子表信息
		if (delIdList != null && delIdList.size() > 0) {
			for (Long delId : delIdList) {
				expPaymentBDao.delById(delId);
			}
		}

	}

	/**
	 * 保存活动申请的信息，并启动流程
	 * @param epb
	 * @throws Exception_Exception
	 */
	public void commitApply(ExpPaymentH eph, List<ExpPaymentB> subList, List<Long> delIdList) throws Exception {

		// 保存主表和子表还有历史表的数据
		saveOrUpdateApply(eph, subList, delIdList);

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
	 * 删除活动申请
	 * @param ids
	 */
	public void deleteActivityApply(Long id) throws Exception {
		// 删除主表信息
		expPaymentHDao.delById(id);
		// 删除活动申请子表信息
		expPaymentBDao.delByExpensesHId(id);
	}

	/**
	 * 删除活动申请
	 * @param ids
	 */
	public void deleteActivityApplys(String ids) throws Exception {
		String array[] = ids.split(",");
		for (int i = 0; i < array.length; i++) {
			Long id = Long.parseLong(array[i]);
			deleteActivityApply(id);
		}
	}

	/**
	 * 提交活动申请
	 * @param ids
	 * @throws Exception_Exception
	 */
	public void onlySubmitApply(String ids) throws Exception {
		String flowKey = configproperties.getProperty("paymentFlowKey");
		String array[] = ids.split(",");
		for (int i = 0; i < array.length; i++) {
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
	 * 财务提交活动申请
	 * @param epb
	 * @throws Exception_Exception
	 */
	public void financeCommitApply(ExpPaymentH eph, List<ExpPaymentB> subList, List<Long> delIdList) throws Exception {

		// 保存主表和子表还有历史表的数据
		saveOrUpdateApply(eph, subList, delIdList);
		// 计算金额
		eph.setResidualAmount(0f);
		eph.setBillState(ExpPaymentH.STATE_APPROVE_END);
		eph.setIsFinish(ExpPaymentH.IS_FINISH);
		// 更改状态
		expPaymentHDao.update(eph);
	}

}
