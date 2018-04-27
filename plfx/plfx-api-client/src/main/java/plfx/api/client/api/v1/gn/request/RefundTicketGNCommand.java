package plfx.api.client.api.v1.gn.request;

import plfx.api.client.common.BaseClientCommand;
import plfx.api.client.common.api.PlfxApiAction;

/****
 * 
 * @类功能说明：航班申请退废票command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月3日下午3:04:58
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@PlfxApiAction(PlfxApiAction.GN_RefundTicket)
public class RefundTicketGNCommand extends BaseClientCommand  {

	/**
	 * 经销商订单号   
	 */
	private String dealerOrderId;

	/**
	 * 乘客姓名   
	 * 多个乘客之间用 ^ 分隔
	 */
	private String passengerName;

	/**
	 * 机票票号  
	 * 票号之间用 ^分隔，并与姓名相对应
	 */
	private String airId;
	
	/**
	 * 申请种类
	 * 1.当日作废2.自愿退票3.非自愿退票 4.差错退款 5.其他
	 */
	private String refundType;
	
	/***
	 *申请理由
	 */
	private String refundMemo;

	/**
	 * 要退的航段  
	 * 格式：SHA-PEK(只能是第一航段已经使用，才能退第二航段，否则全退)非必填
	 */
	private String refundSegment;

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getAirId() {
		return airId;
	}

	public void setAirId(String airId) {
		this.airId = airId;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getRefundMemo() {
		return refundMemo;
	}

	public void setRefundMemo(String refundMemo) {
		this.refundMemo = refundMemo;
	}

	public String getRefundSegment() {
		return refundSegment;
	}

	public void setRefundSegment(String refundSegment) {
		this.refundSegment = refundSegment;
	}
}
