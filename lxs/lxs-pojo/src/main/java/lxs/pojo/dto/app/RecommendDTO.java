package lxs.pojo.dto.app;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class RecommendDTO extends BaseCommand {
	
	/**
	 * 推荐ID
	 */
	private String recommendID;

	/**
	 * 推荐标题
	 */
	private String recommendName;
	
	/**
	 * 图片url（不包含域名,域名从CC里拉取）
	 */
	private String imageURL;
	
	/**
	 * 轮播图类别（用于新增时类别选择）
	 * 2：线路
	 * 3：门票
	 * 4：活动
	 * 5：约伴
	 * 6：链接
	 */
	private Integer recommendType;
	
	/**
	 * 推荐指向地址
	 */
	private String recommendAction;
	
	/**
	 * 简介说明
	 */
	private String note;
	
	/**
	 * 状态
	 * 1：启用
	 * 2：禁用
	 */
	private Integer status;
	
	public String getRecommendID() {
		return recommendID;
	}

	public void setRecommendID(String recommendID) {
		this.recommendID = recommendID;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
