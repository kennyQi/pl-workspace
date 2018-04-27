package hg.payment.pojo.dto.payorder;

import hg.payment.pojo.dto.BaseDTO;

/**
 * 
 * @类功能说明：查询订单返回DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年9月3日下午2:54:04
 *
 */
@SuppressWarnings("serial")
public class PayOrderDTO extends BaseDTO {
	
	
	/**
	 * 是否支付成功
	 * 1.支付成功  2.支付失败  3.有退款 
	 */
	private Integer payStatus;
	
	/**
	 * 付款方帐号
	 */
	private String buyer_email;
	
	/**
	 * 第三方交易订单号
	 */
	private String trade_no;
	
	
	public String getBuyer_email() {
		return buyer_email;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	

	
	

	
	
	


	
	
	
	
	

}
