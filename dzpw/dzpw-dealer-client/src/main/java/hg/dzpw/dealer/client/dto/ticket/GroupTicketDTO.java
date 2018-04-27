package hg.dzpw.dealer.client.dto.ticket;

import java.util.List;

/**
 * @类功能说明：门票
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-10下午3:10:34
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class GroupTicketDTO extends TicketDTO {

	/**
	 * 套票状态
	 */
	private GroupTicketStatusDTO status;

	/**
	 * 套票类型 1 独立单票 2联票
	 */
	private Integer type;

	/**
	 * 单票列表
	 */
	private List<SingleTicketDTO> singleTickets;
	/**
	 * 经销商手续费  元/张
	 */
	private Double dealerSettlementFee = 0d;
	
	/**
	 * 票号
	 */
	private String ticketNo;

	/**
	 * 实际价格
	 */
	private Double price = 0d;
	
	/**
	 * 门票二维码图片地址
	 */
	private String qrCodeUrl;

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public GroupTicketStatusDTO getStatus() {
		return status;
	}

	public void setStatus(GroupTicketStatusDTO status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<SingleTicketDTO> getSingleTickets() {
		return singleTickets;
	}

	public void setSingleTickets(List<SingleTicketDTO> singleTickets) {
		this.singleTickets = singleTickets;
	}

	public Double getDealerSettlementFee() {
		return dealerSettlementFee;
	}

	public void setDealerSettlementFee(Double dealerSettlementFee) {
		this.dealerSettlementFee = dealerSettlementFee;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

}