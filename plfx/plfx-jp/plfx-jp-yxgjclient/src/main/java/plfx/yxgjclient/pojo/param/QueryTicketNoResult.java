package plfx.yxgjclient.pojo.param;

import java.util.List;

/**
 * 查询订单票号返回结果参数
 * @author guotx
 * 2015-07-10
 */
public class QueryTicketNoResult {
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 外部订单号
	 */
	private String extOrderId;
	/**
	 * 新PNR
	 */
	private String ordNewPnr;
	/**
	 * 机票信息
	 */
	private List<TicketInfo> ticketInfos;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getExtOrderId() {
		return extOrderId;
	}
	public void setExtOrderId(String extOrderId) {
		this.extOrderId = extOrderId;
	}
	public String getOrdNewPnr() {
		return ordNewPnr;
	}
	public void setOrdNewPnr(String ordNewPnr) {
		this.ordNewPnr = ordNewPnr;
	}
	public List<TicketInfo> getTicketInfos() {
		return ticketInfos;
	}
	public void setTicketInfos(List<TicketInfo> ticketInfos) {
		this.ticketInfos = ticketInfos;
	}
	
}
