package lxs.api.v1.dto.app;

import java.util.Date;

public class RecommendDTO {

	/**
	 * 推荐标题
	 */
	private String recommendName;

	/**
	 * 图片url 完整URL
	 */
	private String imageURL;

	/**
	 * 轮播图类别（用于新增时类别选择） 2：线路 3：门票 4：活动 5：约伴 6：链接
	 */
	private Integer recommendType;

	/**
	 * 轮播图指向地址 如果是 链接 则展现就可以 若果是线路 按照 objc://fromSubject:withLineID:/线路ID
	 */
	private String recommendAction;

	/**
	 * 简介说明
	 */
	private String note;

	/**
	 * 添加时间
	 */
	private Date createDate;

	public String getRecommendName() {
		return recommendName;
	}

	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Integer getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(Integer recommendType) {
		this.recommendType = recommendType;
	}

	public String getRecommendAction() {
		return recommendAction;
	}

	public void setRecommendAction(String recommendAction) {
		this.recommendAction = recommendAction;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
