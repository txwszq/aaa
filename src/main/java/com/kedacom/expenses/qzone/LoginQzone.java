package com.kedacom.expenses.qzone;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.kedacom.expenses.dama.ChaorenDama;
import com.kedacom.expenses.utils.Encrypt;
import com.kedacom.expenses.utils.HttpClient4Utils;
import com.kedacom.expenses.utils.MyUtils;
import com.kedacom.expenses.utils.ParseUtil;

public class LoginQzone {

	public static String step1 = "http://xui.ptlogin2.qq.com/cgi-bin/xlogin?proxy_url=http%3A//qzs.qq.com/qzone/v6/portal/proxy.html&daid=5&&hide_title_bar=1&low_login=0&qlogin_auto_login=1&no_verifyimg=1&link_target=blank&appid=549000912&style=22&target=self&s_url=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&pt_qr_app=%E6%89%8B%E6%9C%BAQQ%E7%A9%BA%E9%97%B4&pt_qr_link=http%3A//z.qzone.com/download.html&self_regurl=http%3A//qzs.qq.com/qzone/v6/reg/index.html&pt_qr_help_link=http%3A//z.qzone.com/download.html";

	public static String step2 = "http://check.ptlogin2.qq.com/check?regmaster=&pt_tea=2&pt_vcode=1&uin=qqNum&appid=549000912&js_ver=10184&js_type=1&login_sig=LOGINSIG&u1=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&pt_uistyle=40&r=";

//	public static String step3 = "http://ptlogin2.qq.com/login?u=qqNum&p=PWD&verifycode=VCODE&aid=549000912&u1=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&h=1&ptredirect=0&ptlang=2052&daid=5&from_ui=1&dumy=&low_login_enable=0&regmaster=&fp=loginerroralert&action=3-22-1386336049948&mibao_css=&t=1&g=1&js_ver=10059&js_type=1&login_sig=LOGINSIG&pt_rsa=0&pt_qzone_sig=1";
	public static String step3 = "http://ptlogin2.qq.com/login?u=qqNum&verifycode=VCODE&pt_vcode_v1=0&pt_verifysession_v1=3b9f13e24442959b8454aa1e395cd9e40630e30dea8522330da8c912eebe1f34004e040b36ccda137c839fbe6c80c6c489e2bffaa4b637d1&p=PWD&pt_randsalt=2&u1=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&ptredirect=0&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=2-16-1480513398088&js_ver=10184&js_type=1&login_sig=LOGINSIG&pt_uistyle=40&aid=549000912&daid=5&";

	public static void main(String[] args) {
		String qqNum = "396992434";
		String password = "19881013.zyj";
		BasicCookieStore cookie = new BasicCookieStore();
		login(qqNum, password, cookie, "");
		String url = "http://user.qzone.qq.com/" + qqNum;
		String html = HttpClient4Utils.getHtml(url, cookie);
		System.out.println(html);
	}

	/**
	 * 登录
	 * @param qqNum
	 * @param password
	 * @param cookie
	 */
	public static boolean login(String qqNum, String password, BasicCookieStore cookie, String vcode) {
		String loginResult = "";
		String returnSig = step1ReturnSig(cookie);
		String checkUrl = step2ReturnCheckUrl(returnSig, qqNum);
		String checkResult = step3CheckLogin(cookie, checkUrl);
		if (checkResult.startsWith("ptui_checkVC('0'")) {
			String loginurl = step4GetLoginUrl(cookie, checkResult, password, qqNum, returnSig);
			loginResult = HttpClient4Utils.getHtml(loginurl, cookie);
		} else {
			if (vcode != null && !"".equals(vcode)) {
				String loginurl = step4GetLoginUrl(cookie, checkResult, password, qqNum, returnSig, vcode);
				loginResult = HttpClient4Utils.getHtml(loginurl, cookie);
			} else {
				return false;
			}
		}
		System.out.println(loginResult);
		if (loginResult.startsWith("ptuiCB('0','0'")) {
			MyUtils.saveCookie(cookie, MyUtils.qzone, qqNum);
			return true;
		} else {
			return false;
		}
	}

