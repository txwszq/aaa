package com.kedacom.expenses.qzone;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.impl.client.BasicCookieStore;

import com.kedacom.expenses.friends.FriendParamUtils;
import com.kedacom.expenses.login.QzoneLogin;
import com.kedacom.expenses.utils.HttpClient4Utils;
import com.kedacom.expenses.utils.MyUtils;

public class ZhuanFaShuoShuo {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String qqNum = "2387442524";
		String destQQ = "487634234";
		String password = "60607038asd";
		String  tid = "3ab5101dd173d05300420100";
		zhuanfa(qqNum, password, tid, destQQ);
	}

	
	public static void zhuanfa(String qqNum, String password, String tid, String destQQ) {
		
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

		List<NameValuePair> logParam = FriendParamUtils.zhuanfaShuoShuo(destQQ, tid, qqNum);
		String postUrl = "http://taotao.qq.com/cgi-bin/emotion_cgi_forward_v6?g_tk=" + gtk;
		String postMethod = HttpClient4Utils
				.postMethod(postUrl, logParam, cookie, "http://user.qzone.qq.com/" + destQQ);
		System.out.println(postMethod);
	}
}
