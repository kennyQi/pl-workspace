package hg.service.image.command.image;

import hg.service.image.base.BaseCommand;

/**
 * @类功能说明：创建临时图片，适用于上传后未关联到业务实体的图片。
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30上午11:17:56
 */
public class CreateTempImageCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 图片标题
	 */
	private String title;

	/**
	 * 原始图片FdfsFileInfo JSON字符
	 */
	private String fileInfo;

	/**
	 * 图片备注说明
	 */
	private String remark;

	public CreateTempImageCommand() {
	}

	public CreateTempImageCommand(String projectId, String envName) {
		super(projectId, envName);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo == null ? null : fileInfo.trim();
	}

}