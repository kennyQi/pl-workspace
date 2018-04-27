package hgtech.jf.coupon;

import java.util.Map;

/**
 * 优惠券接口
 * 查询、核销
 * @author admin
 *
 */
public interface CouponInterface {
	/**
	 * 查询该优惠码是否可用
	 * @param discountCode
	 * @return
	 */
	public Map<String, String> checkCoupon(String discountCode);
	/**
	 * 使用该优惠码
	 * @param discountCode
	 * @return
	 */
	public Map<String, String> userCoupon(String discountCode);
}
