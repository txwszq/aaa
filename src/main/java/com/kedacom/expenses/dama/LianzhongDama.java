package com.kedacom.expenses.dama;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.WString;

/**
 * 
 * @author cqz
 *         QQ:6199401
 *         email:cheqinzho@qq.com
 * 
 */
public class LianzhongDama
{
	public static final String	USERNAME	= "txwszq";
	public static final String	PASSWORD	= "60607038asd";
	public static final String	AGENTUSER	= "songqing_zhao";
	public static final String	DLLPATH		= "FastVerCode";
	public static final String	IMGPATH		= "D:\\1.jpg";

	public interface FastVerCode extends Library
	{
		FastVerCode	INSTANCE	= (FastVerCode) Native.loadLibrary(DLLPATH, FastVerCode.class);

		/**
		 * 通锟斤拷锟斤拷锟斤拷证锟诫本锟斤拷图片锟斤拷锟斤拷锟斤拷锟斤拷识锟斤拷
		 * 
		 * @param path
		 *            锟斤拷证锟诫本锟斤拷路锟斤拷锟斤拷锟斤拷锟界（c:\1.jpg)
		 * @param UserName
		 *            锟斤拷锟斤拷锟剿猴拷
		 * @param passWord
		 *            锟斤拷锟斤拷锟斤拷锟斤拷
		 * @return
		 */
		public String RecYZM(String path, String UserName, String passWord);

		/**
		 * 锟睫凤拷锟斤拷值
		 * 锟皆达拷锟斤拷锟斤拷证锟斤拷锟斤拷斜锟斤拷锟�
		 * 
		 * @param codeUser
		 *            锟斤拷锟斤拷锟剿猴拷
		 * @param daMaWorker
		 *            锟斤拷锟诫工锟斤拷
		 */
		public void ReportError(String codeUser, String daMaWorker);

		/**
		 * 通锟斤拷锟较达拷锟斤拷证锟斤拷图片锟街节碉拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷证锟斤拷识锟金，凤拷锟斤拷锟斤拷叱谭锟斤拷锟�
		 * 
		 * @param imgByte
		 *            锟斤拷证锟斤拷图片锟街节硷拷
		 * @param len
		 *            锟街节硷拷锟斤拷锟斤拷
		 * @param username
		 *            锟斤拷锟斤拷锟剿猴拷
		 * @param password
		 *            锟斤拷锟斤拷锟斤拷锟斤拷
		 * @return
		 */
		public String RecByte(byte[] imgByte, int len, String username, String password);

		public String GetUserInfo(String UserName, String passWord);

		// LPSTR RecByte_A(BYTE* byte,int len,LPSTR strVcodeUser,LPSTR strVcodePass,LPSTR strAgentUser)

		/**
		 * 通锟斤拷锟较达拷锟斤拷证锟斤拷图片锟街节碉拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷证锟斤拷识锟金，凤拷锟斤拷锟斤拷叱谭锟斤拷锟�锟斤拷锟斤拷锟斤拷锟斤拷锟皆憋拷锟斤拷锟斤拷锟竭碉拷锟斤拷锟诫，一锟斤拷锟斤拷要锟斤拷锟斤拷锟竭碉拷锟斤拷锟竭诧拷锟斤拷使锟斤拷锟斤拷锟竭碉拷锟斤拷锟�
		 * 
		 * @param imgByte
		 *            锟斤拷证锟斤拷图片锟街节硷拷
		 * @param len
		 *            锟街节硷拷锟斤拷锟斤拷
		 * @param username
		 *            锟斤拷锟斤拷锟剿猴拷
		 * @param password
		 *            锟斤拷锟斤拷锟斤拷锟斤拷
		 * @param agentUser
		 *            锟斤拷锟斤拷锟剿猴拷
		 * @return 锟缴癸拷锟斤拷锟斤拷->锟斤拷证锟斤拷锟斤拷|!|锟斤拷锟诫工锟斤拷
		 * 
		 *         锟斤拷台没锟斤拷锟斤拷锟剿凤拷锟斤拷:No Money!
		 *         未注锟结返锟斤拷:No Reg!
		 *         锟较达拷锟斤拷证锟斤拷失锟斤拷:Error:Put Fail!
		 *         识锟斤拷时锟斤拷:Error:TimeOut!
		 *         锟较达拷锟斤拷效锟斤拷证锟斤拷:Error:empty picture!
		 */
		public String RecByte_A(byte[] imgByte, int len, String username, String password, String agentUser);

