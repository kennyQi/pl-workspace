package hsl.pojo.qo.coupon;

import hg.common.component.BaseQo;

/**
 * @类功能说明：查询活动取消原因
 * @类修改者：
 * @修改日期：2014年10月15日下午5:56:16
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：wuyg
 * @创建时间：2014年10月15日下午5:56:16
 * 
 */
public class HslCouponActivityEventQO extends BaseQo {
	private static final long serialVersionUID = 1L;
	/**
	 * 事件类型，默认为活动取消(10)
	 */
	private int eventType;
	/**
	 * 活动标识
	 */
	private String couponActivityId;
	public String getCouponActivityId() {
		return couponActivityId;
	}
	public void setCouponActivityId(String couponActivityId) {
		this.couponActivityId = couponActivityId;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
}
