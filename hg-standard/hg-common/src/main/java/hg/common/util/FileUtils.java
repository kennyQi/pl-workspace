package hg.common.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @类功能说明：文件工具类
 * @类修改者：zzb
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzb
 * @创建时间：2014年12月30日上午9:52:41
 * @版本：V1.0
 */
public class FileUtils {

	/**
	 * @方法功能说明：解压文件
	 * @修改者名字：zzb
	 * @修改时间：2014年12月30日上午11:05:11
	 * @修改内容：
	 * @参数：@param filePath
	 * @return:void
	 * @throws
	 */
	public static void unzip(String zipFile, String descDir)
			throws IOException {
		File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		ZipFile zip = new ZipFile(zipFile);
		for (Enumeration<?> entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + zipEntryName).replaceAll("\\\\", "/");
			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}
			// 输出文件路径信息
			OutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();
		}
	}

	/**
	 * @方法功能说明：创建文件夹/文件
	 * @修改者名字：zzb
	 * @修改时间：2014年12月30日上午11:12:31
	 * @修改内容：
	 * @参数：@param fileName
	 * @参数：@param isDirectory
	 * @参数：@return
	 * @return:File
	 * @throws
	 */
	public static File buildFile(String fileName, boolean isDirectory) {

		File target = new File(fileName);
		if (isDirectory) {
			target.mkdirs();
		} else {
			if (!target.getParentFile().exists()) {
				target.getParentFile().mkdirs();
				target = new File(target.getAbsolutePath());
			}
		}
		return target;
	}
	
	
	/**
	 * @方法功能说明：获取某个文件夹下所有某个后缀的文件
	 * @修改者名字：zzb
	 * @修改时间：2014年12月31日上午10:01:37
	 * @修改内容：
	 * @参数：@param filePath
	 * @参数：@param suffix
	 * @参数：@return
	 * @return:List<File>
	 * @throws
	 */
	public static List<File> getFilesBySuffix(String filePath, final String suffix) {
		final List<File> fileList = new ArrayList<File>();
		File[] directoryList = new File(filePath).listFiles(new FileFilter() {  
            public boolean accept(File file) {
            	
            	if (file.isFile()) {
            		if (file.getName().toLowerCase().endsWith(suffix.toLowerCase()))
            			return true;
            		else
            			return false;
            	} else {
            		fileList.addAll(FileUtils.getFilesBySuffix(file.getAbsolutePath(), suffix));
            	}
				return false;
            }
        });
		
		for (int i = 0; i < directoryList.length; i++) {
			fileList.add(directoryList[i]);
		}
		
		return fileList;
	}
	
	
	
}
