package hg.dzpw.pojo.command.pay;

import hg.dzpw.pojo.common.BaseCommand;

/**
 * @类功能说明：电子票务向商户（经销商）转账
 * @类修改者：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2015-7-21下午5:25:16
 */
@SuppressWarnings("serial")
public class DzpwTransferToDealerCommand extends BaseCommand {
	
	/**
	 * 退款订单号
	 */
	private String orderId;
	
	/**
	 * 转账数额
	 */
	private Double amount;
	
	/**
	 * 收款方CST_NO
	 */
	private String rcvCstNo;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getRcvCstNo() {
		return rcvCstNo;
	}

	public void setRcvCstNo(String rcvCstNo) {
		this.rcvCstNo = rcvCstNo;
	}
	
}
