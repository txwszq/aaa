package com.kedacom.expenses.service.baseconfig;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.baseconfig.ConfPerCosterDao;
import com.kedacom.expenses.model.baseconfig.ConfPerCoster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ConfPerCosterService extends BaseService<ConfPerCoster> {

	@Resource
	private ConfPerCosterDao confPerCosterDao;

	public ConfPerCosterService() {
	}

	@Override
	protected IEntityDao<ConfPerCoster, Long> getEntityDao() {
		return confPerCosterDao;
	}

	public ConfPerCoster getConfPerCosterByUser(String userId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("person_id", userId);
		return (ConfPerCoster) confPerCosterDao.getOne("getAll", map);
	}

}
