package com.kedacom.expenses.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.impl.client.BasicCookieStore;
import org.quartz.JobExecutionContext;

import com.kedacom.core.utils.ApplicationUtil;
import com.kedacom.expenses.friends.FriendParamUtils;
import com.kedacom.expenses.model.qq.FriendCode;
import com.kedacom.expenses.model.qq.QQNumManage;
import com.kedacom.expenses.scheduler.BaseJob;
import com.kedacom.expenses.service.qq.FriendCodeService;
import com.kedacom.expenses.service.qq.QQNumManageService;
import com.kedacom.expenses.utils.HttpClient4Utils;
import com.kedacom.expenses.utils.MyUtils;

public class AddLoginFirendJob extends BaseJob {

	private FriendCodeService fcs = (FriendCodeService) ApplicationUtil.getBean(FriendCodeService.class);

	private QQNumManageService qqservice = (QQNumManageService) ApplicationUtil.getBean(QQNumManageService.class);

	@Override
	public void executeJob(JobExecutionContext paramJobExecutionContext) throws Exception {

		List<QQNumManage> allByState = qqservice.getNeedAdd(1);
		for (QQNumManage qqMap : allByState) {
			try {
				String qqNum = qqMap.getQq_num();

				String gtk = MyUtils.getGtk(qqNum);
				BasicCookieStore cookie = MyUtils.getCookie(qqNum);
				String url = "http://api.pengyou.com/json.php?g_tk=" + gtk + "&mod=friends&act=apply";

				List<FriendCode> hashList = fcs.getAllByState(0);
				List<String> upList = new ArrayList<String>();

				String friendHash = "";
				// 把好友hashcode拼成一个以逗号隔开的字符串
				for (FriendCode code : hashList) {
					String hash = code.getHash_code();
					friendHash += hash + ",";
					upList.add(hash);
				}
				// 要添加好友的hashcode
				String friends = friendHash.substring(0, friendHash.length() - 1);
				List<NameValuePair> addFriendParam = FriendParamUtils.addFriendsParam(friends, gtk);
				String postMethod = HttpClient4Utils.postMethod(url, addFriendParam, cookie);
				// TODO 添加好友
				if (postMethod.startsWith("{\"code\":0")) {
					System.out.println(postMethod);
					// 添加成功,更新添加过的好友
					fcs.updateState(upList);
					Thread.sleep(1000 * 5);
				} else {
					System.out.println(qqNum + "登陆失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
