package hg.common.util.file;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @类功能说明：文件处理工具类<br>
 * 该类仅仅只是提供一些关于文件File的简单方法，其余功能请使用org.apache.commons.io.FileUtils工具包
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年11月5日 下午8:27:44
 */
public class SimpleFileUtil {
	private static final String TEMP_UPLOAD = "temp_upload";//临时目录
	
	/**
	 * @方法功能说明：获取项目的临时目录路径
	 * @return
	 * @throws URISyntaxException
	 */
	@Deprecated
	public static String getTempPath() throws URISyntaxException{
		String rootPath = null;
		//获取项目跟路径
		String classPath = DownloadUtil.class.getClassLoader().getResource("/").toURI().getPath();
		//路径处理
		rootPath = classPath.substring(1,classPath.indexOf("/WEB-INF/classes"));
		rootPath = rootPath.replace(StringUtils.substringAfterLast(rootPath, "/"),SimpleFileUtil.TEMP_UPLOAD);
		//Windows环境下
		if("\\".equals(File.separator)){
			rootPath = rootPath.replace("/", "\\");
		}
		//Linux环境下
		if("/".equals(File.separator)){
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}
	
	/**
	 * @方法功能说明: 获取临时目录路径 
	 * @return
	 */
	public static String getTempDirectoryPath(){
		//受外部部署环境影响，这个路径可能是服务器的临时目录(无文件分隔符)，也可能是所在操作系统的临时目录(有文件分隔符)，所以需要判断
		String sysPath = FileUtils.getTempDirectoryPath();
		if(!sysPath.substring(sysPath.length()-File.separator.length(), sysPath.length()).equals(File.separator))
			sysPath += File.separator;
		return sysPath;
	}
	
	/**
	 * @方法功能说明: 获得随机字符，由UUID和随机数拼接得到
	 * @return
	 */
	public static String getRandomStr(){
		return (UUID.randomUUID()+"").replace("-","").substring(0,16)+(new Random()).nextInt(9999);
	}
	
	/**
	 * @方法功能说明：文件重命名(包含临时目录路径)，由路径获得
	 * @param srcPath 文件路径或文件名(包含后缀名)
	 * @return
	 */
	public static String getPathToRename(String srcPath){
		StringBuilder sb = new StringBuilder(4);
		//获取系统临时目录
		sb.append(getTempDirectoryPath());
		//重新命名，由UUID和随机数拼接得到，在相邻时间段内，保守估计1000次内不会重复
		sb.append(getRandomStr());
		sb.append(".");
		sb.append(StringUtils.substringAfterLast(srcPath, "."));
		return sb.toString();
	}
	
	/**
	 * @方法功能说明：文件重命名(包含临时目录路径)，由URL获得
	 * @param destUrl
	 * @return
	 */
	public static String getURLToRename(String srcUrl){
		StringBuilder sb = new StringBuilder(4);
		//获取系统临时目录
		sb.append(getTempDirectoryPath());
		//重新命名，由UUID和随机数拼接得到，在相邻时间段内，保守估计1000次内不会重复
		sb.append(getRandomStr());
		sb.append(".");
		//后缀名，先截取‘？’前面再截取‘.’后面。之所以这么做，是因为有些网站为了避免重复访问，会在图片后面加随机数参数。其余情况，不考虑
		sb.append(StringUtils.substringBefore(StringUtils.substringAfterLast(srcUrl, "."), "?"));
		return sb.toString();
	}
	
	/**
	 * @方法功能说明：文件是否存在
	 * @修改者名字：chenys
	 * @param file
	 * @return
	 */
	public static boolean isFile(File file){
		if (null == file|| !file.exists() || !file.isFile())
			return false;
		return true;
	}
	
	/**
	 * @方法功能说明：目录是否存在
	 * @修改者名字：chenys
	 * @param file
	 * @return
	 */
	public static boolean isDirectory(File file){
		if(null == file || !file.exists() || !file.isDirectory())
			return false;
		return true;
	}
	
	/**
	 * @方法功能说明：获取文件扩展名
	 * @修改者名字：chenys
	 * @param file
	 * @return
	 */
	public static String getFileExts(File file){
		if(isFile(file))
			return null;
		String path = file.getAbsolutePath();
		return StringUtils.substringAfterLast(path,".").toLowerCase();
	}
}