		/**
		 * 锟斤拷锟斤拷锟斤拷锟�RecYZM_A
		 * 锟斤拷锟筋功锟斤拷:通锟斤拷锟斤拷锟斤拷证锟诫本锟斤拷图片锟斤拷锟斤拷锟斤拷锟斤拷识锟斤拷,锟斤拷锟斤拷锟斤拷锟斤拷锟皆憋拷锟斤拷锟斤拷锟竭碉拷锟斤拷锟诫，一锟斤拷锟斤拷要锟斤拷锟斤拷锟竭碉拷锟斤拷锟竭诧拷锟斤拷使锟斤拷锟斤拷锟竭碉拷锟斤拷锟�
		 * 
		 * @param path
		 *            锟斤拷证锟诫本锟斤拷路锟斤拷锟斤拷锟斤拷锟界（c:\1.jpg)
		 * @param UserName
		 *            锟斤拷锟斤拷锟剿猴拷
		 * @param passWord
		 *            锟斤拷锟斤拷锟斤拷锟斤拷
		 * @param agentUser
		 *            锟斤拷锟斤拷锟剿猴拷
		 * @return
		 *         锟斤拷锟斤拷值锟斤拷锟斤拷:锟侥憋拷锟斤拷 锟缴癸拷锟斤拷锟斤拷->锟斤拷证锟斤拷锟斤拷|!|锟斤拷锟诫工锟斤拷
		 * 
		 *         锟斤拷台没锟斤拷锟斤拷锟剿凤拷锟斤拷:No Money!
		 *         未注锟结返锟斤拷:No Reg!
		 *         锟较达拷锟斤拷证锟斤拷失锟斤拷:Error:Put Fail!
		 *         识锟斤拷时锟斤拷:Error:TimeOut!
		 *         锟较达拷锟斤拷效锟斤拷证锟斤拷:Error:empty picture!
		 */
		public String RecYZM_A(String path, String UserName, String passWord, String agentUser);

		/**
		 * 锟斤拷锟斤拷锟斤拷锟�Reglz
		 * 锟斤拷锟筋功锟斤拷:通锟斤拷锟斤拷锟竭碉拷锟斤拷锟斤拷注锟斤拷锟斤拷锟斤拷锟剿猴拷
		 * 
		 * @param userName
		 *            锟斤拷锟斤拷锟剿猴拷
		 * @param passWord
		 *            锟斤拷锟斤拷锟斤拷锟斤拷
		 * @param email
		 *            QQ锟斤拷锟斤拷
		 * @param qq
		 *            QQ锟斤拷
		 * @param agentid
		 *            锟斤拷锟竭碉拷锟狡癸拷id
		 * @param agentName
		 *            锟斤拷锟斤拷锟剿猴拷
		 * @return
		 *         锟斤拷锟斤拷值锟斤拷锟斤拷:锟斤拷锟斤拷锟斤拷 锟缴癸拷锟斤拷锟斤拷1
		 * 
		 *         注锟斤拷失锟杰凤拷锟斤拷-1 锟斤拷锟界传锟斤拷锟届常
		 *         锟斤拷锟斤拷0 未知锟届常
		 */
		public int Reglz(String userName, String passWord, String email, String qq, String agentid, String agentName);

		/**
		 * 锟斤拷锟斤拷锟斤拷锟�GetUserInfo_A
		 * 锟斤拷锟筋功锟斤拷:锟斤拷询剩锟斤拷锟斤拷证锟斤拷锟斤拷锟�锟斤拷锟斤拷锟斤拷锟斤拷锟皆憋拷锟斤拷锟斤拷锟竭碉拷锟斤拷锟诫，一锟斤拷锟斤拷要锟斤拷锟斤拷锟竭碉拷锟斤拷锟竭诧拷锟斤拷使锟斤拷锟斤拷锟竭碉拷锟斤拷锟�
		 * 
		 * @param UserName
		 *            锟斤拷锟斤拷锟剿猴拷
		 * @param passWord
		 *            锟斤拷锟斤拷锟斤拷锟斤拷
		 * @param agentUser
		 *            锟斤拷锟斤拷锟剿猴拷
		 * @return 锟缴癸拷锟斤拷锟斤拷->剩锟斤拷锟斤拷证锟斤拷锟斤拷锟�
		 */
		public String GetUserInfo_A(String UserName, String passWord, String agentUser);

