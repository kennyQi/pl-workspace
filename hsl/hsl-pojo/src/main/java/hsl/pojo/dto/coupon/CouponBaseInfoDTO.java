package hsl.pojo.dto.coupon;

import java.util.Date;

/**
 * @类功能说明：卡券DTO
 * @类修改者：
 * @修改日期：2014年10月15日下午1:51:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日下午1:51:58
 * 
 */
public class CouponBaseInfoDTO {
	/**
	 * 卡券活动标识
	 */
	private CouponActivityDTO couponActivity;
	/**
	 * 卡券创建日期
	 */
	private Date createDate;

	public CouponActivityDTO getCouponActivity() {
		return couponActivity;
	}

	public void setCouponActivity(CouponActivityDTO couponActivity) {
		this.couponActivity = couponActivity;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
