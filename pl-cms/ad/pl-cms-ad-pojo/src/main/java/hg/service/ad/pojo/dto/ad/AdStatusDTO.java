package hg.service.ad.pojo.dto.ad;

import hg.service.ad.base.BaseDTO;

@SuppressWarnings("serial")
public class AdStatusDTO extends BaseDTO{
	/**
	 * 按广告优先级和广告位的加载条数，自动维护是否显示状态
	 */
	private Boolean isShow;
	/**
	 * 点击数
	 */
	private Integer clickNo;
	
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
}
