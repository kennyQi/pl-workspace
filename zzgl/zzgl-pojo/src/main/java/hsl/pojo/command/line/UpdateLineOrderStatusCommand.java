package hsl.pojo.command.line;

import hg.common.component.BaseCommand;
import hsl.pojo.dto.line.order.LineOrderTravelerDTO;

import java.util.List;

/**
 * @类功能说明：更新线路订单状态
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：renfeng
 * @创建时间：2015年4月3日下午4:19:02
 * @版本：V1.1
 *
 */
@SuppressWarnings("serial")
public class UpdateLineOrderStatusCommand extends BaseCommand {
	
	/**
	 * 订单ID
	 */
	private String orderId;
	/**
	 * 经销商订单号
	 */
	private String dealerOrderCode;
	
	/**
	 * 订单游客列表
	 */
	private List<LineOrderTravelerDTO> travelerList;
	
	/**
	 * 订单状态
	 */
	private Integer orderStatus;	
	
	/** 支付状态 */
	private Integer payStatus;

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<LineOrderTravelerDTO> getTravelerList() {
		return travelerList;
	}

	public void setTravelerList(List<LineOrderTravelerDTO> travelerList) {
		this.travelerList = travelerList;
	}

}
