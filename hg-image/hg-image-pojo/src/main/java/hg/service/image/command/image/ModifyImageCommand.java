package hg.service.image.command.image;

import hg.service.image.base.BaseCommand;

/**
 * @类功能说明：修改图片
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30上午11:17:56
 */
public class ModifyImageCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 图片ID
	 */
	private String imageId;

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

	/**
	 * 删除状态，0未删除 1回收站 2彻底删除待清理
	 */
	private Integer refuse = 0;

	public final static Integer REFUSE_STATUS_NO = 0; // 未删除相册
	public final static Integer REFUSE_STATUS_YES = 1; // 回收站相册
	public final static Integer REFUSE_STATUS_CLEAR = 2; // 彻底删除待清理相册

	/**
	 * 所属相册
	 */
	private String albumId;

	public ModifyImageCommand() {
	}

	public ModifyImageCommand(String projectId, String envName) {
		super(projectId, envName);
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
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

	public Integer getRefuse() {
		return refuse;
	}

	public void setRefuse(Integer refuse) {
		this.refuse = refuse;
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

}