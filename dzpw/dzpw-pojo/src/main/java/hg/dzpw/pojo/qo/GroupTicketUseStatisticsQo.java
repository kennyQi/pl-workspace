package hg.dzpw.pojo.qo;

import hg.dzpw.pojo.common.BasePojoQo;

import java.util.Date;

/**
 * @类功能说明：套票（联票）入园统计查询对象
 * @类修改者：
 * @修改日期：2014-11-20下午3:29:07
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-20下午3:29:07
 */
public class GroupTicketUseStatisticsQo extends BasePojoQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 门票政策ID（如果不为空则忽略门票政策名称）
	 */
	private String ticketPolicyId;

	/**
	 * 门票政策名称（模糊查询，门票政策ID必须为空）
	 */
	private String ticketPolicyName;

	/**
	 * 销售日期开始
	 */
	private Date orderDateBegin;

	/**
	 * 销售日期截止
	 */
	private Date orderDateEnd;

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public String getTicketPolicyName() {
		return ticketPolicyName;
	}

	public void setTicketPolicyName(String ticketPolicyName) {
		this.ticketPolicyName = ticketPolicyName;
	}

	public Date getOrderDateBegin() {
		return orderDateBegin;
	}

	public void setOrderDateBegin(Date orderDateBegin) {
		this.orderDateBegin = orderDateBegin;
	}

	public Date getOrderDateEnd() {
		return orderDateEnd;
	}

	public void setOrderDateEnd(Date orderDateEnd) {
		this.orderDateEnd = orderDateEnd;
	}

}
