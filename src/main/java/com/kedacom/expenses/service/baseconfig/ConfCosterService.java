/**
 * @(#)ConfCosterService.java 2013-11-13 Copyright 2013 it.kedacom.com, Inc. All
 *                            rights reserved.
 */
package com.kedacom.expenses.service.baseconfig;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.baseconfig.ConfCosterDao;
import com.kedacom.expenses.model.baseconfig.ConfCoster;
import com.kedacom.security.dao.SysOrgTypeDao;
import com.kedacom.security.dao.SysUserOrgDao;
import com.kedacom.security.model.SysOrgType;
import com.kedacom.security.model.SysUser;
import com.kedacom.security.model.SysUserOrg;

/**
 * (部门费用审核人Services).
 * @author zhujun
 * @version 2013-11-13
 */
@Service
public class ConfCosterService extends BaseService<ConfCoster> {

	/**
	 * @see com.kedacom.core.service.GenericService#getEntityDao()
	 */
	@Resource
	private ConfCosterDao costerDao;

	@Resource
	private SysUserOrgDao sysUserOrgDao;

	@Resource
	private SysOrgTypeDao sysOrgTypeDao;

	@Override
	protected IEntityDao<ConfCoster, Long> getEntityDao() {
		return costerDao;
	}

	public ConfCoster getByDeptId(Long deptId) {
		return (ConfCoster) costerDao.getOne("getByDept", deptId.toString());
	}

	/**
	 * (根据部门获取部门信息).
	 * @param orgId
	 * @return
	 */
	public List<SysUserOrg> getManagerByOrgId(Long orgId) {
		return sysUserOrgDao.getChargeByOrgId(orgId);
	}

	/**
	 * 根据维度ID获取类型列表
	 * @param demId
	 * @return
	 */
	public SysOrgType getByTyped(long demId) {
		return sysOrgTypeDao.getById(demId);
	}
}