		/**
		 * 锟斤拷锟斤拷锟斤拷锟�RecByte_2
		 * 锟斤拷锟筋功锟斤拷:通锟斤拷锟较达拷锟斤拷证锟斤拷图片锟街节碉拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷证锟斤拷识锟金，凤拷锟斤拷锟斤拷叱谭锟斤拷锟�
		 * 
		 * @param imgByte
		 *            锟斤拷证锟斤拷图片锟街节硷拷
		 * @param len
		 *            锟街节硷拷锟斤拷锟斤拷
		 * @param username
		 *            锟斤拷锟斤拷锟剿猴拷
		 * @param password
		 *            锟斤拷锟斤拷锟斤拷锟斤拷
		 * @param yzmtype
		 *            锟斤拷证锟斤拷锟斤拷锟斤拷
		 * @param yzmlengthMin
		 *            锟斤拷证锟斤拷锟斤拷小位锟斤拷
		 * @param yzmlengthMax
		 *            锟斤拷证锟斤拷锟斤拷锟轿伙拷锟�
		 * @return 锟缴癸拷锟斤拷锟斤拷->锟斤拷证锟斤拷锟斤拷|!|锟斤拷锟诫工锟斤拷
		 * 
		 *         锟斤拷台没锟斤拷锟斤拷锟剿凤拷锟斤拷:No Money!
		 *         未注锟结返锟斤拷:No Reg!
		 *         锟较达拷锟斤拷证锟斤拷失锟斤拷:Error:Put Fail!
		 *         识锟斤拷时锟斤拷:Error:TimeOut!
		 *         锟较达拷锟斤拷效锟斤拷证锟斤拷:Error:empty picture!
		 */
		public String RecByte_2(byte[] imgByte, int len, String username, String password, int yzmtype, int yzmlengthMin, int yzmlengthMax);

		/**
		 * 
		 锟斤拷锟斤拷锟斤拷锟�RecYZM_2
		 * 锟斤拷锟筋功锟斤拷:通锟斤拷锟斤拷锟斤拷证锟诫本锟斤拷图片锟斤拷锟斤拷锟斤拷锟斤拷识锟斤拷
		 * 
		 * @param path
		 *            锟斤拷证锟诫本锟斤拷路锟斤拷锟斤拷锟斤拷锟界（c:\1.jpg)
		 * @param username
		 *            锟斤拷锟斤拷锟剿猴拷
		 * @param password
		 *            锟斤拷锟斤拷锟斤拷锟斤拷
		 * @param yzmtype
		 *            锟斤拷证锟斤拷锟斤拷锟斤拷
		 * @param yzmlengthMin
		 *            锟斤拷证锟斤拷锟斤拷小位锟斤拷
		 * @param yzmlengthMax
		 *            锟斤拷证锟斤拷锟斤拷锟轿伙拷锟�
		 * @return
		 *         锟缴癸拷锟斤拷锟斤拷->锟斤拷证锟斤拷锟斤拷|!|锟斤拷锟诫工锟斤拷
		 * 
		 *         锟斤拷台没锟斤拷锟斤拷锟剿凤拷锟斤拷:No Money!
		 *         未注锟结返锟斤拷:No Reg!
		 *         锟较达拷锟斤拷证锟斤拷失锟斤拷:Error:Put Fail!
		 *         识锟斤拷时锟斤拷:Error:TimeOut!
		 *         锟较达拷锟斤拷效锟斤拷证锟斤拷:Error:empty picture!
		 */
		public String RecYZM_2(String path, String username, String password, int yzmtype, int yzmlengthMin, int yzmlengthMax);

		/**
		 * 锟斤拷锟斤拷锟斤拷锟�RecByte_A_2
		 * 锟斤拷锟筋功锟斤拷:通锟斤拷锟较达拷锟斤拷证锟斤拷图片锟街节碉拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷证锟斤拷识锟金，凤拷锟斤拷锟斤拷叱谭锟斤拷锟�
		 * 
		 * @param imgByte
		 *            锟斤拷证锟斤拷图片锟街节硷拷
		 * @param len
		 *            锟街节硷拷锟斤拷锟斤拷
		 * @param username
		 *            锟斤拷锟斤拷锟剿猴拷
		 * @param password
		 *            锟斤拷锟斤拷锟斤拷锟斤拷
		 * @param yzmtype
		 *            锟斤拷证锟斤拷锟斤拷锟斤拷
		 * @param yzmlengthMin
		 *            锟斤拷证锟斤拷锟斤拷小位锟斤拷
		 * @param yzmlengthMax
		 *            锟斤拷证锟斤拷锟斤拷锟轿伙拷锟�
		 * @param agentUser
		 *            锟斤拷锟斤拷锟剿猴拷
		 * @return
		 *         锟缴癸拷锟斤拷锟斤拷->锟斤拷证锟斤拷锟斤拷|!|锟斤拷锟诫工锟斤拷
		 * 
		 *         锟斤拷台没锟斤拷锟斤拷锟剿凤拷锟斤拷:No Money!
		 *         未注锟结返锟斤拷:No Reg!
		 *         锟较达拷锟斤拷证锟斤拷失锟斤拷:Error:Put Fail!
		 *         识锟斤拷时锟斤拷:Error:TimeOut!
		 *         锟较达拷锟斤拷效锟斤拷证锟斤拷:Error:empty picture!
		 */
		public String RecByte_A_2(byte[] imgByte, int len, String username, String password, int yzmtype, int yzmlengthMin, int yzmlengthMax, String agentUser);

