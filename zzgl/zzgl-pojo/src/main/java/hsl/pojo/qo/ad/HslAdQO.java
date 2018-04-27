package hsl.pojo.qo.ad;


import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class HslAdQO extends BaseQo {
	/**
	 * 广告位ID
	 */
	private String positionId;
	/**
	 * 广告标题
	 */
	private String title;
	/**
	 * 按广告优先级和广告位的加载条数，自动维护是否显示状态
	 */
	private Boolean isShow;
	
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getIsShow() {
		return isShow;
	}
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
}