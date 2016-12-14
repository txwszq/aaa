package com.kedacom.expenses.service.findperson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;


import com.kedacom.expenses.service.base.BaseInfoServer;
import com.kedacom.security.model.SysOrg;
import com.kedacom.security.service.ISysOrg;
import com.kedacom.security.service.ISysUser;
import com.kedacom.security.service.SysUserOrgService;
import com.kedacom.security.service.auth.SysUserService;
import com.kedacom.security.service.impl.SysOrgService;
import com.kedacom.security.util.ContextUtil;

/**
 * 查询审批人工具类
 * @author zhangwenbin
 * @version 2013年12月4日
 */
@Service
public class FindApproverUtil {

	private Log logger = LogFactory.getLog(FindApproverUtil.class);

	// 费用审核员
	public static final String EXPENSE_AUDIT = "expenseAudit";

	// 部门经理审核员
	public static final String DEPARTMENT_AUDIT = "departmentAudit";

	// 财务一级审核员
	public static final String FINANCE_FIRST_AUDIT = "financeFirstAudit";

	// 分管领导审核员
	public static final String LEAD_AUDIT = "leadAudit";

	// 付款人审核员
	public static final String PAYER_AUDIT = "payerAudit";

	// 业务编码
	public static final String BILL_CODE = "billCode";

	@Resource
	private SysUserOrgService sysUserOrgService;

	@Resource
	private SysUserService sysUserService;

	@Resource
	private SysOrgService sysOrgService;

	@Resource
	private BaseInfoServer baseInfoServer;

	/**
	 * 根据sysOrg查询部门负责人
	 * @param sysOrg
	 * @return String类型的用户账户的拼接字符串
	 *         如：abc@kedacom.com,cba@kedacom.com,ccc@kedacom.com
	 */
	public String queryDepHeadByDepId(SysOrg sysOrg) {
		// 查询该部门的主要负责人，可能为多个，通过“,”拼接
		ISysOrg charge = sysUserOrgService.getChageNameByOrgId(sysOrg.getOrgId());
		String depHeads = "";
		if (StringUtils.isNotEmpty(charge.getOwnUser())) {
			String[] chargeIds = charge.getOwnUser().split(",");
			for (int i = 0; i < chargeIds.length; i++) {
				ISysUser user = sysUserService.getById(new Long(chargeIds[i]));
				if (i == chargeIds.length - 1) {
					depHeads = user.getAccount();
				} else {
					depHeads = user.getAccount() + ",";
				}
			}
		}
		return depHeads;
	}

	/**
	 * 根据sysOrg查询组织的分管领导或者上一级的部门负责人
	 * @param sysOrg
	 * @return string类型的用户账户
	 */
	public String queryUpDepHeadByUserId(SysOrg sysOrg) {
		// 需要查询的账户
		String destAccount = "";
		if (sysOrg != null) {
			Long chargeManagerId = sysOrg.getManagerID() == null ? null : sysOrg.getManagerID();
			if (chargeManagerId != null) {// 分管领导存在就结束
				ISysUser chargeManager = sysUserService.getById(chargeManagerId);
				if (chargeManager != null) {
					destAccount = chargeManager.getAccount() == null ? "" : chargeManager.getAccount().toString();
				}
			} else {// 如果没有找到分管领导，找上一级部门负责人
				// 上级部门
				SysOrg upSysOrg = (SysOrg) sysOrgService.getById(sysOrg.getOrgSupId());
				// 查询上级部门的负责人
				destAccount = queryDepHeadByDepId(upSysOrg);
			}
		}
		return destAccount;
	}

	/**
	 * 按照用户id查询费用审核人、财务一级审核人、付款人。且判断是否需要费用审核员。
	 * @param userId
	 * @return
	 */
	public List<Object> findApprovers(Long userId) {
		List<Object> processVar = new ArrayList<Object>();
		// 增加审批人信息
		// 按照人员找个人配置，找不到再按照人员找最近一级费用承担部门的配置
		Map<String, Object> approveMap = baseInfoServer.getAllApprovers(userId);
		String expenseAudits = (String) approveMap.get(this.EXPENSE_AUDIT);
		String financeFirstAudits = (String) approveMap.get(this.FINANCE_FIRST_AUDIT);
		String payerAudits = (String) approveMap.get(this.PAYER_AUDIT);

		// 是否需要费用审核员，0表示需要，非0表示不需要
		Object isNeedExpAssessorVar = new Object();
		// 费用审核
		if (StringUtils.isNotEmpty(expenseAudits)) {
			Object expenseAuditVar = new Object();
			processVar.add(expenseAuditVar);

		} else {
		}
		processVar.add(isNeedExpAssessorVar);

		// 部门审核
		// 根据用户查最近一级费用承担部门
		SysOrg sysOrg = (SysOrg) baseInfoServer.getTakingDeptByUserId(userId);
		String departmentAudits = this.queryDepHeadByDepId(sysOrg);
		if (StringUtils.isNotEmpty(departmentAudits)) {
			Object departmentAuditVar = new Object();
			processVar.add(departmentAuditVar);
		}

		// 财务一级审核
		if (StringUtils.isNotEmpty(financeFirstAudits)) {
			Object financeFirstAuditVar = new Object();
			processVar.add(financeFirstAuditVar);
		}

		// 领导审核
		String leadAudits = queryUpDepHeadByUserId(sysOrg);
		if (StringUtils.isNotEmpty(leadAudits)) {
			Object leadAuditVar = new Object();
			processVar.add(leadAuditVar);
		}

		// 付款人付款
		if (StringUtils.isNotEmpty(payerAudits)) {
			Object payerAuditVar = new Object();
			processVar.add(payerAuditVar);
		}
		// end 增加审批人信息
		return processVar;
	}

	/**
	 * 启动流程
	 * @param businessKey业务主键
	 * @param billCode业务编码
	 * @throws Exception_Exception
	 */
	public String startFlow(String businessKey, String billCode, String flowKey) throws Exception{
		// 增加审核人员信息，和判断分支
		List<Object> processVar = this.findApprovers(ContextUtil.getCurrentUserId());
		// 增加业务编码
		Object billCodeVar = new Object();
		if (processVar != null && processVar.size() > 0) {
			processVar.add(billCodeVar);
		} else {
			processVar = new ArrayList<Object>();
			processVar.add(billCodeVar);
		}

		// 启动流程
		return null;
	}

}
