package slfx.xl.pojo.command.order;

import hg.common.component.BaseCommand;

import java.util.List;

import slfx.xl.pojo.dto.order.LineOrderTravelerDTO;

@SuppressWarnings("serial")
public class ModifyLineOrderTravelerCommand extends BaseCommand {

	/**
	 * 经销商订单编号
	 */
	private String dealerOrderNo;
	
	/**
	 * 游客信息列表lsit
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
