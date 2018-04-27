package plfx.api.client.api.v1.xl.request.command;

import java.util.List;

import plfx.api.client.base.slfx.ApiPayload;
import plfx.xl.pojo.dto.order.LineOrderTravelerDTO;

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
