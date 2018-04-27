package hg.service.image.command.image.spec;

import hg.service.image.base.BaseCommand;

/**
 * @类功能说明：修改图片明细 客户端手工裁剪完图片重新上传fdfs以后，调用该接口更新明细信息
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午4:36:33
 */
public class ModifyImageSpecCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 规格图片id
	 */
	private String imageSpecId;

	/**
	 * FdfsFileInfo JSON字符
	 */
	private String fileInfo;

	public ModifyImageSpecCommand() {
	}

	public ModifyImageSpecCommand(String projectId, String envName) {
		super(projectId, envName);
	}

	public String getImageSpecId() {
		return imageSpecId;
	}

	public void setImageSpecId(String imageSpecId) {
		this.imageSpecId = imageSpecId;
	}

	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo == null ? null : fileInfo.trim();
	}

}