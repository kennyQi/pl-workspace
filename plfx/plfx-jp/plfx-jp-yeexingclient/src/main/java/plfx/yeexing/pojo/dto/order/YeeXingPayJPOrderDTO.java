package plfx.yeexing.pojo.dto.order;

import plfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：国内易行自动扣款返回DTO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月21日下午5:22:19
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YeeXingPayJPOrderDTO extends BaseJpDTO {
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
	
	/**
	 * 经销商id
	 */
	private String dealerOrderCode;
	
	/**
	 * 订单总支付价格
	 */
	private Double totalPrice;
	
	/**
	 * 支付公司流水号
	 */
	private String payid;
	
	/**
	 * 代扣支付状态
	 * 代扣是否成功，T:成功F：失败
	 */
	private String pay_status;

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

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getPayid() {
		return payid;
	}

	public void setPayid(String payid) {
		this.payid = payid;
	}

	public String getPay_status() {
		return pay_status;
	}

	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}
}
