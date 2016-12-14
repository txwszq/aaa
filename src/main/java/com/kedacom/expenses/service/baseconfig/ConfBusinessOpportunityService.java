package com.kedacom.expenses.service.baseconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.baseconfig.ConfBusinessOpportunityDao;
import com.kedacom.expenses.model.baseconfig.ConfBusinessOpportunity;
import com.kedacom.security.dao.SysUserDao;
import com.kedacom.security.model.SysUser;

/**
 * @Description 商机信息service层操作类
 * @author zhangwenbin
 * @date 2013-11-13
 */
@Service
public class ConfBusinessOpportunityService extends
		BaseService<ConfBusinessOpportunity> {

	@Resource
	private ConfBusinessOpportunityDao confBusinessOpportunityDao;

	@Resource
	private SysUserDao sysUserDao;

	protected IEntityDao<ConfBusinessOpportunity, Long> getEntityDao() {
		return this.confBusinessOpportunityDao;
	}

	/**
	 * 根据用户id查询他关联的商机
	 * 
	 * @param userId
	 * @return
	 */
	public List<ConfBusinessOpportunity> getAllByUserId(Long userId) {
		SysUser sysUser = (SysUser) sysUserDao.getById(userId);

		List<ConfBusinessOpportunity> returnList = new ArrayList<ConfBusinessOpportunity>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("boPrincipalCode", sysUser.getUserCode());
		// 按照工号查询，按照主要负责人查
		returnList = confBusinessOpportunityDao.getBySqlKey("getAll", param);

		// 按照id查，按照次要负责人查
		List<ConfBusinessOpportunity> otherTaskers = confBusinessOpportunityDao
				.getAllByOtherTaker(userId);
		if (otherTaskers != null && otherTaskers.size() > 0) {
			returnList.addAll(otherTaskers);
		}
		return returnList;
	}

	/**
	 * 根据用户id查询他是次要负责人的商机
	 * 
	 * @param userId
	 * @return
	 */
	public List<ConfBusinessOpportunity> getAllByOtherTaker(Long userId) {
		return confBusinessOpportunityDao.getAllByOtherTaker(userId);
	}

}
