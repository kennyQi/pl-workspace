package hg.service.image.command.image;

import hg.service.image.base.BaseCommand;

/**
 * @类功能说明：创建图片(同时创建默认 ImageSpec)，根据规格集合剪裁图片。
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30上午11:17:56
 */
public class CreateImageCommand extends BaseCommand {
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
	 * 图片原始名称(加后缀名)
	 */
	private String fileName;

	/**
	 * 图片备注说明
	 */
	private String remark;

	/**
	 * 图片用途
	 */
	private String useTypeId;

	private String albumId;

	/**
	 * 如果要新建相册，起名
	 */
	private String albumTitle;

	public CreateImageCommand() {
	}

	public CreateImageCommand(String projectId, String envName) {
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName == null ? null : fileName.trim();
	}

	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo == null ? null : fileInfo.trim();
	}

	public String getUseTypeId() {
		return useTypeId;
	}

	public void setUseTypeId(String useTypeId) {
		this.useTypeId = useTypeId;
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public String getAlbumTitle() {
		return albumTitle;
	}

	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}

}