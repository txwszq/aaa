package com.kedacom.expenses.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Encrypt {

	/**
	 * 新浪用户名加密算法
	 * @param username
	 * @return
	 */
	public static String sinaEncryptUserName(String username) {
		try {
			username = username.replace("@", "%40");
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			// 获取当前项目路劲 classes
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			File functionscript = new File(path + "encrypt.js");
			Reader reader = new FileReader(functionscript);

			// 开始执行ajava.js里的程序
			engine.eval(reader);

			Invocable invocableEngine = (Invocable) engine;

			// 带参数调用sayHello方法
			String un = invocableEngine.invokeFunction("encrypt", username).toString();
			return un;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * qq密码加密
	 * @param password
	 * @return
	 */
	public static String qqEncrypt(String password, String uin, String vcode) {
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			// 获取当前项目路劲 classes
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			File functionscript = new File(path + "qq_encrypt.js");
			Reader reader = new FileReader(functionscript);

			// 开始执行ajava.js里的程序
			engine.eval(reader);

			Invocable invocableEngine = (Invocable) engine;

			// 带参数调用sayHello方法
			String un = invocableEngine.invokeFunction("getEncryption", password, uin, vcode).toString();
			return un;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encodeURIComponent(String encode) {
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			// 获取当前项目路劲 classes
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			File functionscript = new File(path + "qq_encrypt.js");
			Reader reader = new FileReader(functionscript);

			// 开始执行ajava.js里的程序
			engine.eval(reader);

			Invocable invocableEngine = (Invocable) engine;

			// 带参数调用sayHello方法
			String un = invocableEngine.invokeFunction("encodeURIComponent", encode).toString();
			return un;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取gtk
	 * @param skey
	 * @return
	 */
	public static String getGtk(String skey) {
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			// 获取当前项目路劲 classes
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			File functionscript = new File(path + "qq_encrypt.js");
			Reader reader = new FileReader(functionscript);

			// 开始执行ajava.js里的程序
			engine.eval(reader);

			Invocable invocableEngine = (Invocable) engine;

			// 带参数调用sayHello方法
			String un = invocableEngine.invokeFunction("getToken", skey).toString();
			return un;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		// encodeURIComponent
		// "79DFBEF44311375C5C93CBD29324A3D6"
		// 2067580159
		// 441A665B6C16A13336160622EF7F0D18

		// String qqEncrypt = Encrypt.qqEncrypt("19881013.zzq",
		// HttpClient4Utils.changeChar("\\x00\\x00\\x00\\x00\\x17\\xa9\\x9f\\xb2"),
		// "nypu");
		// System.out.println(qqEncrypt);//715F45BE73E57400FFE5CE08C11478F6
	}
}
