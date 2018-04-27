package hg.common.util.file;

import hg.common.util.Md5FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：FastDFS工具类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：
 * @创建时间：
 */
public class FdfsFileUtil {
	private static String configFilePath;
	private static Logger logger = LoggerFactory.getLogger(FdfsFileUtil.class);

	public static void init() {
		String classPath = "";
		try {
			classPath = new File(FdfsFileUtil.class.getResource("/").getFile())
					.getCanonicalPath();//获取项目跟路径(ClassPath)
		} catch (IOException e) {
			e.printStackTrace();
		}

		configFilePath = classPath + "/fastdfs_client.conf";//FastDFS配置文件路径
		try {
			ClientGlobal.init(configFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			FdfsConfig.getInstance().init();//初始化FastDFS配置对象
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 上传文件
	 * @param fis
	 * @param extName 文件扩展名
	 * @param metaMap
	 *            元数据 例:{"width":"480", "height":"360", "author":"admin"}
	 * @return
	 */
	public static FdfsFileInfo upload(FileInputStream fis, String extName, Map<String, String> metaMap) {
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
			logger.error("获取fastdfs追踪服务器服节点连接失败");
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
				metaList[i] = new NameValuePair(entry.getKey(),
						entry.getValue());
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
				logger.error("要上传的文件不存在");
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
				e.printStackTrace();
			}
			logger.debug("上传文件花费时间: "
					+ (System.currentTimeMillis() - startTime) + " ms");

			if (results == null) {
				logger.error("上传文件失败, 错误码: " + storageClient.getErrorCode());
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
				fdfsFileInfo.setMetaMap(metaMap);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return fdfsFileInfo;
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				logger.error("关闭上传文件输入流失败");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 上传文件
	 * @param file
	 * @param metaMap
	 *            元数据 例:{"width":"480", "height":"360", "author":"admin"}
	 * @return
	 */
	public static FdfsFileInfo upload(File file, Map<String, String> metaMap) {
		if (file == null) {
			return null;
		}

		metaMap.put("md5", Md5FileUtil.getMD5(file));
		
		// 连接追踪服务器用的追踪客户端
		TrackerClient trackerClient = new TrackerClient();

		// 追踪服务器
		TrackerServer trackerServer = null;
		try {
			trackerServer = trackerClient.getConnection();
		} catch (IOException e) {
			logger.error("获取fastdfs追踪服务器服节点连接失败");
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
				metaList[i] = new NameValuePair(entry.getKey(),
						entry.getValue());
			}
		} else {
			metaMap = new HashMap<String, String>();
		}

		// 构建文件输入流
		FileInputStream fis = null;
		try {
			byte[] file_buff = null;

			try {
				fis = new FileInputStream(file);

				if (fis != null) {
					int len = fis.available();
					file_buff = new byte[len];
					fis.read(file_buff);
				}
			} catch (FileNotFoundException e) {
				logger.error("要上传的文件" + file.getName() + "不存在");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 获取可用的存储节点，groupName不指定，使用存储节点中的可用信息
			// String groupName = null;
			// StorageServer[] storageServers = null;
			// try {
			// storageServers = trackerClient.getStoreStorages(trackerServer,
			// groupName);
			// } catch (IOException e) {
			// logger.error("获取存储节点发生异常");
			// e.printStackTrace();
			// }
			// if (storageServers == null) {
			// logger.error("获取存储节点失败, 错误码: " + storageClient.getErrorCode());
			// } else {
			// logger.debug("存储节点数量: " + storageServers.length);
			// for (int k = 0; k < storageServers.length; k++) {
			// logger.debug(k
			// + 1
			// + ". "
			// + storageServers[k].getInetSocketAddress()
			// .getAddress().getHostAddress()
			// + ":"
			// + storageServers[k].getInetSocketAddress()
			// .getPort());
			// }
			// }

			// 解析文件扩展名
			String extensionName = file.getName().substring(
					file.getName().lastIndexOf(".") + 1);
			if (file.getName().lastIndexOf(".") == -1) {
				extensionName = "";
			}
			metaMap.put("extName", extensionName);
			// 上传文件
			long startTime = System.currentTimeMillis();
			String[] results = null;
			try {
				results = storageClient.upload_file(file_buff, extensionName,
						metaList);
				System.out.println(JSON.toJSONString(results));
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.debug("上传文件花费时间: "
					+ (System.currentTimeMillis() - startTime) + " ms");

			if (results == null) {
				logger.error("上传文件失败, 错误码: " + storageClient.getErrorCode());
				return null;
			}

			// 获取分配到的groupName和文件名
			String groupName = results[0];
			String remoteFileName = results[1];
			System.err.println("groupName: " + groupName + ", remoteFileName: "
					+ remoteFileName);

			FdfsFileInfo fdfsFileInfo = new FdfsFileInfo();
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
			} catch (Exception e) {
				e.printStackTrace();
			}

			return fdfsFileInfo;
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				logger.error("关闭上传文件输入流失败");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从FastDFS获取文件
	 * @param groupName
	 * @param remoteFileName
	 * @param domain
	 *            http访问图片的域名或ip，为空时从system.properties中取值
	 * @param getMeta
	 *            是否获取自定义元数据
	 * @return
	 */
	public static File getFile(String groupName, String remoteFileName,
			String domain, Boolean getMeta) {
		if (getMeta == null) {
			getMeta = false;
		}
		FdfsFileInfo fdfsFileInfo = getFileInfo(groupName, remoteFileName,
				getMeta);
		if (StringUtils.isBlank(domain)) {
			domain = FdfsConfig.getInstance().get(
					"fastdfs_tracker_server_address");
		}
		System.out.println("http://" + domain + fdfsFileInfo.getUri());
		return new File("http://" + domain + fdfsFileInfo.getUri());
	}

	/**
	 * 从FastDFS获取文件
	 * @param groupName
	 * @param remoteFileName
	 * @return
	 */
	public static FdfsFileInfo getFileInfo(String groupName,
			String remoteFileName, Boolean getMeta) {
		if (getMeta == null) {
			getMeta = false;
		}
		// 连接追踪服务器用的追踪客户端
		TrackerClient trackerClient = new TrackerClient();

		// 追踪服务器
		TrackerServer trackerServer = null;
		try {
			trackerServer = trackerClient.getConnection();
		} catch (IOException e) {
			logger.error("获取fastdfs追踪服务器服节点连接失败");
			e.printStackTrace();
		}

		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer,
				storageServer);

		FileInfo fileInfo = null;
		NameValuePair[] nameValuePairs = null;
		try {
			fileInfo = storageClient.get_file_info(groupName, remoteFileName);
			if (getMeta) {
				nameValuePairs = storageClient.get_metadata(groupName,
						remoteFileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
		FdfsFileInfo fdfsFileInfo = new FdfsFileInfo();
		fdfsFileInfo.setCrc32(fileInfo.getCrc32());
		fdfsFileInfo.setCreateTimestamp(fileInfo.getCreateTimestamp());
		fdfsFileInfo.setFileSize(fileInfo.getFileSize());
		fdfsFileInfo.setSourceIpAddr(fileInfo.getSourceIpAddr());
		fdfsFileInfo.setUri("/" + groupName + "/" + remoteFileName);
		fdfsFileInfo.setGroupName(groupName);
		fdfsFileInfo.setRemoteFileName(remoteFileName);

		if (getMeta) {
			Map<String, String> metaMap = new HashMap<String, String>();
			for (NameValuePair nameValuePair : nameValuePairs) {
				metaMap.put(nameValuePair.getName(), nameValuePair.getValue());
			}
			fdfsFileInfo.setMetaMap(metaMap);
		}
		return fdfsFileInfo;
	}

	/**
	 * @方法功能说明：从FastDFS删除文件
	 * @param groupName
	 * @param remoteFileName
	 * @return 2，删除错误；-1，DFS抛出异常
	 */
	public static int deleteFile(String groupName, String remoteFileName) {
		// 连接追踪服务器用的追踪客户端
		TrackerClient trackerClient = new TrackerClient();

		// 追踪服务器
		TrackerServer trackerServer = null;
		try {
			trackerServer = trackerClient.getConnection();
		} catch (IOException e) {
			logger.error("获取fastdfs追踪服务器服节点连接失败");
			e.printStackTrace();
		}

		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer,storageServer);
		try {
			return storageClient.delete_file(groupName, remoteFileName);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "b");
		FdfsFileUtil.init();
		File file = new File("D:\\Koala.jpg");
		FdfsFileInfo fdfsFileInfo = upload(file, null);
		System.out.println(JSON.toJSONString(fdfsFileInfo));
	}
}