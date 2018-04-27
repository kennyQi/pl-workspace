package hg.system.command.image;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：图片_添加command
 * @类修改者：zzb
 * @修改日期：2014年9月3日上午10:23:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月3日上午10:23:58
 * 
 */
@SuppressWarnings("serial")
public class ImageModifyCommand extends BaseCommand {

	/**
	 * 图片id
	 */
	private String imageId;
	
	/**
	 * 图片标题
	 */
	private String title;

	/**
	 * 图片备注说明
	 */
	private String remark;

	/**
	 * 归属者
	 */
	private String ownerId;

	/**
	 * 相册
	 */
	private String albumId;

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
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

}
