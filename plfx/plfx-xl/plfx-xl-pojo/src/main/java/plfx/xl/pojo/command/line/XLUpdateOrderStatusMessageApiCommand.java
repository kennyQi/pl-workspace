package plfx.xl.pojo.command.line;

import hg.common.component.BaseCommand;

import java.util.Set;

import plfx.xl.pojo.dto.order.LineOrderTravelerDTO;

/**
 * 
 * @类功能说明：api接口线路状态消息通知command
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年4月13日上午9:33:06
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class XLUpdateOrderStatusMessageApiCommand extends BaseCommand{
	/**
	 * 线路订单id(这里指经销商订单ID:dealerOrderNo)
	 */
	private String lineOrderID;
	
	/**
	 * 游客信息列表set
	 */
	private Set<LineOrderTravelerDTO> travelers;
	
	/**
	 * 平台订单号
	 */
	private String orderNo;

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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
