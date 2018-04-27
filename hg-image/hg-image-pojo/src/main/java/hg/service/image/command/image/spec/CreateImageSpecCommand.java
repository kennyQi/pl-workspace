package hg.service.image.command.image.spec;

import hg.service.image.base.BaseCommand;

/**
 * @类功能说明：创建图片明细 要求服务端根据KEY自动剪裁图片
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午4:36:33
 */
public class CreateImageSpecCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属图片
	 */
	private String imageId;

	/**
	 * FdfsFileInfo JSON字符
	 */
	private String fileInfo;

	/**
	 * 该规格图片在同一张图中的规格key
	 */
	private String key;

	public CreateImageSpecCommand() {
	}

	public CreateImageSpecCommand(String projectId, String envName) {
		super(projectId, envName);
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId == null ? null : imageId.trim();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key == null ? null : key.trim();
	}

	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}

}