package com.kedacom.expenses.job;

import java.util.List;

import org.apache.http.impl.client.BasicCookieStore;
import org.quartz.JobExecutionContext;

import com.kedacom.core.utils.ApplicationUtil;
import com.kedacom.expenses.login.FriendLogin;
import com.kedacom.expenses.model.qq.QQNumManage;
import com.kedacom.expenses.scheduler.BaseJob;
import com.kedacom.expenses.service.qq.QQNumManageService;
import com.kedacom.expenses.utils.HttpClient4Utils;
import com.kedacom.expenses.utils.MyUtils;

public class LoginFirendJob extends BaseJob {

	private QQNumManageService qqservice = (QQNumManageService) ApplicationUtil.getBean(QQNumManageService.class);

	@Override
	public void executeJob(JobExecutionContext paramJobExecutionContext) throws Exception {

		System.out.println("朋友网登录检查开始");
		List<QQNumManage> allByState = qqservice.getAllByState(1);

		for (QQNumManage qqObj : allByState) {
			try {
				String qqNum = qqObj.getQq_num();
				BasicCookieStore cookie = MyUtils.getCookie(qqNum);
				HttpClient4Utils.getHtml("http://home.pengyou.com/index.php", cookie);
				boolean loginSuccess = HttpClient4Utils.loginSuccess(cookie, qqNum);
				if (loginSuccess) {
					MyUtils.saveCookie(cookie, MyUtils.friend, qqNum);
				} else {
					String pwd = qqObj.getQq_pwd();
					System.out.println("朋友网登录失败：" + qqNum);
					Boolean login = FriendLogin.login(qqNum, pwd);
					if (login) {
						System.out.println(qqNum + " 朋友网登录成功");
					} else {
						System.out.println(qqNum + " 朋友网登录失败");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("朋友网登录检查结束");

	}

}
