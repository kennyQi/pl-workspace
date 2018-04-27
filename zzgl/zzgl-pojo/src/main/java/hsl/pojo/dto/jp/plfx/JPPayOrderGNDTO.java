package hsl.pojo.dto.jp.plfx;
import hsl.pojo.dto.BaseDTO;
/****
 * @类功能说明：机票下单结果RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月2日下午1:47:26
 * @版本：V1.0
 * 
 */
@SuppressWarnings("serial")
public class JPPayOrderGNDTO extends BaseDTO {
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
	 * 经销商订单号
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

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
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
}
