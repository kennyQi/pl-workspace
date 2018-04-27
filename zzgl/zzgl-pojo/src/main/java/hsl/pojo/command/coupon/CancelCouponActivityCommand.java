package hsl.pojo.command.coupon;

/**
 * @类功能说明：取消活动command
 * @类修改者：wuyg
 * @修改日期：2014年10月15日下午2:28:04
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：wuyg
 * @创建时间：2014年10月15日下午2:28:04
 * 
 */
public class CancelCouponActivityCommand {
	/**
	 * 人工备注
	 */
	private String remark;
	/**
	 * 活动标识
	 */
	private String couponActivityId;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCouponActivityId() {
		return couponActivityId;
	}

	public void setCouponActivityId(String couponActivityId) {
		this.couponActivityId = couponActivityId;
	}

}
