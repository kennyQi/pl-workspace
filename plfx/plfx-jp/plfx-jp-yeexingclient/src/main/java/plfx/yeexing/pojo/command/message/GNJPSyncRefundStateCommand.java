package plfx.yeexing.pojo.command.message;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：同步国内机票退废票状态通知
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年9月22日上午9:47:58
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class GNJPSyncRefundStateCommand extends BaseCommand{

	/**
	 * 经销商订单号
	 */
	private String dealerOrderCode;
	
	/**
	 * 分销订单号
	 */
	private String orderNo;
	
	/**
	 * 退票状态
	 */
	private String refundstate;
	
	/**
	 * 乘客姓名
	 */
	private String passengername;
	
	/**
	 * 单张总价(totalPrice单人价)
	 */
	private Double ticketprice;
	
	/**
	 * 手续费
	 */
	private Double handling;
	
	/**
	 * 机票票号
	 */
	private String airId;
	
	/**
	 * 拒绝退票理由
	 */
	private String refuseMemo;
	
	/**
	 * 错误信息
	 */
	private String error;
	
	/**
	 * 签名
	 */
	private String sign;

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPassengername() {
		return passengername;
	}

	public void setPassengername(String passengername) {
		this.passengername = passengername;
	}

	public Double getTicketprice() {
		return ticketprice;
	}

	public void setTicketprice(Double ticketprice) {
		this.ticketprice = ticketprice;
	}

	public Double getHandling() {
		return handling;
	}

	public void setHandling(Double handling) {
		this.handling = handling;
	}

	public String getAirId() {
		return airId;
	}

	public void setAirId(String airId) {
		this.airId = airId;
	}

	public String getRefuseMemo() {
		return refuseMemo;
	}

	public void setRefuseMemo(String refuseMemo) {
		this.refuseMemo = refuseMemo;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getRefundstate() {
		return refundstate;
	}

	public void setRefundstate(String refundstate) {
		this.refundstate = refundstate;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