	public static boolean login(String qqNum, String password, BasicCookieStore cookie) {
		String loginResult = "";
		String returnSig = step1ReturnSig(cookie);
		String checkUrl = step2ReturnCheckUrl(returnSig, qqNum);
		String checkResult = step3CheckLogin(cookie, checkUrl);
		String vcode = null;

		if (checkResult.startsWith("ptui_checkVC('0'")) {
			String loginurl = step4GetLoginUrl(cookie, checkResult, password, qqNum, returnSig);
			loginResult = HttpClient4Utils.getHtml(loginurl, cookie);
		} else {
			try {
				InputStream image = HttpClient4Utils.getQzoneImage(cookie, qqNum);
				RenderedImage ri = ImageIO.read(image);
				File f = new File(System.getProperty("user.dir") + "/src/main/resources/qq/qzone/" + qqNum + "/vcode/"
						+ qqNum + ".gif");
				if (!f.isDirectory()) {
					f.mkdirs();
				}
				ImageIO.write(ri, "gif", f);
				vcode = ChaorenDama.myDama(f.getPath());
				String loginurl = step4GetLoginUrl(cookie, checkResult, password, qqNum, returnSig, vcode);
				loginResult = HttpClient4Utils.getHtml(loginurl, cookie);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		System.out.println(loginResult);
		if (loginResult.startsWith("ptuiCB('0','0'")) {
			MyUtils.saveCookie(cookie, MyUtils.qzone, qqNum);
			return true;
		} else {
			ChaorenDama.error(vcode);
			return false;
		}
	}

	/**
	 * 登录第一步获取sig
	 * @param cookie
	 * @return
	 */
	public static String step1ReturnSig(BasicCookieStore cookie) {
		String html = HttpClient4Utils.getHtml(step1, cookie);
		for(Cookie c : cookie.getCookies()) {
			if("pt_login_sig".equals(c.getName())) {
				return c.getValue();
			}
		}
		
		return null;
	}

	/**
	 * 登录第二步获取检验登录的url
	 * @param sig
	 * @param qqNum
	 * @return
	 */
	public static String step2ReturnCheckUrl(String sig, String qqNum) {
		String url = step2.replace("LOGINSIG", sig).replace("qqNum", qqNum) + Math.random();
		return url;
	}

	/**
	 * 登录第三步，检验登录
	 * @param cookie
	 * @param checkUrl
	 * @return
	 */
	public static String step3CheckLogin(BasicCookieStore cookie, String checkUrl) {
		String result = HttpClient4Utils.getHtml(checkUrl, cookie);
		return result;
	}

	/**
	 * 组装登录的url
	 * @param cookie
	 * @param checkResult
	 * @param password
	 * @param qqNum
	 * @param sig
	 * @return
	 */
	public static String step4GetLoginUrl(BasicCookieStore cookie, String checkResult, String password, String qqNum,
			String sig) {
		BasicClientCookie2 ck = new BasicClientCookie2("ptui_loginuin", qqNum);
		ck.setExpiryDate(MyUtils.getDateAfterMonth());
		ck.setDomain(".qq.com");
		ck.setPath("/");
		cookie.addCookie(ck);
		Map<String, String> qqEncryptParam = ParseUtil.getQQEncryptParam(checkResult);
		String uin = HttpClient4Utils.changeChar(qqEncryptParam.get("uin"));
		String vcode = qqEncryptParam.get("vcode");
		String enpwd = Encrypt.qqEncrypt(password, uin, vcode);
		String url = step3.replace("PWD", enpwd).replace("VCODE", vcode).replace("qqNum", qqNum)
				.replace("LOGINSIG", sig);
		return url;
	}

	/**
	 * 组装登录的url
	 * @param cookie
	 * @param checkResult
	 * @param password
	 * @param qqNum
	 * @param sig
	 * @return
	 */
	public static String step4GetLoginUrl(BasicCookieStore cookie, String checkResult, String password, String qqNum,
			String sig, String vcode) {
		BasicClientCookie2 ck = new BasicClientCookie2("ptui_loginuin", qqNum);
		ck.setExpiryDate(MyUtils.getDateAfterMonth());
		ck.setDomain(".qq.com");
		ck.setPath("/");
		cookie.addCookie(ck);
		Map<String, String> qqEncryptParam = ParseUtil.getQQEncryptParam(checkResult);
		String uin = HttpClient4Utils.changeChar(qqEncryptParam.get("uin"));
		String enpwd = Encrypt.qqEncrypt(password, uin, vcode);
		String url = step3.replace("PWD", enpwd).replace("VCODE", vcode).replace("qqNum", qqNum)
				.replace("LOGINSIG", sig);
		return url;
	}

	/**
	 * 获取sig
	 * @param pageInfo
	 * @return
	 */
	private static String parseSig(String pageInfo) {
		Parser parser = null;
		try {
			parser = new Parser(pageInfo);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		NodeFilter filter = new NodeClassFilter(ScriptTag.class);
		NodeList list = null;
		try {
			list = parser.extractAllNodesThatMatch(filter);
		
		String script = ((ScriptTag) list.elementAt(0)).getStringText();
		String loginSig = HttpClient4Utils.splitString(script, "login_sig:\"", 64);
		return loginSig;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
