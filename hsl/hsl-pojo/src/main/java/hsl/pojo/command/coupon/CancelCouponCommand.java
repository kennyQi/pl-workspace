package hsl.pojo.command.coupon;

import java.util.List;

public class CancelCouponCommand{
	/**
	 * 卡券id
	 */
	private String couponId;
	
	/**
	 * 卡券id集合
	 */
	private List<String> ids;
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
}
