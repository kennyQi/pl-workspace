package hg.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016年4月18日下午6:17:28
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：guotx
 * @创建时间：2016年4月18日下午6:17:28
 * @version：
 */
public class WebUtil {

	/**
	 * @方法功能说明：
	 * @修改者名字：guotx
	 * @修改时间：2016年4月20日下午2:16:22
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param downLoadPath 本地机器完整的绝对路径，例如：c:\\file\\text.rar
	 * @参数：@param fileName 下载时候提示客户端保存的文件名
	 * @参数：@throws UnsupportedEncodingException
	 * @参数：@throws IOException 文件不存在时抛出
	 * @return:void
	 * @throws
	 */
	public static void download(HttpServletRequest request,
			HttpServletResponse response, String downLoadPath, String saveAsName)
			throws UnsupportedEncodingException, IOException {
		
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;

		try {
			File file = new File(downLoadPath);
			long fileLength = file.length();
			
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			//先创建输入输出流，异常时客户端不弹出下载窗口
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream;");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(saveAsName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getCause());
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
			response.flushBuffer();
		}
	}

	/**
	 * 
	 * @return utf8 bom 3 byte.
	 */
	public static int[] utf8Bom() {
		// utf-8文件头
		return new int[] { 0xef, 0xbb, 0xbf };
	}

	/**
	 * @方法功能说明：输出带bom头的文件下载
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月4日下午7:45:00
	 * @version：
	 * @修改内容：
	 * @参数：@param response
	 * @参数：@param filename
	 * @参数：@param byteArray
	 * @参数：@throws UnsupportedEncodingException
	 * @参数：@throws IOException
	 * @return:void
	 * @throws
	 */
	public static void writeUtf8File(HttpServletResponse response,
			String filename, byte[] byteArray)
			throws UnsupportedEncodingException, IOException {
		response.setContentType("application/octet-stream;");
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(filename.getBytes("utf-8"), "ISO8859-1"));

		java.io.BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(response.getOutputStream());

			response.setHeader("Content-Length",
					String.valueOf(3 + byteArray.length));
			bos.write(0xef); // utf-8文件头
			bos.write(0xbb);
			bos.write(0xbf);

			bos.write(byteArray);
			bos.flush();
			response.flushBuffer();
		} finally {
			if (bos != null) {
				bos.close();
			}
		}
	}

	/**
	 * @方法功能说明：用utf8 带bom头的格式输出，非文件下载
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月4日下午7:41:34
	 * @version：
	 * @修改内容：
	 * @参数：@param response
	 * @参数：@param byteArray
	 * @参数：@throws IOException
	 * @return:void
	 * @throws
	 */
	public static void responseUtf8Stream(HttpServletResponse response,
			byte[] byteArray) throws IOException {
		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-Length",
				String.valueOf(3 + byteArray.length));
		writeWBom(byteArray, outputStream);
		response.flushBuffer();
	}

	/**
	 * @描述 附加上utf8的BOM
	 * 
	 * @param byteArray
	 * @param outputStream
	 * @throws IOException
	 */
	public static void writeWBom(byte[] byteArray, OutputStream outputStream)
			throws IOException {
		java.io.BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(outputStream);

			bos.write(0xef); // utf-8文件头
			bos.write(0xbb);
			bos.write(0xbf);

			bos.write(byteArray);
			bos.flush();
		} finally {
			if (bos != null) {
				bos.close();
			}
		}
	}

	/**
	 * @方法功能说明：图片下载,没有实在意义，有downLoadFile了
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月27日下午6:06:52
	 * @修改内容：
	 * @参数：request
	 * @参数：response
	 * @参数：downLoadPath 本地绝对路径
	 * @参数：fileName 本地下载文件名
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @return:void
	 * @throws
	 */
	public static void downImgage(HttpServletRequest request,
			HttpServletResponse response, String downLoadPath, String fileName)
			throws UnsupportedEncodingException, IOException {
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;

		try {
			File file = new File(downLoadPath, fileName);
			long fileLength = file.length();
			response.setContentType("image/*");
			System.out.println("file length:" + fileLength);
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// throw new RuntimeException(e.getCause());
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
			response.flushBuffer();
		}
	}

	/**
	 * @方法功能说明：文件上传，实际保存的文件为pathname\filename
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年3月19日上午10:18:48
	 * @version：
	 * @修改内容：指定保存文件名为空使用原文件名
	 * @参数：pathname 要保存至本地的绝对路径
	 * @参数：file 表单提交的文件
	 * @参数：filename 要保存至本地的文件名,若为空则使用所上传文件的原文件名
	 * @throws Exception
	 * @return:void
	 * @throws
	 */
/*	public static void uploadFile(CommonsMultipartFile file, File pathname,
			String filename) throws Exception {
		if (!pathname.exists()) {
			pathname.mkdirs();
			System.err.println("CREATE DIR:" + pathname);
		}
		String fileName = StringUtils.isBlank(filename) ? file.getFileItem()
				.getName() : filename;
		if (file.getFileItem() != null && file.getFileItem().getSize() > 0)
			file.getFileItem().write(new File(pathname, fileName));
	}*/

	/**
	 * 
	 * @描述： 将文本中的回车使用指定字符wrap。
	 * @param str 被包含的文本
	 * @param with 需要扩展的标签
	 * @author guotx 
	 * @version 2016-4-20 下午1:54:06
	 */
	public static String wrapEnter(String str, String with) {
		String r = "";
		String string = "<" + with + ">";
		String string2 = "</" + with + ">";
		String line[] = str.split("\\n");
		for (String s : line) {
			r += string + s + string2;
		}
		return r;
	}

	/**
	 * @param defaultValue
	 * 
	 * @方法功能说明：图片服务器context path
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年4月2日上午11:21:25
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	// public static String getPicServerContextPath(String defaultValue){
	// return JfProperty.getProperties().getProperty("picContextPath",
	// defaultValue);
	// }

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年4月8日下午2:09:57
	 * @version：
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	// public static String getPicSererContextPath(HttpServletRequest request) {
	// return getPicServerContextPath(request.getContextPath());
	// }

	/**
	 * 
	 * @param bytesString
	 *            十进制的 每个字节表示，逗号隔开，如
	 *            123,34,115,116,97,116,117,115,34,58,102,97,108
	 *            ,115,101,44,34,116,101,120,116,34,58,34,-26,-72,-96,-23
	 *            此方法不应该放在这里
	 * @return 返回处理后的字符串
	 * @throws UnsupportedEncodingException
	 */
	public static String getStringFromByte(String bytesString)
			throws UnsupportedEncodingException {

		String s[] = bytesString.split(",");
		byte b[] = new byte[s.length];
		for (int i = 0; i < s.length; i++)
			b[i] = Byte.parseByte(s[i]);
		String string = new String(b);
		return string;
	}
}