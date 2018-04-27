package hsl.pojo.command.ad;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：修改广告Command
 * @类修改者：
 * @修改日期：2014年12月11日下午4:43:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月11日下午4:43:18
 *
 */
public class HslUpdateAdCommand extends BaseCommand{
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 广告的ID
	 */
	private String id;
	/**
	 * 广告位ID
	 */
	private String positionId;
	/**
	 * 图片信息(json)
	 */
	private String imageInfo;
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
	 * 优先级
	 */
	private Integer priority;
	/**
	 * 按广告优先级和广告位的加载条数，自动维护是否显示状态
	 */
	private Boolean isShow;
	/**
	 * 是否立即展示或者是否是隐藏改显示
	 */
	private Boolean show;
	/**
	 * 点击数
	 */
	private Integer clickNo;
	/**
	 * 来源项目
	 */
	private String fromProjectName;
	/**
	 * 来源运行环境
	 */
	private String envName;
	/**
	 * 图片服务返回的图片id
	 */
	private String imageId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Integer getClickNo() {
		return clickNo;
	}
	public void setClickNo(Integer clickNo) {
		this.clickNo = clickNo;
	}
	public Boolean getIsShow() {
		return isShow;
	}
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
	public Boolean getShow() {
		return show;
	}
	public void setShow(Boolean show) {
		this.show = show;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFromProjectName() {
		return fromProjectName;
	}
	public void setFromProjectName(String fromProjectName) {
		this.fromProjectName = fromProjectName;
	}
	public String getEnvName() {
		return envName;
	}
	public void setEnvName(String envName) {
		this.envName = envName;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	
}
