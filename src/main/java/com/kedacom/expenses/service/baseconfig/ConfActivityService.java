/**
 * @(#)ActivityInfoService.java 2013-11-15 Copyright 2013 it.kedacom.com, Inc.
 *                              All rights reserved.
 */
package com.kedacom.expenses.service.baseconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.baseconfig.ConfActivityDao;
import com.kedacom.expenses.model.baseconfig.ConfActivity;
import com.kedacom.security.dao.SysUserDao;
import com.kedacom.security.model.SysUser;

/**
 * (用一句话描述类的主要功能).
 * 
 * @author zhujun
 * @version 2013-11-15
 */
@Service
public class ConfActivityService extends BaseService<ConfActivity> {

	/**
	 * @see com.kedacom.core.service.GenericService#getEntityDao()
	 */

	@Resource
	private ConfActivityDao activityDao;

	@Resource
	private SysUserDao sysUserDao;

	@Override
	protected IEntityDao<ConfActivity, Long> getEntityDao() {
		return activityDao;
	}

	/**
	 * 根据用户id查询他关联的活动
	 * 
	 * @param userId
	 * @return
	 */
	public List<ConfActivity> getAllByUserId(Long userId) {
		SysUser sysUser = (SysUser) sysUserDao.getById(userId);

		List<ConfActivity> returnList = new ArrayList<ConfActivity>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("actCreaterCode", sysUser.getUserCode());
		// 按照工号查询，按照主要负责人查
		returnList = activityDao.getBySqlKey("getAll", param);

		// 按照id查，按照次要负责人查
		List<ConfActivity> otherTaskers = activityDao
				.getAllByOtherTaker(userId);
		if (otherTaskers != null && otherTaskers.size() > 0) {
			returnList.addAll(otherTaskers);
		}
		return returnList;
	}

	/**
	 * 根据用户id查询他是次要负责人的活动
	 * 
	 * @param userId
	 * @return
	 */
	public List<ConfActivity> getAllByOtherTaker(Long userId) {
		return activityDao.getAllByOtherTaker(userId);
	}

}
