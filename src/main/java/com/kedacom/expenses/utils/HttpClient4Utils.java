package com.kedacom.expenses.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.apache.http.util.EntityUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.kedacom.core.utils.ApplicationUtil;
import com.kedacom.expenses.dama.ChaorenDama;
import com.kedacom.expenses.model.qq.QQNumManage;
import com.kedacom.expenses.service.qq.QQNumManageService;

public class HttpClient4Utils {

	private static String friendLoginUrl = "http://ui.ptlogin2.pengyou.com/cgi-bin/login?appid=15004601&qlogin_jumpname=&hide_title_bar=1&s_url=http://www.pengyou.com/index.php%3fmod%3Dlogin2%26act%3Dqqlogin&self_regurl=http://reg.pengyou.com/emailreg.html&css=http://imgcache.qq.com/ptcss/b2/pengyou/15000901/login_page.css";
	private static String checkLoginUrl = "http://check.ptlogin2.pengyou.com/check?uin=username&appid=15004601&js_ver=10050&js_type=0&login_sig=loginSig&u1=http%3A%2F%2Fwww.pengyou.com%2Findex.php%3Fmod%3Dlogin2%26act%3Dqqlogin&r=";
	private static String doFriendLoginUrl = "http://ptlogin2.pengyou.com/login?u=username&p=password&verifycode=vcode&aid=15004601&u1=http%3A%2F%2Fwww.pengyou.com%2Findex.php%3Fmod%3Dlogin2%26act%3Dqqlogin&h=1&ptredirect=1&ptlang=2052&from_ui=1&dumy=&fp=loginerroralert&action=4-27-33914&mibao_css=&t=1&g=1&js_type=0&js_ver=10050&login_sig=loginSig";
	private static String validateUrl = "http://captcha.pengyou.com/getimage?aid=15004601&r=0.3774592921864939&uin=username";
	private static String friendsNavUrl = "http://friend.pengyou.com/index.php?mod=friends&act=tab&u=myHashCode&adtag=bhcard";
	private static String qzoneValidateUrl = "http://captcha.qq.com/getimage?uin=qqNum&aid=549000912&";

	private static String PROXY_ADD = "202.171.253.98";
	private static Integer PROXY_PORT = 80;

