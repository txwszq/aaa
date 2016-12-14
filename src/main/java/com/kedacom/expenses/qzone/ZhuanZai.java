package com.kedacom.expenses.qzone;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.impl.client.BasicCookieStore;

import com.kedacom.expenses.friends.FriendParamUtils;
import com.kedacom.expenses.login.QzoneLogin;
import com.kedacom.expenses.utils.HttpClient4Utils;
import com.kedacom.expenses.utils.MyUtils;

public class ZhuanZai {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String qqNum = "2387442524";
		String destQQ = "487634234";
		String password = "60607038asd";
		String blogid = "1401432659";
		zhuanzaiLog(qqNum, password, blogid, destQQ);
	}

	public static void zhuanzaiLog(String qqNum, String password, String blogid, String destQQ) {
		
		BasicCookieStore cookie = MyUtils.getCookie(qqNum, MyUtils.qzone);
		String html = HttpClient4Utils.getHtml("http://user.qzone.qq.com/" + qqNum, cookie);
		boolean loginSuccess = HttpClient4Utils.qzoneLoginSuccess(html, qqNum);
		if (loginSuccess) {
			MyUtils.saveCookie(cookie, MyUtils.qzone, qqNum);
		} else {
			boolean login = QzoneLogin.login(qqNum, password);
			if (login) {
				System.out.println("登录成功");
				 cookie = MyUtils.getCookie(qqNum, MyUtils.qzone);
			} else {
				System.out.println("登录失败");
				return;
			}
			
		}
	
		String gtk = MyUtils.getQzoneGtk(qqNum);

		List<NameValuePair> logParam = FriendParamUtils.zhuanZaiLog(destQQ, blogid, qqNum, gtk);
		String postUrl = "http://b1.cnc.qzone.qq.com/cgi-bin/blognew/quote_blog?g_tk=" + gtk;
		String postMethod = HttpClient4Utils
				.postMethod(postUrl, logParam, cookie, "http://user.qzone.qq.com/" + destQQ);
		System.out.println(postMethod);
	}
}
