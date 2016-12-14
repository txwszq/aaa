package com.kedacom.expenses.job;

import java.util.List;

import org.quartz.JobExecutionContext;

import com.kedacom.core.utils.ApplicationUtil;
import com.kedacom.expenses.model.qq.QQNumManage;
import com.kedacom.expenses.scheduler.BaseJob;
import com.kedacom.expenses.service.qq.QQNumManageService;
import com.kedacom.expenses.utils.MyUtils;

public class DeleteFriendJob extends BaseJob {

	private QQNumManageService qqservice = (QQNumManageService) ApplicationUtil.getBean(QQNumManageService.class);

	@Override
	public void executeJob(JobExecutionContext paramJobExecutionContext) throws Exception {

		List<QQNumManage> myqqs = qqservice.getAllByState(1);

		System.out.println("检查朋友数量开始");
		for (QQNumManage qqm : myqqs) {
			try {
				String qqNum = qqm.getQq_num();
				List<String> myFriendsList = MyUtils.getMyFriendsList(MyUtils.friendsListUrl, qqNum);
				if (myFriendsList != null) {
					System.out.println(qqNum + " : " + myFriendsList.size());
				} else {
					System.out.println(qqNum + " : " + 0);
				}
				if (myFriendsList.size() >= 920) {
					qqm.setNeed_add_friend(0l);
					qqservice.update(qqm);
				}

				if (myFriendsList.size() >= 960) {
					System.out.println("删除前:" + qqNum + " - " + myFriendsList.size() + "");
					// RemoveFriend.deleteTenFriends(qqNum);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("检查朋友数量结束");

	}

}
