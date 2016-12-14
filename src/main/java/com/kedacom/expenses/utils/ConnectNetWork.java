package com.kedacom.expenses.utils;

import java.io.InputStream;

public class ConnectNetWork {

	/**
	 * 执行CMD命令,并返回String字符串
	 */
	public static String executeCmd(String strCmd) throws Exception {
		Process p = Runtime.getRuntime().exec("cmd /c " + strCmd);
		InputStream inputStream = p.getInputStream();
		StringBuilder sbCmd = new StringBuilder();
		byte[] by = new byte[128];
		int read = inputStream.read(by);
		while (read > 0) {
			String line = new String(by, "gbk");
			sbCmd.append(line + "\n");
			read = inputStream.read(by);
		}
		return sbCmd.toString();
	}

	/**
	 * 连接ADSL
	 */
	public static boolean connAdsl(String adslTitle, String adslName, String adslPass) throws Exception {
		System.out.println("正在建立连接.");
		String adslCmd = "rasdial " + adslTitle + " " + adslName + " " + adslPass;
		System.out.println(adslCmd);
		String tempCmd = executeCmd(adslCmd);
		CommsTools.sleep(2000);
		// 判断是否连接成功
		if (tempCmd.indexOf("已连接") > 0) {
			System.out.println("已成功建立连接.");
			return true;
		} else {
			System.err.println(tempCmd);
			System.err.println("建立连接失败");
			ConnectNetWork.cutAdsl("宽带连接");
			Thread.sleep(2000);
			return false;
		}
	}

	/**
	 * 断开ADSL
	 */
	public static boolean cutAdsl(String adslTitle) throws Exception {
		String cutAdsl = "rasdial " + adslTitle + " /disconnect";
		String result = executeCmd(cutAdsl);

		if (result.indexOf("没有连接") != -1) {
			System.err.println(adslTitle + "连接不存在!");
			return false;
		} else {
			System.out.println("连接已断开");
			return true;
		}
	}

	public static void main(String[] args) throws Exception {
		cutAdsl("宽带连接");
		Thread.sleep(1000);
		// 再连，分配一个新的IP
		while (!connAdsl("宽带连接", "18962535037", "820830")) {

		}
	}
}
