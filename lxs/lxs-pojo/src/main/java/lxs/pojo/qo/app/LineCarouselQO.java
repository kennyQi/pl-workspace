package lxs.pojo.qo.app;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class LineCarouselQO extends BaseQo {

	private String imageURL;

	private String note;
	/**
	 * 轮播图类别（用于新增时类别选择）
	 * 2：线路
	 * 3：门票
	 * 4：活动
	 * 5：约伴
	 * 6：链接
	 */
	private Integer carouselType;//
	

	private Integer carouselLevel;
	
	


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

	public Integer getCarouselLevel() {
		return carouselLevel;
	}

	public void setCarouselLevel(Integer carouselLevel) {
		this.carouselLevel = carouselLevel;
	}

	
}
