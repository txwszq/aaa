package com.kedacom.expenses.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class QQEncryptUtils {

	public static String getUi() throws FileNotFoundException, ScriptException, NoSuchMethodException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		// 获取当前项目路劲 classes
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		File functionscript = new File(path + "qq_encrypt.js");
		Reader reader = new FileReader(functionscript);
		// 开始执行ajava.js里的程序
		engine.eval(reader);
		Invocable invocableEngine = (Invocable) engine;
		String un = invocableEngine.invokeFunction("getUi").toString();
		return un;
	}

	public static void main(String[] args) {
		try {
			// gtk = QQEncrypt.getUi();
			String pwd = "60607038asd";
			String enpwd = QQEncryptUtils.getEncryptPwd(pwd, "myRsaEncrypt");
			System.out.println(enpwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getQzoneGtk(String skey) {
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
			String un = invocableEngine.invokeFunction("getQzoneGtk", skey).toString();
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

	public static String getEncryptPwd(String pwd, String method) {
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			// 获取当前项目路劲 classes
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			File functionscript = new File(path + "regqq.js");
			Reader reader = new FileReader(functionscript);

			// 开始执行ajava.js里的程序
			engine.eval(reader);

			Invocable invocableEngine = (Invocable) engine;

			// 带参数调用sayHello方法
			String un = invocableEngine.invokeFunction(method, pwd).toString();
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
}
