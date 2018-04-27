package plfx.api.client.api.v1.gn.request;

import plfx.api.client.common.BaseClientCommand;
import plfx.api.client.common.api.PlfxApiAction;

/**
 * 
 * @类功能说明：自动扣款command
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月21日下午3:59:04
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@PlfxApiAction(PlfxApiAction.GN_PayJPOrder)
public class JPPayOrderGNCommand extends BaseClientCommand {
	/**
	 * 经销商订单号
	 */
	private String dealerOrderId;
	
	/**
	 * 订单总支付价格
	 */
	private Double totalPrice;
	
	 /**
     * 支持的支付方式     
     * 1-支付宝 2-汇付 6-IPS  7-德付通如都支持，则为1267
     */
    private Integer  payPlatform;
    
	/**
	 * 所有参数经MD5加密算法后得出的结果
	 */
	private String sign;

	
	public Integer getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(Integer payPlatform) {
		this.payPlatform = payPlatform;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
