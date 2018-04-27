package hg.dzpw.dealer.client.dto.ticket;

import hg.dzpw.dealer.client.common.BaseDTO;

import java.util.Date;

/**
 * @类功能说明：门票
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-10下午2:49:16
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class TicketDTO extends BaseDTO {

	
	/**
	 * 有效期开始时间
	 */
	private Date useDateStart;

	/**
	 * 有效期结束时间
	 */
	private Date useDateEnd;

	/**
	 * 门票政策ID
	 */
	private String ticketPolicyId;


	/**
	 * 所属订单ID
	 */
	private String ticketOrderId;

	public Date getUseDateStart() {
		return useDateStart;
	}

	public void setUseDateStart(Date useDateStart) {
		this.useDateStart = useDateStart;
	}

	public Date getUseDateEnd() {
		return useDateEnd;
	}

	public void setUseDateEnd(Date useDateEnd) {
		this.useDateEnd = useDateEnd;
	}

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public String getTicketOrderId() {
		return ticketOrderId;
	}

	public void setTicketOrderId(String ticketOrderId) {
		this.ticketOrderId = ticketOrderId;
	}

}