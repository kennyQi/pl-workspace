package hg.dzpw.pojo.qo;

import hg.dzpw.pojo.common.BasePojoQo;

import java.util.Date;

/**
 * @类功能说明：景区入园统计查询对象
 * @类修改者：
 * @修改日期：2014-11-20下午3:29:07
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-20下午3:29:07
 */
public class ScenicSpotUseStatisticsQo extends BasePojoQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 景区ID（如果不为空则忽略景区名称）
	 */
	private String scenicSpotId;
	
	/**
	 * 景区名称（模糊查询，景区ID必须为空）
	 */
	private String scenicSpotName;

	/**
	 * 销售日期开始
	 */
	private Date orderDateBegin;
	
	/**
	 * 销售日期截止
	 */
	private Date orderDateEnd;
	
	/**
	 * 门票政策类型 单票、联票
	 */
	private Integer ticketPolicyType;
	
	/**
	 * 订单编号
	 */
	private String orderId;
	
	/**
	 * 票务编号
	 */
	private String ticketNo;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public String getScenicSpotName() {
		return scenicSpotName;
	}

	public void setScenicSpotName(String scenicSpotName) {
		this.scenicSpotName = scenicSpotName;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

}
