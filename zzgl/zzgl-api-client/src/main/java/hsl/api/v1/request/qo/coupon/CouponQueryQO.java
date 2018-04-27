package hsl.api.v1.request.qo.coupon;

import hsl.api.base.ApiPayload;

public class CouponQueryQO extends ApiPayload{
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	/**
	 * 判断卡券是否可以使用
	 */
	private Boolean isNoCanUsed;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean getIsNoCanUsed() {
		return isNoCanUsed;
	}

	public void setIsNoCanUsed(Boolean isNoCanUsed) {
		this.isNoCanUsed = isNoCanUsed;
	}
	
	
	
}
