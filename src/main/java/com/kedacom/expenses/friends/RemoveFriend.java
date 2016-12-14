package com.kedacom.expenses.friends;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;

import com.kedacom.core.utils.ApplicationUtil;
import com.kedacom.expenses.model.qq.QQNumManage;
import com.kedacom.expenses.service.qq.FriendCodeService;
import com.kedacom.expenses.utils.HttpClient4Utils;
import com.kedacom.expenses.utils.MyUtils;

public class RemoveFriend {

	private static String breakFriendUrl = "http://api.pengyou.com/json.php";

	private static FriendCodeService fcs = (FriendCodeService) ApplicationUtil.getBean(FriendCodeService.class);

	/**
	 * 处理所有好友，删除重复的好友
	 * @param myqqs
	 * @return
	 */
	public static List<String> handleAllFriends(List<String> myqqs) {
		List<String> allFriendsList = new ArrayList<String>();
		for (String qqNum : myqqs) {
			List<String> myFriendsList = MyUtils.getMyFriendsList(MyUtils.friendsListUrl, qqNum);
			System.out.println(qqNum + " : " + myFriendsList.size());
			allFriendsList.addAll(myFriendsList);
			String gtk = MyUtils.getGtk(qqNum);
			CookieStore cookie;
			try {
				cookie = HttpClient4Utils.readObject(qqNum);
				for (String hashCode : myFriendsList) {
					breakFriend(qqNum, gtk, hashCode, cookie);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return allFriendsList;
	}

	public static List<String> delReapetFriends(List<QQNumManage> myqqs) {
		List<String> allFriendsList = new ArrayList<String>();
		for (QQNumManage qq : myqqs) {
			try {
				String qqNum = qq.getQq_num();
				List<String> myFriendsList = MyUtils.getMyFriendsList(MyUtils.friendsListUrl, qqNum);
				FileUtils.writeLines(new File("C:/f.txt"), myFriendsList, true);
				System.out.println(qqNum + " : " + myFriendsList.size());
				allFriendsList.addAll(myFriendsList);
				String gtk = MyUtils.getGtk(qqNum);
				CookieStore cookie;
				cookie = HttpClient4Utils.readObject(qqNum);
				// for (String hashCode : myFriendsList) {
				// FriendCode friendCode = fcs.getFriendCode(hashCode);
				// if (null != friendCode) {
				// friendCode.setState(1l);
				// }
				// breakFriend(qqNum, gtk, hashCode, cookie);
				// }
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return allFriendsList;
	}

	/**
	 * 删除好友数量大于250的好友
	 * @param qqNum
	 */
	public static void deleteMoreThan250(List<String> myqqs) {
		String url = MyUtils.friendFriendsListUrl;
		int i = 1;
		for (String qqNum : myqqs) {
			List<String> myFriendsList = MyUtils.getMyFriendsList(MyUtils.friendsListUrl, qqNum);
			for (String myFriendHash : myFriendsList) {
				String firendListUrl = url.replace("myFriendHash", myFriendHash);
				List<String> friendFriendsList = MyUtils.getMyFriendsList(firendListUrl, qqNum);
				System.out.println((i++) + " - " + myFriendHash + " : " + friendFriendsList.size());
				if (friendFriendsList.size() > 250) {
					String gtk = MyUtils.getGtk(qqNum);
					CookieStore cookie = MyUtils.getCookie(qqNum);
					List<NameValuePair> breakFriendParam = FriendParamUtils.breakFriendParam(gtk, myFriendHash, qqNum);
					String postMethod = HttpClient4Utils.postMethod(breakFriendUrl, breakFriendParam, cookie);
					System.out.println("删除好友：" + myFriendHash + " - " + postMethod);
				}
			}
		}
	}

	/**
	 * 删除10个好友
	 * @param qqNum
	 */
	public static void deleteTenFriends(String qqNum) {
		String url = MyUtils.friendFriendsListUrl;
		int i = 1;
		List<String> myFriendsList = MyUtils.getMyFriendsList(MyUtils.friendsListUrl, qqNum);
		String gtk = MyUtils.getGtk(qqNum);
		CookieStore cookie = MyUtils.getCookie(qqNum);
		for (String myFriendHash : myFriendsList) {
			String firendListUrl = url.replace("myFriendHash", myFriendHash);
			List<String> friendFriendsList = MyUtils.getMyFriendsList(firendListUrl, qqNum);
			if (i > 10) {
				break;
			}
			if (null != friendFriendsList) {
				System.out.println(i + " - " + myFriendHash + " : " + friendFriendsList.size());
			} else {
				List<NameValuePair> breakFriendParam = FriendParamUtils.breakFriendParam(gtk, myFriendHash, qqNum);
				String postMethod = HttpClient4Utils.postMethod(breakFriendUrl, breakFriendParam, cookie);
				System.out.println(friendFriendsList.size() + " - 删除好友：" + myFriendHash + " - " + postMethod);
				i++;
				continue;
			}

			if (friendFriendsList.size() > 250) {
				List<NameValuePair> breakFriendParam = FriendParamUtils.breakFriendParam(gtk, myFriendHash, qqNum);
				String postMethod = HttpClient4Utils.postMethod(breakFriendUrl, breakFriendParam, cookie);
				System.out.println(friendFriendsList.size() + " - 删除好友：" + myFriendHash + " - " + postMethod);
				i++;
			} else if (friendFriendsList.size() < 10) {
				List<NameValuePair> breakFriendParam = FriendParamUtils.breakFriendParam(gtk, myFriendHash, qqNum);
				String postMethod = HttpClient4Utils.postMethod(breakFriendUrl, breakFriendParam, cookie);
				System.out.println(friendFriendsList.size() + " - 删除好友：" + myFriendHash + " - " + postMethod);
				i++;
			} else if (friendFriendsList.size() > 200) {
				List<NameValuePair> breakFriendParam = FriendParamUtils.breakFriendParam(gtk, myFriendHash, qqNum);
				String postMethod = HttpClient4Utils.postMethod(breakFriendUrl, breakFriendParam, cookie);
				System.out.println(friendFriendsList.size() + " - 删除好友：" + myFriendHash + " - " + postMethod);
				i++;
			} else if (friendFriendsList.size() > 180) {
				List<NameValuePair> breakFriendParam = FriendParamUtils.breakFriendParam(gtk, myFriendHash, qqNum);
				String postMethod = HttpClient4Utils.postMethod(breakFriendUrl, breakFriendParam, cookie);
				System.out.println(friendFriendsList.size() + " - 删除好友：" + myFriendHash + " - " + postMethod);
				i++;
			} else if (friendFriendsList.size() > 150) {
				List<NameValuePair> breakFriendParam = FriendParamUtils.breakFriendParam(gtk, myFriendHash, qqNum);
				String postMethod = HttpClient4Utils.postMethod(breakFriendUrl, breakFriendParam, cookie);
				System.out.println(friendFriendsList.size() + " - 删除好友：" + myFriendHash + " - " + postMethod);
				i++;
			} else if (friendFriendsList.size() > 120) {
				List<NameValuePair> breakFriendParam = FriendParamUtils.breakFriendParam(gtk, myFriendHash, qqNum);
				String postMethod = HttpClient4Utils.postMethod(breakFriendUrl, breakFriendParam, cookie);
				System.out.println(friendFriendsList.size() + " - 删除好友：" + myFriendHash + " - " + postMethod);
				i++;
			} else if (friendFriendsList.size() > 100) {
				List<NameValuePair> breakFriendParam = FriendParamUtils.breakFriendParam(gtk, myFriendHash, qqNum);
				String postMethod = HttpClient4Utils.postMethod(breakFriendUrl, breakFriendParam, cookie);
				System.out.println(friendFriendsList.size() + " - 删除好友：" + myFriendHash + " - " + postMethod);
				i++;
			}
		}
	}

	/**
	 * 检查是否有重复
	 * @param myqqs
	 * @return
	 */
	public static List<String> checkAllFriends(List<String> myqqs) {
		List<String> allFriendsList = new ArrayList<String>();
		for (String qqNum : myqqs) {
			List<String> myFriendsList = MyUtils.getMyFriendsList(MyUtils.friendsListUrl, qqNum);
			System.out.println(qqNum + " : " + myFriendsList.size());
			allFriendsList.addAll(myFriendsList);
		}
		return allFriendsList;
	}

	/**
	 * 删除好友的功能方法
	 * @param qq
	 * @param gtk
	 * @param hashCode
	 */
	public static void breakFriend(String qq, String gtk, String hashCode, CookieStore cookie) {
		try {
			File file = new File(System.getProperty("user.dir") + "/src/main/resources/myFriendList.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			List<String> friendsHashCodeList = FileUtils.readLines(file);
			if ((!"c265e4bd629300c5163bad3fafe701ee3317c49fe00f70cc".equals(hashCode))
					&& friendsHashCodeList.contains(hashCode)) {

				List<NameValuePair> breakFriendParam = FriendParamUtils.breakFriendParam(gtk, hashCode, qq);
				String postMethod = HttpClient4Utils.postMethod(breakFriendUrl, breakFriendParam, cookie);
				System.out.println("删除好友：" + hashCode + " - " + postMethod);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				FileUtils.write(new File(System.getProperty("user.dir") + "/src/main/resources/myFriendList.txt"),
						hashCode + "\r\n", true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			// File file = new File(System.getProperty("user.dir") +
			// "/src/main/resources/myFriendList.txt");
			// file.delete();
			// List<String> qqs = MyUtils.getMyqqs();
			// List<String> myqqs = MyUtils.removeInvaild(qqs);
			// List<String> allFriends = checkAllFriends(myqqs);
			// List<String> allFriends = handleAllFriends(myqqs);
			// System.out.println(allFriends.size());
			// Set<String> set = new HashSet<String>();
			// set.addAll(allFriends);
			// System.out.println(allFriends.size() + " - " + set.size()
			// +" = "+(allFriends.size() - set.size()));
			// deleteMoreThan250(myqqs);
			// deleteTenFriends("1021428003");
			String qq = "2475431386----aa12345";
			String[] split = qq.split("----");
			System.out.println(split.length);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
