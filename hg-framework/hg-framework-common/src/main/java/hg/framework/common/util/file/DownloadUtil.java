package hg.framework.common.util.file;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Vector;

/**
 * @类功能说明：将指定的HTTP网络资源在本地以文件形式存放
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 */
public class DownloadUtil {
	private static final int BUFFER_SIZE = 8096;            // 缓冲区大小
	private Vector<String[]> vDownLoad = new Vector<String[]>();//资源列表

	/**
	 * @方法功能说明：清除下载列表
	 */
	public void resetList() {
		vDownLoad.clear();
	}

	/**
	 * @param url
	 * @param filename
	 * @throws URISyntaxException
	 * @方法功能说明：增加下载列表项
	 */
	public void addItem(String destUrl, String filePath) {
		//校验
		if (StringUtils.isBlank(destUrl))
			throw new IllegalArgumentException("资源url地址为空");
		if (StringUtils.isBlank(filePath))
			filePath = SimpleFileUtil.getURLToRename(destUrl);
		//添加列表项
		String[] down = {destUrl, filePath};
		vDownLoad.add(down);
	}

	/**
	 * @throws IOException
	 * @throws URISyntaxException
	 * @方法功能说明：根据列表下载资源
	 */
	public void downLoadByList() throws IOException {
		//按列表顺序逐个下载
		try {
			for (String[] down : vDownLoad)
				saveToFile(down[0], down[1]);
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * @param destUrl
	 * @param fileName
	 * @throws IOException
	 * @throws URISyntaxException
	 * @方法功能说明：单个下载HTTP资源
	 */
	public static File saveToFile(String destUrl, String filePath) throws IOException {
		//校验
		if (StringUtils.isBlank(destUrl))
			throw new IllegalArgumentException("资源url地址为空");
		if (StringUtils.isBlank(filePath))
			filePath = SimpleFileUtil.getURLToRename(destUrl);
		
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		int size = 0;
		try {
			byte[] buf = new byte[DownloadUtil.BUFFER_SIZE];//字节缓存数组
			// 建立链接
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			// 连接指定的资源
			httpUrl.connect();
			// 获取网络输入流
			bis = new BufferedInputStream(httpUrl.getInputStream());
			// 建立文件
			fos = new FileOutputStream(filePath);
			// 保存文件
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
			
			//返回
			return new File(filePath);
		} finally {
			//关闭资源
			if (null != fos)
				fos.close();
			if (null != bis)
				bis.close();
			if (null != httpUrl)
				httpUrl.disconnect();
		}
	}
	
	/**
	 * @param destUrl
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 * @方法功能说明：根据URL获取(保存)图片文件
	 */
	public static File saveToFile(String destUrl) throws IOException {
		File file = new File(SimpleFileUtil.getURLToRename(destUrl));
		FileUtils.copyURLToFile(new URL(destUrl), file);//由URL复制图片
		return file;
	}
	
	/**
	 * @param destUrl
	 * @param fileName
	 * @throws IOException
	 * @方法功能说明：单个下载HTTP资源(暂时不用，废弃)
	 */
	@Deprecated
	@SuppressWarnings("restriction")
	public void saveToFile2(String destUrl, String fileName) throws IOException {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		
		// 建立链接
		url = new URL(destUrl);
		httpUrl = (HttpURLConnection) url.openConnection();
		// String authString = "username" + ":" + "password";
		String authString = "50301" + ":" + "88888888";
		String auth = "Basic " + new sun.misc.BASE64Encoder().encode(authString.getBytes());
		httpUrl.setRequestProperty("Proxy-Authorization", auth);

		// 连接指定的资源
		httpUrl.connect();
		// 获取网络输入流
		bis = new BufferedInputStream(httpUrl.getInputStream());
		// 建立文件
		fos = new FileOutputStream(fileName);
		// 保存文件
		while ((size = bis.read(buf)) != -1)
			fos.write(buf, 0, size);

		fos.close();
		bis.close();
		httpUrl.disconnect();
	}

	/**
	 * @param proxy
	 * @param proxyPort
	 * @方法功能说明：设置代理服务器(暂时不用，废弃)
	 */
	@Deprecated
	public void setProxyServer(String proxy, String proxyPort) {
		// 设置代理服务器
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", proxy);
		System.getProperties().put("proxyPort", proxyPort);
	}
	
	/**
	 * @param httpPath
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 * @方法功能说明：判断HTTP目标文件是否存在
	 * @修改者名字：chenys
	 */
	public static boolean existsHttpFile(String httpPath) throws IOException {
		HttpURLConnection httpUrl = null;
		try {
			httpUrl = (HttpURLConnection) new URL(httpPath).openConnection();
			httpUrl.connect();
			if (null != httpUrl.getInputStream())
				return true;
			else
				return false;
		} finally {
			if (null != httpUrl)
				httpUrl.disconnect();
		}
	}
	
	/**
	 * @param imgStr   图片二进制数据字符
	 * @param savepath 保存路径
	 * @return
	 * @方法功能说明：对Base64字符解码，以jpg图片格式保存
	 * @修改者名字：chenys
	 */
	public static File saveImgToBase64(String imgStr, String savepath) {
		//校验
		if (StringUtils.isBlank(imgStr))
			return null;
		if (StringUtils.isBlank(savepath))
			savepath = SimpleFileUtil.getTempDirectoryPath();
		
		String fileName = SimpleFileUtil.getRandomStr() + ".jpg";//图片重命名
		OutputStream out = null;//输出流
		try {
			/**
			 * 如果是通过HTML5 canvas标签加密得到二进制数据是以‘data:image/jpeg;base64,’打头
			 * 所以在生成图片前，需要判断是否以这部分大头，如果是就必须要把这部分无用数据去除，以确保图片格式正确
			 */
			int count = imgStr.indexOf(";base64,");
			if (count > 0)
				imgStr = imgStr.substring(count + 8, imgStr.length());

			byte[] byt = Base64.decodeBase64(imgStr);
			//遍历，调整异常数据
			for (int i = 0; i < byt.length; ++i) {
				if (byt[i] < 0) {
					byt[i] += 256;
				}
			}

			out = new FileOutputStream(savepath + fileName);
			IOUtils.write(byt, out);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			IOUtils.closeQuietly(out);
		}
		return new File(savepath + fileName);
	}
}