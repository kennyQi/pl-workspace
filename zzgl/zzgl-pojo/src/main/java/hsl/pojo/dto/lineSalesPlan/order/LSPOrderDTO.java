package hsl.pojo.dto.lineSalesPlan.order;
import hsl.pojo.dto.BaseDTO;
import hsl.pojo.dto.line.LineDTO;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanDTO;

import java.util.List;
/**
 * @类功能说明：线路销售方案的订单（包括团购以及秒杀）
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/11/28 13:55
 */
public class LSPOrderDTO extends BaseDTO {
	/**
	 * 订单基本信息
	 */
	private  LSPOrderBaseInfoDTO orderBaseInfo;
	/**
	 * 订单联系人信息
	 */
	private  LSPOrderLinkInfoDTO orderLinkInfo;
	/**
	 * 订单支付信息
	 */
	private  LSPOrderPayInfoDTO orderPayInfo;
	/**
	 * 订单下单用户快照
	 */
	private  LSPOrderUserDTO orderUser;
	/**
	 * 订单下单状态
	 */
	private  LSPOrderStatusDTO orderStatus;
	/**
	 * 游客信息列表list
	 */
	private List<LSPOrderTravelerDTO> travelerList;
	/**
	 * 关联的线路的销售方案
	 */
	private LineSalesPlanDTO lineSalesPlan;
	/**
	 * 关联的线路信息
	 */
	private LineDTO line;

	public LSPOrderBaseInfoDTO getOrderBaseInfo() {
		return orderBaseInfo;
	}

	public void setOrderBaseInfo(LSPOrderBaseInfoDTO orderBaseInfo) {
		this.orderBaseInfo = orderBaseInfo;
	}

	public LSPOrderLinkInfoDTO getOrderLinkInfo() {
		return orderLinkInfo;
	}

	public void setOrderLinkInfo(LSPOrderLinkInfoDTO orderLinkInfo) {
		this.orderLinkInfo = orderLinkInfo;
	}

	public LSPOrderPayInfoDTO getOrderPayInfo() {
		return orderPayInfo;
	}

	public void setOrderPayInfo(LSPOrderPayInfoDTO orderPayInfo) {
		this.orderPayInfo = orderPayInfo;
	}

	public LSPOrderUserDTO getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(LSPOrderUserDTO orderUser) {
		this.orderUser = orderUser;
	}

	public LSPOrderStatusDTO getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(LSPOrderStatusDTO orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<LSPOrderTravelerDTO> getTravelerList() {
		return travelerList;
	}

	public void setTravelerList(List<LSPOrderTravelerDTO> travelerList) {
		this.travelerList = travelerList;
	}

	public LineSalesPlanDTO getLineSalesPlan() {
		return lineSalesPlan;
	}

	public void setLineSalesPlan(LineSalesPlanDTO lineSalesPlan) {
		this.lineSalesPlan = lineSalesPlan;
	}

	public LineDTO getLine() {
		return line;
	}

	public void setLine(LineDTO line) {
		this.line = line;
	}
}
