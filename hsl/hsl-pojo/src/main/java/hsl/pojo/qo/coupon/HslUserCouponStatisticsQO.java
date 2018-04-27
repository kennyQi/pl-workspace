package hsl.pojo.qo.coupon;
import hg.common.component.BaseQo;
public class HslUserCouponStatisticsQO extends BaseQo {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 卡券活动ID
	 */
	private String couponActivityId;
	/**
	 * 得到卡券时间
	 */
	private String getCouponTime;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCouponActivityId() {
		return couponActivityId;
	}
	public void setCouponActivityId(String couponActivityId) {
		this.couponActivityId = couponActivityId;
	}
	public String getGetCouponTime() {
		return getCouponTime;
	}
	public void setGetCouponTime(String getCouponTime) {
		this.getCouponTime = getCouponTime;
	}
}
