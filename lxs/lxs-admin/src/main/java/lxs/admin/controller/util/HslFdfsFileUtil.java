package lxs.admin.controller.util;

import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.log.util.HgLogger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import com.alibaba.fastjson.JSON;

public class HslFdfsFileUtil extends FdfsFileUtil{

	/**
	 * 上传文件
	 * @param fis
	 * @param extName 文件扩展名
	 * @param metaMap
	 *            元数据 例:{"width":"480", "height":"360", "author":"admin"}
	 * @return
	 */
	public static FdfsFileInfo upload(InputStream fis, String extName, Map<String, String> metaMap) {
		if (fis == null) {
			return null;
		}

		// 连接追踪服务器用的追踪客户端
		TrackerClient trackerClient = new TrackerClient();

		// 追踪服务器
		TrackerServer trackerServer = null;
		try {
			trackerServer = trackerClient.getConnection();
		} catch (IOException e) {
			HgLogger.getInstance().error("lxs_dev","获取fastdfs追踪服务器服节点连接失败");
			e.printStackTrace();
		}

		// 存储服务器
		StorageServer storageServer = null;
		// 连接存储服务器用的存储客户端
		StorageClient storageClient = new StorageClient(trackerServer,
				storageServer);

		// 组装文件的元数据
		NameValuePair[] metaList = null;
		if (metaMap != null && metaMap.size() > 0) {
			metaList = new NameValuePair[metaMap.size()];
			int i = 0;
			for (Entry<String, String> entry : metaMap.entrySet()) {
				metaList[i] = new NameValuePair(entry.getKey(),entry.getValue());
				i++;
			}
		}

		// 构建文件输入流
		try {
			byte[] file_buff = null;

			try {
				if (fis != null) {
					int len = fis.available();
					file_buff = new byte[len];
					fis.read(file_buff);
				}
			} catch (FileNotFoundException e) {
				HgLogger.getInstance().error("lxs_dev","要上传的文件不存在");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// 上传文件
			long startTime = System.currentTimeMillis();
			String[] results = null;
			try {
				results = storageClient.upload_file(file_buff, extName,
						metaList);
				System.out.println(JSON.toJSONString(results));
			} catch (Exception e) {
				HgLogger.getInstance().info("lxs_dev", "【upload】"+"异常，"+HgLogger.getStackTrace(e));
			}
			HgLogger.getInstance().debug("lxs_dev","上传文件花费时间: "
					+ (System.currentTimeMillis() - startTime) + " ms");

			if (results == null) {
				HgLogger.getInstance().error("lxs_dev","上传文件失败, 错误码: " + storageClient.getErrorCode());
				return null;
			}

			// 获取分配到的groupName和文件名
			String groupName = results[0];
			String remoteFileName = results[1];
			System.err.println("groupName: " + groupName + ", remoteFileName: "
					+ remoteFileName);

			FdfsFileInfo fdfsFileInfo = new FdfsFileInfo();//FastDFS的文件信息
			try {
				FileInfo fileInfo = storageClient.get_file_info(groupName,
						remoteFileName);
				fdfsFileInfo.setCrc32(fileInfo.getCrc32());
				fdfsFileInfo.setCreateTimestamp(fileInfo.getCreateTimestamp());
				fdfsFileInfo.setFileSize(fileInfo.getFileSize());
				fdfsFileInfo.setSourceIpAddr(fileInfo.getSourceIpAddr());
				fdfsFileInfo.setUri("/" + groupName + "/" + remoteFileName);
				fdfsFileInfo.setGroupName(groupName);
				fdfsFileInfo.setRemoteFileName(remoteFileName);
				Map<String,String> metaMap1=new HashMap<String,String>();
				metaMap1.put("width", "200");
				metaMap1.put("height", "100");
				fdfsFileInfo.setMetaMap(metaMap1);
			} catch (Exception e) {
				HgLogger.getInstance().info("lxs_dev", "【upload】"+"异常，"+HgLogger.getStackTrace(e));
			}

			return fdfsFileInfo;
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				HgLogger.getInstance().error("lxs_dev","关闭上传文件输入流失败");
				e.printStackTrace();
			}
		}
	}
}
