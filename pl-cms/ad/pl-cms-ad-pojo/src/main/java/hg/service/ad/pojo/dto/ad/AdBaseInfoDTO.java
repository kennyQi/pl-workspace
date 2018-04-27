package hg.service.ad.pojo.dto.ad;

import hg.service.ad.base.BaseDTO;

import java.util.Date;

@SuppressWarnings("serial")
public class AdBaseInfoDTO extends BaseDTO{
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 图片信息(json)
	 */
	private String imageInfo;
	/**
	 * 图片地址
	 */
	private String imagePath;
	/**
	 * 图片名称
	 */
	private String fileName;
	/**
	 * 链接地址
	 */
	private String url;
	/**
	 * 文字备注
	 */
	private String remark;
	/**
	 * 优先级
	 */
	private Integer priority;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 图片服务返回的图片id
	 */
	private String imageId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageInfo() {
		return imageInfo;
	}

	public void setImageInfo(String imageInfo) {
		this.imageInfo = imageInfo;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
