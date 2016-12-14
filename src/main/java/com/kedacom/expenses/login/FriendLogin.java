package com.kedacom.expenses.login;

import org.apache.http.impl.client.BasicCookieStore;

import com.kedacom.expenses.utils.HttpClient4Utils;

public class FriendLogin {

	public static void main(String[] args) {

		String qqNum = "396992434";
		String password = "19881013.zzq";
		Boolean login = login(qqNum, password);
		System.out.println(login);
	}

	public static Boolean login(String qqNum, String password) {
		BasicCookieStore cookie = new BasicCookieStore();
		boolean login = HttpClient4Utils.loginFriend(cookie, qqNum, password);
		return login;
	}
}