		/**
		 * 锟斤拷锟斤拷锟斤拷锟�RecYZM_A_2
		 * 锟斤拷锟筋功锟斤拷:通锟斤拷锟斤拷锟斤拷证锟诫本锟斤拷图片锟斤拷锟斤拷锟斤拷锟斤拷识锟斤拷
		 * 
		 * @param path
		 *            锟斤拷证锟诫本锟斤拷路锟斤拷锟斤拷锟斤拷锟界（c:\1.jpg)
		 * @param username
		 *            锟斤拷锟斤拷锟剿猴拷
		 * @param password
		 *            锟斤拷锟斤拷锟斤拷锟斤拷
		 * @param yzmtype
		 *            锟斤拷证锟斤拷锟斤拷锟斤拷
		 * @param yzmlengthMin
		 *            锟斤拷证锟斤拷锟斤拷小位锟斤拷
		 * @param yzmlengthMax
		 *            锟斤拷证锟斤拷锟斤拷锟轿伙拷锟�
		 * @param agentUser
		 *            锟斤拷锟斤拷锟剿猴拷
		 * @return
		 *         锟缴癸拷锟斤拷锟斤拷->锟斤拷证锟斤拷锟斤拷|!|锟斤拷锟诫工锟斤拷
		 * 
		 *         锟斤拷台没锟斤拷锟斤拷锟剿凤拷锟斤拷:No Money!
		 *         未注锟结返锟斤拷:No Reg!
		 *         锟较达拷锟斤拷证锟斤拷失锟斤拷:Error:Put Fail!
		 *         识锟斤拷时锟斤拷:Error:TimeOut!
		 *         锟较达拷锟斤拷效锟斤拷证锟斤拷:Error:empty picture!
		 */
		public String RecYZM_A_2(String path, String username, String password, int yzmtype, int yzmlengthMin, int yzmlengthMax, String agentUser);

	}

	public static String myDama(String imagePath){
		String recYZM = FastVerCode.INSTANCE.RecYZM_A_2(imagePath,USERNAME,PASSWORD,0,5,5,AGENTUSER);
		return recYZM.split("\\|")[0];
	}
	public static void error(String code){
		FastVerCode.INSTANCE.ReportError(USERNAME, code);
	}
	
	public static void main(String[] args) throws Exception
	{

		// System.out.println("RecYZM:" + FastVerCode.INSTANCE.RecYZM(IMGPATH, USERNAME, PASSWORD));
		 FastVerCode.INSTANCE.ReportError(USERNAME, "ccc5");
		// getCodeByRecByte();
		// System.out.println("GetUserInfo:" + FastVerCode.INSTANCE.GetUserInfo(USERNAME, PASSWORD));
		// System.out.println("Reglz:" + FastVerCode.INSTANCE.Reglz("6199401", "6199401", "6199401@qq.com", "6199401", "ww", "ww"));

		System.out.println("RecYZM_2:" + FastVerCode.INSTANCE.RecYZM_2(IMGPATH, USERNAME, PASSWORD, 0, 3, 5));
		System.out.println("RecYZM_A_2:" + FastVerCode.INSTANCE.RecYZM_A_2(IMGPATH, USERNAME, PASSWORD, 1, 3, 5, AGENTUSER));

		//
	}

	public static void getCodeByRecByte_A() throws Exception
	{
		System.out.println("锟斤拷锟节伙拷取锟斤拷证锟斤拷........");
		byte[] b = toByteArrayFromFile(IMGPATH);
		System.out.println("RecByte:" + FastVerCode.INSTANCE.RecByte(b, b.length, USERNAME, PASSWORD));

	}

	public static void getCodeByRecByte() throws Exception
	{
		System.out.println("锟斤拷锟节伙拷取锟斤拷证锟斤拷........");
		byte[] b = toByteArrayFromFile(IMGPATH);
		System.out.println("RecByte:" + FastVerCode.INSTANCE.RecByte(b, b.length, USERNAME, PASSWORD));

	}

	public static byte[] toByteArray(File imageFile) throws Exception
	{
		BufferedImage img = ImageIO.read(imageFile);
		ByteArrayOutputStream buf = new ByteArrayOutputStream((int) imageFile.length());
		try
		{
			ImageIO.write(img, "jpg", buf);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return buf.toByteArray();
	}

	public static byte[] toByteArrayFromFile(String imageFile) throws Exception
	{
		InputStream is = null;

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try
		{
			is = new FileInputStream(imageFile);

			byte[] b = new byte[1024];

			int n;

			while ((n = is.read(b)) != -1)
			{

				out.write(b, 0, n);

			}// end while

		} catch (Exception e)
		{
			throw new Exception("System error,SendTimingMms.getBytesFromFile", e);
		} finally
		{

			if (is != null)
			{
				try
				{
					is.close();
				} catch (Exception e)
				{}// end try
			}// end if

		}// end try
		return out.toByteArray();
	}
}
