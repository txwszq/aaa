package com.kedacom.expenses.service.baseconfig;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.baseconfig.ConfOtherTakerDao;
import com.kedacom.expenses.model.baseconfig.ConfOtherTaker;
import com.kedacom.security.dao.SysUserDao;
import com.kedacom.security.model.SysUser;

@Service
public class ConfOtherTakerService extends BaseService<ConfOtherTaker> {

	@Resource
	private ConfOtherTakerDao confOtherTakerDao;

	@Resource
	private SysUserDao sysUserDao;

	public ConfOtherTakerService() {
	}

	@Override
	protected IEntityDao<ConfOtherTaker, Long> getEntityDao() {
		return confOtherTakerDao;
	}

	/**
	 * 新增用户角色关系
	 * 
	 * @param roleId
	 * @param userIds
	 * @throws Exception
	 */
	public void add(Long type, Long businessId, Long[] userIds)
			throws Exception {
		if ((type == null) || (businessId == null) || (userIds == null)
				|| (userIds.length == 0)) {
			return;
		}
		for (Long userId : userIds) {
			ConfOtherTaker confOtherTaker = confOtherTakerDao
					.getConfOtherTasker(type, businessId, userId);
			if (confOtherTaker == null) {
				ConfOtherTaker cot = new ConfOtherTaker();
				cot.setBusiness_id(businessId);
				cot.setType(type);
				cot.setUser_id(userId);
				SysUser sysUser = (SysUser) sysUserDao.getById(userId);
				if (sysUser != null) {
					cot.setUser_name(sysUser.getFullname() == null ? ""
							: sysUser.getFullname());
					cot.setUser_account(sysUser.getAccount() == null ? ""
							: sysUser.getAccount());
					cot.setUser_code(sysUser.getUserCode() == null ? ""
							: sysUser.getUserCode());
				}
				confOtherTakerDao.add(cot);
			}
		}
	}
}
