package com.kedacom.expenses.service.qq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.qq.QQNumManageDao;
import com.kedacom.expenses.friends.RemoveFriend;
import com.kedacom.expenses.model.qq.QQNumManage;

@Service
public class QQNumManageService extends BaseService<QQNumManage> {

	@Resource
	private QQNumManageDao qQNumManageDao;

	@Override
	protected IEntityDao<QQNumManage, Long> getEntityDao() {
		return qQNumManageDao;
	}

	public List<QQNumManage> getAllByState(Integer state) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("friend_is_normal", 1);
		return qQNumManageDao.getBySqlKey("getAll", params);
	}

	public List<QQNumManage> getQzoneNormal(Integer state) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("qzone_is_normal", 1);
		return qQNumManageDao.getBySqlKey("getAll", params);
	}

	/**
	 * 获取需要添加朋友的QQ
	 * @param state
	 * @return
	 */
	public List<QQNumManage> getNeedAdd(Integer state) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("need_add_friend", state);
		return qQNumManageDao.getBySqlKey("getAll", params);
	}

	/**
	 * 获取需要添加QQ的QQ
	 * @param state
	 * @return
	 */
	public List<QQNumManage> getNeedAddQQ(Integer state) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("need_add_qzone", state);
		params.put("qzone_is_normal", 1);
		return qQNumManageDao.getBySqlKey("getAll", params);
	}

	public QQNumManage getAllByQQNum(String qqNum) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("qq_num", qqNum);
		return qQNumManageDao.getUnique("getAll", params);
	}

	public void removeReapetFriends() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("friend_is_normal", 1);
		List<QQNumManage> qqList = qQNumManageDao.getBySqlKey("getAll", params);
		RemoveFriend.delReapetFriends(qqList);
	}

	/**
	 * 增加序号
	 * @param qqNum
	 */
	public void addSn(String qqNum) {
		QQNumManage qQNumManage = (QQNumManage) qQNumManageDao.getOne("getByQQNum", qqNum);
		Integer sn = qQNumManage.getSn();
		if (sn == null) {
			sn = 1;
		}
		sn += 1;
		qQNumManage.setSn(sn);
		qQNumManageDao.update(qQNumManage);
	}

	/**
	 * 获取序号最小的QQ
	 */
	public QQNumManage getLimitSnQQ() {
		QQNumManage qQNumManage = (QQNumManage) qQNumManageDao.getOne("getLimitSn", 1);
		return qQNumManage;
	}
}
