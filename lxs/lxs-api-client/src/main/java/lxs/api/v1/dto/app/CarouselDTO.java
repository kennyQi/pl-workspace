package lxs.api.v1.dto.app;

import java.util.Date;

public class CarouselDTO {

	/**
	 * 图片url 完整URL
	 */
	private String imageURL;

	/**
	 * 轮播图类别（用于新增时类别选择） 2：线路 3：门票 4：活动 5：约伴 6：链接
	 */
	private Integer carouselType;

	/**
	 * 轮播图指向地址 如果是 链接 则展现就可以 若果是线路 按照 objc://fromSubject:withLineID:/线路ID
	 */
	private String carouselAction;

	/**
	 * 添加时间
	 */
	private Date createDate;

	/**
	 * 备注
	 */
	private String note;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Integer getCarouselType() {
		return carouselType;
	}

	public void setCarouselType(Integer carouselType) {
		this.carouselType = carouselType;
	}

	public String getCarouselAction() {
		return carouselAction;
	}

	public void setCarouselAction(String carouselAction) {
		this.carouselAction = carouselAction;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
