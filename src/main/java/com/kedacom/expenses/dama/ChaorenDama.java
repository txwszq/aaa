package com.kedacom.expenses.dama;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class ChaorenDama {

	public static final String USERNAME = "txwszq"; // QQ超人打码账号
	public static final String PASSWORD = "60607038asd"; // QQ超人打码密码
	public static final String SOFTID = "3492"; // 缺省为"0" 软件作者一定要提交软件ID，以保证作者提成
	public static final String DLLPATH = "dc";
	public static final String IMGPATH = "D:\\1.jpg";

	public interface DC extends Library {

		DC INSTANCE = (DC) Native.loadLibrary(DLLPATH, DC.class);
		//DC INSTANCE = (DC)System.loadLibrary(DLLPATH);
		public String GetUserInfo(String UserName, String passWord);

		/**
		 * 通过上传验证码图片字节到服务器进行验证码识别，方便多线程发送,这个函数可以保护作者的收入，一定要提交软件ID
		 * @param imgByte 验证码图片字节集
		 * @param len 字节集长度
		 * @param username QQ超人账号
		 * @param password QQ超人密码
		 * @param softId 软件ID
		 * @return 成功返回->验证码结果|!|打码工人 后台没点数了返回:No Money! 未注册返回:No Reg!
		 *         上传验证码失败:Error:Put Fail! 识别超时了:Error:TimeOut!
		 *         上传无效验证码:Error:empty picture!
		 */
		public String RecByte_A(byte[] imgByte, int len, String username, String password, String softId);

		/**
		 * 命令名称:RecYZM_A 命令功能:通过发送验证码本地图片到服务器识别,这个函数可以保护作者的收入，一定要提交软件ID
		 * @param path 验证码本地路径，例如（c:\1.jpg)
		 * @param UserName QQ超人账号
		 * @param passWord QQ超人密码
		 * @param softId 软件ID
		 * @return 返回值类型:文本型 成功返回->验证码结果|!|打码工人 后台没点数了返回:No Money! 未注册返回:No Reg!
		 *         上传验证码失败:Error:Put Fail! 识别超时了:Error:TimeOut!
		 *         上传无效验证码:Error:empty picture!
		 */
		public String RecYZM_A(String path, String UserName, String passWord, String agentUser);

		/**
		 * 无返回值 对打错的验证码进行报告
		 * @param codeUser QQ超人账号
		 * @param daMaWorker 打码工人
		 */
		public void ReportError(String codeUser, String daMaWorker);

		/**
		 * 命令名称:Reglz 命令功能:通过作者的下线注册QQ超人账号
		 * @param userName QQ超人账号
		 * @param passWord QQ超人密码
		 * @param email QQ邮箱
		 * @param qq QQ号
		 * @param agentid 作者的推广id
		 * @param agentName 作者账号
		 * @return 返回值类型:整数型 成功返回1 注册失败返回-1 网络传输异常 返回0 未知异常
		 */

		public int Reglz(String userName, String passWord, String email, String qq, String agentid, String agentName);

	}

	public static String myDama(String imagePath) {
		String recYZM_A = DC.INSTANCE.RecYZM_A(imagePath, USERNAME, PASSWORD, SOFTID);
		String code = recYZM_A.split("\\|")[0];
		return code;
	}

	public static void error(String code){
		DC.INSTANCE.ReportError(USERNAME,code);
	}
	public static void main(String[] args) throws Exception {
		String username = "2069913295";
		String filePath = System.getProperty("user.dir") + "/src/main/resources/qq/" + username + "/vcode/"
		+ username + ".jpg";
		String myDama = myDama(filePath);
		//System.out.println("RecYZM_A:" + DC.INSTANCE.RecYZM_A(IMGPATH, USERNAME, PASSWORD, SOFTID));
		// DC.INSTANCE.ReportError(USERNAME, "ccc5"); 提交错误的识别结果
	//	getCodeByRecByte_A();
		//System.out.println("GetUserInfo:" + DC.INSTANCE.GetUserInfo(USERNAME, PASSWORD));
		// System.out.println("Reglz:" + DC.INSTANCE.Reglz("user", "pass",
		// "123@qq.com", "213123", "ww", "ww"));
		//
		System.out.println(myDama);
	}

	public static void getCodeByRecByte_A() throws Exception {
		System.out.println("正在获取验证码........");
		byte[] b = toByteArrayFromFile(IMGPATH);
		System.out.println("RecByte:" + DC.INSTANCE.RecByte_A(b, b.length, USERNAME, PASSWORD, SOFTID));

	}

	public static byte[] toByteArray(File imageFile) throws Exception {
		BufferedImage img = ImageIO.read(imageFile);
		ByteArrayOutputStream buf = new ByteArrayOutputStream((int) imageFile.length());
		try {
			ImageIO.write(img, "jpg", buf);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return buf.toByteArray();
	}

	public static byte[] toByteArrayFromFile(String imageFile) throws Exception {
		InputStream is = null;

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			is = new FileInputStream(imageFile);

			byte[] b = new byte[1024];

			int n;

			while ((n = is.read(b)) != -1) {

				out.write(b, 0, n);

			}// end while

		} catch (Exception e) {
			throw new Exception("System error,SendTimingMms.getBytesFromFile", e);
		} finally {

			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
				}// end try
			}// end if

		}// end try
		return out.toByteArray();
	}
}
