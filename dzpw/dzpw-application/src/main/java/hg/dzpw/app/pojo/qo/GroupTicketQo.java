package hg.dzpw.app.pojo.qo;

/**
 * @类功能说明：套票（联票）查询对象
 * @类修改者：
 * @修改日期：2014-11-26下午3:25:52
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-26下午3:25:52
 */
public class GroupTicketQo extends TicketQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 套票状态
	 */
	private Integer[] status;
	
	/**
	 * GroupTicket的退款状态
	 */
	private Integer refundCurrent;
	/**
	 * 订单
	 */
	private TicketOrderQo ticketOrdeQo;
	
	/**
	 * 单票
	 */
	private SingleTicketQo singleTicketQo;

	public Integer[] getStatus() {
		return status;
	}
	public void setStatus(Integer... status) {
		this.status = status;
	}
	public TicketOrderQo getTicketOrdeQo() {
		return ticketOrdeQo;
	}
	public void setTicketOrdeQo(TicketOrderQo ticketOrdeQo) {
		this.ticketOrdeQo = ticketOrdeQo;
	}
	public SingleTicketQo getSingleTicketQo() {
		return singleTicketQo;
	}
	public void setSingleTicketQo(SingleTicketQo singleTicketQo) {
		this.singleTicketQo = singleTicketQo;
	}
	public Integer getRefundCurrent() {
		return refundCurrent;
	}
	public void setRefundCurrent(Integer refundCurrent) {
		this.refundCurrent = refundCurrent;
	}
}