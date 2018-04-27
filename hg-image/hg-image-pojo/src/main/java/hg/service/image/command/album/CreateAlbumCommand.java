package hg.service.image.command.album;

import hg.service.image.base.BaseCommand;

/**
 * @类功能说明：创建相册
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午3:40:45
 */
public class CreateAlbumCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 有则不用uuid
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
	 * 删除状态，0未删除 1回收站 2彻底删除待清理
	 */
	private Integer refuse = 0;

	public final static Integer REFUSE_STATUS_NO = 0; // 未删除相册
	public final static Integer REFUSE_STATUS_YES = 1; // 回收站相册
	public final static Integer REFUSE_STATUS_CLEAR = 2; // 彻底删除待清理相册

	/**
	 * 相册路径，不带根目录
	 */
	private String path;

	public CreateAlbumCommand() {
	}
	
	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}


	public CreateAlbumCommand(String projectId, String envName) {
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

	public Integer getRefuse() {
		return refuse;
	}

	public void setRefuse(Integer refuse) {
		this.refuse = refuse;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}