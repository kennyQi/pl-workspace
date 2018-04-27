package plfx.yeexing.pojo.dto.order;

import plfx.jp.pojo.dto.BaseJpDTO;

/*****
 * 
 * @类功能说明：查询退票状态后易行返回的DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年9月16日上午10:46:54
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YeeXingRefundQueryOrderDTO extends BaseJpDTO {
	/**
	 * 是否成功
	 * 表示该次操作是否成功 T:成功F：失败
	 */
	private String is_success;
	
	/**
	 * 错误信息
	 * 格式：错误代码^错误信息
	 */
	private String error;
	
	/**
	 * 订单号
	 * 易行天下订单号（易行天下系统中唯一）
	 */
	private String orderid;
	/***
	 * 退票状态
	 * 1-退票成功；2-退票被拒绝；3-未提交退废票；4-退费票审核中；5-必须退废票
	 */
	private String refundstate;
	
	/**
	 * 乘客姓名
	 */
	private  String passengername;
	
	/**
	 * 机票的价格
	 */
	private Double ticketprice;
	
	/***
	 * 手续费
	 */
	private Double handling;
	/**
	 * 退款时间
	 */
	private String refunddate;
	
	/***
	 * 机票票号
	 */
    private String  airId;
    /***
     * 拒绝退票理由
     */
    private  String refuseMemo;
    
	public String getIs_success() {
		return is_success;
	}

	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getRefundstate() {
		return refundstate;
	}

	public void setRefundstate(String refundstate) {
		this.refundstate = refundstate;
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

	public String getRefunddate() {
		return refunddate;
	}

	public void setRefunddate(String refunddate) {
		this.refunddate = refunddate;
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

    
}
