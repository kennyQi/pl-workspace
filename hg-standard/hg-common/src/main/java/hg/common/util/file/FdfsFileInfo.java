package hg.common.util.file;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * fastdfs上保存的文件的信息
 * 
 * @author yuxx
 */
public class FdfsFileInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * crc校验码
	 */
	private long crc32;

	/**
	 * 保存时间
	 */
	private Date createTimestamp;

	/**
	 * 文件大小（字节）
	 */
	private long fileSize;

	/**
	 * 存储服务器节点IP
	 */
	private String sourceIpAddr;

	/**
	 * 访问相对地址,
	 * 浏览器中使用http://{sysProperties.fastdfs_tracker_server_address}{uri}访问图片
	 */
	private String uri;

	/**
	 * 存储组名
	 */
	private String groupName;

	/**
	 * 存储文件名
	 */
	private String remoteFileName;

	/**
	 * 自定义元数据集
	 */
	private Map<String, String> metaMap;

	/**
	 * 图片服务系统图片id
	 */
	private String imageId;

	public long getCrc32() {
		return crc32;
	}

	public void setCrc32(long crc32) {
		this.crc32 = crc32;
	}

	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getSourceIpAddr() {
		return sourceIpAddr;
	}

	public void setSourceIpAddr(String sourceIpAddr) {
		this.sourceIpAddr = sourceIpAddr;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getRemoteFileName() {
		return remoteFileName;
	}

	public void setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}

	public Map<String, String> getMetaMap() {
		return metaMap;
	}

	public void setMetaMap(Map<String, String> metaMap) {
		this.metaMap = metaMap;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

}