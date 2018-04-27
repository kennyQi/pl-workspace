package hg.dzpw.pojo.command.pay;

import hg.dzpw.pojo.common.BaseCommand;

/**
 * @类功能说明：电子票务向商户（景区）转账
 * @类修改者：
 * @修改日期：2015-5-6下午5:53:52
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-5-6下午5:53:52
 */
@SuppressWarnings("serial")
public class DzpwTransferToMerchantCommand extends BaseCommand {

	/**
	 * 景区ID
	 */
	private String scenicSpotId;

	/**
	 * 转账数额
	 */
	private Double amount;
	
	/**
	 * 单票ID
	 */
	private String singleTicketId;
	
	/**
	 * 订单ID
	 */
	private String ticketOrderId;
	
	/**
	 * 收款方CST_NO
	 */
	private String rcvCstNo;

	public DzpwTransferToMerchantCommand() { }

	public DzpwTransferToMerchantCommand(String scenicSpotId, Double amount) {
		this.scenicSpotId = scenicSpotId;
		this.amount = amount;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getTicketOrderId() {
		return ticketOrderId;
	}

	public void setTicketOrderId(String ticketOrderId) {
		this.ticketOrderId = ticketOrderId;
	}

	public String getRcvCstNo() {
		return rcvCstNo;
	}

	public void setRcvCstNo(String rcvCstNo) {
		this.rcvCstNo = rcvCstNo;
	}

	public String getSingleTicketId() {
		return singleTicketId;
	}

	public void setSingleTicketId(String singleTicketId) {
		this.singleTicketId = singleTicketId;
	}

}
