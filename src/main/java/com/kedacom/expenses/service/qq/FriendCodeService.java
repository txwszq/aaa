package com.kedacom.expenses.service.qq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.qq.FriendCodeDao;
import com.kedacom.expenses.model.qq.FriendCode;

@Service
public class FriendCodeService extends BaseService<FriendCode> {

	@Resource
	private FriendCodeDao friendCodeDao;

	public FriendCodeService() {
	}

	@Override
	protected IEntityDao<FriendCode, Long> getEntityDao() {
		return friendCodeDao;
	}

	public List<FriendCode> getAllByState(Integer state) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("state", state);
		return friendCodeDao.getBySqlKey("getByState", params);
	}

	public FriendCode getFriendCode(String hashCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hash_code", hashCode);
		return friendCodeDao.getUnique("getAll", params);
	}

	public Integer updateState(List<String> list) {
		return friendCodeDao.update("updateState", list);
	}
}
