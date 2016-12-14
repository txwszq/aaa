package com.kedacom.expenses.service.qq;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.BasicCookieStore;
import org.springframework.stereotype.Service;

import com.kedacom.core.db.IEntityDao;
import com.kedacom.core.service.BaseService;
import com.kedacom.expenses.dao.qq.QQNeedAddDao;
import com.kedacom.expenses.friends.FriendParamUtils;
import com.kedacom.expenses.login.QzoneLogin;
import com.kedacom.expenses.model.qq.QQNeedAdd;
import com.kedacom.expenses.model.qq.QQNumManage;
import com.kedacom.expenses.utils.CommsTools;
import com.kedacom.expenses.utils.ConnectNetWork;
import com.kedacom.expenses.utils.HttpClient4Utils;
import com.kedacom.expenses.utils.MyUtils;

@Service
public class QQNeedAddService extends BaseService<QQNeedAdd> {

	@Resource
	private QQNeedAddDao qQNeedAddDao;

	@Resource
	private QQNumManageService qQNumManageService;

	@Override
	protected IEntityDao<QQNeedAdd, Long> getEntityDao() {
		return qQNeedAddDao;
	}

	/**
	 * 获取要添加的QQ
	 * @return
	 */
	public String getDistQQ() {
		QQNeedAdd one = (QQNeedAdd) qQNeedAddDao.getOne("getStateEQZero", 0);
		if (one != null) {
			return one.getQq_num();
		} else {
			return null;
		}
	}
   
	public static void main(String[] args) throws Exception {
		
		List<String> qqList = FileUtils.readLines(new File(System.getProperty("user.dir")+"/qqnum.txt"), "utf-8");
		List<String> needAdd = FileUtils.readLines(new File(System.getProperty("user.dir")+"/needAdd.txt"), "utf-8");
		List<String> done = FileUtils.readLines(new File(System.getProperty("user.dir")+"/done.txt"), "utf-8");
		List<String> error = FileUtils.readLines(new File(System.getProperty("user.dir")+"/error.txt"), "utf-8");
		
		needAdd.remove(done);
		needAdd.remove(error);
		
		QQNeedAddService service = new QQNeedAddService();
		
		int i = 0;
		
		for (String str : qqList) {
			String qqNum = str.split("----")[0];
			String qqPwd = str.split("----")[1];
			Boolean login = QzoneLogin.login(qqNum, qqPwd);
			
			if (login) {
				System.out.println(qqNum + ":登录成功");
			} else {
				System.out.println(qqNum + ":登录失败");
				continue;
			}
			for (int j = 0; j < 4; j++) {
				try {
					CommsTools.sleep(4000);
					String distQQ = needAdd.get(i++);
					boolean sendAddQQ = service.sendAddQQ(distQQ, qqNum);
					if (sendAddQQ) {
						System.out.println("添加成功！我的QQ："+qqNum+"，目标QQ："+distQQ);
						FileUtils.write(new File(System.getProperty("user.dir")+"/done.txt"), distQQ+"\r\n", true);
					} else {
						System.out.println("添加失败！我的QQ："+qqNum);
						FileUtils.write(new File(System.getProperty("user.dir")+"/error.txt"), distQQ+"\r\n", true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			try {
				ConnectNetWork.cutAdsl("宽带连接");
				CommsTools.sleep(1000);
				// 再连，分配一个新的IP
				while (!ConnectNetWork.connAdsl("宽带连接", "18962535037", "19881013.zzq"))
					;
				CommsTools.sleep(2000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void addQQ() {
		QQNumManage qq = qQNumManageService.getLimitSnQQ();
		try {
			ConnectNetWork.cutAdsl("宽带连接");
			CommsTools.sleep(1000);
			// 再连，分配一个新的IP
			while (!ConnectNetWork.connAdsl("宽带连接", "05595526919", "802428"))
				;
			CommsTools.sleep(2000);
			String qqNum = qq.getQq_num();
			String qqPwd = qq.getQq_pwd();
			Boolean login = QzoneLogin.login(qqNum, qqPwd);
			if (login) {
				System.out.println(qqNum + ":登录成功");
			} else {
				System.out.println(qqNum + ":登录失败");
			}
			CommsTools.sleep(5000);
			String distQQ = getDistQQ();
			boolean sendAddQQ = sendAddQQ(distQQ, qqNum);
			if (sendAddQQ) {
				qQNumManageService.addSn(qqNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送添加好友
	 * @param distQQ
	 * @param qqNum
	 * @return
	 */
	public boolean sendAddQQ(String distQQ, String qqNum) {
		BasicCookieStore cookie = MyUtils.getCookie(qqNum, MyUtils.qzone);
		List<NameValuePair> checkAdd = FriendParamUtils.checkAddOfpmd(distQQ, qqNum);
		String gtk = MyUtils.getQzoneGtk(qqNum);
		String referer = "http://user.qzone.qq.com/" + qqNum + "/myhome/friends/ofpmd";
		String checkUrl = "http://w.cnc.qzone.qq.com/cgi-bin/tfriend/friend_authfriend.cgi?g_tk=" + gtk;
		String postMethod = HttpClient4Utils.postMethod(checkUrl, checkAdd, cookie, referer);
		List<NameValuePair> addQQ = null;
		if (postMethod.indexOf("{\"state\":1}}") > 0) {
			addQQ = FriendParamUtils.addQQOfpmd(distQQ, qqNum, gtk, true);
		} else if (postMethod.indexOf("{\"state\":0}}") > 0) {
			addQQ = FriendParamUtils.addQQOfpmd(distQQ, qqNum, gtk, false);
		} else {
			//setState(distQQ);
			return false;
		}
		String addUrl = "http://w.cnc.qzone.qq.com/cgi-bin/tfriend/friend_addfriend.cgi?g_tk=" + gtk;
		String result = HttpClient4Utils.postMethod(addUrl, addQQ, cookie, referer);
		if (result.indexOf("\"code\":0") > 0) {
			//setState(distQQ);
			MyUtils.saveCookie(cookie, MyUtils.qzone, qqNum);
		} else {
			System.out.println(result + "   " + qqNum);
		}
		return true;
	}

	public int setState(String qqNum) {
		return qQNeedAddDao.update("updateStateByNum", 1);
	}
}
