package hg.service.image.command.album;

import hg.service.image.base.BaseCommand;

/**
 * @类功能说明：修改相册
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午3:40:45
 */
public class ModifyAlbumCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 相册ID
	 */
	private String albumId;

	/**
	 * 相册标题
	 */
	private String title;

	/**
	 * 相册备注
	 */
	private String remark;

	/**
	 * 相册路径
	 */
	private String path;

	/**
	 * 如果path有变化，是否修改所有的子目录
	 */
	private Boolean modifySubPath;

	public ModifyAlbumCommand() {
	}

	public ModifyAlbumCommand(String projectId, String envName) {
		super(projectId, envName);
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId == null ? null : albumId.trim();
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Boolean getModifySubPath() {
		return modifySubPath;
	}

	public void setModifySubPath(Boolean modifySubPath) {
		this.modifySubPath = modifySubPath;
	}

}