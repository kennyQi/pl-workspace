package hg.dzpw.pojo.qo;

import java.util.Date;

import hg.dzpw.pojo.common.BasePojoQo;

/**
 * @类功能说明：使用过套票（联票）的用户明细查询对象
 * @类修改者：
 * @修改日期：2014-11-20下午3:49:12
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-20下午3:49:12
 */
public class TicketUsedTouristDetailQo extends BasePojoQo {
	private static final long serialVersionUID = 1L;
	
	// ------------------ 隐含条件 ------------------

	/**
	 * 景区ID（景区入园统计使用）
	 */
	private String scenicSpotId;

	/**
	 * 门票政策ID（联票入园统计使用）
	 */
	private String ticketPolicyId;

	/**
	 * 销售日期开始
	 */
	private Date orderDateBegin;

	/**
	 * 销售日期截止
	 */
	private Date orderDateEnd;
	
	private Integer ticketPolicyType;
	
	// ------------------------------------------

	/**
	 * 用户姓名（模糊查询）
	 */
	private String name;

	/**
	 * 证件号码（模糊查询）
	 */
	private String cerNo;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCerNo() {
		return cerNo;
	}

	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
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

	public Integer getTicketPolicyType() {
		return ticketPolicyType;
	}

	public void setTicketPolicyType(Integer ticketPolicyType) {
		this.ticketPolicyType = ticketPolicyType;
	}

}
