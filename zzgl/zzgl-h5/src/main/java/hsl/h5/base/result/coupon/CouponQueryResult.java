package hsl.h5.base.result.coupon;

import java.util.List;
import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.dto.coupon.CouponDTO;

public class CouponQueryResult extends ApiResult{

	private List<CouponDTO> couponDTOList;

	public List<CouponDTO> getCouponDTOList() {
		return couponDTOList;
	}

	public void setCouponDTOList(List<CouponDTO> couponDTOList) {
		this.couponDTOList = couponDTOList;
	}
}
