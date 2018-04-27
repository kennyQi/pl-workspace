package hg.system.dto.album;

import hg.system.dto.EmbeddDTO;

import java.util.Date;

/**
 * 
 * @类功能说明：一个Image代表一张内容相同的图片，以及它的一组大小不同的文件集合
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月15日下午2:03:26
 * 
 */
@SuppressWarnings("serial")
public class ImageDTO extends EmbeddDTO {

	/**
	 * 图片id
	 */
	private String id;
	
	/**
	 * 图片标题
	 */
	private String title;

	/**
	 * 创建时间
	 */
	private Date createDate;

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
	private AlbumDTO album;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public AlbumDTO getAlbum() {
		return album;
	}

	public void setAlbum(AlbumDTO album) {
		this.album = album;
	}

}