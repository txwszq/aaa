package com.kedacom.expenses.qzone;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.impl.client.BasicCookieStore;

import com.kedacom.expenses.friends.FriendParamUtils;
import com.kedacom.expenses.login.QzoneLogin;
import com.kedacom.expenses.utils.HttpClient4Utils;
import com.kedacom.expenses.utils.MyUtils;

public class SendShuoShuo {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String qqNum = "2890108250";
		String password = "5210364897";
		String content = "test";
		String url = " http://www.baidu.com";
		String picUrl = "http://img01.taobaocdn.com/bao/uploaded/i1/T1lQeIFsxbXXXXXXXX_!!0-item_pic.jpg_460x460.jpg";
		String html = "<div class=\"blog_details_20120222\"><div><br><a title=\"" + url + "\" href=\"" + url + "\">"
				+ url + "</a><br><br><img style=\"width:460px;height:460px\" alt=\"图片\" src=\"" + picUrl
				+ "\">&nbsp;</div></div>";
		// sendShuoshuo(qqNum, password, content, picUrl);
		sendLog(qqNum, password, "2014新潮T恤", html);
	}

	/**
	 * 发送说说
	 * @param qqNum
	 * @param password
	 * @param content
	 * @param picUrl
	 */
	public static void sendShuoshuo(String qqNum, String password, String content, String picUrl) {
		boolean login = QzoneLogin.login(qqNum, password);
		if (login) {
			System.out.println("登录成功");
		} else {
			System.out.println("登录失败");
			return;
		}
		BasicCookieStore cookie = MyUtils.getCookie(qqNum, MyUtils.qzone);
		String gtk = MyUtils.getQzoneGtk(qqNum);

		// String urlInfo = getUrlInfo(url, gtk,cookie);

		// String json = replaceHead(urlInfo);
		// JSONObject jsonObj = (JSONObject)ParseJsonUtils.getValue(json,
		// "data");
		// String picUrl =
		// "http://b398.photo.store.qq.com/psb?/V1319Y9616rv40/5ZQ.dXQ*vCPfNx0kIk73zksszWPrbSTwOLOUbsvfyAg!/b/dNNaSe3BEQAA&bo=gAJVAwAAAAABAPM!";//
		// jsonObj.getString("pics");
		// System.out.println(picUrl);

		try {
			List<NameValuePair> shuoshuoParam = FriendParamUtils.shuoshuoParam(content, qqNum, picUrl);
			String postUrl = "http://user.qzone.qq.com/q/taotao/cgi-bin/emotion_cgi_publish_v6?g_tk=" + gtk;
			String postMethod = HttpClient4Utils.postMethod(postUrl, shuoshuoParam, cookie, "http://user.qzone.qq.com/"
					+ qqNum + "/311");
			System.out.println(postMethod);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 获取请求地址返回的数据，包含图片URL
	 * @param url
	 * @param gtk
	 * @param cookie
	 * @return
	 */
	public static String getUrlInfo(String url, String gtk, BasicCookieStore cookie) {
		try {
			String encode = java.net.URLEncoder.encode(url, "UTF-8");

			String getUrl = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshareget_urlinfo?url=" + encode
					+ "&fupdate=1&g_tk=" + gtk;
			String referer = "http://ctc.qzs.qq.com/qzone/app/mood_v6/html/index.html";

			String html = HttpClient4Utils.getHtml(getUrl, cookie, referer);
			return html;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String replaceHead(String str) {
		str = str.replace("_Callback(", "").replace(");", "");
		return str;
	}

	public static void sendLog(String qqNum, String password, String title, String content) {
		BasicCookieStore cookie = MyUtils.getCookie(qqNum, MyUtils.qzone);
		String html = HttpClient4Utils.getHtml("http://user.qzone.qq.com/" + qqNum, cookie);
		boolean loginSuccess = HttpClient4Utils.qzoneLoginSuccess(html, qqNum);
		if (loginSuccess) {
			MyUtils.saveCookie(cookie, MyUtils.qzone, qqNum);
		} else {
			System.out.println("空间登录失败：" + qqNum);
			Boolean login = QzoneLogin.login(qqNum, password);
			if (login) {
				System.out.println(qqNum + " 空间登录成功");
				// MyUtils.saveCookie(cookie, MyUtils.qzone, qqNum);
			} else {
				System.out.println(qqNum + " 空间登录失败");
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String gtk = MyUtils.getQzoneGtk(qqNum);

		List<NameValuePair> logParam = FriendParamUtils.sendLog(title, content, qqNum, gtk);
		String postUrl = "http://b11.qzone.qq.com/cgi-bin/blognew/add_blog?g_tk=" + gtk;
		String postMethod = HttpClient4Utils.postMethod(postUrl, logParam, cookie,
				"http://ctc.qzs.qq.com/qzone/newblog/v5/editor.html");
		System.out.println(postMethod);
	}
}
