package com.kedacom.expenses.friends;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class FriendParamUtils2 {

	/**
	 * 获得查找朋友的参数
	 * @param page 第几页
	 * @param gender 性别
	 * @param university 大学
	 * @return
	 */
	public static List<NameValuePair> getSearchParam(String page, String gender, String university, String gtk,
			String name, String year) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mod", "search"));
		params.add(new BasicNameValuePair("act", "people"));
		params.add(new BasicNameValuePair("page", page));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("gender", gender));
		// params.add(new BasicNameValuePair("age", "3"));
		params.add(new BasicNameValuePair("entry", "504"));
		params.add(new BasicNameValuePair("university[0][]", university));
		if (year != null) params.add(new BasicNameValuePair("university[0][]", year));
		params.add(new BasicNameValuePair("g_tk", gtk));
		return params;
	}

	/**
	 * 按照姓来搜索
	 * @param page
	 * @param gender
	 * @param gtk
	 * @param name
	 * @return
	 */
	public static List<NameValuePair> findFriendsByFirstName(String page, String gender, String gtk, String name,
			String location, String age) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mod", "search"));
		params.add(new BasicNameValuePair("act", "people"));
		params.add(new BasicNameValuePair("page", page));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("gender", gender));
		params.add(new BasicNameValuePair("age", age));
		params.add(new BasicNameValuePair("location", location));
		params.add(new BasicNameValuePair("entry", "" + RandomUtils.nextInt(600)));
		params.add(new BasicNameValuePair("g_tk", gtk));
		return params;
	}

	/**
	 * 获取分享的参数
	 * @param entryuin 点击分享按钮后，返回的页面信息中有 【 g_iPuin = xxxx】
	 * @param summary 分享征文
	 * @param url
	 * @param gtk
	 * @param pics 图片地址
	 * @return
	 */
	public static List<NameValuePair> getShareParam(String entryuin, String title, String summary, String url,
			String gtk, String pics) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("where", "1"));
		params.add(new BasicNameValuePair("entryuin", entryuin));
		params.add(new BasicNameValuePair("spaceuin", entryuin));
		params.add(new BasicNameValuePair("title", title));
		params.add(new BasicNameValuePair("summary", summary));
		params.add(new BasicNameValuePair("token", gtk));
		params.add(new BasicNameValuePair("sendparam", ""));
		params.add(new BasicNameValuePair("description", ""));
		params.add(new BasicNameValuePair("type", "4"));
		params.add(new BasicNameValuePair("url", url));
		params.add(new BasicNameValuePair("site", ""));
		params.add(new BasicNameValuePair("site", ""));
		params.add(new BasicNameValuePair("share2weibo", "0"));
		params.add(new BasicNameValuePair("pics", pics));
		params.add(new BasicNameValuePair("fupdate", "1"));
		params.add(new BasicNameValuePair("notice", "1"));
		return params;
	}

	/**
	 * 添加好友参数
	 * @param hashCode 对方的hashcode
	 * @param gtk 我的skey加密后的值
	 * @return
	 */
	public static List<NameValuePair> addFriendParam(String hashCode, String gtk) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mod", "friends"));
		params.add(new BasicNameValuePair("act", "apply"));
		params.add(new BasicNameValuePair("u", hashCode));
		params.add(new BasicNameValuePair("valid_input", ""));
		params.add(new BasicNameValuePair("from", "friends_find"));
		params.add(new BasicNameValuePair("g_tk", gtk));
		return params;
	}

	/**
	 * 批量添加好友
	 * @param hashCode 对方的hashcode
	 * @param gtk 我的skey加密后的值
	 * @return
	 */
	public static List<NameValuePair> addFriendsParam(String hashCode, String gtk) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mod", "friends"));
		params.add(new BasicNameValuePair("act", "applypatch"));
		params.add(new BasicNameValuePair("userhash_list", hashCode));
		params.add(new BasicNameValuePair("from", "recommend_athome"));
		params.add(new BasicNameValuePair("g_tk", gtk));
		return params;
	}

	/**
	 * 聊天参数
	 * @param hashCode 对方的hashcode
	 * @param gtk 我的skey加密后的值
	 * @return
	 */
	public static List<NameValuePair> imParam(String hashCode, String gtk, String content) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mod", "chat"));
		params.add(new BasicNameValuePair("act", "c2c_send"));
		params.add(new BasicNameValuePair("target_hash", hashCode));
		params.add(new BasicNameValuePair("message", "[{\"type\":\"text\",\"content\":\"" + content + "\"}]"));
		params.add(new BasicNameValuePair("sign", "543467880"));
		params.add(new BasicNameValuePair("g_tk", gtk));
		return params;
	}

	/**
	 * 发表内容 不带图片
	 * @param content 发表的内容
	 * @param myHashCode 我的hashcode
	 * @return
	 */
	public static List<NameValuePair> sendContent(String content, String myHashCode) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("plattype", "2"));
		params.add(new BasicNameValuePair("format", "json"));
		params.add(new BasicNameValuePair("con", content));
		params.add(new BasicNameValuePair("feedversion", "1"));
		params.add(new BasicNameValuePair("ver", "1"));
		params.add(new BasicNameValuePair("hostuin", myHashCode));
		params.add(new BasicNameValuePair("entryuin", myHashCode));
		params.add(new BasicNameValuePair("noFormSender", "1"));
		params.add(new BasicNameValuePair("plat", "pengyou"));
		return params;
	}

	/**
	 * 发送内容 带图片
	 * @param content 文字内容
	 * @param myHashCode 我的hashcode
	 * @param richval 图片信息
	 * @return
	 */
	public static List<NameValuePair> sendContentWithImage(String content, String myHashCode, String richval) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("plattype", "2"));
		params.add(new BasicNameValuePair("format", "json"));
		params.add(new BasicNameValuePair("con", content));
		params.add(new BasicNameValuePair("feedversion", "1"));
		params.add(new BasicNameValuePair("ver", "1"));
		params.add(new BasicNameValuePair("hostuin", myHashCode));
		params.add(new BasicNameValuePair("richval", richval));
		params.add(new BasicNameValuePair("richtype", "1"));
		params.add(new BasicNameValuePair("subrichtype", "1"));
		params.add(new BasicNameValuePair("entryuin", myHashCode));
		params.add(new BasicNameValuePair("noFormSender", "1"));
		params.add(new BasicNameValuePair("plat", "pengyou"));
		return params;
	}

	/**
	 * 解除好友关系
	 * @param gtk
	 * @param hashCode
	 * @return
	 */
	public static List<NameValuePair> breakFriendParam(String gtk, String hashCode, String token) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mod", "friends"));
		params.add(new BasicNameValuePair("act", "breakfriend"));
		params.add(new BasicNameValuePair("hash", hashCode));
		params.add(new BasicNameValuePair("token_uin", token));
		params.add(new BasicNameValuePair("bai_ck", "null"));
		params.add(new BasicNameValuePair("g_tk", gtk));
		return params;
	}

	/**
	 * 同意好友请求
	 * @param gtk
	 * @param hashCode
	 * @return
	 */
	public static List<NameValuePair> agreeFriendApply(String gtk, String hashCode) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mod", "notice"));
		params.add(new BasicNameValuePair("act", "agreefriendapply"));
		params.add(new BasicNameValuePair("u", hashCode));
		params.add(new BasicNameValuePair("g_tk", gtk));
		return params;
	}

	/**
	 * 获取创建相册的参数
	 * @param hashCode
	 * @param photoName 要创建相册的名字
	 * @return
	 */
	public static List<NameValuePair> createPhoto(String hashCode, String photoName) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("qzreferrer", "http://qzs.pengyou.com/qzone/photo/zone/addAlbum.html"));
		params.add(new BasicNameValuePair("inCharset", "gbk"));
		params.add(new BasicNameValuePair("outCharset", "gbk"));
		params.add(new BasicNameValuePair("hostUin", hashCode));
		params.add(new BasicNameValuePair("notice", "0"));
		params.add(new BasicNameValuePair("callbackFun", "_Callback"));
		params.add(new BasicNameValuePair("format", "fs"));
		params.add(new BasicNameValuePair("plat", "pengyou"));
		params.add(new BasicNameValuePair("source", "pengyou"));
		params.add(new BasicNameValuePair("appid", "4"));
		params.add(new BasicNameValuePair("uin", hashCode));
		params.add(new BasicNameValuePair("birth_time", ""));
		params.add(new BasicNameValuePair("album_type", ""));
		params.add(new BasicNameValuePair("albumname", photoName));
		params.add(new BasicNameValuePair("albumdesc", ""));
		params.add(new BasicNameValuePair("albumclass", "100"));
		params.add(new BasicNameValuePair("priv", "1"));
		params.add(new BasicNameValuePair("question", ""));
		params.add(new BasicNameValuePair("answer", ""));
		params.add(new BasicNameValuePair("whiteList", ""));
		params.add(new BasicNameValuePair("bitmap", "10000000"));
		params.add(new BasicNameValuePair("pypriv", "1"));
		return params;
	}

	/**
	 * 照片分享
	 * @param hashCode
	 * @param albumId 上传图片后返回的 albumid
	 * @param albumTitle 图片名称
	 * @param albumDesc
	 * @param picCount 上传图片后返回的图片总数
	 * @param desc 描述
	 * @param sloc 上传图片后返回的 sloc
	 * @return
	 */
	public static List<NameValuePair> sharePhoto(String hashCode, String albumId, String albumTitle, String albumDesc,
			String picCount, String desc, String sloc) {
		// http://qzs.pengyou.com/qzone/photo/zone/new/upload_complete.htm#aid=V139ib3D1P6oww&a_id=V139ib3D1P6oww&index=0&uptype=Ax&guide=0&viewtype=0
		String refererUrl = "http://qzs.pengyou.com/qzone/photo/zone/new/upload_complete.htm#aid=" + albumId + "&a_id="
				+ albumId + "&index=0&uptype=Ax&guide=0&viewtype=0";
		String codeList = sloc + "?010??" + desc + "??";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("qzreferrer", refererUrl));
		params.add(new BasicNameValuePair("inCharset", "gbk"));
		params.add(new BasicNameValuePair("outCharset", "gbk"));
		params.add(new BasicNameValuePair("hostUin", hashCode));
		params.add(new BasicNameValuePair("notice", "0"));
		params.add(new BasicNameValuePair("callbackFun", "_Callback"));
		params.add(new BasicNameValuePair("format", "fs"));
		params.add(new BasicNameValuePair("plat", "pengyou"));
		params.add(new BasicNameValuePair("source", "pengyou"));
		params.add(new BasicNameValuePair("appid", "4"));
		params.add(new BasicNameValuePair("uin", hashCode));
		params.add(new BasicNameValuePair("albumId", albumId));
		params.add(new BasicNameValuePair("nvip", "1"));
		params.add(new BasicNameValuePair("pub", "1"));
		params.add(new BasicNameValuePair("albumTitle", albumTitle));
		params.add(new BasicNameValuePair("albumDesc", albumDesc));
		params.add(new BasicNameValuePair("picCount", picCount));
		params.add(new BasicNameValuePair("priv", "1"));
		params.add(new BasicNameValuePair("afterUpload", "1"));
		params.add(new BasicNameValuePair("total", "1"));
		params.add(new BasicNameValuePair("modifyType", "1"));
		params.add(new BasicNameValuePair("type", "010"));
		params.add(new BasicNameValuePair("name", ""));
		params.add(new BasicNameValuePair("desc", desc));
		params.add(new BasicNameValuePair("tag", ""));
		params.add(new BasicNameValuePair("codeList", codeList));

		return params;
	}

	/**
	 * 修改朋友网照片名称
	 * @param albumid
	 * @param sloc
	 * @param name
	 * @param gtk
	 * @return
	 */
	public static List<NameValuePair> updatePhotoName(String albumid, String sloc, String name, String gtk) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mod", "photo"));
		params.add(new BasicNameValuePair("act", "setphotoname"));
		params.add(new BasicNameValuePair("albumid", albumid));
		params.add(new BasicNameValuePair("lloc", sloc));
		params.add(new BasicNameValuePair("sloc", sloc));
		params.add(new BasicNameValuePair("question", ""));
		params.add(new BasicNameValuePair("answer", ""));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("g_tk", gtk));
		return params;
	}

	/**
	 * 修改朋友网名称
	 * @param realname
	 * @param gtk
	 * @return
	 */
	public static List<NameValuePair> updateFriendNickName(String realname, String gtk) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("realname", "photo"));
		params.add(new BasicNameValuePair("enname", ""));
		params.add(new BasicNameValuePair("is_show_birth_year", "0"));
		params.add(new BasicNameValuePair("astro", "2"));
		params.add(new BasicNameValuePair("bloodtype", "0"));
		params.add(new BasicNameValuePair("lang_list", ""));
		params.add(new BasicNameValuePair("homecountry", "1"));
		params.add(new BasicNameValuePair("home_prov", "32"));
		params.add(new BasicNameValuePair("home_city", "3205"));
		params.add(new BasicNameValuePair("home_dist", "320503"));
		params.add(new BasicNameValuePair("country", "1"));
		params.add(new BasicNameValuePair("prov", "32"));
		params.add(new BasicNameValuePair("city", "3205"));
		params.add(new BasicNameValuePair("dist", "320503"));
		params.add(new BasicNameValuePair("introduce", ""));
		params.add(new BasicNameValuePair("namechecked", ""));
		params.add(new BasicNameValuePair("g_tk", gtk));
		return params;
	}

	/**
	 * 修改空间名称
	 * @param realname
	 * @param gtk
	 * @return
	 */
	public static List<NameValuePair> updateQzoneNickName(String qzreferrer, String nickname, String qqNum) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("qzreferrer", qzreferrer));
		params.add(new BasicNameValuePair("nickname", nickname));
		params.add(new BasicNameValuePair("emoji", ""));
		params.add(new BasicNameValuePair("sex", "2"));
		params.add(new BasicNameValuePair("birthday", "1989-05-08"));
		params.add(new BasicNameValuePair("province", "32"));
		params.add(new BasicNameValuePair("city", "3205"));
		params.add(new BasicNameValuePair("country", "1"));
		params.add(new BasicNameValuePair("marriage", "0"));
		params.add(new BasicNameValuePair("bloodtype", "5"));
		params.add(new BasicNameValuePair("hp", "0"));
		params.add(new BasicNameValuePair("hc", "0"));
		params.add(new BasicNameValuePair("hco", "0"));
		params.add(new BasicNameValuePair("career", ""));
		params.add(new BasicNameValuePair("cp", "0"));
		params.add(new BasicNameValuePair("cc", "0"));
		params.add(new BasicNameValuePair("cb", "0"));
		params.add(new BasicNameValuePair("cco", "0"));
		params.add(new BasicNameValuePair("lover", ""));
		params.add(new BasicNameValuePair("islunar", "0"));
		params.add(new BasicNameValuePair("mb", "65"));
		params.add(new BasicNameValuePair("uin", qqNum));
		params.add(new BasicNameValuePair("pageindex", "1"));
		params.add(new BasicNameValuePair("fupdate", "1"));
		return params;
	}

	/**
	 * 修改qq空间中照片名称
	 * @param qqNum qq号码
	 * @param albumId 相册ID
	 * @param albumTitle 相册名称
	 * @param albumDesc 相册描述
	 * @param photoName 要修改成的名字
	 * @param picCount 照片数量
	 * @param desc 照片的描述
	 * @param sloc 相片ID
	 * @return
	 */
	public static List<NameValuePair> updateQzonePhotoName(String qqNum, String albumId, String albumTitle,
			String albumDesc, String photoName, String picCount, String desc, String sloc) {
		// http://cnc.qzs.qq.com/qzone/photo/v7/page/photo.html?useqzfl=1&init=photo.v7/module/photoList/index
		// http://qzs.pengyou.com/qzone/photo/zone/modifyPhoto.html#a_id=V139ib3D2iSWBQ&index=&from=photolist
		// String refererUrl =
		// "http://cnc.qzs.qq.com/qzone/photo/zone/new/upload_complete.htm#aid="
		// + albumId
		// + "&a_id="
		// + albumId
		// + "&index=0&uptype=Ax&guide=0&viewtype=0";
		String refererUrl = "http://cnc.qzs.qq.com/qzone/photo/v7/page/photo.html?useqzfl=1&init=photo.v7/module/photoList/index&aid=V146OpxS46rjoG#";
		String codeList = sloc + "?110??" + photoName + "?" + desc + "??";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("qzreferrer", refererUrl));
		params.add(new BasicNameValuePair("inCharset", "gbk"));
		params.add(new BasicNameValuePair("outCharset", "gbk"));
		params.add(new BasicNameValuePair("hostUin", qqNum));
		params.add(new BasicNameValuePair("notice", "0"));
		params.add(new BasicNameValuePair("callbackFun", "_Callback"));
		params.add(new BasicNameValuePair("format", "fs"));
		params.add(new BasicNameValuePair("plat", "qzone"));
		params.add(new BasicNameValuePair("source", "qzone"));
		params.add(new BasicNameValuePair("appid", "4"));
		params.add(new BasicNameValuePair("uin", qqNum));
		params.add(new BasicNameValuePair("albumId", albumId));
		params.add(new BasicNameValuePair("nvip", "1"));
		params.add(new BasicNameValuePair("pub", "1"));
		params.add(new BasicNameValuePair("albumTitle", albumTitle));
		params.add(new BasicNameValuePair("albumDesc", albumDesc));
		params.add(new BasicNameValuePair("picCount", picCount));
		params.add(new BasicNameValuePair("priv", "1"));
		params.add(new BasicNameValuePair("afterUpload", "1"));
		params.add(new BasicNameValuePair("total", "1"));
		params.add(new BasicNameValuePair("modifyType", "1"));
		params.add(new BasicNameValuePair("type", "110"));
		params.add(new BasicNameValuePair("name", photoName));
		params.add(new BasicNameValuePair("desc", desc));
		params.add(new BasicNameValuePair("tag", ""));
		params.add(new BasicNameValuePair("codeList", sloc));

		return params;
	}

	public static List<NameValuePair> updateQzonePhotoName2(String qqNum, String albumId, String albumTitle,
			String albumDesc, String photoName, String picCount, String desc, String sloc) {
		// http://cnc.qzs.qq.com/qzone/photo/v7/page/photo.html?useqzfl=1&init=photo.v7/module/photoList/index
		// http://qzs.pengyou.com/qzone/photo/zone/modifyPhoto.html#a_id=V139ib3D2iSWBQ&index=&from=photolist
		// String refererUrl =
		// "http://cnc.qzs.qq.com/qzone/photo/zone/new/upload_complete.htm#aid="
		// + albumId
		// + "&a_id="
		// + albumId
		// + "&index=0&uptype=Ax&guide=0&viewtype=0";
		String refererUrl = "http://cnc.qzs.qq.com/qzone/photo/v7/page/photo.html?useqzfl=1&init=photo.v7/module/photoList/index&aid=V146OpxS46rjoG#";
		String codeList = sloc + "?100??" + photoName + "?" + desc + "??";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("albumId", albumId));
		params.add(new BasicNameValuePair("nvip", "1"));
		params.add(new BasicNameValuePair("pub", "1"));
		params.add(new BasicNameValuePair("albumTitle", albumTitle));
		params.add(new BasicNameValuePair("albumDesc", albumDesc));
		params.add(new BasicNameValuePair("picCount", picCount));
		params.add(new BasicNameValuePair("priv", "1"));
		params.add(new BasicNameValuePair("afterUpload", "0"));
		params.add(new BasicNameValuePair("total", "1"));
		params.add(new BasicNameValuePair("modifyType", "1"));
		params.add(new BasicNameValuePair("type", "100"));
		params.add(new BasicNameValuePair("name", photoName));
		params.add(new BasicNameValuePair("desc", desc));
		params.add(new BasicNameValuePair("codeList", codeList));
		params.add(new BasicNameValuePair("uin", qqNum));
		params.add(new BasicNameValuePair("hostUin", qqNum));
		params.add(new BasicNameValuePair("plat", "qzone"));
		params.add(new BasicNameValuePair("source", "qzone"));
		params.add(new BasicNameValuePair("inCharset", "gbk"));
		params.add(new BasicNameValuePair("outCharset", "gbk"));
		params.add(new BasicNameValuePair("qzreferrer", refererUrl));
		return params;
	}

	/**
	 * 发送qq留言
	 * @param referer
	 * @param content
	 * @param distQQNum
	 * @param qqNum
	 * @param gtk
	 * @return
	 */
	public static List<NameValuePair> sendQzone(String referer, String content, String distQQNum, String qqNum,
			String gtk) {
		String refererUrl = referer + "#page=1";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("qzreferrer", refererUrl));
		params.add(new BasicNameValuePair("content", content));
		params.add(new BasicNameValuePair("hostUin", distQQNum));
		params.add(new BasicNameValuePair("uin", qqNum));
		params.add(new BasicNameValuePair("format", "fs"));
		params.add(new BasicNameValuePair("iNotice", "1"));
		params.add(new BasicNameValuePair("inCharset", "gbk"));
		params.add(new BasicNameValuePair("outCharset", "gbk"));
		params.add(new BasicNameValuePair("ref", "qzone"));
		params.add(new BasicNameValuePair("json", "1"));
		params.add(new BasicNameValuePair("g_tk", gtk));

		return params;
	}

	/**
	 * 注册QQ
	 * @param verifycode
	 * @param password
	 * @return
	 */
	public static List<NameValuePair> regQQ(String verifycode, String password) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("verifycode", verifycode));
		params.add(new BasicNameValuePair("qzone_flag", "1"));
		params.add(new BasicNameValuePair("country", "1"));
		params.add(new BasicNameValuePair("province", "32"));
		params.add(new BasicNameValuePair("city", "5"));
		params.add(new BasicNameValuePair("isnongli", "0"));
		params.add(new BasicNameValuePair("year", "199" + RandomUtils.nextInt(9)));
		params.add(new BasicNameValuePair("month", String.valueOf(RandomUtils.nextInt(11) + 1)));
		params.add(new BasicNameValuePair("day", String.valueOf(RandomUtils.nextInt(27) + 1)));
		params.add(new BasicNameValuePair("isrunyue", "0"));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("nick", randomString(RandomUtils.nextInt(15) + 1)));
		params.add(new BasicNameValuePair("email", "false"));
		params.add(new BasicNameValuePair("other_email", "false"));
		params.add(new BasicNameValuePair("elevel", "1"));
		params.add(new BasicNameValuePair("sex", "2"));
		params.add(new BasicNameValuePair("qzdate", ""));
		params.add(new BasicNameValuePair("jumpfrom", "58030"));
		params.add(new BasicNameValuePair("csloginstatus", "0"));
		params.add(new BasicNameValuePair("a1s0k0", "r7i5b"));

		return params;
	}

	private static Random randGen = null;
	private static char[] numbersAndLetters = null;

	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			randGen = new Random();
			numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
					.toCharArray();
			// numbersAndLetters =
			// ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
			// randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
		}
		return new String(randBuffer);
	}

	/**
	 * http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_save?g_tk=18296772
	 * 照片分享
	 * @param token 分享照片QQ的gtk
	 * @param album 相册ID
	 * @param photo 照片ID
	 * @param qqNum 分享的QQ
	 * @return
	 */
	public static List<NameValuePair> sharePhoto(String token, String album, String photo, String qqNum) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String qzreferrer = "http://user.qzone.qq.com/1146105934";
		String spaceuin = "1146105934";
		String id = album + ":" + photo;
		params.add(new BasicNameValuePair("qzreferrer", qzreferrer));
		params.add(new BasicNameValuePair("notice", "1"));
		params.add(new BasicNameValuePair("fupdate", "1"));
		params.add(new BasicNameValuePair("platform", "qzone"));
		params.add(new BasicNameValuePair("token", token));
		params.add(new BasicNameValuePair("auto", "0"));
		params.add(new BasicNameValuePair("type", "picture"));
		params.add(new BasicNameValuePair("description", ""));
		params.add(new BasicNameValuePair("share2weibo", "0"));
		params.add(new BasicNameValuePair("onekey", "0"));
		params.add(new BasicNameValuePair("comment", "0"));
		params.add(new BasicNameValuePair("spaceuin", spaceuin));
		params.add(new BasicNameValuePair("id", id));
		params.add(new BasicNameValuePair("reshare", "0"));
		params.add(new BasicNameValuePair("batchid", ""));
		params.add(new BasicNameValuePair("sendparam", ""));
		params.add(new BasicNameValuePair("entryuin", qqNum));
		return params;
	}

	/**
	 * 检查目标好友验证方式
	 * http://w.qzone.qq.com/cgi-bin/tfriend/friend_authfriend.cgi?g_tk
	 * =118394141
	 * @param distQQ
	 * @param qqNum
	 * @return
	 */
	public static List<NameValuePair> checkAdd(String distQQ, String qqNum) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String qzreferrer = "http://user.qzone.qq.com/" + distQQ;
		params.add(new BasicNameValuePair("qzreferrer", qzreferrer));
		params.add(new BasicNameValuePair("sid", "0"));
		params.add(new BasicNameValuePair("ouin", distQQ));
		params.add(new BasicNameValuePair("uin", qqNum));
		params.add(new BasicNameValuePair("fuin", distQQ));
		params.add(new BasicNameValuePair("fupdate", "1"));
		return params;
	}

	/**
	 * 添加QQ好友 http://w.qzone.qq.com/cgi-bin/tfriend/friend_addfriend.cgi?g_tk=
	 * 118394141
	 * @param distQQ
	 * @param qqNum
	 * @param gtk
	 * @param flag
	 * @return
	 */
	public static List<NameValuePair> addQQ(String distQQ, String qqNum, String gtk, Boolean flag) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String qzreferrer = "http://user.qzone.qq.com/" + distQQ;
		params.add(new BasicNameValuePair("qzreferrer", qzreferrer));
		params.add(new BasicNameValuePair("sid", "0"));
		params.add(new BasicNameValuePair("ouin", distQQ));
		params.add(new BasicNameValuePair("uin", qqNum));
		params.add(new BasicNameValuePair("fuin", distQQ));
		params.add(new BasicNameValuePair("fupdate", "1"));
		params.add(new BasicNameValuePair("rd", RandomUtils.nextDouble() + ""));

		if (flag) {
			params.add(new BasicNameValuePair("strmsg", "你好啊" + RandomUtils.nextInt(15)));
		}

		params.add(new BasicNameValuePair("groupId", "0"));
		params.add(new BasicNameValuePair("realname", RandomUtils.nextInt() + ""));
		params.add(new BasicNameValuePair("flag", "0"));
		params.add(new BasicNameValuePair("chat", ""));
		params.add(new BasicNameValuePair("key", ""));
		params.add(new BasicNameValuePair("im", "0"));
		params.add(new BasicNameValuePair("g_tk", gtk));
		params.add(new BasicNameValuePair("from", "8"));
		params.add(new BasicNameValuePair("from_source", "8"));
		return params;
	}

	/**
	 * 修改密码前需要调用
	 * @param pwdNew
	 * @return
	 */
	public static List<NameValuePair> changeBefore(String pwdNew) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("new_psw", pwdNew));
		return params;
	}

	/**
	 * 修改密码
	 * @param pwdOld
	 * @param pwdNew
	 * @param verifycode
	 * @return
	 */
	public static List<NameValuePair> changePwd(String pwdOld, String pwdNew, String verifycode) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("psw_old", pwdOld));
		params.add(new BasicNameValuePair("psw", pwdNew));
		params.add(new BasicNameValuePair("psw_ack", pwdNew));
		params.add(new BasicNameValuePair("verifycode", verifycode));
		params.add(new BasicNameValuePair("method", "2"));
		params.add(new BasicNameValuePair("sub_method", "0"));
		return params;
	}
}
