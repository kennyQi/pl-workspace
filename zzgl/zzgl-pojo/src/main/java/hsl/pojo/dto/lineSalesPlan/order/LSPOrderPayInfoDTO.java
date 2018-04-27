package hsl.pojo.dto.lineSalesPlan.order;
/**
* @类功能说明：订单的支付信息
* @类修改者：
* @公司名称： 浙江票量云科技有限公司
* @部门： 技术部
* @作者： chenxy
* @创建时间：  2015-11-28 14:56:10
* @版本： V1.0
*/
public class LSPOrderPayInfoDTO {
	/**
	 * 支付帐号
	 */
	private String buyerEmail;
	/**
	 * 支付交易号
	 */
	private String payTradeNo;
	/**
	 * 真实支付价格
	 */
	private Double payPrice;

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

	public Double getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}
}