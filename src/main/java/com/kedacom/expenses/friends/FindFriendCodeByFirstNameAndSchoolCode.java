package com.kedacom.expenses.friends;

import java.io.File;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.BasicCookieStore;

import com.kedacom.core.utils.ApplicationUtil;
import com.kedacom.expenses.login.FriendLogin;
import com.kedacom.expenses.model.qq.FriendCodeBak;
import com.kedacom.expenses.model.qq.QQNumManage;
import com.kedacom.expenses.service.qq.FriendCodeBakService;
import com.kedacom.expenses.service.qq.QQNumManageService;
import com.kedacom.expenses.utils.HttpClient4Utils;
import com.kedacom.expenses.utils.MyUtils;

public class FindFriendCodeByFirstNameAndSchoolCode {

	private static String searchUrl = "http://search.pengyou.com/json.php";

	private static FriendCodeBakService friendCodeService = (FriendCodeBakService) ApplicationUtil
			.getBean(FriendCodeBakService.class);

	private QQNumManageService qQNumManageService = (QQNumManageService) ApplicationUtil
			.getBean(QQNumManageService.class);

	public void searchFriends() {
		try {
			// 学校code
			List<String> schoolcodes = FileUtils.readLines(new File(System.getProperty("user.dir")
					+ "/src/main/resources/school/schoolCode.txt"), "utf-8");
			File doneFile = new File(System.getProperty("user.dir") + "/src/main/resources/school/done.txt");
			List<String> done = FileUtils.readLines(doneFile, "utf-8");
			schoolcodes.removeAll(done);

			// 姓
			String firstNameList = FileUtils.readFileToString(new File(System.getProperty("user.dir")
					+ "/src/main/resources/firstName.txt"));
			String[] splitStr = firstNameList.split(",");

			int j = 0;
			List<QQNumManage> allByState = qQNumManageService.getAllByState(1);
			for (String schoolcode : schoolcodes) {
				for (int k = 2008; k <= 2010; k++) {
					String qqNum = "";
					String pwd = "";
					for (String name : splitStr) {
						if (j >= allByState.size()) {
							j = 0;
						}
						qqNum = allByState.get(j).getQq_num();
						pwd = allByState.get(j).getQq_num();
						j++;
						BasicCookieStore cookie = MyUtils.getCookie(qqNum);
						HttpClient4Utils.getHtml("http://home.pengyou.com/index.php", cookie);
						boolean loginSuccess = HttpClient4Utils.loginSuccess(cookie, qqNum);
						if (loginSuccess) {
							MyUtils.saveCookie(cookie, MyUtils.friend, qqNum);
						} else {
							FriendLogin.login(qqNum, pwd);
							cookie = MyUtils.getCookie(qqNum);
						}
						String gtk = MyUtils.getGtk(qqNum);
						for (int i = 1; i <= 21; i++) {
							try {
								String referer = "http://search.pengyou.com/index.php";
								List<NameValuePair> findFriendsByFirstName = FriendParamUtils.getSearchParam(i + "",
										1 + "", schoolcode, gtk, name, k + "");
								String postMethod = HttpClient4Utils.postMethod(searchUrl, findFriendsByFirstName,
										cookie, referer);
								System.out.println("-" + postMethod);
								if (postMethod.startsWith("{\"code\":0")) {
									int totalPage = totalPage(postMethod);
									parseResult(postMethod, schoolcode);
									if (i >= Math.ceil(totalPage / 14)) {
										break;
									}
								}
								Thread.sleep(3000);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				FileUtils.write(doneFile, schoolcode + "\r\n", true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// String qqNum = "2387442524";
		// searchFriends(qqNum);
	}

	/**
	 * 把结果写到文件中
	 * @param json
	 * @param schoolid
	 */
	public static synchronized void parseResult(String json, String filename) {
		JSONObject object = new JSONObject();
		JSONArray jsonArray = object.fromObject(json).getJSONObject("result").getJSONArray("users");
		for (int i = 0; i < jsonArray.size(); i++) {
			try {
				String hashCode = (String) ((JSONObject) jsonArray.get(i)).get("hash");

				FriendCodeBak friendCode = friendCodeService.getFriendCode(hashCode);
				if (friendCode == null) {
					FriendCodeBak fc = new FriendCodeBak();
					fc.setHash_code(hashCode);
					fc.setState(0l);
					friendCodeService.add(fc);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static int totalPage(String json) {
		JSONObject object = new JSONObject();
		int total = object.fromObject(json).getJSONObject("result").getInt("total");
		return total;
	}
}
