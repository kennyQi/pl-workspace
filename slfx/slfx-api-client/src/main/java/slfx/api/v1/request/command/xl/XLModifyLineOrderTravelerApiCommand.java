package slfx.api.v1.request.command.xl;

import java.util.List;

import slfx.api.base.ApiPayload;
import slfx.xl.pojo.dto.order.LineOrderTravelerDTO;

@SuppressWarnings("serial")
public class XLModifyLineOrderTravelerApiCommand extends ApiPayload{
	
	/**
	 * 经销商订单编号
	 */
	private String dealerOrderNo;
	
	/**
	 * 游客信息列表set
	 * */
	private List<LineOrderTravelerDTO> travelers;

	public String getDealerOrderNo() {
		return dealerOrderNo;
	}

	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
	}

	public List<LineOrderTravelerDTO> getTravelers() {
		return travelers;
	}

	public void setTravelers(List<LineOrderTravelerDTO> travelers) {
		this.travelers = travelers;
	}
}
