package com.kedacom.expenses.service.qq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.qq.FriendCodeBakDao;
import com.kedacom.expenses.model.qq.FriendCodeBak;

@Service
public class FriendCodeBakService extends BaseService<FriendCodeBak> {

	@Resource
	private FriendCodeBakDao friendCodeBakDao;

	public FriendCodeBakService() {
	}

	@Override
	protected IEntityDao<FriendCodeBak, Long> getEntityDao() {
		return friendCodeBakDao;
	}

	public List<FriendCodeBak> getAllByState(Integer state) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("state", state);
		return friendCodeBakDao.getBySqlKey("getByState", params);
	}

	public FriendCodeBak getFriendCode(String hashCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hash_code", hashCode);
		return friendCodeBakDao.getUnique("getAll", params);
	}

	public Integer updateState(List<String> list) {
		return friendCodeBakDao.update("updateState", list);
	}
}
