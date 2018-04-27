package hg.service.ad.domain.model.image;

import hg.system.model.M;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @类功能说明：图片——一个Image代表一张内容相同的图片，以及它的一组大小不同的裁剪图集合
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月15日下午2:03:26
 */
@Embeddable
public class Image {
	
	/**
	 * 图片系统中的标识
	 */
	@Column(name = "IMG_IMAGE_ID", length = 64)
	private String imageId;
	
	/**
	 * 图片标题
	 */
	@Column(name = "IMG_TITLE", length = 512)
	private String title;

	/**
	 * 图片备注说明
	 */
	@Column(name = "IMG_REMARK", columnDefinition = M.TEXT_COLUM)
	private String remark;

	/**
	 * 图片访问相对地址
	 */
	@Column(name = "IMG_URL")
	private String url;

	
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}