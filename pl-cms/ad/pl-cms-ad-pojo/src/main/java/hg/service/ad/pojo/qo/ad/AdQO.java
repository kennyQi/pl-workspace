package hg.service.ad.pojo.qo.ad;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class AdQO extends BaseQo {

	/**
	 * true查询优先级排在上一位的广告，false查询优先级排在下一位的广告
	 */
	private Boolean lastOrNextPriority;

	/**
	 * 用于比较的优先级
	 */
	private Integer targetPriority;

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
	private Boolean show;

	public Boolean getLastOrNextPriority() {
		return lastOrNextPriority;
	}

	public void setLastOrNextPriority(Boolean lastOrNextPriority) {
		this.lastOrNextPriority = lastOrNextPriority;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getTargetPriority() {
		return targetPriority;
	}

	public void setTargetPriority(Integer targetPriority) {
		this.targetPriority = targetPriority;
	}

}