	/**
	 * get方式请求页面
	 * @param URL
	 * @param cookie
	 * @return
	 */
	public static String getHtml(String URL, CookieStore cookie) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// 注意！此处是DefaultHttpClient,而非HttpClient
		// httpClient.getCredentialsProvider().setCredentials(new
		// AuthScope("195.141.3.178", 8080), new
		// UsernamePasswordCredentials("username", "password"));
		// 使用抢先认证
		HttpHost proxy = new HttpHost(PROXY_ADD, PROXY_PORT);
		// httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
		// proxy);
		HttpGet request = new HttpGet(URL);
		request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:12.0) Gecko/20100101 Firefox/12.0");
		if (cookie != null) {
			httpClient.setCookieStore(cookie);
		}
		try {
			HttpResponse httpResponse = httpClient.execute(request);
			String res = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			cookie = httpClient.getCookieStore();

			httpClient.getConnectionManager().shutdown();
//			request.abort();
			return (res);
		} catch (IOException e) {
			return (e.toString());
		} catch (ParseException e) {
			return (e.toString());
		}
	}

	/**
	 * get方式请求页面
	 * @param URL
	 * @param cookie
	 * @return
	 */
	public static String getHtml(String URL, CookieStore cookie, String referer) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// 注意！此处是DefaultHttpClient,而非HttpClient
		HttpGet request = new HttpGet(URL);
		if (cookie != null) {
			httpClient.setCookieStore(cookie);
		}
		try {
			HttpResponse httpResponse = httpClient.execute(request);
			request.setHeader("Referer", referer);

			String res = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			cookie = httpClient.getCookieStore();

			httpClient.getConnectionManager().shutdown();
			return (res);
		} catch (IOException e) {
			return (e.toString());
		} catch (ParseException e) {
			return (e.toString());
		}
	}

	/**
	 * 上传文件
	 * @param gtk
	 * @param cookie
	 * @param file
	 */
	public static synchronized String uploadFile(String gtk, CookieStore cookie, File file, String uin) {
		String content = null;
		boolean flag = true;
		String[] uploadUrls = {"http://hzup.photo.pengyou.com/cgi-bin/upload/cgi_upload_image?g_tk=gtk",
				"http://up.photo.pengyou.com/cgi-bin/upload/cgi_upload_image?g_tk=gtk",
				"http://xaup.photo.pengyou.com/cgi-bin/upload/cgi_upload_image?g_tk=gtk",
				"http://gzup.photo.pengyou.com/cgi-bin/upload/cgi_upload_image?g_tk=gtk",
				"http://shup.photo.pengyou.com/cgi-bin/upload/cgi_upload_image?g_tk=gtk"};
		int i = 0;
		do {
			if (i > 4) {
				break;
			}
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(uploadUrls[i++].replace("gtk", gtk));
			if (cookie != null) {
				client.setCookieStore(cookie);
			}
			HttpResponse response = null;
			try {
				MultipartEntity multipartEntity = new MultipartEntity();
				FileBody cbFileBody = new FileBody(file);
				multipartEntity.addPart("refer", new StringBody("shuoshuo"));
				multipartEntity.addPart("domain", new StringBody("pengyou"));
				multipartEntity.addPart("charset", new StringBody("utf-8"));
				multipartEntity.addPart("uin", new StringBody(uin));
				multipartEntity.addPart("uploadtype", new StringBody("1"));
				multipartEntity.addPart("albumtype", new StringBody("2"));
				multipartEntity.addPart("exttype", new StringBody("1"));
				multipartEntity.addPart("watertype", new StringBody("1"));
				multipartEntity.addPart("output_type", new StringBody("jsonhtml"));
				multipartEntity.addPart("filename", cbFileBody);

				httppost.setEntity(multipartEntity);

				response = client.execute(httppost);

				content = EntityUtils.toString(response.getEntity(), "GBK");
				flag = content.contains("error");
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			content = getRichval(content);
			client.getConnectionManager().shutdown();
		} while (flag);
		return content;
	}

	/**
	 * 上传文件
	 * @param gtk
	 * @param cookie
	 * @param file
	 * @param uin qq号码
	 * @return
	 */
	public static synchronized Map<String, String> uploadFile2(String gtk, CookieStore cookie, File file, String uin) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String content = "";
		boolean flag = true;
		String[] uploadUrls = {"http://hzup.photo.pengyou.com/cgi-bin/upload/cgi_upload_image?g_tk=gtk",
				"http://up.photo.pengyou.com/cgi-bin/upload/cgi_upload_image?g_tk=gtk",
				"http://xaup.photo.pengyou.com/cgi-bin/upload/cgi_upload_image?g_tk=gtk",
				"http://gzup.photo.pengyou.com/cgi-bin/upload/cgi_upload_image?g_tk=gtk",
				"http://shup.photo.pengyou.com/cgi-bin/upload/cgi_upload_image?g_tk=gtk"};
		int i = 0;
		do {
			if (i > 4) {
				break;
			}
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(uploadUrls[i++].replace("gtk", gtk));
			if (cookie != null) {
				client.setCookieStore(cookie);
			}
			HttpResponse response = null;
			try {
				MultipartEntity multipartEntity = new MultipartEntity();
				FileBody cbFileBody = new FileBody(file);
				multipartEntity.addPart("refer", new StringBody("shuoshuo"));
				multipartEntity.addPart("domain", new StringBody("pengyou"));
				multipartEntity.addPart("charset", new StringBody("utf-8"));
				multipartEntity.addPart("uin", new StringBody(uin));
				multipartEntity.addPart("uploadtype", new StringBody("1"));
				multipartEntity.addPart("albumtype", new StringBody("2"));
				multipartEntity.addPart("exttype", new StringBody("1"));
				multipartEntity.addPart("watertype", new StringBody("1"));
				multipartEntity.addPart("output_type", new StringBody("jsonhtml"));
				multipartEntity.addPart("filename", cbFileBody);

				httppost.setEntity(multipartEntity);

				response = client.execute(httppost);

				content = EntityUtils.toString(response.getEntity(), "GBK");
				flag = content.contains("error");
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			resultMap = getRichvalReturnMap(content);
			client.getConnectionManager().shutdown();
		} while (flag);
		return resultMap;
	}

	/**
	 * 上传文件的关联关系加上
	 * @param content
	 * @return
	 */
	private static String getRichval(String content) {
		String handle = content
				.replace(
						"<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\"/></head><body><script type=\"text/javascript\">document.domain=\"pengyou.com\";frameElement._Callback(",
						"").replace(");</script></body></html>", "");
		JSONObject object = (JSONObject) new JSONObject().fromObject(handle).get("data");
		String albumid = (String) object.get("albumid");
		String lloc = (String) object.get("lloc");
		Integer height = (Integer) object.get("height");
		String sloc = (String) object.get("sloc");
		Integer width = (Integer) object.get("width");
		Integer type = (Integer) object.get("type");
		return (albumid + "," + lloc + "," + sloc + "," + type + "," + height + "," + width);
	}

	/**
	 * 上传文件的关联关系加上
	 * @param content
	 * @return
	 */
	private static Map<String, String> getRichvalReturnMap(String content) {
		Map<String, String> map = new HashMap<String, String>();
		String handle = content
				.replace(
						"<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\"/></head><body><script type=\"text/javascript\">document.domain=\"pengyou.com\";frameElement._Callback(",
						"").replace(");</script></body></html>", "");
		JSONObject object = (JSONObject) new JSONObject().fromObject(handle).get("data");
		String albumid = (String) object.get("albumid");
		String sloc = (String) object.get("sloc");
		Integer totalpic = (Integer) object.get("totalpic");
		map.put("albumid", albumid);
		map.put("sloc", sloc);
		map.put("totalpic", totalpic + "");
		return map;
	}

	/**
	 * 获取我的hashcode
	 * @param cookie
	 * @return
	 */
	public static String getMyHash(BasicCookieStore cookie) {
		String pageInfo = HttpClient4Utils.getHtml("http://www.pengyou.com/index.php?mod=userinfo&act=nethome", cookie);
		String hash = null;
		Parser parser = null;
		try {
			parser = new Parser(pageInfo);
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
		String script = ((ScriptTag) list.elementAt(2)).getStringText();
		hash = splitString(script, ",\"hash\":\"", 48);
		return hash;
	}

	/**
	 * 获取页面login_sig参数
	 * @param pageInfo
	 * @return
	 */
	public static String getFriendLoginSig(String pageInfo) {
		Parser parser = null;
		try {
			parser = new Parser(pageInfo);
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
		String script = ((ScriptTag) list.elementAt(1)).getStringText();
		String loginSig = splitString(script, "var g_login_sig=encodeURIComponent(\"", 64);
		return loginSig;
	}

	/**
	 * 截取符串
	 * @param srcString
	 * @param key
	 * @param length
	 * @return
	 */
	public static String splitString(String srcString, String key, Integer length) {
		try {
			int indexOf = srcString.indexOf(key);
			int beginPostion = indexOf + key.length();
			return srcString.substring(beginPostion, beginPostion + length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 把\x1221\x1212 格式的字符串换成String
	 * @param str
	 * @return
	 */
	public static String changeChar(String str) {
		String[] ss = StringUtils.split(str, "\\x");
		byte[] bs = new byte[ss.length];

		for (int i = 0; i < ss.length; i++) {
			bs[i] = (byte) Integer.parseInt(ss[i], 16);
		}
		try {
			return new String(bs, "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 系列化
	 * @param o
	 * @throws Exception
	 */
	public static void writeObject(Object o, File f) throws Exception {
		if (!f.isDirectory()) {
			f.mkdirs();
		}
		if (f.exists()) {
			f.delete();
		}
		FileOutputStream os = new FileOutputStream(f);
		// ObjectOutputStream 核心类
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(o);
		oos.close();
		os.close();
	}

	/**
	 * 反序列化
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static BasicCookieStore readObject(String username, String type) throws Exception {
		File f = null;
		if (type.equals(MyUtils.qzone)) {
			f = new File(System.getProperty("user.dir") + "/src/main/resources/qq/qzone/" + username + "/user.bin");
		} else if (type.equals(MyUtils.friend)) {
			f = new File(System.getProperty("user.dir") + "/src/main/resources/qq/" + username + "/user.bin");
		}
		if (!f.exists()) {
			return new BasicCookieStore();
		}
		InputStream is = new FileInputStream(f);
		// ObjectOutputStream 核心类
		ObjectInputStream ois = new ObjectInputStream(is);
		return (BasicCookieStore) ois.readObject();
	}

	/**
	 * 反序列化
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static BasicCookieStore readObject(String username) throws Exception {
		File f = new File(System.getProperty("user.dir") + "/src/main/resources/qq/" + username + "/user.bin");
		if (!f.exists()) {
			return new BasicCookieStore();
		}
		InputStream is = new FileInputStream(f);
		// ObjectOutputStream 核心类
		ObjectInputStream ois = new ObjectInputStream(is);
		return (BasicCookieStore) ois.readObject();
	}

	/**
	 * post方式请求页面
	 * @param params
	 * @return
	 */
	public static String postMethod(String postUrl, List<NameValuePair> params, CookieStore cookie) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(postUrl);
		if (cookie != null) {
			client.setCookieStore(cookie);
		}
		try {
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();// 设置请求和传输超时时间
			httppost.setConfig(requestConfig);
			httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:12.0) Gecko/20100101 Firefox/12.0");
			// Post请求
			// 设置参数
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			// 发送请求
			HttpResponse httpresponse = client.execute(httppost);
			HttpEntity entity = httpresponse.getEntity();
			String body = EntityUtils.toString(entity);
			return body;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
		return "";
	}

	/**
	 * post方式请求页面
	 * @param params
	 * @return
	 */
	public static String postMethod(String postUrl, List<NameValuePair> params, CookieStore cookie, String referer) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpHost proxy = new HttpHost(PROXY_ADD, PROXY_PORT);
		// client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
		// proxy);
		HttpPost httppost = new HttpPost(postUrl);
		if (cookie != null) {
			client.setCookieStore(cookie);
		}
		try {
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();// 设置请求和传输超时时间
			httppost.setConfig(requestConfig);
			httppost.setHeader("Referer", referer);
			httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:12.0) Gecko/20100101 Firefox/12.0");
			// Post请求
			// 设置参数
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			// 发送请求
			HttpResponse httpresponse = client.execute(httppost);
			HttpEntity entity = httpresponse.getEntity();
			String body = EntityUtils.toString(entity);
			httppost.abort();
			return body;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
		return "";
	}

	/**
	 * 登录朋友网
	 * @param cookie
	 * @param username
	 * @param password
	 * @param vcode
	 * @return
	 */
	public static boolean loginFriend(BasicCookieStore cookie, String username, String password, String vcode) {
		String uin = null;
		String enpwd = null;
		String loginSig = null;
		String datetime = new Date().getTime() + "";
		// 第一次登录请求
		if (vcode == null) {
			// 访问登录页面
			String getHtml = HttpClient4Utils.getHtml(friendLoginUrl, cookie);
			// 获取login_sig
			loginSig = getFriendLoginSig(getHtml);
			// 检验是否需要验证码
			String checkurl = checkLoginUrl.replace("username", username).replace("loginSig", loginSig) + Math.random();
			BasicClientCookie2 ck = new BasicClientCookie2("chkuin", username);
			ck.setExpiryDate(new Date("Thu Jan 02 08:00:00 CST 2020"));
			ck.setDomain("ptlogin2.pengyou.com");
			ck.setPath("/");
			cookie.addCookie(ck);
			String checkResult = HttpClient4Utils.getHtml(checkurl, cookie);
			// 获取检验登录的返回值
			Map<String, String> qqEncryptParam = ParseUtil.getQQEncryptParam(checkResult);
			String result = qqEncryptParam.get("result");
			uin = changeChar(qqEncryptParam.get("uin"));
			vcode = qqEncryptParam.get("vcode");
			// 1表示需要验证码
			if (result.equals("1")) {
				// 把uin loginSig datetime 序列化起来
				setCookieValue("uin", uin, cookie);
				setCookieValue("loginSig", loginSig, cookie);
				setCookieValue("datetime", datetime, cookie);
				try {
					writeObject(cookie, new File(System.getProperty("user.dir") + "/src/main/resources/qq/" + username
							+ "/user.bin"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}
		} else {
			// 把uin loginSig datetime 反序列化出来
			uin = getCookieValue("uin", cookie);
			loginSig = getCookieValue("loginSig", cookie);
			datetime = getCookieValue("datetime", cookie);
		}
		enpwd = EncryptUtils.qqEncrypt(password, uin, vcode);
		String loginurl = doFriendLoginUrl.replace("username", username).replace("loginSig", loginSig)
				.replace("datetime", datetime).replace("password", enpwd).replace("vcode", vcode);
		String html = HttpClient4Utils.getHtml(loginurl, cookie);
		System.out.println(html);
		try {
			writeObject(cookie, new File(System.getProperty("user.dir") + "/src/main/resources/qq/" + username
					+ "/user.bin"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 登录朋友网
	 * @param cookie
	 * @param username
	 * @param password
	 * @param vcode
	 * @return
	 */
	public static boolean loginFriend(BasicCookieStore cookie, String username, String password) {
		String uin = null;
		String enpwd = null;
		String loginSig = null;
		String datetime = new Date().getTime() + "";
		String vcode = null;
		// 访问登录页面
		String getHtml = HttpClient4Utils.getHtml(friendLoginUrl, cookie);
		// 获取login_sig
		loginSig = getFriendLoginSig(getHtml);
		// 检验是否需要验证码
		String checkurl = checkLoginUrl.replace("username", username).replace("loginSig", loginSig) + Math.random();
		BasicClientCookie2 ck = new BasicClientCookie2("chkuin", username);
		ck.setExpiryDate(new Date("Thu Jan 02 08:00:00 CST 2020"));
		ck.setDomain("ptlogin2.pengyou.com");
		ck.setPath("/");
		cookie.addCookie(ck);
		String checkResult = HttpClient4Utils.getHtml(checkurl, cookie);
		// 获取检验登录的返回值
		Map<String, String> qqEncryptParam = ParseUtil.getQQEncryptParam(checkResult);
		String result = qqEncryptParam.get("result");
		uin = changeChar(qqEncryptParam.get("uin"));
		vcode = qqEncryptParam.get("vcode");
		// 1表示需要验证码
		if (result.equals("1")) {
			try {
				InputStream image = HttpClient4Utils.getImage(cookie, username);
				// RenderedImage ri = ImageIO.read(image);
				// String filePath = System.getProperty("user.dir") +
				// "/src/main/resources/qq/" + username + "/vcode/"
				// + username + ".gif";
				// File f = new File(filePath);
				// if (!f.isDirectory()) {
				// f.mkdirs();
				// }
				// ImageIO.write(ri, "gif", f);
				byte[] btImg = HttpClient4Utils.readInputStream(image);
				String fileName = System.getProperty("user.dir") + "/src/main/resources/qq/" + username + "/vcode/"
						+ username + ".gif";
				HttpClient4Utils.writeImageToDiskByFriends(btImg, username);
				vcode = ChaorenDama.myDama(fileName);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		// 把uin loginSig datetime 反序列化出来
		// uin = getCookieValue("uin", cookie);
		// loginSig = getCookieValue("loginSig", cookie);
		// datetime = getCookieValue("datetime", cookie);

		enpwd = EncryptUtils.qqEncrypt(password, uin, vcode);
		String loginurl = doFriendLoginUrl.replace("username", username).replace("loginSig", loginSig)
				.replace("datetime", datetime).replace("password", enpwd).replace("vcode", vcode);
		String html = HttpClient4Utils.getHtml(loginurl, cookie);
		System.out.println(html);
		if (html.startsWith("ptuiCB('0','0'")) {
			try {
				writeObject(cookie, new File(System.getProperty("user.dir") + "/src/main/resources/qq/" + username
						+ "/user.bin"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else if (html.startsWith("ptuiCB('19','0'")) {
			QQNumManageService qqNumManageService = (QQNumManageService) ApplicationUtil
					.getBean(QQNumManageService.class);
			QQNumManage entity = qqNumManageService.getAllByQQNum(username);
			entity.setFriend_is_normal(2l);
			qqNumManageService.update(entity);
			return false;
		} else {
			ChaorenDama.DC.INSTANCE.ReportError("txwszq", vcode);
			return false;
		}

	}

	/**
	 * 获取cookie中的值
	 * @param name
	 * @param cookie
	 * @return
	 */
	public static String getCookieValue(String name, BasicCookieStore cookie) {
		List<Cookie> cookies = cookie.getCookies();
		String value = "";
		for (Cookie ck : cookies) {
			if (name.equals(ck.getName())) {
				return ck.getValue();
			}
		}
		return value;
	}

	/**
	 * 把值放入cookie中
	 * @param name
	 * @param cookie
	 * @return
	 */
	public static void setCookieValue(String key, String value, BasicCookieStore cookie) {
		BasicClientCookie2 ck = new BasicClientCookie2(key, value);
		cookie.addCookie(ck);
	}

	/**
	 * 检验登录是否成功
	 * @param cookie
	 * @return
	 */
	public static boolean loginSuccess(BasicCookieStore cookie) {
		String html = HttpClient4Utils.getHtml("http://www.pengyou.com/index.php?mod=userinfo&act=nethome", cookie);
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
			String script = ((ScriptTag) list.elementAt(2)).getStringText();
			if (script != null && script.indexOf("wp._CUSER = {") > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean loginSuccess(BasicCookieStore cookie, String qqNum) {
		String html = HttpClient4Utils.getHtml("http://www.pengyou.com/index.php", cookie);
		if (html.indexOf("此帐号已被封禁") > 0) {
			System.out.println("此帐号已被封禁:" + qqNum);
			QQNumManageService qqNumManageService = (QQNumManageService) ApplicationUtil
					.getBean(QQNumManageService.class);
			QQNumManage entity = qqNumManageService.getAllByQQNum(qqNum);
			entity.setFriend_is_normal(0l);
			qqNumManageService.update(entity);
			return true;
		}
		if (html.indexOf("退出") > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 检查QQ空间登录是否成功
	 * @param html 空间主页返回的信息 http://user.qzone.qq.com/396992434
	 * @param qqNum qq号码
	 * @return
	 */
	public static boolean qzoneLoginSuccess(String html, String qqNum) {
		String checkStr = "与我相关";
		if (html.indexOf(checkStr) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取好友数量
	 * @param cookie
	 * @param myHashCode
	 * @return
	 */
	public static Integer getFriends(BasicCookieStore cookie, String myHashCode) {
		String url = friendsNavUrl.replace("myHashCode", myHashCode);
		String html = getHtml(url, cookie);
		Parser parser = null;
		try {
			parser = new Parser(html);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		NodeFilter filter = new NodeClassFilter(ScriptTag.class);
		NodeList list = null;
		try {
			list = parser.extractAllNodesThatMatch(filter);
			ScriptTag tag = (ScriptTag) list.elementAt(7);
			String[] split = tag.getStringText().split(";");
			String json = split[0].replace("var friendList = ", "").trim();
			JSONObject jo = new JSONObject();
			JSONObject fromObject = jo.fromObject(json);
			Set entrySet = fromObject.entrySet();
			return entrySet.size();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 获取验证码
	 * @param cookie
	 * @return
	 */
	public static InputStream getImage(BasicCookieStore cookie, String username) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// 注意！此处是DefaultHttpClient,而非HttpClient
		HttpGet request = new HttpGet(validateUrl.replace("username", username));
		if (cookie != null) {
			httpClient.setCookieStore(cookie);
		}
		try {
			HttpResponse httpResponse = httpClient.execute(request);
			InputStream content = httpResponse.getEntity().getContent();
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取验证码
	 * @param cookie
	 * @return
	 */
	public static InputStream getImage(String url, BasicCookieStore cookie) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpHost proxy = new HttpHost(PROXY_ADD, PROXY_PORT);
		// httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
		// proxy);
		// 注意！此处是DefaultHttpClient,而非HttpClient
		HttpGet request = new HttpGet(url);
		request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:12.0) Gecko/20100101 Firefox/12.0");
		if (cookie != null) {
			httpClient.setCookieStore(cookie);
		}
		try {
			HttpResponse httpResponse = httpClient.execute(request);
			InputStream content = httpResponse.getEntity().getContent();
			BasicClientCookie2 ck = new BasicClientCookie2("uoc", RandomUtils.nextInt(100) + "-"
					+ RandomUtils.nextInt(99) + "-" + RandomUtils.nextInt(99) + "-" + RandomUtils.nextInt(99) + "-"
					+ RandomUtils.nextInt(99) + "-" + RandomUtils.nextInt(99) + "-" + RandomUtils.nextInt(99) + "-"
					+ RandomUtils.nextInt(99));
			ck.setDomain("zc.qq.com");
			ck.setPath("/");
			cookie.addCookie(ck);
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * QQ空间验证码
	 * @param cookie
	 * @param qqNum
	 * @return
	 */
	public static InputStream getQzoneImage(BasicCookieStore cookie, String qqNum) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// 注意！此处是DefaultHttpClient,而非HttpClient
		HttpGet request = new HttpGet(qzoneValidateUrl.replace("qqNum", qqNum) + Math.random());
		if (cookie != null) {
			httpClient.setCookieStore(cookie);
		}
		try {
			HttpResponse httpResponse = httpClient.execute(request);
			InputStream content = httpResponse.getEntity().getContent();
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	public static String writeImageToDisk(byte[] img, String qqNum) {
		try {
			File path = new File(System.getProperty("user.dir") + "/src/main/resources/qq/qzone/" + qqNum + "/vcode/");
			if (!path.isDirectory()) {
				path.mkdirs();
			}
			File file = new File(path.getPath() + "/" + qqNum + ".gif");
			FileOutputStream fops = new FileOutputStream(file);
			fops.write(img);
			fops.flush();
			fops.close();
			return file.getPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String writeImageToDiskByFriends(byte[] img, String qqNum) {
		try {
			File path = new File(System.getProperty("user.dir") + "/src/main/resources/qq/" + qqNum + "/vcode/");
			if (!path.isDirectory()) {
				path.mkdirs();
			}
			File file = new File(path.getPath() + "/" + qqNum + ".gif");
			FileOutputStream fops = new FileOutputStream(file);
			fops.write(img);
			fops.flush();
			fops.close();
			return file.getPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String username = "396992434";
		String password = "19881013.zzq";

		BasicCookieStore cookie = new BasicCookieStore();
		String getHtml = HttpClient4Utils.getHtml(friendLoginUrl, cookie);
		String loginSig = getFriendLoginSig(getHtml);
		String checkurl = checkLoginUrl.replace("username", username).replace("loginSig", loginSig) + Math.random();
		BasicClientCookie2 ck = new BasicClientCookie2("chkuin", username);
		ck.setDomain("ptlogin2.pengyou.com");
		ck.setPath("/");
		cookie.addCookie(ck);
		String checkResult = HttpClient4Utils.getHtml(checkurl, cookie);
		System.out.println(checkResult);

		Map<String, String> qqEncryptParam = ParseUtil.getQQEncryptParam(checkResult);
		String result = qqEncryptParam.get("result");
		String uin = changeChar(qqEncryptParam.get("uin"));
		String vcode = qqEncryptParam.get("vcode");
		String enpwd = EncryptUtils.qqEncrypt(password, uin, vcode);

		String datetime = new Date().getTime() + "";
		String loginurl = doFriendLoginUrl.replace("username", username).replace("loginSig", loginSig)
				.replace("datetime", datetime).replace("password", enpwd).replace("vcode", vcode);
		System.out.println("doFriendLoginUrl : " + loginurl);
		String loginResult = HttpClient4Utils.getHtml(loginurl, cookie);
		System.out.println(loginResult);
		System.out.println("after login cookie: " + cookie);

		loginResult = HttpClient4Utils.getHtml("http://www.pengyou.com/index.php?mod=login2&act=qqlogin", cookie);
		System.out.println(loginResult);
		System.out.println("after :" + cookie);

		// loginResult =
		// HttpClient4Utils.getHtml("http://home.pengyou.com/index.php?mod=home",
		// cookie);
		// System.out.println(loginResult);
		// System.out.println("after2 :" + cookie);
	}
}
