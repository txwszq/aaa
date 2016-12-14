package com.kedacom.expenses.utils;

import java.util.HashMap;
import java.util.Map;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class ParseUtil {

	/**
	 * 获取pdetail
	 * @param content
	 * @return
	 */
	public static String getPdetail(String content) {
		String pdetail = "";
		String key = "$CONFIG['page_id']='";
		Parser parser = null;
		try {
			parser = new Parser(content);
		} catch (ParserException e1) {
			e1.printStackTrace();
		}
		NodeFilter filter = new NodeClassFilter(ScriptTag.class);
		NodeList list = null;
		try {
			list = parser.extractAllNodesThatMatch(filter);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {
			ScriptTag script = (ScriptTag) list.elementAt(i);
			String childrenHTML = script.getChildrenHTML();
			if (childrenHTML.indexOf(key) >= 0) {
				for (String line : childrenHTML.split(";")) {
					if (line.indexOf(key) >= 0) {
						pdetail = line.replace(key, "").replace("'", "");
					}
				}
			}
		}
		return pdetail;
	}

	/**
	 * 获取uid
	 * @param content
	 * @return
	 */
	public static String getUid(String content) {
		String uid = "";
		String key = "$CONFIG['uid']='";
		Parser parser = null;
		try {
			parser = new Parser(content);
		} catch (ParserException e1) {
			e1.printStackTrace();
		}
		NodeFilter filter = new NodeClassFilter(ScriptTag.class);
		NodeList list = null;
		try {
			list = parser.extractAllNodesThatMatch(filter);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {
			ScriptTag script = (ScriptTag) list.elementAt(i);
			String childrenHTML = script.getChildrenHTML();
			if (childrenHTML.indexOf(key) >= 0) {
				for (String line : childrenHTML.split(";")) {
					if (line.indexOf(key) >= 0) {
						uid = line.replace(key, "").replace("'", "");
					}
				}
			}
		}
		return uid;
	}

	/**
	 * 获取location
	 * @param content
	 * @return
	 */
	public static String getLocation(String content) {
		String location = "";
		String key = "$CONFIG['location']='";
		Parser parser = null;
		try {
			parser = new Parser(content);
		} catch (ParserException e1) {
			e1.printStackTrace();
		}
		NodeFilter filter = new NodeClassFilter(ScriptTag.class);
		NodeList list = null;
		try {
			list = parser.extractAllNodesThatMatch(filter);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {
			ScriptTag script = (ScriptTag) list.elementAt(i);
			String childrenHTML = script.getChildrenHTML();
			if (childrenHTML.indexOf(key) >= 0) {
				for (String line : childrenHTML.split(";")) {
					if (line.indexOf(key) >= 0) {
						location = line.replace(key, "").replace("'", "");
					}
				}
			}
		}
		return location;
	}

	/**
	 * 获取微博主题的mid
	 * @param cookie
	 * @param url
	 * @return
	 */
	public static String getMid(String content) {
		String mid = "";
		Parser parser = null;
		try {
			parser = new Parser(content);
		} catch (ParserException e1) {
			e1.printStackTrace();
		}
		NodeFilter filter = new NodeClassFilter(ScriptTag.class);
		NodeList list = null;
		try {
			list = parser.extractAllNodesThatMatch(filter);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {
			ScriptTag script = (ScriptTag) list.elementAt(i);
			String childrenHTML = script.getChildrenHTML();
			if (childrenHTML.indexOf("WB_detail") > 0) {
				mid = childrenHTML.substring(childrenHTML.indexOf("mid=\\\"") + 6,
						childrenHTML.indexOf("mid=\\\"") + 22);
			}
		}
		return mid;
	}

	/*
	 * public static void getFansUser(String url,String cookie){ Weibo weibo =
	 * new Weibo(); for (int i = 1; i < 11; i++) { try { GetMethod getMethod =
	 * new GetMethod(url+"?page="+i); String returnPage =
	 * weibo.getReturnPage(getMethod, cookie); returnPage =
	 * returnPage.replaceAll("\\r\\n", "").replace("\\t", ""); returnPage =
	 * returnPage.replaceAll("\\\\", ""); Parser parser = null; try { parser =
	 * new Parser(returnPage); } catch (ParserException e1) {
	 * e1.printStackTrace(); } NodeFilter filter = new
	 * NodeClassFilter(Div.class); NodeList list = null; try { list =
	 * parser.extractAllNodesThatMatch(filter); } catch (ParserException e) {
	 * e.printStackTrace(); } for (int j = 0; j < list.size(); j++) { Div div =
	 * (Div) list.elementAt(j);
	 * if("con_left".equals(div.getAttribute("class"))){ NodeList children =
	 * div.getChildren(); Div userNameDiv = (Div)children.elementAt(1); LinkTag
	 * usnLink = (LinkTag)userNameDiv.getChild(1); String username =
	 * usnLink.getStringText(); int fans = 0; System.out.println(username); Div
	 * fansCountDiv = (Div)children.elementAt(3); NodeList nodeList =
	 * fansCountDiv.getChildren(); SimpleNodeIterator it = nodeList.elements();
	 * if(it != null){ while(it.hasMoreNodes()){ Node node = it.nextNode();
	 * if(node instanceof LinkTag){ LinkTag fansLink = (LinkTag)node;
	 * if(fansLink.getAttribute("href").indexOf("fans") > 0){ fans =
	 * Integer.parseInt(fansLink.getStringText()); } } } }
	 * MyFileUtils.writeFile(fans, username); } } } catch (Exception e) {
	 * e.printStackTrace(); } } try { System.out.println(url);
	 * FileUtils.write(new
	 * File(Thread.currentThread().getContextClassLoader().getResource
	 * ("").getPath()+"fansUrl-1.txt"), url+"\r\n", true); } catch (IOException
	 * e) { e.printStackTrace(); } }
	 */

	/**
	 * 获取QQ登录时加密的参数
	 * @param content
	 * @return
	 */
	public static Map<String, String> getQQEncryptParam(String content) {
		Map<String, String> map = new HashMap<String, String>();
		String replace = content.replace("ptui_checkVC(", "").replace(");", "").replaceAll("'", "");
		System.out.println(replace);
		String[] split = replace.split(",");
		map.put("result", split[0]);
		map.put("vcode", split[1]);
		map.put("uin", split[2].replaceAll("\\\\", "\\\\\\\\"));
		return map;
	}
}
