package hsl.pojo.command.ad;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class HslCreateAdCommand extends BaseCommand{
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 广告位ID
	 */
	private String positionId;
	/**
	 * 图片信息(json)
	 */
	private String imageInfo;
	/**
	 * 图片名字
	 */
	private String fileName;
	/**
	 * 图片地址
	 */
	private String imagePath;
	/**
	 * 链接地址
	 */
	private String url;
	/**
	 * 文字备注
	 */
	private String remark;
	/**
	 * 点击数
	 */
	private Integer clickNo;
	/**
	 * 来源项目id
	 */
	private String fromProjectId;
	/**
	 * 来源运行环境
	 */
	private String envName;
	/**
	 * 是否显示
	 */
	private Boolean isShow;//修改和新增默认为显示
	/**
	 * 立即展示
	 */
	private Boolean show;
	/**
	 * 优先级
	 */
	private Integer priority;
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
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
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
	public Integer getClickNo() {
		return clickNo;
	}
	public void setClickNo(Integer clickNo) {
		this.clickNo = clickNo;
	}
	public String getFromProjectId() {
		return fromProjectId;
	}
	public void setFromProjectId(String fromProjectId) {
		this.fromProjectId = fromProjectId;
	}
	public String getEnvName() {
		return envName;
	}
	public void setEnvName(String envName) {
		this.envName = envName;
	}
	public Boolean getIsShow() {
		return isShow;
	}
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Boolean getShow() {
		return show;
	}
	public void setShow(Boolean show) {
		this.show = show;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
}
