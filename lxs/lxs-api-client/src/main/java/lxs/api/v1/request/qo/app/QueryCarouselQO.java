package lxs.api.v1.request.qo.app;

import lxs.api.base.ApiPayload;

/**
 * 
 * @类功能说明：查询轮播图接口请求
 * @类修改者：
 * @修改日期：2015年9月18日上午11:06:40
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月18日上午11:06:40
 */
@SuppressWarnings("serial")
public class QueryCarouselQO extends ApiPayload{
	/**
	 * 轮播图级别（用于菜单展示）
	 * 1：首页
	 * 2：线路
	 * 3：门票
	 */
	
	private Integer carouselLevel;

	public Integer getCarouselLevel() {
		return carouselLevel;
	}

	public void setCarouselLevel(Integer carouselLevel) {
		this.carouselLevel = carouselLevel;
	}
	
	
}
