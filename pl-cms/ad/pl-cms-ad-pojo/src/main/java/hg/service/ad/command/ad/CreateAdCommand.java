package hg.service.ad.command.ad;

/**
 * 
 * @类功能说明：添加广告图
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2015年3月11日下午3:55:47
 * 
 */
public class CreateAdCommand {

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 图片系统中的图片id
	 */
	private String imageId;

	/**
	 * 图片访问相对路径
	 */
	private String imageUrl;

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
	private Boolean show;

	/**
	 * 广告位
	 */
	private String positionId;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

}
