package plfx.xl.pojo.command.line;

import hg.common.component.BaseCommand;

import java.util.Set;

import plfx.xl.pojo.dto.order.LineOrderTravelerDTO;

/**
 * 
 * @类功能说明：线路修改订单金额通知command
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年6月4日上午9:18:50
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class XLUpdateOrderSalePriceMessageApiCommand extends BaseCommand{
	/**
	 * 线路订单id(这里指经销商订单ID:dealerOrderNo)
	 */
	private String lineOrderID;
	
	/**
	 * 游客信息列表set
	 */
	private Set<LineOrderTravelerDTO> travelers;
	
	/**
	 * 销售总价
	 */
	private Double salePrice;
	
	/**
	 * 发送的消息类型
	 */
	private String type;
	
	/**
	 * 平台订单号
	 */
	private String orderNo;
	

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public String getLineOrderID() {
		return lineOrderID;
	}

	public void setLineOrderID(String lineOrderID) {
		this.lineOrderID = lineOrderID;
	}

	public Set<LineOrderTravelerDTO> getTravelers() {
		return travelers;
	}

	public void setTravelers(Set<LineOrderTravelerDTO> travelers) {
		this.travelers = travelers;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
