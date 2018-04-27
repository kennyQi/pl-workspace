package hsl.pojo.dto.line.order;
import javax.persistence.Embeddable;

/**
* @类功能说明：线路订单支付信息
* @类修改者：
* @公司名称： 浙江票量云科技有限公司
* @部门： 技术部
* @作者： chenxy
* @创建时间：  2015-11-18 14-33-06
* @版本： V1.0
*/
@Embeddable
public class LineOrderPayInfoDTO {
	/**
	 * 支付帐号（如果存在支付订金和支付尾款的时候，支付帐号用&分割）
	 */
	private String buyerEmail;
	/**
	 * 支付交易号（如果存在支付订金和支付尾款的时候，支付交易号用&分割）
	 */
	private String payTradeNo;

	/**
	 * 现金支付价格(使用支付宝支付的价格)
	 */
	private Double cashPrice;

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}

	public Double getCashPrice() {
		return cashPrice;
	}

	public void setCashPrice(Double cashPrice) {
		this.cashPrice = cashPrice;
	}
}