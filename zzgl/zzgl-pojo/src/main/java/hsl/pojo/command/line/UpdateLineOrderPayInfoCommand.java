package hsl.pojo.command.line;
import hg.common.component.BaseCommand;
/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/11/18 14:43
 */
public class UpdateLineOrderPayInfoCommand extends BaseCommand{
	/**
	 * 线路订单
	 */
	private  String lineId;
	/**
	 * 支付帐号
	 */
	private  String buyerEmail;
	/**
	 * 支付交易号
	 */
	private  String payTradeNo;

	/**
	 * 现金支付价格(使用支付宝支付的价格)
	 */
	private Double cashPrice;

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

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
