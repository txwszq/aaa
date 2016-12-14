package com.kedacom.expenses.login;

import org.apache.http.impl.client.BasicCookieStore;

import com.kedacom.expenses.qzone.LoginQzone;

public class QzoneLogin {

	public static void main(String[] args) {

		String qqNum = "1416183846";
		String password = "60607038asd";
		Boolean login = login(qqNum, password);
		System.out.println(login);
	}

	public static Boolean login(String qqNum, String password) {
		BasicCookieStore cookie = new BasicCookieStore();
		boolean login = LoginQzone.login(qqNum, password, cookie);
		return login;
	}
}
