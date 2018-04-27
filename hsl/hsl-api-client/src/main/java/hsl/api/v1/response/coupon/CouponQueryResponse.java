package hsl.api.v1.response.coupon;

import java.util.List;

import hsl.api.base.ApiResponse;
import hsl.pojo.dto.coupon.CouponDTO;

public class CouponQueryResponse extends ApiResponse{

	private List<CouponDTO> couponDTOList;

	public List<CouponDTO> getCouponDTOList() {
		return couponDTOList;
	}

	public void setCouponDTOList(List<CouponDTO> couponDTOList) {
		this.couponDTOList = couponDTOList;
	}
}
