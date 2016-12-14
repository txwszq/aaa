package com.kedacom.expenses.job;

import java.util.List;

import org.apache.http.impl.client.BasicCookieStore;
import org.quartz.JobExecutionContext;

import com.kedacom.core.utils.ApplicationUtil;
import com.kedacom.expenses.login.QzoneLogin;
import com.kedacom.expenses.model.qq.QQNumManage;
import com.kedacom.expenses.scheduler.BaseJob;
import com.kedacom.expenses.service.qq.QQNumManageService;
import com.kedacom.expenses.utils.HttpClient4Utils;
import com.kedacom.expenses.utils.MyUtils;

public class QzoneJob extends BaseJob {

	private QQNumManageService qqservice = (QQNumManageService) ApplicationUtil.getBean(QQNumManageService.class);

	@Override
	public void executeJob(JobExecutionContext paramJobExecutionContext) throws Exception {
		System.out.println("空间登录检查开始");
		List<QQNumManage> allByState = qqservice.getQzoneNormal(1);
		for (QQNumManage qq : allByState) {
			String qqNum = qq.getQq_num();
			BasicCookieStore cookie = MyUtils.getCookie(qqNum, MyUtils.qzone);
			String html = HttpClient4Utils.getHtml("http://user.qzone.qq.com/" + qqNum, cookie);
			boolean loginSuccess = HttpClient4Utils.qzoneLoginSuccess(html, qqNum);
			if (loginSuccess) {
				MyUtils.saveCookie(cookie, MyUtils.qzone, qqNum);
			} else {
				System.out.println("空间登录失败：" + qqNum);
				String password = qq.getQq_pwd();
				Boolean login = QzoneLogin.login(qqNum, password);
				if (login) {
					System.out.println(qqNum + " 空间登录成功");
					// MyUtils.saveCookie(cookie, MyUtils.qzone, qqNum);
				} else {
					System.out.println(qqNum + " 空间登录失败");
				}
				Thread.sleep(10000);
			}
		}
		System.out.println("空间登录检查结束");
	}

}
