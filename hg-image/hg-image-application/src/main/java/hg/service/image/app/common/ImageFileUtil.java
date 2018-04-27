package hg.service.image.app.common;

import hg.common.util.Md5FileUtil;
import hg.common.util.SpringContextUtil;
import hg.common.util.file.DownloadUtil;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.common.util.file.SimpleFileUtil;
import hg.common.util.image.ImageUtil;
import hg.service.image.domain.model.Image;
import hg.service.image.domain.model.ImageSpec;
import hg.service.image.pojo.exception.ImageException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * @类功能说明：图片服务文件工具类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-12-15下午7:13:50
 * @版本：V1.0
 */
public class ImageFileUtil {

	/**
	 * @方法功能说明：加载DFS图片文件
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 下午4:37:38
	 * @修改内容：
	 * @param fileInfo
	 * @return
	 * @throws ImageException
	 * @throws MalformedURLException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static File loadRemoteFile(String fileInfo) throws ImageException,
			MalformedURLException, IOException {
		// FastDFS配置
		Object obj = SpringContextUtil.getBean("fastDFSConfig");
		if (null == obj)
			throw new ImageException(
					ImageException.NEED_FASTDFS_CONFIG_NOTEXIST,
					"FastDFS配置不能为空");
		FastDFSConfig fdsCon = (FastDFSConfig) obj;

		// 下载原图
		FdfsFileInfo srcdfsInfo = JSONObject.parseObject(fileInfo,
				FdfsFileInfo.class);// 获取DFS的文件信息
		String url = fdsCon.getIpAddr() + srcdfsInfo.getUri();
		File srcFile = DownloadUtil.saveToFile(url);
		return srcFile;
	}
	
	public static void autoHandleImageSpec(Image image) throws ImageException,
			MalformedURLException, IOException {
		File srcFile = loadRemoteFile(image.getDefaultImageSpec().getFileInfo());
		
		for (String key : image.getImageSpecMap().keySet()) {
			ImageSpec imageSpec = image.getImageSpecMap().get(key);

			// 按规格KEY进行图片剪裁
			String destPath = SimpleFileUtil.getPathToRename(srcFile
					.getAbsolutePath());
			ImageUtil.compress(srcFile, destPath, imageSpec.getWidth(),
					imageSpec.getHeight());
			File destFile = new File(destPath);// 得到剪裁图

			// 剪裁图上传
			Map<String, String> metaMap = new HashMap<String, String>(1);// 元数据集
			
			FdfsFileUtil.init();
			FdfsFileInfo destdfsInfo = FdfsFileUtil.upload(destFile, metaMap);
			destdfsInfo.setMetaMap(metaMap);// 设置元数据集

			// 删除剪裁过程中产生的临时图片
			destFile.delete();
			
			imageSpec.addFileInfo(image, destdfsInfo);
			
		}

	}

	/**
	 * @方法功能说明：图片剪裁处理
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 下午4:37:38
	 * @修改内容：
	 * @param width
	 * @param height
	 * @param srcFile
	 * @return
	 * @throws IOException
	 */
	public static FdfsFileInfo imageHandler(int width, int height, File srcFile)
			throws IOException {
		if (width < 1 || height < 1)
			return null;

		// 按规格KEY进行图片剪裁
		String destPath = SimpleFileUtil.getPathToRename(srcFile
				.getAbsolutePath());
		ImageUtil.compress(srcFile, destPath, width, height);
		File destFile = new File(destPath);// 得到剪裁图

		// 剪裁图上传
		Map<String, String> metaMap = new HashMap<String, String>(1);// 元数据集
		metaMap.put("md5", Md5FileUtil.getMD5(destFile));
		FdfsFileUtil.init();
		FdfsFileInfo destdfsInfo = FdfsFileUtil.upload(destFile, null);
		destdfsInfo.setMetaMap(metaMap);// 设置元数据集

		// 删除剪裁过程中产生的临时图片
		destFile.delete();
		// 返回DFS文件信息的JSON字符
		return destdfsInfo;
	}

	/**
	 * 
	 * @方法功能说明：判断图片文件在FDFS上是否已存在
	 * @修改者名字：yuxx
	 * @修改时间：2014年12月30日下午2:08:19
	 * @修改内容：
	 * @参数：@param fileInfo FDFS已有文件信息，图片对象
	 * @参数：@param image
	 * @参数：@return
	 * @参数：@throws ImageException
	 * @参数：@throws IOException
	 * @return:boolean
	 * @throws
	 */
	public boolean isImageExist(Image image) throws ImageException, IOException {
		// 加载原图
		File srcFile = ImageFileUtil.loadRemoteFile(image.getDefaultImageSpec()
				.getFileInfo());
		String md5 = Md5FileUtil.getMD5(srcFile);
		// 判断图片文件MD5是否相等
		if (!image.getDefaultImageSpec().getFileMd5().equals(md5))
			return false;
		return true;
	}
}