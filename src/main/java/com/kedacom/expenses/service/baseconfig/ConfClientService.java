package com.kedacom.expenses.service.baseconfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.baseconfig.ConfClientDao;
import com.kedacom.expenses.model.baseconfig.ConfClient;

/**
 * @Description 客户信息service层操作类
 * @author zhangwenbin
 * @date 2013-11-13
 */
@Service
@Transactional
public class ConfClientService extends BaseService<ConfClient> {

	@Resource
	private ConfClientDao confClientDao;

	protected IEntityDao<ConfClient, Long> getEntityDao() {
		return this.confClientDao;
	}

	/**
	 * 按照客户名称模糊查询
	 * @param clientName
	 * @return
	 */
	public List<ConfClient> getListByName(String clientName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("clientName", clientName);
		return confClientDao.getBySqlKey("getAll", map);
	}

}
