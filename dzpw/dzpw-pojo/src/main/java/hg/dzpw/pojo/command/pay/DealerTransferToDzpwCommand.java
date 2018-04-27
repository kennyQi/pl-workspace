package hg.dzpw.pojo.command.pay;

import hg.dzpw.pojo.common.BaseCommand;

/**
 * @类功能说明：经销商向电子票务转账
 * @类修改者：
 * @修改日期：2015-5-6下午5:53:52
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-5-6下午5:53:52
 */
@SuppressWarnings("serial")
public class DealerTransferToDzpwCommand extends BaseCommand {
	
	/**
	 * 经销商ID
	 */
	private String dealerId;
	
	/**
	 * 转账数额
	 */
	private Double amount;
	
	/**
	 * 电子票务的门票订单ID
	 */
	private String dzpwOrderId;
	
	/**
	 * 接入商城订单号
	 */
	private String originalOrderNo;
	
	public DealerTransferToDzpwCommand() {}

	public DealerTransferToDzpwCommand(String dealerId, Double amount,
			String dzpwOrderId, String originalOrderNo) {
		this.dealerId = dealerId;
		this.amount = amount;
		this.dzpwOrderId = dzpwOrderId;
		this.originalOrderNo = originalOrderNo;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getOriginalOrderNo() {
		return originalOrderNo;
	}

	public void setOriginalOrderNo(String originalOrderNo) {
		this.originalOrderNo = originalOrderNo;
	}

	public String getDzpwOrderId() {
		return dzpwOrderId;
	}

	public void setDzpwOrderId(String dzpwOrderId) {
		this.dzpwOrderId = dzpwOrderId;
	}
	
}
