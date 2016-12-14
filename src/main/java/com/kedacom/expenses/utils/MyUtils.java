package com.kedacom.expenses.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class MyUtils {

	public static String respath = System.getProperty("user.dir") + "/src/main/resources/";

	public static String qzone = "qzone";
	public static String friend = "friend";

	public static String path = System.getProperty("user.dir") + "/src/main/resources/";
	public static String filepath = path + "all.txt";
	public static String donepath = path + "done.txt";

	public static String friendsListUrl = "http://mix.pengyou.com/index.php?mod=friends&act=manage&adtag=friends_nav";
	public static String friendFriendsListUrl = "http://friend.pengyou.com/index.php?mod=friends&act=tab&u=myFriendHash&adtag=VIST_FRIENDS_MORE";

	/**
	 * 删除封号的QQ
	 * @param myqqs
	 * @return
	 */
	public static List<String> removeInvaild(List<String> myqqs) {
		myqqs.remove("2497027639");
		myqqs.remove("1243150081");
		myqqs.remove("1416183846");
		myqqs.remove("2553417662");
		myqqs.remove("2289612471");
		myqqs.remove("2553417662");
		myqqs.remove("1021428003");
		myqqs.remove("2213038082");
		/* 后面的30个QQ */
		myqqs.remove("2591205317");
		myqqs.remove("2388540432");
		myqqs.remove("2569510586");
		myqqs.remove("2591802654");
		myqqs.remove("2476780756");
		myqqs.remove("2591085331");
		myqqs.remove("2590055150");
		myqqs.remove("2311715259");
		myqqs.remove("1353352354");
		myqqs.remove("2475195711");
		myqqs.remove("2476051238");
		myqqs.remove("2590965891");
		myqqs.remove("2628738216");
		myqqs.remove("2590929533");
		myqqs.remove("2590171969");
		myqqs.remove("2591314986");
		myqqs.remove("2590267366");
		myqqs.remove("2591273363");
		myqqs.remove("2592523315");
		myqqs.remove("2592852186");
		myqqs.remove("2476876535");
		myqqs.remove("2592668834");
		myqqs.remove("2495122112");
		myqqs.remove("2592065510");
		myqqs.remove("2590796569");
		myqqs.remove("2695456878");
		myqqs.remove("2219598384");
		myqqs.remove("2592987577");
		myqqs.remove("2592127808");
		myqqs.remove("2409803030");
		return myqqs;
	}

	/**
	 * 删除重复项
	 * @param filepath
	 * @throws IOException
	 */
	public static void removeRepeat(String filepath) throws IOException {
		File file = new File(filepath);
		FileUtils.copyFile(file, new File(filepath + ".bak"));
		List<String> list = FileUtils.readLines(file);
		Set<String> set = new HashSet<String>();
		set.addAll(list);
		FileUtils.writeLines(file, set);
	}

	/**
	 * 删除一完成的数据
	 * @param srcList
	 * @param removeList
	 * @return
	 */
	public static List<String> removeDone(List<String> srcList, List<String> removeList) {
		Set<String> set = new HashSet<String>();
		set.addAll(srcList);
		for (String string : removeList) {
			set.remove(string);
		}
		List<String> list = new ArrayList<String>();
		list.addAll(set);
		return list;
	}

	/**
	 * 获取好友列表
	 * @param qqNum
	 * @return
	 */
	public static List<String> getMyFriendsList(String url, String qqNum) {
		try {
			BasicCookieStore cookie = HttpClient4Utils.readObject(qqNum);
			String html = HttpClient4Utils.getHtml(url, cookie);
			String json = "";
			if (url.equals(friendsListUrl)) {
				json = pareHtml(html);
			} else if (url.startsWith("http://friend.pengyou.com/index.php")) {
				json = parseFriendFriendsHtml(html);
			}
			return stringJsonToList(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取好友数量
	 * @param qqNum
	 * @return
	 */
	public static int getFirendsCount(String qqNum) {
		List<String> myFriendsList = getMyFriendsList(friendsListUrl, qqNum);
		return myFriendsList.size();
	}

	/**
	 * 解析html
	 * @param html
	 * @return
	 */
	private static String pareHtml(String html) {
		Parser parser = null;
		try {
			parser = new Parser(html);
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
		// for (int i = 0; i < list.size(); i++) {
		// String script = ((ScriptTag) list.elementAt(i)).getStringText();
		// System.out.println(script);
		// System.out.println("--------------------------------------"+i);
		// }

		String indexOfStr = "'friends_list': ";
		String script = ((ScriptTag) list.elementAt(6)).getStringText();
		int indexOf = script.indexOf("}}", script.indexOf(indexOfStr) + indexOfStr.length());
		int subIndexOf = script.indexOf(indexOfStr) + indexOfStr.length();
		int length = indexOf - subIndexOf + 2;
		String json = HttpClient4Utils.splitString(script, "'friends_list': ", length);
		return json;
	}

	/**
	 * 解析html
	 * @param html
	 * @return
	 */
	private static String parseFriendFriendsHtml(String html) {
		Parser parser = null;
		try {
			parser = new Parser(html);
		} catch (ParserException e1) {
			e1.printStackTrace();
			return "{}";
		}
		NodeFilter filter = new NodeClassFilter(ScriptTag.class);
		NodeList list = null;
		try {
			list = parser.extractAllNodesThatMatch(filter);
		} catch (ParserException e) {
			e.printStackTrace();
			return "{}";
		}
		// for (int i = 0; i < list.size(); i++) {
		// String script = ((ScriptTag) list.elementAt(i)).getStringText();
		// System.out.println(script);
		// System.out.println("--------------------------------------"+i);
		// }

		String indexOfStr = "var friendList = ";
		String script = ((ScriptTag) list.elementAt(5)).getStringText();
		int indexOf = script.indexOf("}}", script.indexOf(indexOfStr) + indexOfStr.length());
		int subIndexOf = script.indexOf(indexOfStr) + indexOfStr.length();
		int length = indexOf - subIndexOf + 2;
		String json = HttpClient4Utils.splitString(script, indexOfStr, length);
		return json;
	}

	/**
	 * 把json数据转换成list
	 * @param json
	 * @return list中存放的好友的hashcode
	 */
	private static List<String> stringJsonToList(String json) {
		List<String> list = new ArrayList<String>();
		JSONObject obj = new JSONObject();
		JSONObject jsonObj = obj.fromObject(json);
		Set entrySet = jsonObj.entrySet();
		for (Iterator iterator = entrySet.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			list.add(object.toString().split("=")[0]);
		}
		return list;
	}

	/**
	 * 获取QQ号
	 * @return
	 */
	public static List<String> getMyqqs() {
		try {
			return FileUtils.readLines(new File(System.getProperty("user.dir") + "/src/main/resources/myqq.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> getMyqqs(String filePath) {
		try {
			return FileUtils.readLines(new File(path + filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取gtk
	 * @param qqNum
	 * @return
	 */
	public static String getGtk(String qqNum) {
		try {
			BasicCookieStore cookie = HttpClient4Utils.readObject(qqNum);
			String gtk = EncryptUtils.getGtk(HttpClient4Utils.getCookieValue("skey", cookie));
			return gtk;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getGtk(BasicCookieStore cookie, String qqNum) {
		try {
			String gtk = EncryptUtils.getGtk(HttpClient4Utils.getCookieValue("skey", cookie));
			return gtk;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取gtk
	 * @param qqNum
	 * @return
	 */
	public static String getQzoneGtk(String qqNum) {
		try {
			BasicCookieStore cookie = getCookie(qqNum, qzone);
			String gtk = QQEncryptUtils.getQzoneGtk(HttpClient4Utils.getCookieValue("skey", cookie));
			return gtk;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取cookie
	 * @param qqNum
	 * @return
	 */
	public static BasicCookieStore getCookie(String qqNum) {
		try {
			BasicCookieStore readObject = HttpClient4Utils.readObject(qqNum);
			// List<Cookie> cookies = readObject.getCookies();
			// for (Cookie cookie : cookies) {
			// BasicClientCookie bc = (BasicClientCookie) cookie;
			// bc.setExpiryDate(new Date("Thu Jan 02 08:00:00 CST 2020"));
			// }
			return readObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取cookie
	 * @param qqNum
	 * @return
	 */
	public static BasicCookieStore getCookie(String qqNum, String type) {
		try {
			BasicCookieStore readObject = HttpClient4Utils.readObject(qqNum, type);
			return readObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取MyHashCode
	 * @param qqNum
	 * @return
	 */
	public static String getMyHashCode(String qqNum) {
		try {
			BasicCookieStore readObject = HttpClient4Utils.readObject(qqNum);
			String myHash = HttpClient4Utils.getMyHash(readObject);
			return myHash;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把内容处理一下，目的是为了防止重复
	 * @param content
	 * @return
	 */
	public static String handle(String content) {
		int i = (int) ((Math.random() * 18) + (Math.random() * 17));
		String handle = "";
		for (int j = 0; j < i; j++) {
			handle += "`";
		}
		return handle + content;
	}

	public static void main(String[] args) {
		// testRemoveDone();
		// getMyFriendsList("396992434");
		// testRemoveRepeat();
		System.out.println(getDateAfterMonth());
	}

	public static void testRemoveRepeat() {

		try {
			removeRepeat(filepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testRemoveDone() {
		try {
			List<String> readLines = FileUtils.readLines(new File(filepath));
			System.out.println("readLines:" + readLines.size());
			List<String> doneLines = FileUtils.readLines(new File(donepath));
			System.out.println("doneLines:" + doneLines.size() + " - " + (readLines.size() - doneLines.size()));
			List<String> removeDone = removeDone(readLines, doneLines);
			System.out.println("removeDone:" + removeDone.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 打印cookie信息
	 * @param cookie
	 */
	public static void printCookie(BasicCookieStore cookie) {
		List<Cookie> cookies = cookie.getCookies();
		for (Cookie ck : cookies) {
			System.out.println(ck.getName() + " -- " + ck.getValue() + " -- " + ck.getExpiryDate());
		}
	}

	/**
	 * 获取当前一个月后的时间
	 * @return
	 */
	public static Date getDateAfterMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1); // 得到后一个月
		return calendar.getTime();
	}

	/**
	 * 获取当前一年后的时间
	 * @return
	 */
	public static Date getDateAfterYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, 1);
		return calendar.getTime();
	}

	public static void saveCookie(BasicCookieStore cookie, String type, String qqNum) {
		File f = null;
		if (type.equals(qzone)) {
			f = new File(System.getProperty("user.dir") + "/src/main/resources/qq/qzone/" + qqNum + "/user.bin");
		} else {
			f = new File(System.getProperty("user.dir") + "/src/main/resources/qq/" + qqNum + "/user.bin");
		}
		try {
			HttpClient4Utils.writeObject(cookie, f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取访问QQList
	 * @return
	 */
	public static List<String> getVistQQList() {
		try {
			List<String> readLines = FileUtils.readLines(new File(path + "visit/visitQQList.txt"));
			return readLines;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把数据转换成list map
	 * @param list
	 * @return
	 */
	public static Map<String, String> getListMap(List<String> list) {
		Map<String, String> map = new HashMap<String, String>();
		for (String line : list) {
			String[] split = line.split(":");
			map.put(split[0], split[1]);
		}
		return map;
	}
}
