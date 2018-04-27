package lxs.pojo.command.app;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class ModifyRecommendCommand extends BaseCommand {
	
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
	private String recommendActionCheck;
	
	/**
	 * 简介说明
	 */
	private String note;
	
	/**
	 * 图片信息(json)
	 */
	private String imageInfo;
	/**
	 * 图片名字
	 */
	private String fileName;

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

	public String getRecommendActionCheck() {
		return recommendActionCheck;
	}

	public void setRecommendActionCheck(String recommendActionCheck) {
		this.recommendActionCheck = recommendActionCheck;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getImageInfo() {
		return imageInfo;
	}

	public void setImageInfo(String imageInfo) {
		this.imageInfo = imageInfo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
