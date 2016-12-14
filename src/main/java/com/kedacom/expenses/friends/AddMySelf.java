package com.kedacom.expenses.friends;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;

import com.kedacom.expenses.utils.HttpClient4Utils;
import com.kedacom.expenses.utils.MyUtils;

public class AddMySelf {

	private static String addFriendUrl = "http://api.pengyou.com/json.php?g_tk=gtk&mod=friends&act=apply";

	public static Boolean add(CookieStore cookie, String gtk) {
		String url = addFriendUrl.replace("gtk", gtk);
		List<NameValuePair> addFriendParam = FriendParamUtils2.addFriendParam(
				"c265e4bd629300c5163bad3fafe701ee3317c49fe00f70cc", gtk);
		String postMethod = HttpClient4Utils.postMethod(url, addFriendParam, cookie);
		System.out.println(postMethod);
		if (postMethod.startsWith("{\"code\":0\"")) {
			return true;
		} else {
			return false;
		}

	}

	public static void main(String[] args) {
		List<String> myqqs = MyUtils.getMyqqs();
		// String[] myqqs = {"1243150081"};
		for (String myqq : myqqs) {
			CookieStore cookie = MyUtils.getCookie(myqq);
			String gtk = MyUtils.getGtk(myqq);
			Boolean add = add(cookie, gtk);
			if (add) {
				try {
					Thread.sleep(1000 * 60 * 3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
